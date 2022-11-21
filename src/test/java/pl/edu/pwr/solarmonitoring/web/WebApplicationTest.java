package pl.edu.pwr.solarmonitoring.web;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebApplicationTest {

    private static final String LOGIN = "jan";
    private static final String PASSWORD = "password";

    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeEach
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Chrome\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.get("https://34.116.171.10:3000/");
    }

    @Test
    @Order(1)
    public void successRegistrationTest() {
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[1]/span")).click();
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[2]/input")).sendKeys("Jan");
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[3]/input")).sendKeys("Kowalski");
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[4]/input")).sendKeys("jan.kowalski@gmail.com");
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[5]/input")).sendKeys(LOGIN);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[6]/div/input")).sendKeys(PASSWORD);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[7]/div/input")).sendKeys(PASSWORD);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[8]/button")).click();

        String response = getAlertText();
        Assertions.assertEquals("Rejestracja przebiegła pomyślnie", response, "Lack of registration confirmation");
    }

    @Test
    @Order(2)
    public void changeUserDataTest() {
        login(PASSWORD);

        String newFirstName = "Jan1";
        String newSecondName = "Kowalski1";
        String newEmail = "new@email.com";
        String newPassword = "password1";
        clickElementByText("Użytkownik ");
        webDriver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys(newFirstName);
        webDriver.findElement(By.xpath("//*[@id=\"secondName\"]")).sendKeys(newSecondName);
        webDriver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(newEmail);
        webDriver.findElement(By.xpath("//*[@id=\"oldPassword\"]")).sendKeys(PASSWORD);
        webDriver.findElement(By.xpath("//*[@id=\"newPassword\"]")).sendKeys(newPassword);
        webDriver.findElement(By.xpath("//*[@id=\"confirmPassword\"]")).sendKeys(newPassword);
        clickElementByText("Zapisz ");

        String response = getAlertText();
        Assertions.assertEquals("Zmiany zostały zapisane pomyślnie", response, "Save changes error");

        closeAlert();
        logout();
        login(newPassword);
        clickElementByText("Użytkownik ");
        webDriver.findElement(By.xpath("//*[@id=\"oldPassword\"]")).sendKeys(newPassword);
        webDriver.findElement(By.xpath("//*[@id=\"newPassword\"]")).sendKeys(PASSWORD);
        webDriver.findElement(By.xpath("//*[@id=\"confirmPassword\"]")).sendKeys(PASSWORD);
        clickElementByText("Zapisz ");
    }

    @Test
    @Order(3)
    public void addSolaxInverterTest() {
        login(PASSWORD);

        clickElementByText("Falowniki");
        clickElementByText("Dodaj falownik ");
        webDriver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Falownik Solax");
        webDriver.findElement(By.xpath("//*[@id=\"serialNumber\"]")).sendKeys("SP82NGPHQN");
        webDriver.findElement(By.xpath("//*[@id=\"tokenId\"]")).sendKeys("202205121833375649400215");
        clickElementByText("Dodaj", 1);

        WebElement inverterCard = findCardWithHeader("Falownik Solax");
        String status = getInverterParameter(inverterCard, "Status: ");
        boolean statusCheck = status.equals("ACTIVE") || status.equals("STANDBY");
        String brand = getInverterParameter(inverterCard, "Marka: ");
        boolean brandCheck = brand.equals("Solax");
        Assertions.assertTrue(statusCheck, "Incorrect status");
        Assertions.assertTrue(brandCheck, "Incorrect brand");
    }

    @Test
    @Order(4)
    public void addSolarEdgeInverterTest() {
        login(PASSWORD);

        clickElementByText("Falowniki");
        clickElementByText("Dodaj falownik ");
        webDriver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("Falownik SolarEdge");
        Select select = new Select(webDriver.findElement(By.id("type")));
        select.selectByValue("solar-edge");
        webDriver.findElement(By.xpath("//*[@id=\"apiKey\"]")).sendKeys("1DRBXFVD2U2R3EMYJ57YBRZ2BS7J59WN");
        webDriver.findElement(By.xpath("//*[@id=\"siteId\"]")).sendKeys("1410702");
        clickElementByText("Dodaj", 1);

        WebElement inverterCard = findCardWithHeader("Falownik SolarEdge");
        String status = getInverterParameter(inverterCard, "Status: ");
        boolean statusCheck = status.equals("ACTIVE") || status.equals("STANDBY");
        String brand = getInverterParameter(inverterCard, "Marka: ");
        boolean brandCheck = brand.equals("SolarEdge");
        Assertions.assertTrue(statusCheck, "Incorrect status");
        Assertions.assertTrue(brandCheck, "Incorrect brand");
    }

    @Test
    @Order(9)
    public void deleteAllInverters() {
        login(PASSWORD);

        clickElementByText("Falowniki");
        clickElementByText("Lista falowników ");

        By deleteButtonBy = By.xpath("//button[@class='btn btn-danger btn-sm']");
        int size = webDriver.findElements(deleteButtonBy).size();
        for (int i = 0; i < size; i++) {
            WebElement button = webDriver.findElement(deleteButtonBy);
            button.click();
            clickElementByText("Tak, usuń");
            wait.until(ExpectedConditions.invisibilityOf(webDriver.findElement(By.xpath("//div[@class='modal-body']//p[contains(text(), 'Ładowanie...')]"))));
        }
    }

    @Test
    @Order(10)
    public void deleteAccountTest() {
        login(PASSWORD);
        assertLogin();

        webDriver.findElement(By.xpath("//*[@id=\"responsive-navbar-nav\"]/div[1]/a[3]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div/div[2]/form/button[2]")).click();
        webDriver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]")).click();

        login(PASSWORD);
        String response = getAlertText();
        Assertions.assertEquals("Niepoprawne logowanie", response, "Account wasn't removed");

    }

    private void login(String password) {
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[2]/input")).sendKeys(LOGIN);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[3]/div/input")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/form/div/div[4]/button")).click();
    }

    private void logout() {
        clickElementByText("Wyloguj ");
    }

    private void closeAlert() {
        webDriver.findElement(By.xpath("//div[@role='alert']/button")).click();
    }

    private String getAlertText() {
        return webDriver.findElement(By.xpath("//div[@role='alert']")).getText();
    }

    private void clickElementByText(String text) {
        clickElementByText(text, 0);
    }

    private void clickElementByText(String text, int index) {
        webDriver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).get(index).click();
    }

    private void assertLogin() {
        String header =  webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[1]")).getText();
        Assertions.assertEquals("O aplikacji", header, "Incorrect header of about card");
    }

    private WebElement findCardWithHeader(String text) {
        return webDriver.findElement(By.xpath("//div[@class='card' and .//div[@class='card-header']//div[contains(text(),'" + text + "')]]"));
    }

    private String getInverterParameter(WebElement inverterCard, String parameter) {
        return inverterCard.findElement(By.xpath(".//div[@class='list-group-item' and contains(text(), '" + parameter + "')]")).getText().split(parameter)[1];
    }


    @AfterEach
    public void after() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
