# Use Cases v2 — Lab 6

## UC-01 — Registar beneficiário
- Ator principal: Gestor de Beneficiários
- Atores secundários: Sistema Externo de Validação
- Objetivo: registar um novo beneficiário no sistema
- Pré-condições: o utilizador está autenticado; o sistema está disponível
- Trigger: o utilizador seleciona a opção "criar beneficiário"
- Pós-condições (sucesso): o beneficiário é guardado no sistema com identificador único
- Pós-condições (falha/cancelamento): nenhum beneficiário é registado ou fica em rascunho
- Requisitos relacionados: REQ-001, REQ-002

### Fluxo principal (happy path)
1. O Gestor de Beneficiários inicia o registo.
2. O sistema apresenta o formulário.
3. O utilizador introduz os dados.
4. O sistema valida os campos obrigatórios.
5. O sistema verifica a consistência dos dados.
6. O utilizador confirma o registo.
7. O sistema guarda o beneficiário.
8. O sistema apresenta confirmação.

### Fluxos alternativos
A1. O utilizador guarda como rascunho → o sistema guarda dados parciais.  
A2. Campos opcionais não preenchidos → o sistema aceita o registo.

### Exceções / erros
E1. Campos obrigatórios em falta → o sistema bloqueia o registo.  
E2. Dados inválidos → o sistema pede correção.

---

## UC-05 — Atribuir itens doados
- Ator principal: Administrador
- Atores secundários: Sistema Externo de Validação
- Objetivo: atribuir itens a um beneficiário elegível
- Pré-condições: o beneficiário existe e está validado; o administrador tem permissões
- Trigger: o administrador seleciona um beneficiário
- Pós-condições (sucesso): os itens são atribuídos e registados
- Pós-condições (falha/cancelamento): nenhuma alteração é feita
- Requisitos relacionados: REQ-004, REQ-007, REQ-008, REQ-009

### Fluxo principal (happy path)
1. O administrador consulta beneficiários.
2. O sistema apresenta a lista.
3. O administrador seleciona um beneficiário.
4. O sistema verifica elegibilidade.
5. O administrador escolhe itens.
6. O sistema valida disponibilidade.
7. O administrador confirma.
8. O sistema regista a atribuição.
9. O sistema atualiza stock.
10. O sistema mostra confirmação.

### Fluxos alternativos
A1. O administrador cancela → o sistema não altera nada.  
A2. Stock insuficiente → o sistema propõe atribuição parcial.

### Exceções / erros
E1. Beneficiário não elegível → o sistema bloqueia a ação.  
E2. Utilizador sem permissões → o sistema bloqueia e regista tentativa.

## Notas da variante (obrigatório)
- Variante: Segurança / Auditoria
- O sistema impede ações sem permissões (UC-05 E2)
- O sistema regista ações críticas (logs)
