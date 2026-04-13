# Acceptance Criteria — Lab 7

## REQ-001 — Registar beneficiário
- AC-1: O sistema deve permitir inserir dados obrigatórios
- AC-2: O sistema deve validar campos obrigatórios
- AC-3: O sistema deve guardar o beneficiário com ID único

---

## REQ-002 — Validar dados do beneficiário (Given/When/Then)
- Given que o utilizador preenche o formulário
- When submete dados inválidos
- Then o sistema deve mostrar erro e impedir submissão

---

## REQ-003 — Submeter candidatura
- AC-1: O sistema deve permitir submissão
- AC-2: O sistema deve alterar estado para “submitted”
- AC-3: O sistema deve confirmar submissão

---

## REQ-004 — Validar elegibilidade (Given/When/Then)
- Given que o beneficiário existe
- When não cumpre critérios
- Then o sistema deve marcar como não elegível

---

## REQ-008 — Restringir acesso (variant)
- AC-1: Apenas administradores podem atribuir itens
- AC-2: Utilizadores sem permissão devem ser bloqueados
- AC-3: Tentativas inválidas devem ser registadas

---

## REQ-009 — Registar ações (variant)
- AC-1: O sistema deve registar atribuições
- AC-2: O sistema deve registar tentativas falhadas
- AC-3: O sistema deve guardar timestamp e utilizador
