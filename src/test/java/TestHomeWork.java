import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestHomeWork {

    private WebDriver driver;

    private static final String URL = "https://otus.ru";
    private static final String FIRSTNAME = "Alexander";
    private static final String LASTNAME = "Yakushev";
    private static final String DATEBIRTH = "20.05.1990";

    @BeforeAll
    public static void init() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void HomeWorkTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By signInButton = By.xpath("//button[@class = 'header3__button-sign-in']");
        By user = By.xpath("//*[@data-name='user-info']");
        By profile = By.cssSelector("[href*='/lk/biography/personal']");
        By firstName = By.xpath("//input[@name='fname']");
        By lastName = By.xpath("//input[@name='lname']");
        By dateOfBirth = By.xpath("//input[@name='date_of_birth']");
        By countryInput = By.cssSelector("input[name='country']+div");
        By country = By.cssSelector("button[title=Россия]");
        By cityInput = By.cssSelector("input[name='city']+div");
        By city = By.cssSelector("button[title=Москва]");
        By englishLevelInput = By.cssSelector("input[name='english_level']+div");
        By englishLevel = By.cssSelector("button[title='Средний (Intermediate)']");
        By relocate = By.xpath("//text()[contains(.,'Да')]/ancestor::label[contains(@class, 'radio')]");
        By idEmail = By.cssSelector("input[id='id_email']");
        By idPhone = By.cssSelector("input[id='id_phone']");
        By submit = By.xpath("//button[@title='Сохранить и продолжить']");

        driver.get(URL);
        driver.findElement(signInButton).click();
        inputAutorizationData("ceyogo9446@bitvoo.com", "Ceyogo9446!");
        driver.findElement(user).click();
        driver.findElement(profile).click();
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(FIRSTNAME);
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(LASTNAME);
        driver.findElement(dateOfBirth).clear();
        driver.findElement(dateOfBirth).sendKeys(DATEBIRTH + Keys.ENTER);
        dropdownMenuClick(countryInput, country);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(dateOfBirth));
        dropdownMenuClick(englishLevelInput,englishLevel);
        driver.findElement(relocate).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(cityInput)));
        dropdownMenuClick(cityInput, city);
        driver.findElement(submit).click();
        driver.manage().deleteAllCookies();
        driver.get(URL);
        driver.findElement(signInButton).click();
        inputAutorizationData("ceyogo9446@bitvoo.com", "Ceyogo9446!");
        driver.findElement(user).click();
        driver.findElement(profile).click();

        Assertions.assertEquals("Alexander", driver.findElement(firstName).getAttribute("value"));
        Assertions.assertEquals("Yakushev", driver.findElement(lastName).getAttribute("value"));
        Assertions.assertEquals("20.05.1990", driver.findElement(dateOfBirth).getAttribute("value"));
        Assertions.assertEquals("Россия", driver.findElement(countryInput).getText());
        Assertions.assertEquals("Москва", driver.findElement(cityInput).getText());
        Assertions.assertTrue(driver.findElement(By.id("id_ready_to_relocate_1")).isEnabled());
        Assertions.assertEquals("ceyogo9446@bitvoo.com", driver.findElement(idEmail).getAttribute("value"));
        Assertions.assertEquals("+7 901 222-33-45", driver.findElement(idPhone).getAttribute("value"));
    }

    public void inputAutorizationData(String email, String password) {
        By inputEmail = By.xpath("//input[@type='text' and contains(@placeholder, 'Электронная почта')]");
        By inputPass = By.xpath("//input[@type='password' and contains(@placeholder, 'Введите пароль')]");
        By enterButton = By.xpath("//button[contains(text(), 'Войти') and (@type ='submit')]");
        driver.findElement(inputEmail).sendKeys(email);
        driver.findElement(inputPass).sendKeys(password);
        driver.findElement(enterButton).click();
    }

    public void dropdownMenuClick(By menuLocator, By buttonLocator) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(menuLocator)).click().perform();
        driver.findElement(buttonLocator).click();
    }

    @AfterEach
    public void close() {
        if (driver != null)
            driver.quit();
    }
}
