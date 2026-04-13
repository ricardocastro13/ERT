# Requirements Validation — Lab 7

## Participants / roles
- Client/Stakeholders: Equipa AMS
- DevTeam: Equipa AMS
- Facilitator: Ricardo
- Scribe: Ricardo
- Reviewer: Equipa
- Tester: Equipa

## Selected requirements (min. 6)
- REQ-001 (Variant impact: No)
- REQ-002 (Variant impact: No)
- REQ-003 (Variant impact: No)
- REQ-004 (Variant impact: No)
- REQ-008 (Variant impact: Yes)
- REQ-009 (Variant impact: Yes)

## Variant-driven validation questions (min. 3)
1. Como é garantido que apenas utilizadores autorizados executam ações críticas?
2. Que ações devem ser obrigatoriamente registadas para auditoria?
3. Como o sistema reage a tentativas de acesso não autorizado?

---

## Validation results

### REQ-001 — Registar beneficiário
- Status: Needs rewrite
- Issues found:
  - descrição vaga
  - não define campos obrigatórios
- Proposed fix:
  - especificar dados obrigatórios (nome, escola, etc.)
- Expected evidence:
  - Demo do formulário + validação

---

### REQ-002 — Validar dados do beneficiário
- Status: Valid
- Issues found:
  - falta detalhe de regras
- Proposed fix:
  - adicionar regras de validação
- Expected evidence:
  - Testes de validação

---

### REQ-003 — Submeter candidatura
- Status: Valid
- Issues found:
  - não define estados
- Proposed fix:
  - adicionar estados (draft, submitted)
- Expected evidence:
  - Demonstração do fluxo

---

### REQ-004 — Validar elegibilidade
- Status: Needs rewrite
- Issues found:
  - critérios não definidos
- Proposed fix:
  - listar critérios (pontos, escola, etc.)
- Expected evidence:
  - Teste com dados válidos e inválidos

---

### REQ-008 — Restringir acesso
- Status: Valid
- Issues found:
  - não define papéis
- Proposed fix:
  - definir roles (admin, gestor)
- Expected evidence:
  - Teste de permissões

---

### REQ-009 — Registar ações (audit log)
- Status: Valid
- Issues found:
  - não define quais ações
- Proposed fix:
  - listar ações críticas (atribuição, login, etc.)
- Expected evidence:
  - Verificação de logs 
