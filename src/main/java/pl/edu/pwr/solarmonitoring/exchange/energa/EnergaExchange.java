package pl.edu.pwr.solarmonitoring.exchange.energa;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import pl.edu.pwr.solarmonitoring.model.User;

import java.time.Duration;

@Component
@Slf4j
public class EnergaExchange {

    private static final String URL = "https://mojlicznik.energa-operator.pl/";

    public EnergaData getEnergaData(User user) throws InterruptedException {
    }

        public EnergaData getEnergaData(User user) throws InterruptedException {
        log.debug("getEnergyData for " + user);
        if (user.getCounter() != null) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\mikol\\OneDrive - Politechnika Wroclawska\\Desktop\\chromedriver.exe");
            WebDriver webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.get(URL);
            webDriver.findElement(By.xpath("//*[@id=\"loginRadio\"]")).click();
            webDriver.findElement(By.xpath("//*[@id=\"j_username\"]")).sendKeys(user.getCounter().getLogin());
            webDriver.findElement(By.xpath("//*[@id=\"j_password\"]")).sendKeys(user.getCounter().getPassword());
            webDriver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[4]/button")).click();
            webDriver.findElement(By.xpath("//*[@id=\"mainMenu\"]/ul/li[2]/a")).click();

            Actions action = new Actions(webDriver);
            WebElement bar = webDriver.findElement(By.xpath("/html/body/*[name()='svg'][1]/*[name()='rect' and @fill='#bce05c'][1]"));
            action.moveToElement(bar).perform();

            Thread.sleep(10000L);
            return new EnergaData(0D, 1D);
        } else {
            String msg = "User hasn't set counter credentials.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

}
