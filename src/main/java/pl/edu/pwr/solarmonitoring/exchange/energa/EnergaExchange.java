/*
 * SolarMonitoring
 * Copyright (C) 2022 Mikołaj Chmielecki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * LICENSE file in root directory contains a copy of the GNU General Public License.
 */

package pl.edu.pwr.solarmonitoring.exchange.energa;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.pwr.solarmonitoring.model.User;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

@Component
@Slf4j
public class EnergaExchange {

    private static final String ENERGA_URL = "https://mojlicznik.energa-operator.pl/";

    @Value("${selenium.grid.url}")
    private String GRID_URL;


    public EnergaData getEnergaDataForBeforeMonth(User user) {
        log.debug("getEnergyData for " + user);
        if (user.getCounter() != null) {
            WebDriver webDriver = null;
            try {
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\chmimiko\\Downloads\\chromedriver_win32\\chromedriver.exe");
                FirefoxOptions options = new FirefoxOptions();
                webDriver = new RemoteWebDriver(new URL(GRID_URL), options);
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                login(webDriver, user);

                // go to energy view
                webDriver.findElement(By.xpath("//*[@id=\"mainMenu\"]/ul/li[2]/a")).click();

                int currentMonth = LocalDate.now().getMonthValue() - 1;
                Double chargeEnergy = getEnergyInCurrentMonth(webDriver, currentMonth, 4);

                // go to remit energy
                new Select(webDriver.findElement(By.xpath("//*[@id=\"meterobjecttochart\"]"))).selectByValue("A-");
                Double remitEnergy = getEnergyInCurrentMonth(webDriver, currentMonth, 5);

                webDriver.quit();
                return new EnergaData(chargeEnergy, remitEnergy);
            } catch (Exception e) {
                String msg = "Error during communication with Energa website";
                log.error(e.getMessage());
                if (webDriver != null) {
                    webDriver.quit();
                }
                throw new RuntimeException(msg, e.getCause());
            }
        } else {
            String msg = "User hasn't set counter credentials.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param webDriver
     * @param currentMonth - indexed from 0, if 0 change year to before
     * @return
     */
    private Double getEnergyInCurrentMonth(WebDriver webDriver, int currentMonth, int index) {
        if (currentMonth == 0) {
            webDriver.findElement(By.xpath("//*[@id=\"buttonPrev\"]")).click();
            currentMonth = 12;
        }
        Actions action = new Actions(webDriver);
        WebElement bar = webDriver.findElement(By.xpath(String.format("/html/body/*[name()='svg'][1]/*[name()='rect' and @fill='#bce05c'][%s]", currentMonth)));
        action.moveToElement(bar).perform();

        By valueBoxXpath = By.xpath(String.format("/html/body/*[name()='svg'][%s]/*[name()='text'][2]/*[name()='tspan']", index));
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(valueBoxXpath));
        WebElement valueBox = webDriver.findElement(valueBoxXpath);

        if (currentMonth == 12) {
            webDriver.findElement(By.xpath("//*[@id=\"buttonNext\"]")).click();
        }

        String value = valueBox.getText().replaceAll("[^0-9.]+", " ").trim().split(" ")[0];
        return Double.parseDouble(value);

    }

    private void login(WebDriver webDriver, User user) {
        webDriver.get(ENERGA_URL);
        webDriver.findElement(By.xpath("//*[@id=\"loginRadio\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"j_username\"]")).sendKeys(user.getCounter().getLogin());
        webDriver.findElement(By.xpath("//*[@id=\"j_password\"]")).sendKeys(user.getCounter().getPassword());
        webDriver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[4]/button")).click();
    }

}
