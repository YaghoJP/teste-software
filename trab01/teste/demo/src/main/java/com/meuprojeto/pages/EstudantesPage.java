package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EstudantesPage {

    private Page page;
    private final SideMenuComponent sideMenu; 
    private  Locator addEstudanteButton ;
    private  Locator successMessage;
    private Locator inputPesquisa;
    private  Locator entriesStatus;
    
    public EstudantesPage(Page page) {
        this.page = page;
        this.sideMenu = new SideMenuComponent(page);
        this.addEstudanteButton = page.locator("button:has-text('Adicionar novo estudante')"); 
        this.inputPesquisa = page.getByPlaceholder("Busque por nome, CPF..."); 
    }

    public AddEstudantePage clicarAdicionarNovoEstudante() {
        addEstudanteButton.click();
        return new AddEstudantePage(page);
    }

    public Locator getMensagemSucessoLocator() {
        this.successMessage = page.locator("div:has-text('Estudante cadastrado com sucesso!')"); // (Substitua seletor)
        return successMessage;
    }

    public Locator buscarEstudante(String nome) {
        inputPesquisa.fill(nome);
        this.entriesStatus = page.locator("text=Mostrando 1 de 1 entradas");
        return entriesStatus;
    }

    public Locator buscarEstudanteInexistente(String nome) {
        inputPesquisa.fill(nome);
        this.entriesStatus = page.locator("text=Nenhum estudante foi adicionado ainda. Que tal adicionar?");
        return entriesStatus;
    }
}