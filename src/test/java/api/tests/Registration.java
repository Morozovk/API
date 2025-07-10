package api.tests;

import api.models.ErrorResponseBodyModel;
import api.models.RegisterRequestBodyModel;
import api.models.RegisterResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Registration extends TestBase {

    @Tag("Registration")
    @DisplayName("Регистрация пользователя")
    @Test
    void registerSuccessfulTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel(email, password);

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

    @Tag("Registration")
    @DisplayName("Регистрация с некорректным email")
    @Test
    void registerUnSuccessfulNotCorrectEmailTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel("1234", password);

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

    @Tag("Registration")
    @DisplayName("Регистрация без обязательного поля email")
    @Test
    void registerUnSuccessfulNotEmailTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel("", password);

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

    @Tag("Registration")
    @DisplayName("Регистрация без обязательного поля password")
    @Test
    void registerUnSuccessfulNotPasswordTest() {
        RegisterRequestBodyModel registerData = new RegisterRequestBodyModel(email, "");

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
