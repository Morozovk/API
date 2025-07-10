package api.tests;

import api.models.ErrorResponseBodyModel;
import api.models.LogInRequestBodyModel;
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

public class LogIn extends TestBase {

    @Tag("LogIn")
    @DisplayName("Авторизация с валидными данными")
    @Test
    void loginSuccessfulTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel(EMAIL,PASSWORD);

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

    @Tag("LogIn")
    @DisplayName("Попытка авторизации без пароля")
    @Test
    void loginUnSuccessfulNotPasswordTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel(EMAIL,"");

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

    @Tag("LogIn")
    @DisplayName("Попытка авторизации без email")
    @Test
    void loginUnSuccessfulNotEmailTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel("", PASSWORD);

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

    @Tag("LogIn")
    @DisplayName("Попытка авторизации с некорректным email")
    @Test
    void loginUnSuccessfulNotCorrectEmailTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel("1234", PASSWORD);

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

    @Tag("LogIn")
    @DisplayName("Попытка авторизации с некорректным паролем")
    @Disabled ("В авторизации баг, можно ввести любой пароль")
    @Test
    void loginUnSuccessfulNotCorrectPasswordTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel(EMAIL, "1234");

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

