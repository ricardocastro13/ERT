# REM v1 — Lab 4

---

## REQ-001 — Registar beneficiário
- Stakeholder: Gestor
- Description: o sistema deve permitir registar beneficiários
- Objective: garantir gestão de beneficiários
- Type: FR
- Priority: H
- CSF: CSF-1
- Acceptance Criteria:
  - sistema permite inserir dados
  - sistema guarda beneficiário
- Validation: Teste
- Variant impact: No

Preconditions:
- utilizador autenticado

Postconditions:
- beneficiário registado

---

## REQ-002 — Validar dados do beneficiário
- Stakeholder: Sistema
- Description: validar dados introduzidos
- Objective: garantir dados corretos
- Type: FR
- Priority: H
- CSF: CSF-1
- Acceptance Criteria:
  - sistema valida campos obrigatórios
  - sistema rejeita dados inválidos
- Validation: Teste
- Variant impact: No

---

## REQ-003 — Submeter candidatura
- Stakeholder: Gestor
- Description: permitir submissão de candidatura
- Objective: gerir candidaturas
- Type: FR
- Priority: H
- CSF: CSF-1
- Preconditions:
- candidatura criada

Postconditions:
- candidatura submetida
- Acceptance Criteria:
  - sistema permite submissão
  - sistema confirma envio
- Validation: Demo
- Variant impact: No

---

## REQ-004 — Validar elegibilidade
- Stakeholder: Sistema
- Description: verificar elegibilidade do beneficiário
- Objective: garantir atribuições corretas
- Type: FR
- Priority: H
- CSF: CSF-1
- Acceptance Criteria:
  - sistema verifica critérios
  - sistema marca elegível/não elegível
- Validation: Teste
- Variant impact: No

Preconditions:
- beneficiário registado

Postconditions:
- estado atualizado

---

## REQ-008 — Restringir acesso (VARIANT)
- Stakeholder: Administrador
- Description: controlar acessos ao sistema
- Objective: segurança
- Type: NFR
- Priority: H
- CSF: CSF-2
- Acceptance Criteria:
  - acesso restrito por roles
  - acessos inválidos bloqueados
- Validation: Teste
- Variant impact: Yes

---

## REQ-009 — Registar ações (VARIANT)
- Stakeholder: Administrador
- Description: registar ações no sistema
- Objective: auditoria
- Type: NFR
- Priority: H
- CSF: CSF-3
- Acceptance Criteria:
  - sistema guarda logs
  - logs incluem utilizador e ação
- Validation: Review
- Variant impact: Yes

---

## REQ-010 — Gestão de permissões (VARIANT)
- Stakeholder: Administrador
- Description: gerir permissões de utilizadores
- Objective: segurança
- Type: NFR
- Priority: M
- CSF: CSF-2
- Acceptance Criteria:
  - sistema permite definir roles
  - sistema aplica permissões
- Validation: Teste
- Variant impact: Yes

---

## REQ-011 — Consultar histórico
- Stakeholder: Administrador
- Description: consultar histórico de operações
- Objective: auditoria
- Type: FR
- Priority: M
- CSF: CSF-3
- Acceptance Criteria:
  - sistema mostra histórico
  - sistema permite filtros
- Validation: Demo
- Variant impact: No
