package com.meuprojeto.tests;

import com.meuprojeto.pages.AgendarEntrevistaPage;
import com.meuprojeto.pages.EscolasPage;
import com.meuprojeto.pages.EntrevistasPage;
import com.meuprojeto.pages.LoginPage;
import com.microsoft.playwright.TimeoutError;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EntrevistasPageTest extends BaseTest {

    @Test
    void deveExibirTituloDaPaginaDeEntrevistas() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate("https://app.development.teai.com.br");

        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();

        assertThat(entrevistasPage.getPageTitleLocator()).isVisible();
    }

    @Test
    void deveAbrirPaginaDeAgendamentoAoClicarEmAgendarNovaEntrevista() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate("https://app.development.teai.com.br");

        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();

        AgendarEntrevistaPage agendarPage = entrevistasPage.clicarAgendarNovaEntrevista();

        assertThat(agendarPage.getPageTitleLocator()).isVisible();
    }

    @Test
    void deveEncontrarEntrevistaExistenteNaTabela() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate("https://app.development.teai.com.br");

        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");
        EntrevistasPage entrevistasPage = escolasPage.getSideMenu().clicarEntrevistas();

        String nomeEstudante = "Aluno2";

        try {
            assertThat(entrevistasPage.buscarEntrevistaPorEstudante(nomeEstudante)).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: A entrevista para " + nomeEstudante + " não foi encontrada na tabela.");
        }
    }
}
