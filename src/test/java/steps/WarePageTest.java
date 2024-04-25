package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WarePageTest {

    private static WebDriver webDriver;
    private WebDriverWait wait;

    @Before
    public void before() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
    }

    @Допустим("тестовый стенд запущен и открыта страница {string}")
    public void тестовый_стенд_запущен_и_открыта_страница(String string) {
        webDriver.get(string);
        webDriver.manage().window().maximize();
    }

    @Допустим("открыта страница с товарами")
    public void открыта_страница_с_товарами() {
        WebElement dropDownBar = webDriver.findElement(By.id("navbarDropdown"));
        dropDownBar.click();
        WebElement waresButton = webDriver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/div/a[1]"));
        waresButton.click();
    }

    @Допустим("таблица товары содержит {int} наименования")
    public void таблица_товары_содержит_наименования(Integer int1) {
        webDriver.navigate().refresh(); //иначе staleElementException при повторном вызове метода
        WebElement wareTable = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/table/tbody"));
        List<WebElement> wares = wareTable.findElements(By.tagName("tr"));
        int lastWare = wares.size();
        Assertions.assertEquals(int1, lastWare);
    }

    @Допустим("нажимаем на кнопку добавить")
    public void нажимаем_на_кнопку_добавить() {
        WebElement addBtn = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/button"));
        addBtn.click();
    }

    @Допустим("окно Добавление товара видимо")
    public void окно_добавление_товара_видимо() {
        waitUntilElementToBeVisible(By.xpath("//*[@id=\"editModalLabel\"]"));
    }

    @Допустим("поле Наименование заполняется значением {string}")
    public void поле_наименование_заполняется_значением(String string) {
        WebElement nameField = webDriver.findElement(By.id("name"));
        nameField.click();
        nameField.sendKeys(string);
    }

    @Допустим("в поле Тип выбран тип {string}")
    public void в_поле_тип_выбран_тип(String string) {
        WebElement type = webDriver.findElement(By.id("type"));
        Select typeSelect = new Select(type);
        List<WebElement> typeOptions = typeSelect.getOptions();
        if(string.contains("Овощ")){
            typeSelect.selectByValue("VEGETABLE");
        }
        if(string.contains("Фрукт")){
            typeSelect.selectByValue("FRUIT");
        }
    }

    @Допустим("чекбокс Экзотический (заполнен|не заполнен)$")
    public void чекбокс_экзотический(String string) {
        WebElement chechBox = webDriver.findElement(By.id("exotic"));
        if(string.startsWith("не")){
            Assertions.assertTrue(!chechBox.isSelected());
        }
        else { chechBox.click();
                Assertions.assertTrue(chechBox.isSelected());}
    }

    @Допустим("нажимаем на кнопку сохранить")
    public void нажимаем_на_кнопку_сохранить() {
        WebElement saveBtn = webDriver.findElement(By.id("save"));
        saveBtn.click();
    }

    @Допустим("таблица товары содержит наименование {string}")
    public void таблица_товары_содержит_наименование(String string) {
        WebElement lastElement = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/table/tbody/tr[5]/td[1]"));
        Assertions.assertTrue(lastElement.getText().contains(string));
    }

    private void waitUntilElementToBeVisible(By locator){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    @After
    public void after(){
        webDriver.close();
    }
}
