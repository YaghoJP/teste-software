package com.meuprojeto.tests;

import com.meuprojeto.pages.AgendarEntrevistaPage;
import com.meuprojeto.pages.EscolasPage;
import com.meuprojeto.pages.EntrevistasPage;
import com.meuprojeto.pages.LoginPage;
import com.meuprojeto.utils.DateHelper;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.TimeoutError;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AgendamentoEntrevistaTest extends BaseTest {
    
    @Test
    void deveAgendarNovaEntrevistaComSucesso() {
        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";
        loginPage.navigate(url);
        
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        assertThat(escolasPage.getPageTitleLocator()).isVisible();
        
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();
        assertThat(entrevistasPage.getPageTitleLocator()).isVisible();
        
        AgendarEntrevistaPage agendarPage = entrevistasPage.clicarAgendarNovaEntrevista();
        assertThat(agendarPage.getPageTitleLocator()).isVisible();
        
        String dataFutura = DateHelper.obterDataFutura(7);
        agendarPage.selecionarEstudante("Ronaldo Nazario"); // Ajustar para estudante existente
        agendarPage.preencherDataEntrevista(dataFutura);
        agendarPage.clicarAgendar();
        
        try {
            assertThat(entrevistasPage.buscarEntrevistaPorEstudante("Ronaldo Nazario")).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: A entrevista para Ronaldo Nazario não foi encontrada na tabela.");
        }
    }
    
    @Test
    void deveExibirErroAoAgendarComDataPassada() {
        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";
        loginPage.navigate(url);
        
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        assertThat(escolasPage.getPageTitleLocator()).isVisible();
        
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();
        AgendarEntrevistaPage agendarPage = entrevistasPage.clicarAgendarNovaEntrevista();
        
        String dataPassada = DateHelper.obterDataPassada(7);
        agendarPage.selecionarEstudante("Ronaldo Nazario");
        agendarPage.preencherDataEntrevista(dataPassada);
        agendarPage.clicarAgendar();
        
        try {
            assertThat(agendarPage.getMensagemErroLocator()).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: Mensagem de erro não foi exibida ao tentar agendar com data passada.");
        }
    }
    
    @Test
    void deveExibirErroAoAgendarSemSelecionarEstudante() {
        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";
        loginPage.navigate(url);
        
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        assertThat(escolasPage.getPageTitleLocator()).isVisible();
        
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();
        AgendarEntrevistaPage agendarPage = entrevistasPage.clicarAgendarNovaEntrevista();
        
        String dataFutura = DateHelper.obterDataFutura(7);
        agendarPage.preencherDataEntrevista(dataFutura);
        agendarPage.clicarAgendar();
        
        try {
            assertThat(agendarPage.getMensagemErroLocator()).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: Mensagem de erro não foi exibida ao tentar agendar sem selecionar estudante.");
        }
    }
    
    @Test
    void deveExibirErroAoAgendarComCamposVazios() {
        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";

        loginPage.navigate(url);
        
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        assertThat(escolasPage.getPageTitleLocator()).isVisible();
        
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();
        AgendarEntrevistaPage agendarPage = entrevistasPage.clicarAgendarNovaEntrevista();
        
        agendarPage.clicarAgendar();
        
        try {
            assertThat(agendarPage.getMensagemErroLocator()).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: Mensagem de erro não foi exibida ao tentar agendar com campos vazios.");
        }
    }
}