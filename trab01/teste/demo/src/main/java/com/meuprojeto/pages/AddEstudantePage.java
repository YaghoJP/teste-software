package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddEstudantePage {

    private  Page page;
    private  Locator nomeInput;
    private  Locator cpfInput;
    private  Locator dataNascimentoInput;
    private  Locator generoInput;
    private  Locator paisInput;
    private  Locator anoEscolarInput;
    private  Locator nomeRespInput;
    private  Locator parentescoInput;
    private  Locator telefoneInput;
    private  Locator professorInput;
    private  Locator cepInput;
    private  Locator ruaInput;
    private  Locator numeroInput;
    private  Locator bairroInput;
    private  Locator cadastrarButton;
    private  Locator pageTitle;

    public AddEstudantePage(Page page) {
        this.page = page;
        this.nomeInput = page.getByLabel("Nome Completo"); 
        this.cpfInput = page.getByLabel("CPF"); 
        this.dataNascimentoInput = page.getByLabel("Data de Nascimento");
        this.generoInput = page.getByLabel("Gênero"); 
        this.paisInput = page.getByLabel("País de Nascimento"); 
        this.anoEscolarInput = page.getByLabel("Ano Escolar");  
        this.nomeRespInput = page.getByLabel("Nome do Responsável"); 
        this.parentescoInput = page.getByLabel("Parentesco");
        this.telefoneInput = page.getByLabel("Telefone");  
        this.professorInput = page.getByLabel("Professor Responsável");  
        this.cepInput = page.getByLabel("CEP");
        this.ruaInput = page.getByLabel("Rua"); 
        this.numeroInput = page.getByLabel("Número");
        this.bairroInput = page.getByLabel("Bairro");
        this.cadastrarButton = page.locator("button:has-text('Cadastrar Estudante')"); 
        this.pageTitle = page.getByLabel("h1:has-text('Estudantes')");
    }


    public void preencherFormulario(String nome, String cpf, String dataNasc, String genero,
                                   String pais, String anoEscolar, String nomeResp,
                                   String parentesco, String telefone, String professor,
                                   String cep,  String bairro ) {
        nomeInput.fill(nome);
        cpfInput.fill(cpf);
        dataNascimentoInput.fill(dataNasc);
        generoInput.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(genero)).click();
        paisInput.fill(pais); 
        anoEscolarInput.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(anoEscolar)).click(); 
        nomeRespInput.fill(nomeResp); 
        parentescoInput.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(parentesco)).click();
        telefoneInput.fill(telefone);  
        professorInput.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(professor)).click(); 
        cepInput.fill(cep); 

        assertThat(ruaInput).not().isEmpty();
        numeroInput.fill("100");
        bairroInput.fill(bairro);
    }


    public EstudantesPage clicarCadastrar() {
        cadastrarButton.click();

        return new EstudantesPage(page);
    }

    public Locator getPageTitleLocator() {
        return pageTitle;
    }
}