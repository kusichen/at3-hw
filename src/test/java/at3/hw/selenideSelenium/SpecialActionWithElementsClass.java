package at3.hw.selenideSelenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SpecialActionWithElementsClass {

    @Test
    @DisplayName("Перетащить элемент в корзину и удалить")
    void dragAndDropGoodAndRemove() {
        Configuration.baseUrl = "http://localhost:8080";
        open("/");

        //Находим товар для перетаскивания
        SelenideElement tvBoxCard = $(withText("TV-box"));

        //Находим корзину, куда перетаскиваем
        SelenideElement goodsBasket = $(withText("Корзина"));

        //Выполняем Drag-and-Drop
        com.codeborne.selenide.Selenide.actions().dragAndDrop(tvBoxCard, goodsBasket).perform();

        //Проверяем счетчик
        goodsBasket.shouldHave(Condition.text("(1)"));
    }

    @Test
    @DisplayName("Удалить товар из корзины")
    void removeGood() {

        SelenideElement goodsBasket = $(withText("Корзина"));
        goodsBasket.shouldBe(visible);

        goodsBasket.click();

        //Удалить товар
        $("button[data-action='remove'][data-index='0']").click();

        //Проверить, что товар удален
        $("#empty-cart").shouldHave(Condition.text("Пусто"));
    }
}
