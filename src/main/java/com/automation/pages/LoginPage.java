package com.automation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

/**
 * Example Page Object Model for a Login Page.
 * This encapsulates all the locators and actions for the login screen.
 */
public class LoginPage {
    private final Page page;

    // Locators
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        // Basic CSS selectors that would fit many standard login forms
        this.usernameInput = page.locator("input[name='username'], input[id='username'], input[type='text']").first();
        this.passwordInput = page.locator("input[name='password'], input[id='password'], input[type='password']").first();
        this.loginButton = page.locator("button[type='submit'], input[type='submit'], i.fa-sign-in").first();
    }

    public void navigateTo(String url) {
    page.navigate(url, new Page.NavigateOptions()
            .setWaitUntil(com.microsoft.playwright.options.WaitUntilState.DOMCONTENTLOADED));
}


    public void login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }
}
