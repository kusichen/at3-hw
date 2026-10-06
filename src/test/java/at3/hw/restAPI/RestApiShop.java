package at3.hw.restAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestApiShop {

    private RequestSpecification requestRQ = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080/")
            .log(LogDetail.ALL)
            .build();

    @Test
    @Order(1)
    @DisplayName("Проверка, что товаров в магазине нет")
    void checkEmptyGoodsShop() {

        given()
                .baseUri("http://localhost:8080/")
                .log()
                .all()
                .queryParam("page", 0)
                .queryParam("size", 100)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    @Order(2)
    @DisplayName("Проверка, что товары все еще отсутствуют в магазине")
    void checkEmptyGoodsShopAgain() {
        given()
                .spec(requestRQ)
                .queryParam("page", 0)
                .queryParam("size", 1)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    @Order(3)
    @DisplayName("Создание карточки товара")
    void createGoodCardInShop() {
        given()
                .spec(requestRQ)
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "smart-clock",
                        "price":555.55
                        }
                        """)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @DisplayName("Получить список товаров")
    void getGoodsInShop() {
        given()
                .spec(requestRQ)
                .queryParam("size", 1)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods[0].name", equalTo("smart-clock"));
    }

    //Переменная для сохранения ID товара, который потом удалим
    static int goodIdToDelete;

    @Test
    @Order(5)
    @DisplayName("Вернуть карточку товара и сравнить тело ответа через библиотеку AssertJ")
    void getGoodCardAndAssert() {

        Response response = given()
                .spec(requestRQ)
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .extract().response();

        Assertions.assertTrue(response.path("goods[0].name").equals("smart-clock"));

        goodIdToDelete = response.path("goods[0].id");
    }

    @AfterAll
    static void deleteGoodCardInShop() {

        given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .pathParams("id", goodIdToDelete)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

}

