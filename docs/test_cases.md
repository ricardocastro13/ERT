# Test Cases — Lab 9

## TC-001 — Registo de beneficiário com sucesso (Happy Path)
- Type: System
- Priority: H
- Related requirements: REQ-001, REQ-002
- Preconditions: O utilizador está autenticado como Gestor de Beneficiários; o sistema está disponível.
- Test data: Nome="João Silva", NIF="123456789", Email="joao@email.com", Telefone="912345678"
- Steps:
  1. O utilizador acede à opção "Criar beneficiário".
  2. O sistema apresenta o formulário de registo.
  3. O utilizador preenche todos os campos obrigatórios com dados válidos.
  4. O utilizador confirma o registo.
- Expected results:
  - O sistema valida os campos obrigatórios sem erros.
  - O sistema guarda o beneficiário com um ID único gerado automaticamente.
  - O sistema apresenta mensagem de confirmação de registo.
- Notes: Cobre o fluxo principal do UC-01.

---

## TC-002 — Rejeição de registo com campos obrigatórios em falta (Negative)
- Type: System
- Priority: H
- Related requirements: REQ-001, REQ-002
- Preconditions: O utilizador está autenticado como Gestor de Beneficiários.
- Test data: Nome="" (vazio), NIF="123456789", Email="joao@email.com"
- Steps:
  1. O utilizador acede à opção "Criar beneficiário".
  2. O utilizador deixa o campo "Nome" vazio e preenche os restantes.
  3. O utilizador tenta confirmar o registo.
- Expected results:
  - O sistema identifica o campo "Nome" como obrigatório em falta.
  - O sistema apresenta mensagem de erro indicando o campo em falta.
  - O sistema bloqueia o registo e não cria nenhum beneficiário.
- Notes: Cobre exceção E1 do UC-01.

---

## TC-003 — Rejeição de registo com dados inválidos (Negative)
- Type: System
- Priority: H
- Related requirements: REQ-002
- Preconditions: O utilizador está autenticado como Gestor de Beneficiários.
- Test data: Nome="João Silva", NIF="ABC" (formato inválido), Email="email-invalido"
- Steps:
  1. O utilizador acede à opção "Criar beneficiário".
  2. O utilizador preenche os campos com dados inválidos (NIF não numérico, email sem formato válido).
  3. O utilizador tenta confirmar o registo.
- Expected results:
  - O sistema deteta os dados inválidos (NIF e Email).
  - O sistema apresenta mensagens de erro específicas para cada campo inválido.
  - O sistema impede a submissão e solicita correção.
- Notes: Cobre exceção E2 do UC-01.

---

## TC-004 — Submissão de candidatura com sucesso (Happy Path)
- Type: System
- Priority: H
- Related requirements: REQ-003
- Preconditions: Existe um beneficiário registado e válido no sistema.
- Test data: Beneficiário ID=BEN-001 (previamente registado com dados completos)
- Steps:
  1. O utilizador seleciona a opção "Submeter candidatura" para o beneficiário BEN-001.
  2. O sistema valida os dados do beneficiário.
  3. O utilizador confirma a submissão.
- Expected results:
  - O sistema regista a candidatura com sucesso.
  - O estado da candidatura muda para "submitted".
  - O sistema apresenta confirmação da submissão.
- Notes: Cobre o fluxo principal do UC-03.

---

## TC-005 — Validação de elegibilidade com beneficiário não elegível (Negative/Alternative)
- Type: System
- Priority: H
- Related requirements: REQ-004
- Preconditions: Existe um beneficiário registado que não cumpre os critérios de elegibilidade; o administrador está autenticado.
- Test data: Beneficiário ID=BEN-002 (critérios de elegibilidade não satisfeitos)
- Steps:
  1. O administrador consulta a lista de beneficiários.
  2. O administrador seleciona o beneficiário BEN-002.
  3. O sistema verifica a elegibilidade.
- Expected results:
  - O sistema marca o beneficiário como "não elegível".
  - O sistema bloqueia a atribuição de itens.
  - O sistema apresenta mensagem informando que o beneficiário não cumpre os critérios.
- Notes: Cobre exceção E1 do UC-05.

---

## TC-006 — Atribuição de itens doados com stock insuficiente (Alternative Flow)
- Type: System
- Priority: M
- Related requirements: REQ-005, REQ-004
- Preconditions: O beneficiário existe, está validado e é elegível; o administrador tem permissões; o stock disponível é inferior à quantidade solicitada.
- Test data: Beneficiário ID=BEN-001 (elegível), Item="Kit alimentar" (stock=1, quantidade solicitada=5)
- Steps:
  1. O administrador seleciona o beneficiário BEN-001.
  2. O sistema confirma elegibilidade.
  3. O administrador seleciona 5 unidades do item "Kit alimentar" (apenas 1 disponível).
  4. O sistema deteta stock insuficiente.
- Expected results:
  - O sistema informa que o stock é insuficiente para a quantidade solicitada.
  - O sistema propõe atribuição parcial (1 unidade disponível).
  - O administrador pode aceitar a atribuição parcial ou cancelar.
- Notes: Cobre fluxo alternativo A2 do UC-05.

---

## TC-007 — Bloqueio de acesso a utilizador sem permissões (Negative)
- Type: System
- Priority: H
- Related requirements: NFR-004, REQ-007
- Preconditions: Um utilizador com role "Gestor" (sem permissões de administrador) está autenticado.
- Test data: Utilizador com role="Gestor", ação tentada="Atribuir itens doados"
- Steps:
  1. O utilizador com role "Gestor" tenta aceder à funcionalidade "Atribuir itens doados".
  2. O sistema verifica as permissões do utilizador.
- Expected results:
  - O sistema bloqueia o acesso à funcionalidade.
  - O sistema apresenta mensagem de erro indicando permissões insuficientes.
  - O sistema regista a tentativa de acesso inválido nos logs (com timestamp e identificação do utilizador).
- Notes: Cobre exceção E2 do UC-05 e critérios de REQ-008 (AC-2, AC-3).

---

## TC-008 — Tempo de resposta dentro do limite (Boundary)
- Type: Performance
- Priority: H
- Related requirements: NFR-001
- Preconditions: O sistema está em condições normais de carga (até 100 utilizadores simultâneos).
- Test data: 100 pedidos concorrentes de consulta de beneficiários
- Steps:
  1. Configurar um cenário com 100 utilizadores simultâneos.
  2. Cada utilizador executa uma consulta de lista de beneficiários.
  3. Medir o tempo de resposta de cada pedido.
- Expected results:
  - 95% dos pedidos (≥ 95 de 100) recebem resposta em ≤ 2 segundos.
  - Nenhum pedido excede 5 segundos de tempo de resposta.
  - O sistema não apresenta erros de timeout.
- Notes: Boundary test — valida o limiar exato de NFR-001 (≤ 2s para 95% dos pedidos).

---

## TC-009 — Registo de ações críticas nos logs de auditoria
- Type: Integration
- Priority: H
- Related requirements: REQ-007, NFR-004
- Preconditions: O administrador está autenticado; o sistema de logs está ativo.
- Test data: Administrador ID=ADM-001, Beneficiário ID=BEN-001, Item="Kit alimentar"
- Steps:
  1. O administrador atribui um item ao beneficiário BEN-001.
  2. Consultar os logs do sistema após a atribuição.
- Expected results:
  - O sistema regista a atribuição nos logs.
  - O registo inclui: identificação do utilizador (ADM-001), timestamp da ação e descrição da operação.
  - O log é consultável e persistente.
- Notes: Valida que as ações críticas são auditáveis conforme REQ-007 e NFR-006.

---

## TC-010 — Registo de beneficiário com campos opcionais vazios (Alternative Flow)
- Type: System
- Priority: M
- Related requirements: REQ-001
- Preconditions: O utilizador está autenticado como Gestor de Beneficiários.
- Test data: Nome="Maria Costa", NIF="987654321", Email="maria@email.com", Telefone="" (vazio, campo opcional)
- Steps:
  1. O utilizador acede à opção "Criar beneficiário".
  2. O utilizador preenche apenas os campos obrigatórios, deixando campos opcionais vazios.
  3. O utilizador confirma o registo.
- Expected results:
  - O sistema aceita o registo sem erro.
  - O beneficiário é guardado com ID único.
  - Os campos opcionais ficam vazios ou com valor por defeito.
- Notes: Cobre fluxo alternativo A2 do UC-01.
