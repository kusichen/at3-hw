package at3.hw.selenideSelenium;

import com.codeborne.selenide.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import utils.BaseTest;
import utils.ConfigClass;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Task3ClassTest extends BaseTest {

    public static String goodIdOne;
    public static String goodIdTwo;

    //Прежде всего проверяем есть ли товары в магазине
    @BeforeAll
    static void checkExistingGoods() {
        GoodsInShopCheckClass goodsInShopCheckClass = new GoodsInShopCheckClass();

        goodsInShopCheckClass.checkProductsExisting();

    }

    @BeforeEach
    void setUp() {
        // Базовый URL
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = ConfigClass.get("app.url");
        // Страница авторизации
        open("/");

    }

    @AfterEach
    void tearDown(){
        Selenide.closeWebDriver();
    }

    /**Пока данный метод задизаблен из-за дефекта. Невозможно удалить некоторые товары.
    Вместо этого будет удаление некоторых товаров, которые создали через админку или API
    @AfterEach
    void deleteCurrentGoodList(){

        DeleteGoodListClass deleteGoodListClass = new DeleteGoodListClass();
        deleteGoodListClass.deleteVisibleProductsViaApi();
    }**/

    //Доп.метод 1. Подготовка тестовых данных для подсчета суммы товаров в корзине
    public void createOneCard() {
        Response response = given()
                .baseUri(ConfigClass.get("app.url"))
                .auth().preemptive()
                .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
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

        this.goodIdOne = response.jsonPath().getString("data.id");
    }

    //Доп.метод 2. Подготовка тестовых данных для подсчета суммы товаров в корзине
    public void createOtherCard() {
        Response response = given()
                .baseUri(ConfigClass.get("app.url"))
                .auth()
                .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "Галстук",
                        "price":57.77
                        }
                        """)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        this.goodIdTwo = response.jsonPath().getString("data.id");
    }

    @Test
    @Order(1)
    @DisplayName("Добавление трех единиц товара и проверка, что общая сумма менее 300 рублей")
    void checkAlertMore300() {
        Random random = new Random();

        //Добавляем три товара в корзины
        for (int i = 0; i < 3; i++) {
            ElementsCollection availableButtons = $$x("//button[text()='В корзину']");
            int totalAvailable = availableButtons.size();

            if (totalAvailable == 0) {
                throw new IllegalStateException("При итерации поиска товара " + (i + 1) + " доступных товаров больше нет");
            }

            int randomIndex = random.nextInt(totalAvailable);
            availableButtons.get(randomIndex).click();
            sleep(1000);
        }

        //Открываем корзину
        $(By.id("open-cart-btn")).click();

        SelenideElement priceElement = $x("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]");
        priceElement.shouldBe(visible);

        String cleanPriceText = priceElement.text().replaceAll("[^0-9.,]", "").replace(",", ".");
        double totalAmount = cleanPriceText.isEmpty() ? 0.0 : Double.parseDouble(cleanPriceText);

        //Сравниваем сумму товаров больше или меньше 300 р.
        if (totalAmount <= 300.0) {
            $x("//button[contains(text(), 'Оформить заказ')]").click();

            String roundTotalAmount = String.format("%.2f", totalAmount);
            System.out.println("Добавлено 3 уникальных товара. Сумма (" + roundTotalAmount + " ₽) <= 300 ₽. Заказ оформлен!");


            SelenideElement container = $("#toast-container");
            container.shouldBe(visible);
            sleep(ConfigClass.getInt("element.timeout"));
            String containerText = container.getText();
            org.assertj.core.api.Assertions.assertThat(containerText)
                    .as("Должен отображаться текст 'Заказ принят в обработку!' ")
                    .contains("Заказ принят в обработку!");
        } else {
            $x("//button[contains(text(), 'Оформить заказ')]").click();

            var alert = switchTo().alert(Duration.ofSeconds(10));

            org.assertj.core.api.Assertions.assertThat(alert.getText())
                    .as("Текст алерта должен содержать информацию о превышении лимита")
                    .contains("превышает лимит 300 ₽");

            alert.accept();

            System.out.println("Добавлено 3 товара. Сумма (" + totalAmount + " ₽) > 300 ₽. Появился алерт блокировки");
        }
    }

    @Test
    @Order(2)
    @DisplayName("Проверка корректного подсчета общей суммы в корзине")
    void checkCorrectSum() {
        //Запуск метода создания товара "Свитер-XXL"
        createOneCard();

        // Запуск метода создания товара "Галстук"
        createOtherCard();

        sleep(ConfigClass.getInt("element.timeout"));

        //Обновить страницу в браузере
        refresh();
        // Ищем элемент Свитер после добавления
        $("[data-id='" + goodIdOne + "'] h4").shouldBe(visible, Duration.ofSeconds(10));

        // Ищем элемент Галстук после добавления
        $("[data-id='" + goodIdTwo + "'] h4").shouldBe(visible, Duration.ofSeconds(10));

        //Поиск цены товара Свитер
        double priceCard1 = Double.parseDouble(
                $("#card-" + goodIdOne + "> div:nth-child(2)").getText().replaceAll("[^0-9.]", "")
        );
        $("button[data-action='add-to-cart'][data-name='Свитер-XXL']").click();

        //Поиск цены товара Галстук
        double priceCard2 = Double.parseDouble(
                $("#card-" + goodIdTwo + "> div:nth-child(2)").getText().replaceAll("[^0-9.]", "")
        );
        $("button[data-action='add-to-cart'][data-name='Галстук']").click();

        double expectedTotal = priceCard1 + priceCard2;

        $("#open-cart-btn").click();
        sleep(ConfigClass.getInt("element.timeout"));

        String totalSumText = $x("//*[@id=\"total-price\"]").shouldBe(Condition.visible).getText();

        //Поиск полученной суммы в корзине
        double actualTotal = Double.parseDouble(totalSumText.replaceAll("[^0-9.]", ""));

        //Сравнение суммы товаров из корзины с ожидаемым результатом
        org.assertj.core.api.Assertions.assertThat(actualTotal)
                .as("Сумма товаров в корзине должна ровняться сумме цен на товаровы")
                .isEqualTo(expectedTotal);

    }

    @Test
    @Order(3)
    @DisplayName("Добавление товара через админку")
    void addGoodByAdmin() {

        $(byText("Администрирование")).click();

        //Авторизация
        $(By.id("username")).setValue("admin");
        $(By.id("password")).setValue("secret123");
        $("button[type='submit']").click();

        //Заполнение карточки товара в Админке
        $("input[placeholder='Название']").setValue("Калькулятор-NEW-MODEL");
        $("input[placeholder='Цена']").setValue("22.00");
        $(By.id("add-btn")).click();

        sleep(ConfigClass.getInt("element.timeout"));
        SelenideElement container = $("#toast-container");
        container.shouldBe(visible);
        sleep(ConfigClass.getInt("element.timeout"));
        String containerText = container.getText();
        org.assertj.core.api.Assertions.assertThat(containerText)
                .as("Должен отображаться текст 'Товар успешно добавлен!' ")
                .containsAnyOf("Товар успешно добавлен!");
    }

    @Test
    @DisplayName("Редактирование товара через админку")
    @Order(4)
    void editGoodByAdmin() {
        open("/admin");

        //Авторизация
        $(By.id("username")).setValue("admin");
        $(By.id("password")).setValue("secret123");
        $("button[type='submit']").click();

        $x("//*[@value=\"Свитер-XXL\"]").clear();
        $x("//*[@value=\"Свитер-XXL\"]").setValue("Пулловер-XXL");

        $("button.btn-upd[data-id='" + goodIdOne + "']").click();

        SelenideElement container = $("#toast-container");
        container.shouldBe(visible);
        sleep(ConfigClass.getInt("element.timeout"));
        String containerText = container.getText();
        org.assertj.core.api.Assertions.assertThat(containerText)
                .as("Должен отображаться текст,то товар обновлен ")
                .containsAnyOf("обновлен");

        refresh();
        sleep(ConfigClass.getInt("element.timeout"));

        // Переход в магазин
        $("a[href='/']").click();

        $x("//*[text()='Пулловер-XXL']").shouldBe(visible);
    }

    @AfterAll
    public static void deleteGoodOne() {
        Response response = given()
                .baseUri(ConfigClass.get("api.url"))
                .auth()
                .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
                .pathParam("id", goodIdOne)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
    }

    @AfterAll
    public static void deleteGoodTwo() {
        Response response = given()
                .baseUri(ConfigClass.get("api.url"))
                .auth()
                .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
                .pathParam("id", goodIdTwo)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all()
                .extract().response();

        org.assertj.core.api.Assertions.assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
    }

    @AfterAll
    public static void deleteGoodThree() {

        open(ConfigClass.get("app.url"));
        //Ищем товар в магазине
        SelenideElement productCard = $x("//*[h4[text()='Калькулятор-NEW-MODEL']]").shouldHave(text("Калькулятор-NEW-MODEL"));

        //Берем его значение  "data-id"
        String goodIdThree = productCard.getAttribute("data-id");

        System.out.println("ID найденного товара: " + goodIdThree);

        Response response = given()
                .baseUri(ConfigClass.get("api.url"))
                .auth()
                .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
                .pathParam("id", goodIdThree)
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
