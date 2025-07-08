package api.tests;

import api.models.LogInRequestBodyModel;
import api.models.LogInResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogIn extends TestBase {

    @Tag("User List")
    @DisplayName("")
    @Test
    void loginSuccessfulTest() {
        LogInRequestBodyModel authData = new LogInRequestBodyModel(email,password);

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
}

