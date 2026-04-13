# Objectives & Critical Success Factors (CSFs) — Lab 4

## Variant
- Variant number: 1
- Persona: Administrador do sistema
- Key constraint focus: Segurança e Auditoria

---

## Objectives (3)

### OBJ-1 — Garantir gestão eficiente de beneficiários
- Description (why/outcome): permitir registo e gestão correta de beneficiários
- Stakeholders impacted: Gestor de Beneficiários
- Success signal (how we know): beneficiários registados e consultáveis
- Variant-driven: No

### OBJ-2 — Assegurar controlo de acessos seguro (VARIANT)
- Description: garantir que apenas utilizadores autorizados acedem a funcionalidades críticas
- Stakeholders impacted: Administrador
- Success signal: acessos não autorizados são bloqueados
- Variant-driven: Yes

### OBJ-3 — Garantir rastreabilidade das operações (VARIANT)
- Description: permitir auditoria completa das ações realizadas no sistema
- Stakeholders impacted: Administrador, Auditor
- Success signal: todas as ações críticas ficam registadas
- Variant-driven: Yes

---

## CSFs (3)

### CSF-1 — Registo e validação de beneficiários (OBJ-1)
- Linked requirements:
  - REQ-001
  - REQ-002
  - REQ-003

### CSF-2 — Controlo de acessos (OBJ-2)
- Linked requirements:
  - REQ-008
  - REQ-009
  - REQ-010

### CSF-3 — Auditoria e logs (OBJ-3)
- Linked requirements:
  - REQ-009
  - REQ-011
  - REQ-012
