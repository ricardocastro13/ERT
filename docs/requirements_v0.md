# Requirements v0 — Lab 2 (AMS)

| Item | Requirement | Type | Stakeholder | Priority | Variant? |
|---:|---|---|---|---|---|
| 1 | O sistema deve permitir registar um beneficiário com dados obrigatórios | FR | Gestor | H | No |
| 2 | O sistema deve validar os dados do beneficiário antes de guardar | FR | Sistema | H | No |
| 3 | O sistema deve permitir submeter uma candidatura | FR | Gestor | H | No |
| 4 | O sistema deve validar a elegibilidade com base em critérios definidos | FR | Sistema | H | No |
| 5 | O sistema deve permitir atribuir itens doados a beneficiários | FR | Administrador | H | Yes |
| 6 | O sistema deve restringir o acesso apenas a utilizadores autorizados | NFR | Administrador | H | Yes |
| 7 | O sistema deve registar todas as ações críticas no sistema | NFR | Administrador | H | Yes |
| 8 | O sistema deve permitir listar doações | FR | Gestor | M | No |
| 9 | O sistema deve permitir consultar o estado do beneficiário | FR | Gestor | M | No |
| 10 | O sistema deve guardar ações com timestamp e utilizador | NFR | Administrador | H | Yes |

---

## Ambiguity rewrite (min. 5)

1) Original: "O sistema deve ser seguro"  
   Rewritten: "O sistema deve restringir o acesso com base em roles e bloquear acessos não autorizados"

2) Original: "O sistema deve ser rápido"  
   Rewritten: "O sistema deve responder em menos de 2 segundos em condições normais"

3) Original: "O sistema deve validar dados"  
   Rewritten: "O sistema deve validar campos obrigatórios e rejeitar dados inválidos"

4) Original: "O sistema deve registar ações"  
   Rewritten: "O sistema deve registar ações críticas com utilizador e timestamp"

5) Original: "O sistema deve controlar acessos"  
   Rewritten: "O sistema deve permitir apenas utilizadores autorizados executar ações sensíveis"
