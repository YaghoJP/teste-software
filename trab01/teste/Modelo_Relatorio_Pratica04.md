---
title: Relatório Prática 03 - Teste de Sistemas com Playwright
author: Yagho Petini - Hudson Perrut
RA: a2380366
date: 31-10-2025
tags:
  - markdown
  - vscode
---

## 1. Descrição do Sistema Testado (SUT)


O sistema testado é o TEAEdu. Este sistema é voltado para a preparação e condução do PEI (Plano de Educação Individualizado) para aluno disagnosticados com o TEA (Transtorno do Espectro Autista). O sistema permite o cadastramento de aluno e educadores que realizarão acompanhamentos indivualizados através de uma entrevista elaborado com base na faixa etária. 

---

## 2. Jornadas de Usuário Testadas

### 2.1. Jornada de Usuário 01: [Logar no sistema e realizar o cadastro de um novo estudante]

// Aqui vai, passo a passo, a primeira jornada de usuário que sua dupla escolheu automatizar (o caminho feliz).

**Passos:**

1.  O usuário com credenciais válidas irá logar no sistema
2.  O usuário logado será redirecionado para a página de lista de escolas
3.  O usuário selecionará no menu lateral a opção "Estudantes"
4.  Na aba estudantes deve clicar no botão "Adicionar novo estudante"
5.  Será informado todos os campos obrigatórios para criação de um estudante
6.  O usuário clica no botão "cadastrar estudante"
7.  A mensagem "usuário cadastrado será exibida" e a tela voltará para as listagens

### 2.2. Jornada de Usuário 02: [Logar no sistema e agendar uma nova entrevista pedagógica]

**Passos:**

1.  O usuário com credenciais válidas (educador/administrador) irá logar no sistema
2.  O usuário logado será redirecionado para a página inicial (dashboard ou lista de escolas)
3.  O usuário selecionará no menu lateral a opção "Entrevistas" ou "Entrevistas Pedagógicas"
4.  Na página de entrevistas deve clicar no botão "Agendar Nova Entrevista" ou ícone "+"
5.  O formulário de agendamento será exibido
6.  O usuário digita no campo de autocomplete o nome do estudante (ex: "João")
7.  O sistema exibe a lista de estudantes correspondentes
8.  O usuário seleciona o estudante desejado na lista (ex: "Cristiano Ronaldo")
9.  O usuário seleciona uma data futura válida no campo "Data da Entrevista" (ex: 06/12/2025)
10. O usuário clica no botão "Agendar Entrevista"
11. A mensagem "Entrevista agendada com sucesso!" será exibida
12. O sistema retorna para a tela de listagem de entrevistas
13. A nova entrevista aparece nas entrevistas disponíveis com os dados: Nome do Estudante, Data da Entrevista e Status

---

### 2.3. Jornada de Caminho Alternativo: [Logar no sistema e procurar um estudante inexistente]

1.  O usuário com credenciais válidas irá logar no sistema
2.  O usuário logado será redirecionado para a página de lista de escolas
3.  O usuário selecionará no menu lateral a opção "Estudantes"
4.  Na aba estudantes deve preencher o input de filtros com um nome de estudante inexistente
5.  A consulta deve retornar nenhum estudante encontrado


## 3. Page Objects Implementados

**1: Classe LoginPage:**

A classe LoginPage representa a página de login do sistema. Ela contém os Locator de input para login e o botão de logar, ela possui a função de porta de entrada ao sistema, caso o usuário tenha um login válido através do método 'login' é retornado o Page Object (PO) EscolaPage. 

```java
package com.meuprojeto.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;


    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {

        this.page = page; 
        

        this.usernameInput = page.locator("#email"); 
        this.passwordInput = page.locator("#password"); 
        this.loginButton = page.locator("button[type='submit']"); 
    }

    public void navigate(String url) {
        page.navigate(url);
    }


    public EscolasPage login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();

        return new EscolasPage(page);
    }
}
```

**2: Classe EscolasPage:**

A classe EscolasPage representa a página inicial do sistema. Ela contém os Locator do menu Lateral e do título da página para verificar se o login funcionou corretamente. Nela podemos acessar outras PO através da Page MenuComponent. 

```java
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
```

**3: Classe SideMenuComponent:**

A classe SideMenuComponent representa o menu lateral do sistema. Ela contém os Locator para as páginas necessárias do sistema.

```java
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
```


**4: Classe AgendarEntrevistaPage:**
A classe AgendarEntrevistaPage representa a página/modal de agendamento de entrevistas do sistema. Ela encapsula todos os elementos e ações que podem ser executadas nessa página, como selecionar um estudante, preencher a data da entrevista, clicar no botão de agendar e verificar mensagens de sucesso ou erro.

```java
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
        this.estudanteInput = page.getByLabel("Buscar estudante");
        this.dataEntrevistaInput = page.getByLabel("Data da Entrevista");
        this.agendarButton = page.locator("button:has-text('Agendar entrevista')");
        this.mensagemSucesso = page.locator("div:has-text('Entrevista agendada com sucesso!')");
        this.mensagemErro = page.locator("p.Mui-error:has-text('A data não pode ser no passado.')");
        this.pageTitle = page.locator("span:has-text('Informações da entrevista')");
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
```

**5: Classe EstudantesPage:**

A classe EstudantesPage representa a página de Listagem dos Estudantes. Ela contém os Locator para as listagem, o botão para cadastrar um novo estudante e o PO da página de adicionar um novo estudante.

```java
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
```

**6: Classe EstudantesPage:**

A classe AddEstudantePage representa a página de Adicionar um Estudantes. Ela contém os Locator para os inputs de cadastros e o botão para cadastrar um novo estudante além do PO da página de estudantes.

```java
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
```

**6: Classe: EntrevistasPage.java**

Descrição:
A classe EntrevistasPage representa a página de listagem e gerenciamento de entrevistas no sistema.
Ela encapsula os elementos e ações que podem ser realizadas nessa tela, permitindo que os testes interajam com a interface de forma estruturada e reutilizável.

Por meio desse Page Object, é possível:
- Navegar até o formulário de agendamento de uma nova entrevista.
- Acessar o menu lateral (SideMenuComponent) para transitar entre seções do sistema.
- Verificar o título da página para confirmar que a navegação foi bem-sucedida.
- Buscar entrevistas específicas na tabela com base no nome do estudante.

```java
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
```
## 4. Testes Automatizados

**1: Classe BaseTest**
A classe BaseTest contém os dados iniciais para iniciar os testes usando o Playwright além das limpezas que são feitas após cada teste, ela é a base para outras classes de testes

```java
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
```
**2: Classe AgendamentoEntrevistaTest**
A classe AgendamentoEntrevistaTest contém os testes automatizados relacionados ao agendamento de entrevistas no sistema. Ela utiliza o padrão Page Object para interagir com as páginas de login, escolas, entrevistas e agendamento.

Os testes validam tanto o fluxo de sucesso (agendar uma nova entrevista) quanto os casos de erro (tentar agendar com data passada), garantindo que o sistema se comporte conforme esperado.

```java
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
        agendarPage.selecionarEstudante("Aluno2"); // Ajustar para estudante existente
        agendarPage.preencherDataEntrevista(dataFutura);
        agendarPage.clicarAgendar();
        
        try {
            assertThat(entrevistasPage.buscarEntrevistaPorEstudante("Aluno2")).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: A entrevista para Aluno2 não foi encontrada na tabela.");
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
        agendarPage.selecionarEstudante("Aluno2");
        agendarPage.preencherDataEntrevista(dataPassada);
        agendarPage.clicarAgendar();
        
        try {
            assertThat(agendarPage.getMensagemErroLocator()).isVisible();
        } catch (TimeoutError e) {
            fail("FALHA NA VERIFICAÇÃO: Mensagem de erro não foi exibida ao tentar agendar com data passada.");
        }
    }
    
}
```

**3: Classe CadastroEstudanteTest**
A classe CadastroEstudanteTest contém os testes relacionados as jornadas 1 e 3.

```java
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
```

**3: Classe EntrevistasPageTest**
A classe EntrevistasPageTest contém os testes relacionados as jornadas 2, assim como a classe AgendamentoEntrevistaTest.

```java
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

```

## 5. Laudo de Problemas nos Requisitos

| ID | Localização no Documento (Épico/História) | Descrição do Problema | Tipo (Ambiguidade, Omissão, Inconsistência) | Sugestão de Melhoria |
|---|---|---|---|---|
| REQ-01 | 3.1: Cadastro de um Novo Estudante | O requisito não especifica o formato do campo CPF (com ou sem máscara) nem a validação a ser aplicada (se o dígito verificador é calculado). | Omissão | Detalhar a regra de validação do CPF, incluindo formato esperado e tratamento de valores inválidos. |
| REQ-02 | 3.2 Exclusão de um Estudante | O requisito não especifíca o que deve acontencer caso o estudante tenha uma entrevista pedagógica vinculada, | Omissão | Detalhar se deve excluir a entrevista também ou apenas não permitir a exclusão|
| REQ-03 | 4.1 Agendamento de Entrevista Pedagógica | O requisito não especifíca o que deve ser considerado como uma data válida (finais de semana e feriados são válidos?) | Omissão | Detalhar o que é uma data válida|
| REQ-04 | 4.1 Agendamento de Entrevista Pedagógica | O requisito não especifíca se pode haver mais de uma entrevista pedagógica no mesmo dia para um estudante | Omissão | Detalhar se pode haver mais de uma entrevista no mesmo dia para um mesmo estudante|
| REQ-05 | 4.2 Início de uma Entrevista Agendada | Uma entrevista é agendada porém eu posso inicializar ela a qualquer momento | inconsistências | Se preciso agendar uma entrevista deveria poder inicia-la apenas no dia agendado|
| REQ-06 | 5.1 Cadastro de Tipos de Eventos | O requisito não especifíca quais campos são obrigatórios para cadastro de um tipo de evento | omissão | Detalhar se o campo descrição é obrigatório|
---

## 6. Laudo de Defeitos Encontrados

*Com base nos seus testes e na análise de requisitos, preencha este laudo para cada defeito que seria encontrado no sistema. Adapte os campos "Resultado Atual" conforme necessário, já que são defeitos teóricos.*

---
**Defeito ID:** DEF-01

**Título:** O sistema não exibe mensagem de erro ao tentar cadastrar um estudante com CPF inválido.

**Passos para Reproduzir:**

1. Navegar para a página de "Cadastro de Estudante".
2. Preencher todos os campos obrigatórios com dados válidos.
3. Inserir um CPF com formato ou dígito verificador inválido no campo "CPF" (ex: "123.456.789-00").
4. Clicar no botão "Cadastrar Estudante".

**Resultado Esperado:** O sistema deveria exibir uma mensagem de erro clara abaixo do campo CPF, informando que o valor é inválido, e o cadastro não deveria ser concluído.

**Resultado Atual (Teórico):** Com base na omissão do requisito REQ-01, o sistema provavelmente aceitaria o valor ou apresentaria um erro inesperado (ex: erro 500), pois não há uma validação especificada.

**Severidade:** Alta

**Defeito ID: DEF-02**

**Título:** O sistema não exibe mensagem de erro ao tentar agendar uma entrevista sem selecionar um estudante.

**Passos para Reproduzir:**

1. Navegar para a página de "Entrevistas".
2. Clicar em "Agendar Nova Entrevista".
3. Não selecionar nenhum estudante no campo "Buscar estudante".
4. Preencher a data da entrevista (válida ou futura).
5. Clicar no botão "Agendar entrevista".

**Resultado Esperado:** O sistema deveria exibir uma mensagem de erro clara indicando que o campo de estudante é obrigatório, impedindo que a entrevista seja agendada.

**Resultado Atual:** O sistema não exibe nenhuma mensagem de erro ou validação visível. A tentativa de agendamento pode falhar silenciosamente ou gerar comportamento inesperado, deixando o usuário sem orientação sobre o erro.

**Severidade: Alta**

**Defeito ID:** DEF-03

**Título:** O sistema permite a exclusão de um estudante que tenha entrevista vinculada a ele.

**Passos para Reproduzir:**

1. Navegar para a página de "Cadastro de Entrevista".
2. Vinculas um estudante para a Entrevista.
3. Gerar a entrevista.
4. Navegar para a página de "Estudantes".
5. Excluir o estudante com a entrevista vinculada.

**Resultado Esperado:** O sistema deveria exibir uma mensagem de alerta dizendo que o estudante tem uma entrevista vinculada e perguntar se deseja excluir a entrevista também, caso a entrevista já tenha sido realizada não permite a exclusão do estudante.

**Resultado Atual (Teórico):** Atualmente o sistema permite a exclusão e a entrevista continua listada porém com erro.

**Severidade:** Alta

**Defeito ID:** DEF-04

**Título:** O sistema não permite filtrar um CPF que contenha máscara.

**Passos para Reproduzir:**

1. Navegar para a página de "Estudantes".
2. Filtrar um CPF que está com máscara (EX: 111.111.111-11).

**Resultado Esperado:** O sistema deveria tratar o caso do usuário informar um CPF com máscara e permitir a filtragem desse caso.

**Resultado Atual (Teórico):** Atualmente o sistema não consegue encontrar um usuário com o CPF com máscaras.

**Severidade:** Média