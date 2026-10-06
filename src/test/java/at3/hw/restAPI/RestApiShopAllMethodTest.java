package at3.hw.restAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestApiShopAllMethodTest {

    private RequestSpecification requestSpecificationApi = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .log(LogDetail.ALL)
            .build();

    //Пререквест создание карточки товара
    @Test
    @Order(1)
    @DisplayName("Создать карточку товара")
    @Tag("api-test")
    void createGoodCard() {
        Response response = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "lap-top",
                        "price":122555.00
                        }
                        """)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус кода должен быть 200")
                .isEqualTo(200);
    }

    @Test
    @Order(2)
    @DisplayName("Негативный сценарий.Создание карточки товара с уже существующим наименованием")
    @Tag("api-test")
    void createGoodCardWithExistingName() {
        Response response = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "lap-top",
                        "price":198555.00
                        }
                        """)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус кода должен быть 400")
                .isEqualTo(400);
    }

    //Инициализация переменных для карточки товара
    static Integer goodsId;
    static String goodName;
    static Float goodPrice;

    //Пререквест запрос на получение Id товара для дальнейших проверок
    @Test
    @Order(3)
    @DisplayName("Получение списка товара")
    @Tag("api-test")
    void getGoodsListToFetchId() {
        Response response = given()
                .spec(requestSpecificationApi)
                .when()
                .get("/goods/list");

        int respSize = response.jsonPath().getList("goods").size();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус кода должен быть 200")
                .isEqualTo(200);

        Assertions.assertTrue(respSize > 0);

        if (respSize > 0) {
            goodsId = response.body().path("goods[0].id");
            goodName = response.body().path("goods[0].name");
            goodPrice = response.body().path("goods[0].price");
        } else {
            System.out.println("Массив товаров пустой! Создайте карточку товара");
        }

    }

    @Test
    @Order(4)
    @DisplayName("Получение товара по ID")
    @Tag("api-test")
    void getGoodById() {
        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId)
                .when()
                .get("/goods/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        Assertions.assertTrue(response.body().path("id").equals(goodsId));

        System.out.println(goodsId);
    }

    @Test
    @Order(5)
    @DisplayName("Получение карточки по несуществующему ID")
    @Tag("api-test")
    void getNotExistingGoodById() {

        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", -1)
                .when()
                .get("/goods/{id}")
                .then()
                .log().all()
                .statusCode(404)
                .extract().response();

        String responseValue = response.path("message");
        Assertions.assertTrue(responseValue.contains("not found"));
    }

    @Test
    @Order(6)
    @DisplayName("Частичное обновление товара")
    @Tag("api-test")
    void updateGoodCardInShop() {
        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "lap-top",
                        "price":200000.00
                        }
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .log().ifValidationFails()
                .extract().response();

        Assertions.assertTrue(response.path("price").equals(200000.00f));

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);


    }

    @Test
    @Order(7)
    @DisplayName("Частичное обновление товара c несуществующим ID")
    @Tag("api-test")
    void patchNotExistingGoodCardInShop() {
        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", 0)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "lap-top-new-model",
                        "price":70000.00
                        }
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .log().ifValidationFails()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 404")
                .isEqualTo(404);
    }

    @Test
    @Order(8)
    @DisplayName("Негативный! Частичное обновление товара/Ожидается BadRequest")
    @Tag("api-test")
    void patchGoodNotCorrectly() {
        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", 0)
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "lap-top-new-model",
                                             
                        }
                        """)
                .when()
                .patch("/goods/{id}")
                .then()
                .log().ifValidationFails()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 400")
                .isEqualTo(400);
    }


    @Test
    @Order(9)
    @DisplayName("Негативный! Удаление несуществующего товара")
    @Tag("api-test")
    void deleteNoneExistingGood() {
        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", -1)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 404")
                .isEqualTo(404);

    }

    @Test
    @Order(10)
    @DisplayName("Удаление существующего товара")
    @Tag("api-test")
    void deleteExistingGood() {

        Response response = given()
                .spec(requestSpecificationApi)
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);

    }

}
