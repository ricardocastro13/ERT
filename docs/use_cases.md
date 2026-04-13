# Use Cases — Lab 5

## UC-01 — Registar beneficiário
- Primary actor: Gestor de Beneficiários
- Supporting actors: Sistema Externo de Validação
- Goal: registar um novo beneficiário no sistema
- Preconditions: o utilizador está autenticado
- Trigger: o utilizador inicia o registo de beneficiário
- Postconditions (success): o beneficiário é guardado no sistema
- Postconditions (failure/cancel): os dados não são guardados
- Related requirements: REQ-001, REQ-002

### Main flow (happy path)
1. O utilizador inicia o registo.
2. O sistema apresenta o formulário.
3. O utilizador introduz os dados.
4. O sistema valida os dados.
5. O utilizador confirma.
6. O sistema guarda o beneficiário.

### Alternative flows
A1. O utilizador guarda como rascunho → o sistema guarda dados parciais.  
A2. Campos opcionais não preenchidos → o sistema aceita o registo.

### Exceptions / errors
E1. Campos obrigatórios em falta → o sistema bloqueia o registo.  
E2. Dados inválidos → o sistema pede correção.

---

## UC-03 — Submeter candidatura
- Primary actor: Gestor de Beneficiários
- Supporting actors: -
- Goal: submeter candidatura no sistema
- Preconditions: beneficiário registado
- Trigger: o utilizador decide submeter candidatura
- Postconditions (success): candidatura submetida com sucesso
- Postconditions (failure/cancel): candidatura não é submetida
- Related requirements: REQ-003

### Main flow (happy path)
1. O utilizador seleciona submeter candidatura.
2. O sistema valida os dados do beneficiário.
3. O utilizador confirma a submissão.
4. O sistema regista a candidatura.

### Alternative flows
A1. O utilizador cancela a operação → o sistema não submete a candidatura.

### Exceptions / errors
E1. Dados inválidos → o sistema impede a submissão e apresenta erro.

---

## UC-05 — Atribuir itens doados
- Primary actor: Administrador
- Supporting actors: Sistema Externo de Validação
- Goal: atribuir itens a um beneficiário
- Preconditions: o beneficiário existe e está validado
- Trigger: o administrador seleciona um beneficiário
- Postconditions (success): os itens são atribuídos
- Postconditions (failure/cancel): nenhuma alteração é feita
- Related requirements: REQ-004, REQ-008, REQ-009

### Main flow (happy path)
1. O administrador consulta beneficiários.
2. O sistema apresenta lista.
3. O administrador seleciona um beneficiário.
4. O sistema verifica elegibilidade.
5. O administrador escolhe itens.
6. O sistema valida stock.
7. O administrador confirma.
8. O sistema regista atribuição.

### Alternative flows
A1. O administrador cancela → o sistema não altera nada.

### Exceptions / errors
E1. Beneficiário não elegível → o sistema bloqueia ação.
