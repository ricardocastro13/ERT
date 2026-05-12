# Test Plan — Lab 10

## 1) Scope
- Slice covered: Gestão de beneficiários e atribuição de itens doados (UC-01, UC-03, UC-05), incluindo a variante Segurança/Auditoria.
- Out of scope: Módulo de relatórios, integrações com sistemas externos de validação, interface mobile, gestão de inventário avançada (beyond stock check), configuração de roles e permissões (administration UI).

## 2) Test strategy (static + dynamic)

### Static testing (reviews)
- What we review: Clareza dos requisitos (REQ-001 a REQ-007, NFR-001, NFR-004), qualidade dos critérios de aceitação, completude dos test cases (TC-001 a TC-010), e clareza dos cenários Gherkin.
- Review checklist:
  1. Cada REQ tem uma descrição não ambígua e mensurável?
  2. Cada AC é verificável (sem termos vagos como "deve funcionar corretamente")?
  3. Cada TC tem passos reprodutíveis e resultados esperados observáveis?
  4. Os cenários Gherkin usam Given/When/Then sem lógica implícita?
  5. A cobertura inclui happy path, alternative, negative e boundary?
- Key issues found during review:
  - REQ-001 AC original não especificava quais campos são obrigatórios.
  - REQ-007 AC não definia período de retenção dos logs.
  - REQ-004 AC não especificava quais critérios de elegibilidade concretos são verificados.

### Dynamic testing (planned execution)

| Level | What we test | Examples | Evidence |
|---|---|---|---|
| Unit | Regras de validação, lógica de elegibilidade | Validação de NIF (formato, comprimento), validação de email, verificação de campos obrigatórios | Testes unitários planeados (tests/unit/) |
| Integration | Interação entre componentes | Registo de beneficiário → persistência em BD → consulta; Atribuição de item → atualização de stock → registo em log | Testes de integração planeados (tests/integration/) |
| System | Fluxos end-to-end da slice | UC-01 completo (registo), UC-05 completo (atribuição com verificação de elegibilidade e stock) | Notas de execução manual (docs/evidence/) |
| Acceptance (BDD) | Comportamento vs AC | Cenários Gherkin: registo válido, registo inválido, bloqueio de acesso, atribuição parcial | bdd/features/lab9.feature |

## 3) TDD plan (at least 2 candidates)

- Candidate 1 — Validação de dados do beneficiário (REQ-002):
  - Rule: O NIF deve ter exatamente 9 dígitos numéricos. O email deve conter "@" e um domínio válido. O nome não pode estar vazio nem conter apenas espaços.
  - TDD cycle: Escrever testes para inputs válidos e inválidos antes de implementar a função de validação.

- Candidate 2 — Verificação de elegibilidade (REQ-004):
  - Rule: Um beneficiário é elegível se tem candidatura com estado "submitted" e cumpre todos os critérios definidos (e.g., residência na área, rendimento abaixo do limiar).
  - TDD cycle: Escrever testes para beneficiário elegível, não elegível (critério em falta), e caso limite (limiar exato) antes de implementar a lógica.

- Why TDD is suitable: Ambas as regras são funções puras com inputs/outputs bem definidos, sem dependências externas, e com múltiplos edge cases que beneficiam de testes escritos antecipadamente.

## 4) BDD plan (what behaviors become scenarios)

- Feature: Gestão de beneficiários e atribuição de itens doados
- Scenarios (existentes em bdd/features/lab9.feature):
  1. Happy path — Registo de beneficiário com dados válidos
  2. Negative path — Registo com campos obrigatórios em falta
  3. Negative path — Registo com dados inválidos
  4. Alternative flow — Atribuição de itens com stock insuficiente
  5. Negative path — Bloqueio de acesso sem permissões
- Links to REQs: REQ-001, REQ-002, REQ-004, REQ-005, NFR-004, REQ-007

## 5) Coverage goals

- Happy path: TC-001 (registo de beneficiário), TC-004 (submissão de candidatura) — cobrem os fluxos principais de UC-01 e UC-03.
- Alternative flows: TC-006 (stock insuficiente → atribuição parcial), TC-010 (campos opcionais vazios) — cobrem variações legítimas do fluxo.
- Negative/error tests: TC-002 (campos obrigatórios em falta), TC-003 (dados inválidos), TC-005 (beneficiário não elegível), TC-007 (acesso sem permissões) — cobrem 4 cenários de erro distintos.
- Boundary tests: TC-008 (tempo de resposta ≤ 2s para 95% dos pedidos) — valida o limiar exato da NFR-001.

## 6) NFR validation approach

- NFR-001 (Tempo de resposta ≤ 2 segundos para 95% dos pedidos):
  - How we verify: Teste de carga com 100 pedidos concorrentes (TC-008). Medição com ferramenta de load testing (e.g., k6, JMeter). Critério de aceitação: ≥ 95% dos pedidos respondidos em ≤ 2s. Evidência: relatório de performance guardado em docs/evidence/.

- NFR-004 (Apenas utilizadores autorizados podem aceder a dados sensíveis):
  - How we verify: Teste manual de controlo de acesso (TC-007). Tentativa de acesso com role insuficiente. Verificação de que o sistema bloqueia e regista a tentativa. Evidência: screenshots e logs guardados em docs/evidence/.

## 7) Evidence recording and responsibilities

- Where results are stored (repo paths):
  - Test cases: docs/test_cases.md
  - BDD feature files: bdd/features/lab9.feature
  - Traceability: docs/traceability_req_ac_tc.md
  - Evidence (screenshots, logs, reports): docs/evidence/
  - Unit/Integration tests (quando implementados): tests/unit/, tests/integration/

- Who maintains traceability: O responsável de QA da equipa atualiza a matriz de rastreabilidade a cada sprint/iteração.

- How updates are tracked: Cada alteração aos test cases, AC ou DoD é commitada no GitHub com mensagem de commit referenciando o REQ ou TC afetado. Pull requests incluem revisão da rastreabilidade.
