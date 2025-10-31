package com.meuprojeto.tests;

import com.meuprojeto.pages.AddEstudantePage;
import com.meuprojeto.pages.EscolasPage;
import com.meuprojeto.pages.EstudantesPage;
import com.meuprojeto.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import com.microsoft.playwright.TimeoutError;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CadastroEstudanteTest extends BaseTest { 

    @Test
    void deveCadastrarNovoEstudanteComSucesso() {

        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";

        loginPage.navigate(url);
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");

        assertThat(escolasPage.getPageTitleLocator()).isVisible();

        EstudantesPage estudantesPage = escolasPage.getSideMenu().clicarEstudantes();

        AddEstudantePage addEstudantePage = estudantesPage.clicarAdicionarNovoEstudante();

        addEstudantePage.preencherFormulario(
                "Aluno5",
                "224.745.150-03",
                "17/03/2003",
                "Masculino",
                "Brasil",
                "Educação Infantil",
                "Cleber",
                "Pai",
                "44999999999",
                "Professor IMA",
                "87365000",
                "Centro"      
        );
        addEstudantePage.clicarCadastrar();


        try {
            assertThat(estudantesPage.buscarEstudante("Aluno4")).isVisible(); // Espera até 10s
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: O estudante Aluno4 não foi encontrado na tabela após a busca.");
        }
       
    }

    @Test
    void buscarEstudanteInexistente() {

        LoginPage loginPage = new LoginPage(page);
        String url = "https://app.development.teai.com.br";

        loginPage.navigate(url);
        EscolasPage escolasPage = loginPage.login("grupo2@email.com", "senha123");

        assertThat(escolasPage.getPageTitleLocator()).isVisible();

        EstudantesPage estudantesPage = escolasPage.getSideMenu().clicarEstudantes();

        assertThat(estudantesPage.buscarEstudanteInexistente("NomeInexistente12345646454")).isVisible(); // Espera até 10s    
    }
}