# Traceability — REQ → AC → Test Cases / BDD (Lab 10)

| Requirement | Acceptance Criteria | Test Cases | BDD Scenario |
|---|---|---|---|
| REQ-001 — Registar beneficiário (FR) | AC-1: O sistema permite inserir dados obrigatórios; AC-2: O sistema valida campos obrigatórios; AC-3: O sistema guarda o beneficiário com ID único | TC-001, TC-002, TC-010 | Feature: Gestão de beneficiários / Scenario: Happy path — Registo de beneficiário com dados válidos |
| REQ-002 — Validar dados do beneficiário (FR) | AC-1: O sistema valida campos obrigatórios; AC-2: O sistema rejeita dados inválidos e impede submissão | TC-001, TC-002, TC-003 | Feature: Gestão de beneficiários / Scenario: Negative path — Registo com campos obrigatórios em falta; Scenario: Negative path — Registo com dados inválidos |
| REQ-003 — Submeter candidatura (FR) | AC-1: O sistema permite submissão; AC-2: O sistema altera estado para "submitted"; AC-3: O sistema confirma submissão | TC-004 | — |
| REQ-004 — Validar elegibilidade (FR) | AC-1: O sistema verifica critérios de elegibilidade; AC-2: O sistema marca como não elegível quando critérios não são cumpridos | TC-005, TC-006 | Feature: Gestão de beneficiários / Scenario: Alternative flow — Atribuição de itens com stock insuficiente |
| REQ-005 — Atribuir itens doados (FR, variant) | AC-1: O sistema permite selecionar itens disponíveis; AC-2: O sistema regista atribuição; AC-3: O sistema atualiza stock após atribuição | TC-006 | Feature: Gestão de beneficiários / Scenario: Alternative flow — Atribuição de itens com stock insuficiente |
| REQ-007 — Registar ações (FR, variant) | AC-1: O sistema regista atribuições nos logs; AC-2: O sistema regista tentativas falhadas; AC-3: O sistema guarda timestamp e utilizador | TC-007, TC-009 | Feature: Gestão de beneficiários / Scenario: Negative path — Bloqueio de acesso sem permissões |
| NFR-001 — Tempo de resposta ≤ 2s (NFR) | AC-1: 95% dos pedidos respondem em ≤ 2 segundos; AC-2: Nenhum pedido excede 5 segundos em condições normais | TC-008 | — |
| NFR-004 — Acesso restrito a utilizadores autorizados (NFR) | AC-1: Apenas utilizadores autorizados acedem a dados sensíveis; AC-2: Acessos não autorizados são bloqueados e registados | TC-007 | Feature: Gestão de beneficiários / Scenario: Negative path — Bloqueio de acesso sem permissões |

## Notes
- REQ-005 and REQ-007 are variant-driven requirements (Segurança/Auditoria).
- NFR-001 and NFR-004 are the two required non-functional requirements.
- Each REQ maps to at least 2 AC items and at least 1 TC or 1 BDD Scenario.
