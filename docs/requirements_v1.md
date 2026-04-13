
# Requirements v1 — Lab 3

## REQ-001 — Registar beneficiário
- Description: permitir registo de beneficiários com dados obrigatórios
- Objective: garantir gestão de beneficiários
- Acceptance Criteria:
  - sistema permite inserir dados obrigatórios
  - sistema guarda beneficiário com ID único
- Variant impact: No

---

## REQ-002 — Validar dados do beneficiário
- Description: validar dados antes de guardar
- Objective: garantir qualidade dos dados
- Acceptance Criteria:
  - sistema valida campos obrigatórios
  - sistema rejeita dados inválidos
- Variant impact: No

---

## REQ-003 — Submeter candidatura
- Description: permitir submissão de candidatura
- Objective: gerir candidaturas
- Acceptance Criteria:
  - sistema permite submissão
  - sistema confirma submissão
- Variant impact: No

---

## REQ-004 — Validar elegibilidade
- Description: verificar critérios de elegibilidade
- Objective: garantir atribuição correta
- Acceptance Criteria:
  - sistema verifica critérios
  - sistema marca elegível/não elegível
- Variant impact: No

---

## REQ-005 — Atribuir itens doados
- Description: permitir atribuição de itens a beneficiários
- Objective: gerir distribuição de recursos
- Acceptance Criteria:
  - sistema permite selecionar itens
  - sistema regista atribuição
- Variant impact: Yes

---

## REQ-006 — Restringir acesso
- Description: controlar acesso por roles
- Objective: garantir segurança do sistema
- Acceptance Criteria:
  - acesso restrito por permissões
  - acessos inválidos bloqueados
- Variant impact: Yes

---

## REQ-007 — Registar ações
- Description: registar ações críticas no sistema
- Objective: garantir auditoria
- Acceptance Criteria:
  - sistema guarda logs
  - logs incluem utilizador e timestamp
- Variant impact: Yes

---

## NFRs

- NFR-001: Tempo de resposta ≤ 2 segundos para 95% dos pedidos  
- NFR-002: Disponibilidade do sistema ≥ 99% mensal  
- NFR-003: Logs devem ser guardados durante 12 meses  
- NFR-004: Apenas utilizadores autorizados podem aceder a dados sensíveis  
- NFR-005: Dados devem ser validados antes de processamento  
- NFR-006: Sistema deve registar todas as operações críticas  
