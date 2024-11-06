package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Service
public class SeleniumService {

    public String runAutomation(String candidateId) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver");

        // Set up ChromeOptions for headless operation
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the candidate profile page
            driver.get("https://ui.boondmanager.com/candidates/" + candidateId + "/actions");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

            // Wait for and click the "Create Action" button
            WebElement createActionButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bmc-btn.bm-tooltips.bmb-rectangle")));
            createActionButton.click();

            // Wait for the dropdown to open and select the "Email" option
            WebElement emailOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ember-power-select-options-ember183 > li:nth-child(8)")));
            emailOption.click();

            // Wait for the message box to be available and type the message
            WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tinymce > p")));
            messageBox.click();

            // Generate current timestamp for the message
            String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String message = "An automatic email was sent at " + currentDateTime;
            messageBox.sendKeys(message);

            // Wait for the "Submit" button and click it
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bmc-btn.bm-tooltips.bmb-rectangle.bmb-dropdown.bmb-validate")));
            submitButton.click();

            return "Script completed successfully.";
        } catch (Exception e) {
            return "Error during automation: " + e.getMessage();
        } finally {
            driver.quit();
        }
    }
}
