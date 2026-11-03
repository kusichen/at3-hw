package at3.hw.selenideSelenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Task3ClassTest {

    public static String goodId;

    @BeforeAll
    static void setUp() {
        // Базовый URL
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://localhost:8080";
        // Страница авторизации
        open("/");
    }

    public void createCard() {
        Response response = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "Свитер-XXL",
                        "price":47.55
                        }
                        """)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

       this.goodId = response.jsonPath().getString("data.id");
    }

    @Test
    @Order(1)
    @DisplayName("Добавление трех единиц товара и проверка, что общая сумма менее 300 рублей")
     void checkAlertMore300() {

        ElementsCollection addToCartButtons = $$x("//button[contains(text(), 'В корзину')]");

        if (addToCartButtons.size() < 3) {
            throw new IllegalStateException("На странице доступно меньше 3 товаров для добавления в корзину!");
        }

        for (int i = 0; i < 3; i++) {
            addToCartButtons.get(i).click();
        }

        $(By.id("open-cart-btn")).click();

        SelenideElement priceElement = $x("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]");
        priceElement.shouldBe(visible);

        String cleanPriceText = priceElement.text().replaceAll("[^0-9]", "");
        int totalAmount = cleanPriceText.isEmpty() ? 0 : Integer.parseInt(cleanPriceText);

        if (totalAmount <= 300) {
            $x("//button[contains(text(), 'Оформить заказ')]").click();
            System.out.println("Добавлено 3 товара. Сумма (" + totalAmount + " ₽) <= 300 ₽. Заказ оформлен!");
        } else {
            System.out.println("Добавлено 3 товара. Пропуск: Сумма (" + totalAmount + " ₽) превышает 300 ₽.");
        }

        SelenideElement container = $("#toast-container");
        container.shouldBe(visible);
        sleep(2000);
       String containerText = container.getText();
        org.assertj.core.api.Assertions.assertThat(containerText)
                .as("Должен отображаться текст 'Заказ принят в обработку!' ")
                .containsAnyOf("Заказ принят в обработку!");
    }

    @Test
    @Order(2)
    @DisplayName("Проверка корректного подсчета общей суммы в корзине")
    void checkCorrectSum(){
        //Запуск метода создания товара "Свитер-XXL"
        createCard();

        sleep(3000);

        //Обновить страницу в браузере
        refresh();
        // Ищем элемент после добавления
        $("[data-id='" + goodId + "'] h4").shouldBe(visible, Duration.ofSeconds(10));

            double priceCard1 = Double.parseDouble(
                    $("#card-"+goodId + "> div:nth-child(2)").getText().replaceAll("[^0-9.]", "")
            );
            $("button[data-action='add-to-cart'][data-name='Свитер-XXL']").click();

            double priceCard2 = Double.parseDouble(
                    $("#card-9 > div:nth-child(2)").getText().replaceAll("[^0-9.]", "")
            );
            $x("//*[@id=\"card-9\"]/button").click();

            double expectedTotal = priceCard1 + priceCard2;

            $("#open-cart-btn").click();
            sleep(2000);

            String totalSumText = $x("//*[@id=\"total-price\"]").shouldBe(Condition.visible).getText();
            double actualTotal = Double.parseDouble(totalSumText.replaceAll("[^0-9.]", ""));
    }

    @Test
    @Order(3)
    @DisplayName("Добавление товара через админку")
    void addGoodByAdmin(){

        // 1. Закрываем корзину (найдите крестик по его тексту или классу)
        $x("//*[@id=\"close-modal\"]").click(); // или $(byText("×")).click();

// 2. Ждем, пока модалка полностью скроется из DOM или станет невидимой
        $("#cartModal").shouldBe(Condition.hidden);

// 3. Теперь клик по админке пройдет успешно
        $(byText("Администрирование")).click();

        //Авторизация
        $(By.id("username")).setValue("admin");
        $(By.id("password")).setValue("secret123");
        $("button[type='submit']").click();

        //Заполнение карточки товара в Админке
        $("input[placeholder='Название']").setValue("Калькулятор-NEW-MODEL");
        $("input[placeholder='Цена']").setValue("22.00");
        $(By.id("add-btn")).click();

        SelenideElement container = $("#toast-container");
        container.shouldBe(visible);
        sleep(2000);
        String containerText = container.getText();
        org.assertj.core.api.Assertions.assertThat(containerText)
                .as("Должен отображаться текст 'Товар успешно добавлен!' ")
                .containsAnyOf("Товар успешно добавлен!");
    }

    @Test
    @DisplayName("Редактирование товара через админку")
    @Order(4)
    void editGoodByAdmin(){
        open("/admin");

        $x("//*[@value=\"Свитер-XXL\"]").clear();
        $x("//*[@value=\"Свитер-XXL\"]").setValue("Пулловер-XXL");

        $("button.btn-upd[data-id='" + goodId + "']").click();

        SelenideElement container = $("#toast-container");
        container.shouldBe(visible);
        sleep(2000);
        String containerText = container.getText();
        org.assertj.core.api.Assertions.assertThat(containerText)
                .as("Должен отображаться текст,то товар обновлен ")
                .containsAnyOf("обновлен");

        refresh();
        sleep(3000);

        // Переход в магазин
        $("a[href='/']").click();

        $x("//*[text()='Пулловер-XXL']").shouldBe(visible);
    }

   @AfterAll
   public static void deleteTestData(){
        Response response = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodId)
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
