package at3.hw.hw5XpathCss;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckXpathCssClass {

    @BeforeAll
    public static void openSite() {

        Configuration.baseUrl = "http://localhost:8080";
        Configuration.browser = "chrome";
        Configuration.browserSize = "2540x1080";
        Configuration.timeout = 1000;
        open("http://localhost:8080");
    }

    @Test
    @Order(1)
    @DisplayName("Список названий товаров через Xpath")
    public void checkXpathGoodNamesList() {
        String goodNameListXpath = ("//div[@class='product-card']/h4");
        $x(goodNameListXpath).shouldBe(visible);
        SelenideElement productCardsList = $x(goodNameListXpath);
        productCardsList.shouldBe(visible);
    }

    @Test
    @Order(2)
    @DisplayName("Список названий товаров через Css")
    public void checkСssGoodNamesList() {
        String goodNameListCss = ".product-card h4";
        $(goodNameListCss).shouldBe(visible);
        SelenideElement productCardsList = $(goodNameListCss);
        productCardsList.shouldBe(visible);
    }

    @Test
    @Order(3)
    @DisplayName("Список цен товаров через Xpath")
    public void checkXpathPriceList() {
        String goodPriceListXpath = ("//*[contains(text(), '₽')]");
        $x(goodPriceListXpath).shouldBe(visible);
        SelenideElement priceCardsList = $x(goodPriceListXpath);
        priceCardsList.shouldBe(visible);
    }

    @Test
    @Order(4)
    @DisplayName("Название товаров с ценой 25 Css")
    public void checkCssPrice25() {
        String goodPrice25Css = ".product-card[data-price='25'] h4";
        $(goodPrice25Css).shouldBe(visible);
        SelenideElement priceCard25Css = $(goodPrice25Css);
        priceCard25Css.shouldBe(visible);
    }

    @Test
    @Order(5)
    @DisplayName("Название товаров с ценой 25 xPath")
    public void checkXpathPrice25() {
        String goodPrice25Xpath = ("//div[@class = 'product-card' and @data-price='25']/h4");
        $x(goodPrice25Xpath).shouldBe(visible);
        SelenideElement priceCard25Xpath = $x(goodPrice25Xpath);
        priceCard25Xpath.shouldBe(visible);
    }

    @Test
    @Order(6)
    @DisplayName("Цена товара с названием Стакан через XPath")
    public void checkXpathSpecialCardName() {
        String goodSpecialNameXpath = ("//div[@class = 'product-card' and @data-name='Стакан']");
        $x(goodSpecialNameXpath).shouldBe(visible);
        SelenideElement goodSpecialName = $x(goodSpecialNameXpath);
        goodSpecialName.shouldBe(visible);
    }

    @Test
    @Order(7)
    @DisplayName("Цена товара с названием Стакан через Css")
    public void checkCssSpecialCardName() {
        String goodSpecialCardNameCss = ".product-card[data-name='Стакан']";
        $(goodSpecialCardNameCss).shouldBe(visible);
        SelenideElement goodSpecialCardName = $(goodSpecialCardNameCss);
        goodSpecialCardName.shouldBe(visible);
    }

    //Добавить товар в корзину
    @Test
    @Order(8)
    public void addProductToCart() {
        SelenideElement addToCartButton = $("button[data-action='add-to-cart'][data-name='apple-watch']");
        addToCartButton.shouldBe(visible).shouldBe(enabled);

        addToCartButton.click();
    }

    @Test
    @Order(9)
    @DisplayName("Кнопка корзины через Css")
    public void onlineBasketButton() {
        SelenideElement basketButtonCss = $("#open-cart-btn");

        $(basketButtonCss).shouldBe(visible);
    }

    @Test
    @DisplayName("Список товаров в корзине через Xpath")
    public void checkCardList() {
        sleep(1000);
        SelenideElement basketButtonCss = $("#open-cart-btn");
        basketButtonCss.click();
        String listGoodsCardBasketXpath = ("//*[@id=\"cart-items\"]");
        $x(listGoodsCardBasketXpath).shouldBe(visible);
        SelenideElement listGoodsCardBasket = $x(listGoodsCardBasketXpath);
        listGoodsCardBasket.shouldBe(visible);
    }
}
