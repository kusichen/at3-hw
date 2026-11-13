package at3.hw.selenideSelenium;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import utils.BaseTest;
import utils.ConfigClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class DeleteGoodListClass extends BaseTest {


    /**
     * Получаем все товары, сохраняем в массив чисел
     * и отправляет запросы на удаление товаров.
     */
    public void deleteVisibleProductsViaApi() {
        System.out.println("Сбор товаров с UI для получения ID...");

        Configuration.baseUrl = ConfigClass.get("app.url");
        Selenide.open("/");

        // Локатор для всех карточек товаров на странице магазина
        final ElementsCollection productCards = $$(".product-card");

        // Создаем динамический список для хранения числовых id
        List<Integer> idList = new ArrayList<>();

        //Проходим по каждой карточке в магазине и извлекаем атрибут data-id
        for (SelenideElement card : productCards) {
            String rawId = card.getAttribute("data-id");
            if (rawId != null && !rawId.isEmpty()) {
                idList.add(Integer.parseInt(rawId));
            }
        }

        // Если товаров на странице нет, выходим из метода
        if (idList.isEmpty()) {
            System.out.println("На странице нет товаров для удаления.");
            return;
        }

        //Конвертируем динамический список в массив
        int[] productIds = idList.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("Создан массив товаров на удаления: " + java.util.Arrays.toString(productIds));

        //Последовательно вызываем API для каждого id из массива
        for (int id : productIds) {
            System.out.println("Отправка запроса на удаление товара с id: " + id);

            RestAssured.given()
                    .baseUri(ConfigClass.get("app.url"))
                    .auth()
                    .basic(ConfigClass.get("admin.username"), ConfigClass.get("admin.password"))
                    .when()
                    .delete("/api/products/" + id)
                    .then()
                    .statusCode(200);
        }
        System.out.println("Все полученные товары в списке успешно удалены");
    }
}
