package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "path/to/your/chromedriver");

        // Set up ChromeOptions for headless operation
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless mode for automation
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Initialize WebDriver with the ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        try {
            // Define candidateId (Can be passed as a parameter or hardcoded)
            String candidateId = "239"; // Example ID (can be dynamically passed)

            // Navigate to the candidate profile page
            System.out.println("Navigating to candidate profile page...");
            driver.get("https://ui.boondmanager.com/candidates/" + candidateId + "/actions");

            // Wait for the "Create Action" button to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
            WebElement createActionButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bml-page-content .bmc-btn.bm-tooltips.bmb-rectangle")));
            System.out.println("Clicking 'Create Action' button...");
            createActionButton.click();

            // Wait for the dropdown to open and select the "Email" option
            WebElement emailOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bmc-power-select-ember158 > span.ember-power-select-selected-item")));
            System.out.println("Selecting 'Email' from the dropdown...");
            emailOption.click();

            WebElement emailChoice = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ember-power-select-options-ember183 > li:nth-child(8)")));
            emailChoice.click();

            // Wait for the message box to be available and type the message
            WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tinymce > p")));
            System.out.println("Typing the message in the text box...");
            messageBox.click();

            // Generate current timestamp for the message
            String currentDateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            String message = "An automatic email was sent at " + currentDateTime;
            messageBox.sendKeys(message);

            // Wait for the "Submit" button and click it
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ember177 > div.bmc-modal-native.open.bmc-modal-native_large > div > div.bmc-modal-native-box_footer > button.bmc-btn.bm-tooltips.bmb-rectangle.bmb-dropdown.bmb-validate > span")));
            System.out.println("Submitting the message...");
            submitButton.click();

            // Log the completion of the script
            System.out.println("Script completed successfully.");

        } catch (Exception e) {
            // Catch and print any exceptions
            System.err.println("Error during automation: " + e.getMessage());
        } finally {
            // Close the browser at the end
            driver.quit();
        }
    }
}
