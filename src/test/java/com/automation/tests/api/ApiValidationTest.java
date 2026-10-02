package com.automation.tests.api;

import com.automation.tests.base.BaseTest;
import com.automation.tests.base.AiTestWatcher;
import com.automation.config.ConfigReader;
import com.microsoft.playwright.APIResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AiTestWatcher.class)
public class ApiValidationTest extends BaseTest {

    private final String apiUrl = ConfigReader.get("API_URL");

    @Test
    void testGetUserSuccess() {
        System.out.println("Running API Test: Get User (Positive Scenario)");
        APIResponse response = apiContext.get(apiUrl + "/users/1");
        
        assertEquals(200, response.status(), "API should return 200 OK");
        
        String body = response.text();
        assertTrue(body.contains("Leanne Graham"), "Response should contain expected user data");
    }

    @Test
    void testResourceNotFound() {
        System.out.println("Running API Test: Endpoint Unavailable (Negative Scenario)");
        
        // Intentionally hitting an endpoint that doesn't exist
        APIResponse response = apiContext.get(apiUrl + "/invalid-endpoint-12345");
        
        // The API should handle it gracefully and return a 404 status code
        assertEquals(404, response.status(), "API should correctly return 404 for invalid endpoints");
    }
}
