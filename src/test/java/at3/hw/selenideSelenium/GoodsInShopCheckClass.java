package at3.hw.selenideSelenium;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class GoodsInShopCheckClass {

        /**
         * Проверяем наличие товаров в магазине.
         * Если товаров нет, создаются несколько тестовых товаров
         */
        public void checkProductsExisting() {
            System.out.println("Проверка наличие товаров в магазине");

            Configuration.baseUrl = "http://localhost:8080";
            open("/");

            // Локатор карточек товаров в магазине
            final ElementsCollection productCards = $$(".product-card");

            final Random random = new Random();

            if (productCards.isEmpty()) {
                System.out.println("Товары не найдены! Нужно создать карточки товаров");

                //Создаем 5 товаров
                for (int i = 1; i <= 5; i++) {

                    double goodPrice = BigDecimal.valueOf(random.nextDouble() * 100.0)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    createNewProduct("Товар №" + i, goodPrice);
                }

                //Обновляем страницу
                refresh();
                // Убеждаемся, что товары теперь действительно отображаются
                productCards.first().shouldBe(visible);
                System.out.println("Товары успешно созданы и отображаются на странице.");
            } else {
                System.out.println("В магазине уже есть товары (" + productCards.size() + " шт.)");
            }
        }


        // Вспомогательный метод для создания одного товара через UI
        private void createNewProduct(String name, double price) {

            // Заполнение формы создания
            $("input[placeholder='Название']").setValue(name);
            $("input[placeholder='Цена']").setValue(String.valueOf(price));
            $(By.id("add-btn")).click();

            // Ждем, пока форма закроется или появится уведомление об успехе
            SelenideElement container = $("#toast-container");
            container.shouldBe(visible);
            sleep(2000);
            String containerText = container.getText();
            org.assertj.core.api.Assertions.assertThat(containerText)
                    .as("Должен отображаться текст 'Товар успешно добавлен!' ")
                    .containsAnyOf("Товар успешно добавлен!");
        }
}
