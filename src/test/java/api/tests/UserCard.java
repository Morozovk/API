package api.tests;

import api.models.UserCardRequestBodyModel;
import api.models.UserCardResponseBodyModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static api.spec.BaseSpec.baseResponseSpec;
import static api.spec.BaseSpec.requestBaseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UserCard extends TestBase {

    @Tag("UserCard")
    @DisplayName("Добавление рабочего места юзера в карточку пользователя")
    @Test
    void newEntryUserCardTest() {
        UserCardRequestBodyModel infoData = new UserCardRequestBodyModel(name, job);

        UserCardResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(infoData)

                        .when()
                        .post("/users")

                        .then()
                        .spec(baseResponseSpec(201))
                        .extract().as(UserCardResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals(name, response.getName());
            assertEquals(job, response.getJob());
            assertNotNull(response.getId());
            assertNotEquals("", response.getId());
            assertNotNull(response.getCreatedAt());
            assertNotEquals("", response.getCreatedAt());
        });
    }

    @Tag("UserCard")
    @DisplayName("Изменение с помощью Put рабочего места юзера в карточку пользователя")
    @Test
    void updateJobInUserCardPutTest() {
        UserCardRequestBodyModel infoData = new UserCardRequestBodyModel(name, "zion resident");

        UserCardResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(infoData)

                        .when()
                        .put("/users/2")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(UserCardResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals(name, response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
            assertNotEquals("", response.getUpdatedAt());
        });
    }

    @Tag("UserCard")
    @DisplayName("Изменение с помощью Patch рабочего места юзера в карточку пользователя")
    @Test
    void updateJobInUserCardPatchTest() {
        UserCardRequestBodyModel infoData = new UserCardRequestBodyModel(name, "zion resident");

        UserCardResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(infoData)

                        .when()
                        .patch("/users/2")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(UserCardResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals(name, response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
            assertNotEquals("", response.getUpdatedAt());
        });
    }

    @Tag("UserCard")
    @DisplayName("Изменение с помощью Put рабочего места юзера в карточку пользователя")
    @Test
    void updateNameInUserCardPutTest() {
        UserCardRequestBodyModel infoData = new UserCardRequestBodyModel("Neo", job);

        UserCardResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(infoData)

                        .when()
                        .put("/users/2")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(UserCardResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Neo", response.getName());
            assertEquals(job, response.getJob());
            assertNotNull(response.getUpdatedAt());
            assertNotEquals("", response.getUpdatedAt());
        });
    }

    @Tag("UserCard")
    @DisplayName("Изменение с помощью Patch рабочего места юзера в карточку пользователя")
    @Test
    void updateNameInUserCardPatchTest() {
        UserCardRequestBodyModel infoData = new UserCardRequestBodyModel("Neo", job);

        UserCardResponseBodyModel response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)
                        .body(infoData)

                        .when()
                        .patch("/users/2")

                        .then()
                        .spec(baseResponseSpec(200))
                        .extract().as(UserCardResponseBodyModel.class));

        step("Проверяем ответ", () -> {
            assertEquals("Neo", response.getName());
            assertEquals(job, response.getJob());
            assertNotNull(response.getUpdatedAt());
            assertNotEquals("", response.getUpdatedAt());
        });
    }

    @Tag("UserCard")
    @DisplayName("Удаление карточки юзера пользователя")
    @Test
    void deleteUserCardPatchTest() {

        Response response = step("Отправляем запрос", () ->
                given(requestBaseSpec)
                        .header("x-api-key", apiKey)

                        .when()
                        .delete("/users/2")

                        .then()
                        .spec(baseResponseSpec(204)))
                        .extract()
                        .response();

        step("Проверяем ответ", () -> {
            assertTrue(response.body().asString().isEmpty());
        });
    }
}
