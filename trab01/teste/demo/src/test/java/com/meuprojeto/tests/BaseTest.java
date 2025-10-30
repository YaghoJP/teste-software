package com.meuprojeto.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public abstract class BaseTest {


    static Playwright playwright;
    static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false) 
                .setSlowMo(50));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setLocale("pt-BR"));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}