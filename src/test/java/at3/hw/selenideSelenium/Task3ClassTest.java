package at3.hw.selenideSelenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class Task3ClassTest {

    @Test
     void setUp() {
        // Базовый URL
        Configuration.baseUrl = "http://localhost:8080";
        // Страница авторизации
       open("/");

      //  $(By.id("username")).setValue("admin");
      //  $(By.id("password")).setValue("secret123");
       // $("button[type='submit']").click();*/

        ElementsCollection addToCartButtons = $$x("//button[contains(text(), 'В корзину')]");

        // Проверяем, что на странице есть хотя бы 3 товара для добавления
        if (addToCartButtons.size() < 3) {
            throw new IllegalStateException("На странице доступно меньше 3 товаров для добавления в корзину!");
        }

        // 2. Кликаем ровно по первым трем кнопкам из коллекции
        for (int i = 0; i < 3; i++) {
            addToCartButtons.get(i).click();
        }

        // 3. Переходим в корзину
        $(By.id("open-cart-btn")).click();

        // 4. Находим и считываем итоговую стоимость
        SelenideElement priceElement = $x("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]");
        priceElement.shouldBe(visible);

        String cleanPriceText = priceElement.text().replaceAll("[^0-9]", "");
        int totalAmount = cleanPriceText.isEmpty() ? 0 : Integer.parseInt(cleanPriceText);

        // 5. Проверяем условие по сумме (менее или равно 300 рублей)
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
}
