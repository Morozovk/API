package api.tests;

import api.models.ErrorResponseBodyModel;
import api.models.RegisterRequestBodyModel;
import api.models.RegisterResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static api.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Registration")
@DisplayName("Проверка регистрации")
public class Registration extends TestBase {

    @DisplayName("Регистрация пользователя")
    @Test
    void registerSuccessfulTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel(EMAIL, PASSWORD);

        RegisterResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(registerData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(RegisterResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertNotNull(response.getToken());
            assertNotEquals("", response.getToken());
            assertNotNull(response.getId());
            assertNotEquals("", response.getId());
        });
    }

    @DisplayName("Регистрация с некорректным email")
    @Test
    void registerUnSuccessfulNotCorrectEmailTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel("1234", PASSWORD);

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(registerData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Note: Only defined users succeed registration", response.getError());
        });
    }

    @DisplayName("Регистрация без обязательного поля email")
    @Test
    void registerUnSuccessfulNotEmailTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel("", PASSWORD);

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(registerData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @DisplayName("Регистрация без обязательного поля password")
    @Test
    void registerUnSuccessfulNotPasswordTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel(EMAIL, "");

        ErrorResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(registerData)

                        .when()
                        .post("/register")

                        .then()
                        .spec(baseResponseSpec(400))
                        .extract().as(ErrorResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Missing password", response.getError());
        });
    }
}
