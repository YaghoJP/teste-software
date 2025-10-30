// AgendarEntrevistaPage.java
package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AgendarEntrevistaPage {
    private Page page;
    private final Locator estudanteInput;
    private final Locator dataEntrevistaInput;
    private final Locator agendarButton;
    private final Locator mensagemSucesso;
    private final Locator mensagemErro;
    private final Locator pageTitle;

    public AgendarEntrevistaPage(Page page) {
        this.page = page;
        this.estudanteInput = page.getByLabel("Estudante");
        this.dataEntrevistaInput = page.getByLabel("Data da Entrevista");
        this.agendarButton = page.locator("button:has-text('Agendar entrevista')");
        this.mensagemSucesso = page.locator("div:has-text('Entrevista agendada com sucesso!')");
        this.mensagemErro = page.locator("p:has-text('A data não pode ser no passado.')");
        this.pageTitle = page.locator("h1:has-text('Agendar')");
    }

    public void selecionarEstudante(String nomeEstudante) {
        estudanteInput.fill(nomeEstudante);
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(nomeEstudante)).click();
    }

    public void preencherDataEntrevista(String data) {
        dataEntrevistaInput.fill(data);
    }

    public EntrevistasPage clicarAgendar() {
        agendarButton.click();
        return new EntrevistasPage(page);
    }

    public Locator getPageTitleLocator() {
        return pageTitle;
    }

    public Locator getMensagemSucessoLocator() {
        return mensagemSucesso;
    }

    public Locator getMensagemErroLocator() {
        return mensagemErro;
    }
}