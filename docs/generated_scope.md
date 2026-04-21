


## Selected slice
- Slice: A
- Short description: Registo de beneficiário e submissão de candidatura com validação de dados e registo de ações críticas (auditoria).

## Actors / roles
- Primary actor: Gestor de Beneficiários
- Secondary actor (optional): Administrador

## Use Cases implemented
- UC-01: Registar beneficiário
- UC-03: Submeter candidatura

## Requirements implemented (max 10)
- REQ-001 — Registar beneficiário
- REQ-002 — Validar dados do beneficiário
- REQ-003 — Submeter candidatura
- REQ-008 — Restringir acesso
- REQ-009 — Registar ações

## Variant constraints implemented (min. 2)
- Constraint 1: Apenas utilizadores autorizados podem executar ações críticas (controlo de acessos).
- Constraint 2: O sistema deve registar ações críticas (submissões e tentativas inválidas) com utilizador e timestamp (auditoria).

## Decisões de implementação
- A plataforma será desenvolvida em Java.
- A versão inicial será desktop.
- Esta decisão reduz a complexidade de infraestrutura.
- Evita, nesta fase, desenvolvimento avançado de UI/UX.
- O foco é validar o fluxo funcional e regras do sistema.

## Out of scope (explicit)
- UC-04 Validar elegibilidade
- UC-05 Atribuir itens doados
- UC-06 Consultar estado do beneficiário
- Gestão de stock
- Gestão de doadores
- Interface web
- Base de dados avançada
- Sistema completo de autenticação
- 
