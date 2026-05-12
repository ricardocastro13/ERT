# AC & DoD Updates — Lab 10

## Acceptance Criteria improvements (min. 3)

### Item 1 (variant-driven)
- Requirement: REQ-007 — Registar ações
- Before:
  - AC-1: O sistema deve registar atribuições
  - AC-2: O sistema deve registar tentativas falhadas
  - AC-3: O sistema deve guardar timestamp e utilizador
- After:
  - AC-1: O sistema deve registar todas as atribuições de itens nos logs, incluindo: ID do administrador, ID do beneficiário, lista de itens atribuídos e timestamp (formato ISO 8601).
  - AC-2: O sistema deve registar todas as tentativas de acesso não autorizado, incluindo: ID do utilizador, funcionalidade tentada, motivo do bloqueio e timestamp.
  - AC-3: Os logs devem ser persistentes, consultáveis por data e utilizador, e retidos durante pelo menos 12 meses (conforme NFR-003).
- Why changed:
  - Os AC originais eram vagos ("registar atribuições" não especifica que dados). Durante a criação do TC-009, foi necessário definir exatamente que informação o log deve conter para poder verificar o resultado esperado. A adição do período de retenção alinha com NFR-003 e torna o critério mensurável.

### Item 2
- Requirement: REQ-002 — Validar dados do beneficiário
- Before:
  - AC-1: O sistema valida campos obrigatórios
  - AC-2: O sistema rejeita dados inválidos
- After:
  - AC-1: O sistema valida que os campos Nome, NIF e Email estão preenchidos antes de permitir submissão. Campos vazios ou com apenas espaços são rejeitados.
  - AC-2: O sistema valida o formato dos dados: NIF deve conter exatamente 9 dígitos numéricos; Email deve conter "@" seguido de domínio válido. Dados que não cumprem o formato são rejeitados com mensagem de erro específica por campo.
  - AC-3 (novo): Mensagens de erro devem identificar claramente o campo problemático e o tipo de erro (campo em falta vs. formato inválido).
- Why changed:
  - "Valida campos obrigatórios" não especificava quais campos são obrigatórios. "Rejeita dados inválidos" não definia o que constitui dados inválidos. Ao escrever TC-002 e TC-003, foi impossível definir dados de teste sem conhecer as regras de validação concretas. O AC-3 foi adicionado porque os cenários BDD negative path exigiam verificar mensagens de erro específicas.

### Item 3
- Requirement: REQ-004 — Validar elegibilidade
- Before:
  - AC-1: O sistema verifica critérios
  - AC-2: O sistema marca elegível/não elegível
- After:
  - AC-1: O sistema verifica os seguintes critérios de elegibilidade: (a) o beneficiário tem candidatura com estado "submitted"; (b) o beneficiário cumpre os critérios definidos pelo programa (e.g., residência na área, rendimento abaixo do limiar configurado).
  - AC-2: O sistema marca o beneficiário como "elegível" se todos os critérios forem satisfeitos, ou "não elegível" com indicação do(s) critério(s) não cumprido(s).
  - AC-3 (novo): Quando o beneficiário é marcado como "não elegível", o sistema impede a atribuição de itens e apresenta mensagem com o motivo.
- Why changed:
  - "Verifica critérios" era completamente ambíguo — não havia forma de escrever um teste sem saber quais critérios. O TC-005 exigia definir um beneficiário "não elegível", o que é impossível sem critérios concretos. O AC-3 foi adicionado para tornar o bloqueio verificável (testado em TC-005).

### Item 4
- Requirement: REQ-001 — Registar beneficiário
- Before:
  - AC-1: O sistema deve permitir inserir dados obrigatórios
  - AC-2: O sistema deve guardar o beneficiário com ID único
- After:
  - AC-1: O sistema apresenta um formulário com os campos obrigatórios (Nome, NIF, Email) e campos opcionais (Telefone, Morada). O utilizador pode submeter o formulário apenas quando todos os campos obrigatórios estão preenchidos.
  - AC-2: Após submissão válida, o sistema guarda o beneficiário com um ID único gerado automaticamente (formato: BEN-XXXX) e apresenta mensagem de confirmação com o ID atribuído.
  - AC-3 (novo): O registo é aceite mesmo que campos opcionais fiquem vazios.
- Why changed:
  - "Inserir dados obrigatórios" não listava quais dados são obrigatórios. O TC-010 (alternative flow com campos opcionais) revelou que não havia distinção clara entre campos obrigatórios e opcionais. O AC-3 foi adicionado para cobrir explicitamente o fluxo alternativo A2 do UC-01.

## DoD updates (min. 2)

1. Proposed DoD change:
   - Before: "Testes executados e aprovados."
   - After: "Todos os test cases associados (TC-###) foram executados e aprovados. A matriz de rastreabilidade (docs/traceability_req_ac_tc.md) confirma cobertura de: pelo menos 1 happy path, 1 alternative flow, 2 negative/error e 1 boundary test por feature entregue."
   - Why: A formulação original era impossível de verificar — não definia quais testes nem que nível de cobertura. Durante o Lab 9, constatámos que sem cobertura mínima definida, era possível "passar" com apenas happy path tests. A nova formulação garante cobertura verificável e rastreável.

2. Proposed DoD change:
   - Before: "Código revisto por pelo menos 1 membro da equipa."
   - After: "Código revisto por pelo menos 1 membro da equipa. A revisão inclui verificação de que: (a) os logs de auditoria são gerados para todas as operações críticas (conforme REQ-007); (b) o controlo de acesso está implementado nas funcionalidades restritas (conforme NFR-004). Revisão documentada via pull request com checklist de segurança."
   - Why: A revisão de código sem critérios de segurança específicos não garante conformidade com a variante Segurança/Auditoria. O TC-007 e TC-009 demonstraram que os aspetos de logging e controlo de acesso são frequentemente esquecidos na implementação. Esta atualização integra a verificação de segurança no processo de revisão.

3. Proposed DoD change:
   - Before: (não existia explicitamente)
   - After: "Critérios de aceitação (AC) revistos e atualizados se os testes revelarem ambiguidade, informação em falta ou gaps de cobertura. Alterações documentadas em docs/ac_dod_updates.md com justificação."
   - Why: O processo de escrita de testes no Lab 9 revelou múltiplas ambiguidades nos AC originais (campos obrigatórios não listados, critérios de elegibilidade vagos, conteúdo dos logs não especificado). Sem esta regra no DoD, os AC permanecem desatualizados e os testes ficam baseados em suposições.
