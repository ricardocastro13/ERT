# Requirements v0 — Lab 2 (AMS)

| Item | Requirement | Type | Stakeholder | Priority | Variant? |
|---:|---|---|---|---|---|
| 1 | The system shall allow registering a beneficiary with required data | FR | Gestor | H | No |
| 2 | The system shall validate beneficiary data before saving | FR | Sistema | H | No |
| 3 | The system shall allow submitting an application | FR | Gestor | H | No |
| 4 | The system shall validate eligibility based on defined criteria | FR | Sistema | H | No |
| 5 | The system shall allow assigning donated items to beneficiaries | FR | Administrador | H | Yes |
| 6 | The system shall restrict access to authorized users only | NFR | Administrador | H | Yes |
| 7 | The system shall log all critical actions in the system | NFR | Administrador | H | Yes |
| 8 | The system shall allow listing donations | FR | Gestor | M | No |
| 9 | The system shall allow consulting beneficiary status | FR | Gestor | M | No |
| 10 | The system shall store user actions with timestamp and user ID | NFR | Administrador | H | Yes |

---

## Ambiguity rewrite (min. 5)

1) Original: "The system should be secure"  
   Rewritten: "The system shall restrict access based on user roles and block unauthorized actions"

2) Original: "The system should be fast"  
   Rewritten: "The system shall respond to user actions in less than 2 seconds under normal load"

3) Original: "The system should validate data"  
   Rewritten: "The system shall validate mandatory fields and reject invalid input before submission"

4) Original: "The system should track actions"  
   Rewritten: "The system shall log all critical actions including user ID and timestamp"

5) Original: "The system should control access"  
   Rewritten: "The system shall allow only authorized roles to perform sensitive operations"
