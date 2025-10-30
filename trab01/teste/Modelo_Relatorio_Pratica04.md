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

// Aqui vai a descrição do que é testado pela Classe 2.
```java
// Aqui vai o código completo da Classe 2
```

## 4. Testes Automatizados

// Aqui vai a descrição do que é testado pela Classe de Teste 1.
```java
// Aqui vai o código completo da Classe de Teste 1
```

// Aqui vai a descrição do que é testado pela Classe de Teste 2.
```java
// Aqui vai o código completo da Classe de Teste 2
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

---
**Defeito ID:** DEF-02

**Título:** ...
...