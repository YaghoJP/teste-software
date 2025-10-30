// EntrevistasPage.java
package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EntrevistasPage {
    private Page page;
    private final SideMenuComponent sideMenu;
    private final Locator addEntrevistaButton;
    private final Locator pageTitle;

    public EntrevistasPage(Page page) {
        this.page = page;
        this.sideMenu = new SideMenuComponent(page);
        this.addEntrevistaButton = page.locator("button:has-text('Agendar Nova Entrevista')");
        this.pageTitle = page.locator("h1:has-text('Entrevistas')");
    }

    public AgendarEntrevistaPage clicarAgendarNovaEntrevista() {
        addEntrevistaButton.click();
        return new AgendarEntrevistaPage(page);
    }

    public SideMenuComponent getSideMenu() {
        return this.sideMenu;
    }

    public Locator getPageTitleLocator() {
        return pageTitle;
    }

    public Locator buscarEntrevistaPorEstudante(String nomeEstudante) {
        return page.locator("table tbody tr:has-text('" + nomeEstudante + "')");
    }
}
