package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SideMenuComponent {

    private  Page page;

    private Locator estudantesLink;
    private Locator entrevistasLink;
    
    public SideMenuComponent(Page page) {
        this.page = page;
        this.estudantesLink = page.locator("nav a:has-text('Estudantes')"); 
        this.entrevistasLink = page.locator("nav a:has-text('Entrevistas')");
    }

    public EstudantesPage clicarEstudantes() {
        estudantesLink.click();
        return new EstudantesPage(page);
    }

    public EntrevistasPage clicarEntrevistas() {
        entrevistasLink.click();
        return new EntrevistasPage(page);
    }
}