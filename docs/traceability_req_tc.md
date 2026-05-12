# Traceability — Requirements ↔ Test Cases (Lab 9)

## Selected requirements (8)
- REQ-001 — Registar beneficiário (FR)
- REQ-002 — Validar dados do beneficiário (FR)
- REQ-003 — Submeter candidatura (FR)
- REQ-004 — Validar elegibilidade (FR)
- REQ-005 — Atribuir itens doados (FR — variant)
- REQ-007 — Registar ações (FR — variant)
- NFR-001 — Tempo de resposta ≤ 2 segundos para 95% dos pedidos (NFR)
- NFR-004 — Apenas utilizadores autorizados podem aceder a dados sensíveis (NFR)

## Mapping (REQ → TC)
| Requirement (REQ-###) | Test Cases (TC-###) | Notes |
|---|---|---|
| REQ-001 | TC-001, TC-002, TC-010 | Happy path, negative (campos em falta), alternative (campos opcionais) |
| REQ-002 | TC-001, TC-002, TC-003 | Validação positiva e negativa (campos em falta, dados inválidos) |
| REQ-003 | TC-004 | Happy path de submissão de candidatura |
| REQ-004 | TC-005, TC-006 | Elegibilidade negativa e atribuição com stock insuficiente |
| REQ-005 | TC-006 | Fluxo alternativo com stock insuficiente |
| REQ-007 | TC-007, TC-009 | Registo de ações críticas e tentativas inválidas |
| NFR-001 | TC-008 | Boundary test — limiar de performance (≤ 2s, 95%) |
| NFR-004 | TC-007, TC-009 | Bloqueio de acesso sem permissões + auditoria |

## Coverage summary

| Coverage type | Test Cases |
|---|---|
| Happy path | TC-001, TC-004 |
| Alternative flow | TC-006, TC-010 |
| Negative / Error | TC-002, TC-003, TC-005, TC-007 |
| Boundary | TC-008 |
| Audit / Logging | TC-009 |
