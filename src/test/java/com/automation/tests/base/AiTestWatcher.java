package com.automation.tests.base;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * This watcher listens for JUnit test failures.
 * When a test fails, it takes the error and sends it to Gemini AI for analysis.
 */
public class AiTestWatcher implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        
        System.out.println("\n=======================================================");
        System.out.println("❌ TEST FAILED: " + testName);

        // Take a screenshot of the failure state
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest) {
            Page page = ((BaseTest) testInstance).getPage();
            if (page != null) {
                String screenshotPath = "reports/screenshots/" + testName.replaceAll("[^a-zA-Z0-9.-]", "_") + ".png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
                System.out.println("📸 Screenshot saved to: " + screenshotPath);
            }
        }

        System.out.println("🧠 Asking AI for root-cause analysis (via OpenRouter)...");
        
        String analysis = com.automation.ai.OpenRouterClient.analyzeFailure(testName, cause.getMessage());
        
        System.out.println("\n🤖 AI DIAGNOSIS:");
        System.out.println(analysis);
        System.out.println("=======================================================\n");
    }
}
