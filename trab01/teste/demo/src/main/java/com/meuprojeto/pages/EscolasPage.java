package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EscolasPage {

    private  Page page;
    private  SideMenuComponent sideMenu;
    private  Locator pageTitle;

    public EscolasPage(Page page) {
        this.page = page;
        this.sideMenu = new SideMenuComponent(page);
        this.pageTitle = page.locator("h1:has-text('Escolas')"); // (Substitua seletor)
    }

    // Método para acessar o menu
    public SideMenuComponent getSideMenu() {
        return this.sideMenu;
    }

    // Método para o teste verificar se está na página certa
    public Locator getPageTitleLocator() {
        return pageTitle;
    }
}