package at3.hw.hw5XpathCss;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import java.time.Duration;

public class CheckXpathCssClass {

    public static void main(String[] args) {

        // 1. Инициализируем драйвер для браузера Chrome
        WebDriver driver = new ChromeDriver();

        try {
            // Настраиваем неявное ожидание элементов (до 10 секунд)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Разворачиваем окно браузера на весь экран
            driver.manage().window().maximize();

            // 2. Открываем сайт
            driver.get("http://localhost:8080/");

            List<WebElement> productElements = driver.findElements(By.xpath("//h4"));

            List<WebElement> productElementsGoods = driver.findElements(By.xpath("//*[contains(text(), '₽')]"));

// 2. Извлекаем текст из каждого элемента в список строк (Java 8+)
            List<String> productNames = productElements.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());


            //Извлекаем цену товара
            List<String> productPrice = productElementsGoods.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            //Извлекаем наименование товара с ценой 25 р
            WebElement productNameByXPath = driver.findElement(By.xpath("//h4[following-sibling::*[contains(text(), '25')]]"));

            WebElement priceElement = driver.findElement(By.xpath("//h4[text()='Стакан']/following-sibling::*[contains(text(), '₽')]"));

            List<WebElement> cartItems = driver.findElements(By.xpath("//*[@id=\"cart-items\"]"));
            List<String> listOfGoods = cartItems.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"open-cart-btn\"]")));
            // 3. Click the button
            button.click();


/*
// Выведет: [apple-watch, TV-box, Стакан, USB-hub]
            System.out.println(productNames);

// Выведет: [74444,9800,150,25]
            System.out.println(productPrice);

// Выведет Usb-hub));
            System.out.println("Товар найденный через XPath: " + productNameByXPath.getText());

// Выведет: 150 ₽
            System.out.println("Цена товара Стакан: " + priceElement.getText());

// Список товараов в корзине
            System.out.println("Список товаров в корзине:" + listOfGoods);
*/




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Обязательно закрываем браузер и завершаем сессию драйвера
            driver.quit();
        }
    }
}
