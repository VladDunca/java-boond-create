package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

@Service
public class SeleniumService {

    private String username;
    private String password;

    public SeleniumService() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
            this.username = properties.getProperty("usernameED");
            this.password = properties.getProperty("passwordED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String runAutomation(String candidateId) {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Chrome Driver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = getChromeOptions();

// Enable ChromeDriver logs for more detailed tracking
        System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        WebDriver driver = new ChromeDriver(options);


        try {
            Actions actions = new Actions(driver);
            driver.get("https://ui.boondmanager.com/candidates/" + candidateId + "/actions");

            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

            WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#login-field")));
            usernameField.click();
            usernameField.sendKeys(this.username);

            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ember4")));
            passwordField.click();
            passwordField.sendKeys(this.password);

            WebElement submitLogin = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#login > div.bml-login-page > div.bml-login-page_content > div.bml-login_content > div.bml-login_content-connect-form > div.bml-login-credentials_button > button")));
            submitLogin.click();

            driver.manage().getCookies();

            WebElement createActionButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bmc-btn.bm-tooltips.bmb-rectangle")));
            createActionButton.click();

            WebElement modalBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bmc-modal-native-box")));
            wait.until(ExpectedConditions.elementToBeClickable(modalBox));

            WebElement dropdownOptions = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.bmc-modal-native.open.bmc-modal-native_large div.bmc-modal-native-box_content div.bmc-field-select_trigger")));
            dropdownOptions.click();

            WebElement emailOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.bmc-field-select_dropdown_options > ul > li:nth-child(8)")));
            emailOption.click();

            WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tinymce > p")));
            messageBox.click();

            String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String message = "An automatic email was sent at " + currentDateTime;
            messageBox.sendKeys(message);

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.bmc-modal-native-box_footer > button.bmc-btn.bm-tooltips.bmb-rectangle.bmb-dropdown.bmb-validate > span")));
            submitButton.click();

            return "Script completed successfully.";
        } catch (Exception e) {
            return "Error during automation: " + e.getMessage();
        } finally {
            driver.quit();
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        // options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Enable verbose logging for better debugging
        options.addArguments("--enable-logging");
        options.addArguments("--v=1");  // Set verbosity level to 1


        // Disable GPU acceleration if issues arise
        options.addArguments("--disable-gpu");

        // Disable any extensions that might interfere with the tests
        options.addArguments("--disable-extensions");

        return options;
    }
}
