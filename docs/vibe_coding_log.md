# Vibe Coding Log — Lab 8

## Tool used
- Codex / Lovable / Other:
- Environment/stack:

## Iteration 1 (With Claude.AI)

eu preciso que tu me fazes uma applicacao só codigo em java com os proximos requirement aue vou te mandar


A Aplicaçao é uma aplicaçao de doaçao ela faz isso :

REQ-001 — Registar beneficiário 
REQ-002 — Validar dados do beneficiário 
REQ-003 — Submeter candidatura 
REQ-008 — Restringir acesso 
REQ-009 — Registar ações !deve ser interativo com a consola

**Generated output (what appeared):**
Claro! Manda os requisitos e eu trato do código em Java. 👍

Credenciais de teste:
UtilizadorPasswordPerfiladminadmin123ADMINoperadoroper456OPERADOR

Requisitos implementados:

REQ-001 — Registar beneficiário → Menu opção 1, recolhe nome, NIF, email, telefone, idade e morada
REQ-002 — Validar dados → NIF com 9 dígitos, email com formato válido, telefone PT, idade ≥ 18, nome/morada mínimos, NIF não duplicado
REQ-003 — Submeter candidatura → Menu opção 3, liga candidatura ao beneficiário pelo ID
REQ-008 — Restringir acesso → Login obrigatório com 3 tentativas máx; as opções "Gerir Candidaturas" e "Auditoria" são bloqueadas para perfil OPERADOR
REQ-009 — Registar ações → Todas as ações (login, logout, registo, aprovação, tentativas falhadas, acessos negados) ficam registadas com timestamp e utilizador


Doacaoapp.java:

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ============================================================
//  MODELO — Beneficiário
// ============================================================
class Beneficiario {
private static int contadorId = 1;

    private int id;
    private String nome;
    private String nif;
    private String email;
    private String telefone;
    private int idade;
    private String morada;
    private String estadoCandidatura; // PENDENTE | APROVADA | REJEITADA
    private LocalDateTime dataCriacao;

    public Beneficiario(String nome, String nif, String email,
                        String telefone, int idade, String morada) {
        this.id               = contadorId++;
        this.nome             = nome;
        this.nif              = nif;
        this.email            = email;
        this.telefone         = telefone;
        this.idade            = idade;
        this.morada           = morada;
        this.estadoCandidatura = "PENDENTE";
        this.dataCriacao      = LocalDateTime.now();
    }

    // Getters
    public int    getId()                 { return id; }
    public String getNome()               { return nome; }
    public String getNif()                { return nif; }
    public String getEmail()              { return email; }
    public String getTelefone()           { return telefone; }
    public int    getIdade()              { return idade; }
    public String getMorada()             { return morada; }
    public String getEstadoCandidatura()  { return estadoCandidatura; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }

    // Setter
    public void setEstadoCandidatura(String estado) { this.estadoCandidatura = estado; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
            "┌─────────────────────────────────────────────┐\n" +
            "│  ID          : %-29d│\n" +
            "│  Nome        : %-29s│\n" +
            "│  NIF         : %-29s│\n" +
            "│  Email       : %-29s│\n" +
            "│  Telefone    : %-29s│\n" +
            "│  Idade       : %-29d│\n" +
            "│  Morada      : %-29s│\n" +
            "│  Candidatura : %-29s│\n" +
            "│  Registado em: %-29s│\n" +
            "└─────────────────────────────────────────────┘",
            id, nome, nif, email, telefone, idade, morada,
            estadoCandidatura, dataCriacao.format(fmt));
    }
}

// ============================================================
//  MODELO — Utilizador do sistema (REQ-008)
// ============================================================
class Utilizador {
private String username;
private String password;
private String perfil; // ADMIN | OPERADOR

    public Utilizador(String username, String password, String perfil) {
        this.username = username;
        this.password = password;
        this.perfil   = perfil;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPerfil()   { return perfil; }
}

// ============================================================
//  MODELO — Registo de Ação (REQ-009)
// ============================================================
class RegistoAcao {
private static int contadorLog = 1;

    private int id;
    private String utilizador;
    private String acao;
    private String detalhes;
    private LocalDateTime timestamp;

    public RegistoAcao(String utilizador, String acao, String detalhes) {
        this.id         = contadorLog++;
        this.utilizador = utilizador;
        this.acao       = acao;
        this.detalhes   = detalhes;
        this.timestamp  = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] [%s] %s — %s (detalhe: %s)",
            timestamp.format(fmt), utilizador, acao,
            acao, detalhes);
    }
}

// ============================================================
//  SERVIÇO — Validação (REQ-002)
// ============================================================
class ValidacaoService {

    public static List<String> validarBeneficiario(String nome, String nif,
            String email, String telefone, int idade, String morada) {
        List<String> erros = new ArrayList<>();

        if (nome == null || nome.trim().length() < 3)
            erros.add("Nome deve ter pelo menos 3 caracteres.");

        if (!validarNif(nif))
            erros.add("NIF inválido (deve ter 9 dígitos).");

        if (!validarEmail(email))
            erros.add("Email inválido.");

        if (!validarTelefone(telefone))
            erros.add("Telefone inválido (9 dígitos, começa por 2, 3 ou 9).");

        if (idade < 18 || idade > 120)
            erros.add("Idade deve ser entre 18 e 120 anos.");

        if (morada == null || morada.trim().length() < 5)
            erros.add("Morada deve ter pelo menos 5 caracteres.");

        return erros;
    }

    private static boolean validarNif(String nif) {
        if (nif == null) return false;
        nif = nif.trim();
        return nif.matches("\\d{9}");
    }

    private static boolean validarEmail(String email) {
        if (email == null) return false;
        return email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean validarTelefone(String tel) {
        if (tel == null) return false;
        tel = tel.trim();
        return tel.matches("[239]\\d{8}");
    }
}

// ============================================================
//  APLICAÇÃO PRINCIPAL
// ============================================================
public class DoacaoApp {

    // ── Estado global ──────────────────────────────────────
    private static Map<String, Utilizador>  utilizadores  = new HashMap<>();
    private static List<Beneficiario>       beneficiarios = new ArrayList<>();
    private static List<RegistoAcao>        auditLog      = new ArrayList<>();
    private static Utilizador               sessaoAtual   = null;
    private static Scanner                  sc            = new Scanner(System.in);

    // ── Cor ANSI (funciona na maioria dos terminais) ───────
    static final String RESET  = "\u001B[0m";
    static final String VERDE  = "\u001B[32m";
    static final String VERMELHO = "\u001B[31m";
    static final String AMARELO = "\u001B[33m";
    static final String AZUL   = "\u001B[34m";
    static final String CIANO  = "\u001B[36m";
    static final String NEGRITO = "\u001B[1m";

    // ──────────────────────────────────────────────────────
    public static void main(String[] args) {
        inicializarUtilizadores();
        mostrarBanner();
        menuLogin();
    }

    // ============================================================
    //  INICIALIZAÇÃO
    // ============================================================
    static void inicializarUtilizadores() {
        utilizadores.put("admin",    new Utilizador("admin",    "admin123",  "ADMIN"));
        utilizadores.put("operador", new Utilizador("operador", "oper456",   "OPERADOR"));
    }

    // ============================================================
    //  BANNER
    // ============================================================
    static void mostrarBanner() {
        System.out.println(AZUL + NEGRITO);
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         🤝  SISTEMA DE DOAÇÕES — v1.0  🤝        ║");
        System.out.println("║      Gestão de Beneficiários e Candidaturas      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    // ============================================================
    //  REQ-008 — RESTRINGIR ACESSO (Login)
    // ============================================================
    static void menuLogin() {
        int tentativas = 0;
        while (tentativas < 3) {
            System.out.println(CIANO + "\n─── LOGIN ───────────────────────────────────────" + RESET);
            System.out.print("  Utilizador : ");
            String user = sc.nextLine().trim();
            System.out.print("  Password   : ");
            String pass = sc.nextLine().trim();

            Utilizador u = utilizadores.get(user);
            if (u != null && u.getPassword().equals(pass)) {
                sessaoAtual = u;
                registarAcao("LOGIN", "Sessão iniciada com perfil " + u.getPerfil());
                System.out.println(VERDE + "\n  ✔ Bem-vindo/a, " + user
                    + "! Perfil: " + u.getPerfil() + RESET);
                menuPrincipal();
                return;
            } else {
                tentativas++;
                registarAcaoAnonima("LOGIN_FALHA",
                    "Tentativa " + tentativas + " para utilizador '" + user + "'");
                System.out.println(VERMELHO + "  ✘ Credenciais incorretas. ("
                    + (3 - tentativas) + " tentativa(s) restante(s))" + RESET);
            }
        }
        System.out.println(VERMELHO + "\n  ⛔ Número máximo de tentativas atingido. A sair..." + RESET);
    }

    // ============================================================
    //  MENU PRINCIPAL
    // ============================================================
    static void menuPrincipal() {
        while (true) {
            System.out.println(AZUL + "\n╔══════════════════════════════════════════════════╗");
            System.out.println("║              MENU PRINCIPAL                      ║");
            System.out.println("╠══════════════════════════════════════════════════╣" + RESET);
            System.out.println("  1. Registar Beneficiário");
            System.out.println("  2. Listar Beneficiários");
            System.out.println("  3. Submeter Candidatura");
            System.out.println("  4. Gerir Candidaturas" +
                (sessaoAtual.getPerfil().equals("ADMIN") ? "" : AMARELO + "  [ADMIN]" + RESET));
            System.out.println("  5. Consultar Auditoria" +
                (sessaoAtual.getPerfil().equals("ADMIN") ? "" : AMARELO + "  [ADMIN]" + RESET));
            System.out.println("  0. Terminar Sessão");
            System.out.println(AZUL + "╚══════════════════════════════════════════════════╝" + RESET);
            System.out.print("  Opção: ");

            String opcao = sc.nextLine().trim();
            switch (opcao) {
                case "1" -> registarBeneficiario();
                case "2" -> listarBeneficiarios();
                case "3" -> submeterCandidatura();
                case "4" -> gerirCandidaturas();
                case "5" -> consultarAuditoria();
                case "0" -> {
                    registarAcao("LOGOUT", "Sessão terminada.");
                    System.out.println(AMARELO + "\n  Sessão terminada. Até breve! 👋" + RESET);
                    sessaoAtual = null;
                    menuLogin();
                    return;
                }
                default  -> System.out.println(VERMELHO + "  Opção inválida." + RESET);
            }
        }
    }

    // ============================================================
    //  REQ-001 — REGISTAR BENEFICIÁRIO
    // ============================================================
    static void registarBeneficiario() {
        System.out.println(CIANO + "\n─── REGISTAR BENEFICIÁRIO ───────────────────────" + RESET);

        String nome     = lerCampo("Nome completo");
        String nif      = lerCampo("NIF (9 dígitos)");
        String email    = lerCampo("Email");
        String telefone = lerCampo("Telefone (9 dígitos)");
        int    idade    = lerInteiro("Idade");
        String morada   = lerCampo("Morada");

        // REQ-002 — Validar dados
        List<String> erros = ValidacaoService.validarBeneficiario(
            nome, nif, email, telefone, idade, morada);

        if (!erros.isEmpty()) {
            System.out.println(VERMELHO + "\n  ✘ Dados inválidos:" + RESET);
            erros.forEach(e -> System.out.println(VERMELHO + "    • " + e + RESET));
            registarAcao("REGISTO_FALHA",
                "Tentativa de registo com dados inválidos — NIF: " + nif);
            return;
        }

        // Verificar NIF duplicado
        boolean nifExiste = beneficiarios.stream()
            .anyMatch(b -> b.getNif().equals(nif.trim()));
        if (nifExiste) {
            System.out.println(VERMELHO + "  ✘ Já existe um beneficiário com o NIF " + nif + RESET);
            registarAcao("REGISTO_FALHA", "NIF duplicado: " + nif);
            return;
        }

        Beneficiario b = new Beneficiario(
            nome.trim(), nif.trim(), email.trim(),
            telefone.trim(), idade, morada.trim());
        beneficiarios.add(b);

        registarAcao("REGISTO_BENEFICIARIO",
            "Novo beneficiário registado — ID: " + b.getId() + ", NIF: " + nif);

        System.out.println(VERDE + "\n  ✔ Beneficiário registado com sucesso!" + RESET);
        System.out.println(b);
    }

    // ============================================================
    //  LISTAR BENEFICIÁRIOS
    // ============================================================
    static void listarBeneficiarios() {
        System.out.println(CIANO + "\n─── LISTA DE BENEFICIÁRIOS ──────────────────────" + RESET);
        if (beneficiarios.isEmpty()) {
            System.out.println("  (Nenhum beneficiário registado.)");
            return;
        }
        beneficiarios.forEach(b -> System.out.println(b));
        registarAcao("LISTAR_BENEFICIARIOS",
            "Listagem consultada — " + beneficiarios.size() + " registo(s).");
    }

    // ============================================================
    //  REQ-003 — SUBMETER CANDIDATURA
    // ============================================================
    static void submeterCandidatura() {
        System.out.println(CIANO + "\n─── SUBMETER CANDIDATURA ────────────────────────" + RESET);
        if (beneficiarios.isEmpty()) {
            System.out.println("  (Nenhum beneficiário registado. Registe primeiro.)");
            return;
        }

        System.out.print("  ID do beneficiário: ");
        int id = lerInteiroRaw();

        Beneficiario ben = encontrarPorId(id);
        if (ben == null) {
            System.out.println(VERMELHO + "  ✘ Beneficiário não encontrado." + RESET);
            return;
        }

        if (!ben.getEstadoCandidatura().equals("PENDENTE")) {
            System.out.println(AMARELO + "  ⚠ Este beneficiário já tem candidatura: "
                + ben.getEstadoCandidatura() + RESET);
            return;
        }

        System.out.println(VERDE + "  ✔ Candidatura submetida para: " + ben.getNome() + RESET);
        System.out.println("  Estado: " + AMARELO + "PENDENTE (aguarda avaliação)" + RESET);

        registarAcao("SUBMETER_CANDIDATURA",
            "Candidatura submetida — ID beneficiário: " + id + ", Nome: " + ben.getNome());
    }

    // ============================================================
    //  GERIR CANDIDATURAS (ADMIN)
    // ============================================================
    static void gerirCandidaturas() {
        if (!verificarAdmin()) return;

        System.out.println(CIANO + "\n─── GERIR CANDIDATURAS ──────────────────────────" + RESET);
        List<Beneficiario> pendentes = beneficiarios.stream()
            .filter(b -> b.getEstadoCandidatura().equals("PENDENTE"))
            .toList();

        if (pendentes.isEmpty()) {
            System.out.println("  (Não há candidaturas pendentes.)");
            return;
        }

        pendentes.forEach(b ->
            System.out.printf("  [ID %d] %-25s → %s%n",
                b.getId(), b.getNome(), b.getEstadoCandidatura()));

        System.out.print("\n  ID a avaliar: ");
        int id = lerInteiroRaw();
        Beneficiario ben = encontrarPorId(id);

        if (ben == null || !ben.getEstadoCandidatura().equals("PENDENTE")) {
            System.out.println(VERMELHO + "  ✘ ID inválido ou candidatura não está pendente." + RESET);
            return;
        }

        System.out.println("  1 → APROVAR    2 → REJEITAR    0 → Cancelar");
        System.out.print("  Decisão: ");
        String dec = sc.nextLine().trim();

        switch (dec) {
            case "1" -> {
                ben.setEstadoCandidatura("APROVADA");
                System.out.println(VERDE + "  ✔ Candidatura APROVADA." + RESET);
                registarAcao("APROVAR_CANDIDATURA",
                    "Candidatura aprovada — ID: " + id + ", Nome: " + ben.getNome());
            }
            case "2" -> {
                ben.setEstadoCandidatura("REJEITADA");
                System.out.println(VERMELHO + "  ✘ Candidatura REJEITADA." + RESET);
                registarAcao("REJEITAR_CANDIDATURA",
                    "Candidatura rejeitada — ID: " + id + ", Nome: " + ben.getNome());
            }
            default  -> System.out.println("  Operação cancelada.");
        }
    }

    // ============================================================
    //  REQ-009 — CONSULTAR AUDITORIA (ADMIN)
    // ============================================================
    static void consultarAuditoria() {
        if (!verificarAdmin()) return;

        System.out.println(CIANO + "\n─── REGISTO DE AÇÕES (AUDITORIA) ────────────────" + RESET);
        if (auditLog.isEmpty()) {
            System.out.println("  (Nenhuma ação registada.)");
            return;
        }
        auditLog.forEach(r -> System.out.println("  " + r));
        System.out.println(AZUL + "\n  Total: " + auditLog.size() + " registo(s)." + RESET);
    }

    // ============================================================
    //  HELPERS — Auditoria
    // ============================================================
    static void registarAcao(String acao, String detalhes) {
        String user = (sessaoAtual != null) ? sessaoAtual.getUsername() : "SISTEMA";
        auditLog.add(new RegistoAcao(user, acao, detalhes));
    }

    static void registarAcaoAnonima(String acao, String detalhes) {
        auditLog.add(new RegistoAcao("ANONIMO", acao, detalhes));
    }

    // ============================================================
    //  HELPERS — Controlo de Acesso
    // ============================================================
    static boolean verificarAdmin() {
        if (sessaoAtual != null && sessaoAtual.getPerfil().equals("ADMIN"))
            return true;
        System.out.println(VERMELHO +
            "  ⛔ Acesso negado. É necessário perfil ADMIN." + RESET);
        registarAcao("ACESSO_NEGADO",
            "Tentativa de acesso a funcionalidade ADMIN pelo utilizador: "
            + (sessaoAtual != null ? sessaoAtual.getUsername() : "?"));
        return false;
    }

    // ============================================================
    //  HELPERS — Leitura de Input
    // ============================================================
    static String lerCampo(String label) {
        System.out.print("  " + label + ": ");
        return sc.nextLine();
    }

    static int lerInteiro(String label) {
        while (true) {
            System.out.print("  " + label + ": ");
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println(VERMELHO + "  Valor numérico inválido." + RESET);
            }
        }
    }

    static int lerInteiroRaw() {
        while (true) {
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println(VERMELHO + "  Valor inválido. Tente novamente: " + RESET);
            }
        }
    }

    static Beneficiario encontrarPorId(int id) {
        return beneficiarios.stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElse(null);
    }
}

**Kept (accepted):**
- ...

**Rejected (feature drift / out of scope):**
- ...

**Manual verification:**
- Happy path:
- Alternative flow:
- Exception/error path:

**Changes made after generation (manual edits):**
- ...

---

## Iteration 2 (with Claude.AI)
**Prompt (summary or paste):**


**Generated output:**

**Kept:**
- ...

**Rejected:**
- ...

**Manual verification:**
- Happy path:
- Alternative flow:
- Exception/error path:

**Changes made after generation:**
- ...

---

## Notes (lessons learned)
- What requirement ambiguity caused problems?
- What constraints were missing initially?
- What would you change in your requirements next?
