Feature: Gestão de beneficiários e atribuição de itens doados
  O objetivo desta feature é validar o registo de beneficiários,
  a validação dos seus dados e a atribuição segura de itens doados,
  garantindo controlo de acesso e auditoria.

  # REQ links: REQ-001, REQ-002, REQ-004, REQ-005, NFR-004, REQ-007

  Scenario: Happy path — Registo de beneficiário com dados válidos
    Given que o utilizador está autenticado como "Gestor de Beneficiários"
    And o sistema está disponível
    When o utilizador preenche o formulário com Nome "João Silva", NIF "123456789" e Email "joao@email.com"
    And confirma o registo
    Then o sistema guarda o beneficiário com um ID único
    And o sistema apresenta uma mensagem de confirmação

  Scenario: Negative path — Registo com campos obrigatórios em falta
    Given que o utilizador está autenticado como "Gestor de Beneficiários"
    When o utilizador submete o formulário com o campo Nome vazio
    Then o sistema apresenta uma mensagem de erro indicando "Campo obrigatório em falta"
    And o sistema impede o registo do beneficiário

  Scenario: Negative path — Registo com dados inválidos
    Given que o utilizador está autenticado como "Gestor de Beneficiários"
    When o utilizador submete o formulário com NIF "ABC" e Email "invalido"
    Then o sistema apresenta mensagens de erro para cada campo inválido
    And o sistema impede a submissão até à correção dos dados

  Scenario: Alternative flow — Atribuição de itens com stock insuficiente
    Given que o administrador está autenticado
    And existe um beneficiário elegível "BEN-001"
    And o item "Kit alimentar" tem stock de 1 unidade
    When o administrador tenta atribuir 5 unidades de "Kit alimentar" ao beneficiário
    Then o sistema informa que o stock é insuficiente
    And o sistema propõe uma atribuição parcial de 1 unidade

  Scenario: Negative path — Bloqueio de acesso sem permissões
    Given que o utilizador está autenticado com role "Gestor"
    When o utilizador tenta aceder à funcionalidade "Atribuir itens doados"
    Then o sistema bloqueia o acesso com mensagem "Permissões insuficientes"
    And o sistema regista a tentativa nos logs com timestamp e identificação do utilizador
