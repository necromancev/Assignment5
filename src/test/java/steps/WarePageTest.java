package steps;

import io.cucumber.java.Before;
import io.cucumber.java.ru.И;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WarePageTest {

    private static WebDriver webDriver;

    @Before
    public void before() {
        webDriver = new ChromeDriver();
    }

    @И("тестовый стенд запущен и открыта страница {string}")
    public void тестовый_стенд_запущен_и_открыта_страница(String string) {
        webDriver.get(string);
        webDriver.manage().window().maximize();
    }

    @И("открыта страница с товарами")
    public void открыта_страница_с_товарами() {
        WebElement dropDownBar = webDriver.findElement(By.id("navbarDropdown"));
        dropDownBar.click();
        WebElement waresButton = webDriver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/div/a[1]"));
        waresButton.click();
    }

    @И("нажимаем на кнопку добавить")
    public void нажимаем_на_кнопку_добавить() {
        WebElement addBtn = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/button"));
        addBtn.click();
    }

    @И("поле Наименование заполняется значением {string}")
    public void поле_наименование_заполняется_значением(String string) {
        WebElement nameField = webDriver.findElement(By.id("name"));
        nameField.click();
        nameField.sendKeys(string);
    }
}
