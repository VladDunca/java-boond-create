package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SeleniumServiceTest {

    @Mock
    private WebDriver driver;

    @Mock
    private WebDriverWait wait;

    @Mock
    private WebElement webElement;

    @InjectMocks
    private SeleniumService seleniumService;

    @Value("${usernameED}")
    private String testUsername;

    @Value("${passwordED}")
    private String testPassword;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/testConfig.properties")) {
            properties.load(fis);
            testUsername = properties.getProperty("usernameED");
            testPassword = properties.getProperty("passwordED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunAutomation_Success() {
        doNothing().when(driver).get(anyString());
        when(wait.until(any())).thenReturn(webElement);
        when(webElement.isDisplayed()).thenReturn(true);

        String username = testUsername;
        String password = testPassword;

        String result = seleniumService.runAutomation("376");

        verify(driver).get("https://ui.boondmanager.com/candidates/376/actions");
        verify(driver).quit();
        assert(result.contains("Script completed successfully"));
    }

    @Test
    public void testRunAutomation_Failure() {
        testUsername = null;

        String result = seleniumService.runAutomation("123");

        assert(result.contains("Error during automation: Missing credentials"));
    }
}
