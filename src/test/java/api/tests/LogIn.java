package api.tests;

import api.models.ErrorResponseBodyModel;
import api.models.LogInAndRegistrationRequestBodyModel;
import api.models.LogInResponseBodyModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static api.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("LogIn")
@DisplayName("Проверки авторизации")
public class LogIn extends TestBase {

    @DisplayName("Авторизация с валидными данными")
    @Test
    void loginSuccessfulTest() {
        LogInAndRegistrationRequestBodyModel authData = new LogInAndRegistrationRequestBodyModel(EMAIL,PASSWORD);

        LogInResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(LogInResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertNotNull(response.getToken());
            assertNotEquals("", response.getToken());
        });
    }

    @DisplayName("Попытка авторизации без пароля")
    @Test
    void loginUnSuccessfulNotPasswordTest() {
        LogInAndRegistrationRequestBodyModel authData = new LogInAndRegistrationRequestBodyModel(EMAIL,"");

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @DisplayName("Попытка авторизации без email")
    @Test
    void loginUnSuccessfulNotEmailTest() {
        LogInAndRegistrationRequestBodyModel authData = new LogInAndRegistrationRequestBodyModel("", PASSWORD);

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @DisplayName("Попытка авторизации с некорректным email")
    @Test
    void loginUnSuccessfulNotCorrectEmailTest() {
        LogInAndRegistrationRequestBodyModel authData =
                new LogInAndRegistrationRequestBodyModel("1234", PASSWORD);

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("user not found", response.getError());
        });
    }

    @DisplayName("Попытка авторизации с некорректным паролем")
    @Disabled ("В авторизации баг, можно ввести любой пароль")
    @Test
    void loginUnSuccessfulNotCorrectPasswordTest() {
        LogInAndRegistrationRequestBodyModel authData =
                new LogInAndRegistrationRequestBodyModel(EMAIL, "1234");

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(authData)

                        .when()
                        .post("/login")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("user not found", response.getError());
        });
    }
}

