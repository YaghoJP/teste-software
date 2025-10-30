package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;


    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {

        this.page = page; 
        

        this.usernameInput = page.locator("#email"); 
        this.passwordInput = page.locator("#password"); 
        this.loginButton = page.locator("button[type='submit']"); 
    }

    public void navigate(String url) {
        page.navigate(url);
    }


    public EscolasPage login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();

        return new EscolasPage(page);
    }
}