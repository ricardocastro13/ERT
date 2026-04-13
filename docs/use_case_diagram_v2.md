# Use Case Diagram v2 — Lab 6

## System boundary
- System name: AMS — Aid Management System
- Slice covered: Intake & Discovery for beneficiary registration, validation and item allocation

## Actors (2–4)
- A1: Gestor de Beneficiários
- A2: Doador
- A3: Sistema Externo de Validação
- A4: Administrador

## Use cases (min. 6)
- UC-01: Registar beneficiário
- UC-02: Validar dados do beneficiário
- UC-03: Submeter candidatura
- UC-04: Validar elegibilidade
- UC-05: Atribuir itens doados
- UC-06: Consultar estado do beneficiário

## Notes on refinement
- The diagram focuses only on the Intake & Discovery slice of AMS.
- Actors were refined as external roles and systems.
- Use case names follow the verb + object rule.
- `<<include>>` is used only for mandatory and reusable behavior.
- `<<extend>>` is used only for optional or conditional behavior.

## Diagram file
- Path: `docs/diagrams/use_case_diagram_v2.puml`
