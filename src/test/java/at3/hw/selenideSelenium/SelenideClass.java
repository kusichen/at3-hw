package at3.hw.selenideSelenium;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelenideClass {

    @BeforeAll
    static void setUp() {
        // Базовый URL
        Configuration.baseUrl = "http://localhost:8080";
        // Страница авторизации
        open("/login");
    }

    @Test
    @Order(1)
    @DisplayName("Логин и пароль пользователя вводятся неверно")
    void checkAuthWithInvalidData() {
        $(By.id("username")).setValue("user-a");
        $(By.id("password")).setValue("qwerty123");
        $("button[type='submit']").click();

        // проверка отображения элемента на странице
        $x("//*[contains(text(), 'Неверные учетные данные')]").shouldBe(visible);
    }

    @Test
    @Order(2)
    void addGoodAndCheck() {
        // Вводим корректные данные (Selenide автоматически очистит поле перед setValue)
        $(By.id("username")).setValue("admin");
        $(By.id("password")).setValue("secret123");
        $("button[type='submit']").click();

        // Заполнение формы нового товара
        $("input[placeholder='Название']").setValue("Картон");
        $("input[placeholder='Цена']").setValue("1987");
        $(By.id("add-btn")).click();

        // Переход в магазин
        $("a[href='/']").click();

        // Проверяем, что созданный товар появился и виден (sleep больше не нужен)
        $x("//*[text()='Картон']").shouldBe(visible);
    }

    @Test
    @Order(3)
    void addToOnlineBasket() {
        // Добавление в корзину и открытие корзины
        $x("//*[h4[text()='Картон']]//button[contains(text(), 'В корзину')]").click();
        $(By.id("open-cart-btn")).click();

        // Проверяем отображение товара в корзине
        $x("//h2[contains(text(), 'Корзина')]/following-sibling::*[contains(., 'Картон')]").shouldBe(visible);
    }

    @Test
    @Order(4)
    void checkGoodsAfterRefresh() {
        // Обновление страницы средствами Selenide
        refresh();

        // Открываем корзину повторно
        $(By.id("open-cart-btn")).click();

        // Проверяем, что цена в корзине НЕ равна "0" (исправлена логика проверки AssertJ)
        $x("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]")
                .shouldNotHave(Condition.text("0"));

    }

    @Test
    @Order(5)
    void addGoodsToBasketMoreThan300RubAndMakeOrder() {
        //Закрываем модальное окно корзины
        $(By.id("close-modal")).click();

        ElementsCollection addToCartButton = $$x("//button[contains(text(), 'В корзину')]");

        // Добавляем товары в корзину по очереди
        for (SelenideElement button : addToCartButton) {
            button.click();
        }

        // Открываем корзину
        $(By.id("open-cart-btn")).click();

        // Находим элемент с итоговой ценой и дожидаемся его видимости
        SelenideElement priceElement = $x("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]");
        priceElement.shouldBe(Condition.visible);

        // Преобразуем валюту в число
        String cleanPriceText = priceElement.text().replaceAll("[^0-9]", "");
        int totalAmount = cleanPriceText.isEmpty() ? 0 : Integer.parseInt(cleanPriceText);

        // Проверяем условие, что сумма больше 300 рублей
        if (totalAmount > 300) {
            // Находим и кликаем на кнопку "Оформить заказ"
            $x("//button[contains(text(), 'Оформить заказ')]").click();

            Alert alert = Selenide.switchTo().alert();

            String alertText = alert.getText();
            System.out.println("Алерт: " + alertText);

        } else {
            System.out.println("Товары добавлены. Сумма товаров " + totalAmount + " ₽. Кнопка 'Оформить заказ' не нажималась.");
        }
    }
}
