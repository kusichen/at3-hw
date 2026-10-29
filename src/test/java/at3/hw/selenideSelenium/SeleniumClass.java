package at3.hw.selenideSelenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumClass {

    static WebDriver driver = new ChromeDriver();

    @BeforeAll
    static void setUp(){


        driver.get("http://localhost:8080/login");
    }

    @Test
    @Order(1)
    @DisplayName("Логин и пароль пользователя вводятся неверно")
    void checkAuthWithInvalidData(){
        driver.findElement(By.id("username")).sendKeys("user-a");
        driver.findElement(By.id("password")).sendKeys("qwerty123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement alertMessage = driver.findElement(By.xpath("//*[contains(text(), 'Неверные учетные данные')]"));
        alertMessage.isDisplayed();
    }

    @Test
    @Order(2)
    void addGoodAndCheck() throws InterruptedException {
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement goodName = driver.findElement(By.cssSelector("input[placeholder='Название']"));
        WebElement goodPrice = driver.findElement(By.cssSelector("input[placeholder='Цена']"));
        WebElement clickToAdd = driver.findElement(By.id("add-btn"));
        WebElement goToShop = driver.findElement(By.cssSelector("a[href='/']"));

        goodName.clear();
        goodName.sendKeys("Картон");
        goodPrice.clear();
        goodPrice.sendKeys("1987");
        clickToAdd.click();
        goToShop.click();

        sleep(1000);

        WebElement myAddedGood = driver.findElement(By.xpath("//*[text()='Картон']"));
        assert myAddedGood.isDisplayed();

    }

    @Test
    @Order(3)
    void addToOnlineBasket(){

        WebElement addGoodToOnlineBasket = driver.findElement(By.xpath("//*[h4[text()='Картон']]//button[contains(text(), 'В корзину')]"));
        WebElement basketOnline = driver.findElement(By.xpath("//*[@id=\"open-cart-btn\"]"));

        addGoodToOnlineBasket.click();
        basketOnline.click();

        WebElement addedGoodInBasket = driver.findElement(By.xpath("//h2[contains(text(), 'Корзина')]/following-sibling::*[contains(., 'Картон')]"));

        assert addedGoodInBasket.isDisplayed();

    }

    @Test
    @Order(4)
    void checkGoodsAdterRefresh(){

        driver.navigate().refresh();

        WebElement basketOnline = driver.findElement(By.xpath("//*[@id=\"open-cart-btn\"]"));
        basketOnline.click();


        org.assertj.core.api.Assertions.assertThat(driver.findElement(By.xpath("//h2[contains(text(), 'Корзина')]/following-sibling::*//span[contains(@id, 'price')]")).getText())
              .as("Сумма товаров в корзине не должна быть равна нулю")
              .isNotEqualTo("0");

    }


    @AfterAll
   static void tearDown(){
        driver.quit();
    }

}
