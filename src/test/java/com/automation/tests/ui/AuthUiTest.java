package com.automation.tests.ui;

import com.automation.tests.base.BaseTest;
import com.automation.tests.base.AiTestWatcher;
import com.automation.config.ConfigReader;
import com.automation.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AiTestWatcher.class)
public class AuthUiTest extends BaseTest {

    // Read values dynamically from the .env file!
    private final String baseUrl = ConfigReader.get("BASE_URL");
    private final String validUser = ConfigReader.get("TEST_USERNAME");
    private final String validPass = ConfigReader.get("TEST_PASSWORD");

    @Test
    void testValidLogin() {
        System.out.println("Running UI Test: Valid Login Scenario");
        LoginPage loginPage = new LoginPage(page);
        
        loginPage.navigateTo(baseUrl + "/login");
        loginPage.login(validUser, validPass);
        
        // Note: Playwright handles hardcoded waits automatically (auto-waiting).
        // It will automatically wait for the DOM to update before grabbing text!
        String bodyText = page.locator("body").textContent();
        assertTrue(bodyText.contains("Welcome to the Wrong Area"), 
                "Login failed for valid credentials.");
    }

    @Test
    void testInvalidLogin() {
        System.out.println("Running UI Test: Invalid Login Scenario (Negative Test)");
        LoginPage loginPage = new LoginPage(page);
        
        loginPage.navigateTo(baseUrl + "/login");
        // Inject bad credentials
        loginPage.login("wrongUser", "badPassword!");
        
        // Assert the red error banner pops up
        String bodyText = page.locator("body").textContent();
        assertTrue(bodyText.contains("Your username is invalid!"), 
                "Expected error message not found for invalid login.");
    }
}
