package com.automation.tests.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static APIRequestContext apiContext; // Used for REST API Tests
    
    protected BrowserContext context;
    protected Page page;

    public Page getPage() {
        return page;
    }

    @BeforeAll
    static void launchBrowserAndApi() {
        playwright = Playwright.create();
        // Headless = false so you can watch the browser in action
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        
        // Setup API Context for REST automation
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        apiContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setExtraHTTPHeaders(headers));
    }

    @AfterAll
    static void closeBrowserAndApi() {
        if (apiContext != null) {
            apiContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultNavigationTimeout(90000);
    }

}
