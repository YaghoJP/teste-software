---
title: Relatório Prática 03 - Teste de Sistemas com Playwright
author: XXXXXX
RA: XXXXXX
date: AAAA-MM-DD
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


### 2.3. Jornada de Caminho Alternativo: [Título da Jornada]

// Aqui vai, passo a passo, o cenário de caminho alternativo ou de erro que sua dupla escolheu automatizar.

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

## 3. Page Objects Implementados

// Aqui vai a descrição do que é testado pela Classe 1.
```java
// Aqui vai o código completo da Classe 1
```

**2: Classe AgendarEntrevistaPage:**
A classe AgendarEntrevistaPage representa a página/modal de agendamento de entrevistas do sistema. Ela encapsula todos os elementos e ações que podem ser executadas nessa página, como selecionar um estudante, preencher a data da entrevista, clicar no botão de agendar e verificar mensagens de sucesso ou erro.

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


## 4. Testes Automatizados

// Aqui vai a descrição do que é testado pela Classe de Teste 1.
```java
// Aqui vai o código completo da Classe de Teste 1
```
**2: Classe AgendamentoEntrevistaTest**
A classe AgendamentoEntrevistaTest contém os testes automatizados relacionados ao agendamento de entrevistas no sistema. Ela utiliza o padrão Page Object para interagir com as páginas de login, escolas, entrevistas e agendamento.

Os testes validam tanto o fluxo de sucesso (agendar uma nova entrevista) quanto os casos de erro (tentar agendar com data passada), garantindo que o sistema se comporte conforme esperado.

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