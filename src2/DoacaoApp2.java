import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

// ============================================================
//  SEGURANÇA — Hash de passwords com Salt (SHA-256)
// ============================================================
class SegurancaUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    /** Gera um salt aleatório de 16 bytes em hex */
    public static String gerarSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return bytesParaHex(salt);
    }

    /** SHA-256 da password + salt */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String entrada = salt + password;
            byte[] hash = digest.digest(entrada.getBytes(StandardCharsets.UTF_8));
            return bytesParaHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer hash da password.", e);
        }
    }

    private static String bytesParaHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    /** Verifica se a password fornecida corresponde ao hash armazenado */
    public static boolean verificarPassword(String password, String salt, String hashArmazenado) {
        return hashPassword(password, salt).equals(hashArmazenado);
    }

    /**
     * Valida a força da password:
     *  - Mínimo 8 caracteres
     *  - Pelo menos 1 maiúscula
     *  - Pelo menos 1 minúscula
     *  - Pelo menos 1 dígito
     *  - Pelo menos 1 caractere especial
     */
    public static List<String> validarForcaPassword(String password) {
        List<String> erros = new ArrayList<>();
        if (password == null || password.length() < 8)
            erros.add("Mínimo 8 caracteres.");
        if (password == null || !password.matches(".*[A-Z].*"))
            erros.add("Pelo menos 1 letra maiúscula.");
        if (password == null || !password.matches(".*[a-z].*"))
            erros.add("Pelo menos 1 letra minúscula.");
        if (password == null || !password.matches(".*\\d.*"))
            erros.add("Pelo menos 1 número.");
        if (password == null || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"))
            erros.add("Pelo menos 1 caractere especial (!@#$%...).");
        return erros;
    }
}

// ============================================================
//  MODELO — Utilizador (REQ-008 reforçado)
// ============================================================
class Utilizador {
    public enum Perfil { ADMIN, OPERADOR, VISUALIZADOR }

    private String username;
    private String passwordHash;
    private String salt;
    private Perfil perfil;
    private int tentativasFalhadas;
    private boolean bloqueado;
    private LocalDateTime ultimoLogin;
    private LocalDateTime criadoEm;
    private boolean primeiroLogin; // obriga a mudar password no 1.º acesso

    public Utilizador(String username, String passwordPlain, Perfil perfil) {
        this.username     = username;
        this.salt         = SegurancaUtils.gerarSalt();
        this.passwordHash = SegurancaUtils.hashPassword(passwordPlain, this.salt);
        this.perfil       = perfil;
        this.bloqueado    = false;
        this.tentativasFalhadas = 0;
        this.criadoEm     = LocalDateTime.now();
        this.primeiroLogin = true;
    }

    public boolean autenticar(String passwordPlain) {
        return SegurancaUtils.verificarPassword(passwordPlain, salt, passwordHash);
    }

    public void atualizarPassword(String novaPasswordPlain) {
        this.salt         = SegurancaUtils.gerarSalt();
        this.passwordHash = SegurancaUtils.hashPassword(novaPasswordPlain, this.salt);
        this.primeiroLogin = false;
    }

    public void registarTentativaFalhada() {
        tentativasFalhadas++;
        if (tentativasFalhadas >= 3) bloqueado = true;
    }

    public void resetarTentativas() { tentativasFalhadas = 0; }
    public void setUltimoLogin(LocalDateTime dt) { this.ultimoLogin = dt; }

    public String   getUsername()         { return username; }
    public Perfil   getPerfil()           { return perfil; }
    public boolean  isBloqueado()         { return bloqueado; }
    public boolean  isPrimeiroLogin()     { return primeiroLogin; }
    public int      getTentativasFalhadas(){ return tentativasFalhadas; }
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public LocalDateTime getCriadoEm()    { return criadoEm; }

    // Só o admin pode desbloquear
    public void desbloquear() { bloqueado = false; tentativasFalhadas = 0; }
    public void setPerfil(Perfil p) { this.perfil = p; }
}

// ============================================================
//  MODELO — Beneficiário + Candidatura (REQ-001/003 unificados)
// ============================================================
class Beneficiario {
    private static int contadorId = 1;

    public enum EstadoCandidatura { SUBMETIDA, EM_ANALISE, APROVADA, REJEITADA }

    private int id;
    private String nome;
    private String nif;
    private String email;
    private String telefone;
    private int    idade;
    private String morada;
    private String motivacao;          // descrição da necessidade
    private EstadoCandidatura estado;
    private LocalDateTime dataCriacao;
    private String criadoPor;          // username do operador
    private String observacoesAdmin;

    public Beneficiario(String nome, String nif, String email, String telefone,
                        int idade, String morada, String motivacao, String criadoPor) {
        this.id           = contadorId++;
        this.nome         = nome;
        this.nif          = nif;
        this.email        = email;
        this.telefone     = telefone;
        this.idade        = idade;
        this.morada       = morada;
        this.motivacao    = motivacao;
        this.estado       = EstadoCandidatura.SUBMETIDA;
        this.dataCriacao  = LocalDateTime.now();
        this.criadoPor    = criadoPor;
    }

    // Getters
    public int    getId()         { return id; }
    public String getNome()       { return nome; }
    public String getNif()        { return nif; }
    public String getEmail()      { return email; }
    public String getTelefone()   { return telefone; }
    public int    getIdade()      { return idade; }
    public String getMorada()     { return morada; }
    public String getMotivacao()  { return motivacao; }
    public EstadoCandidatura getEstado() { return estado; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getCriadoPor()  { return criadoPor; }

    public void setEstado(EstadoCandidatura e)    { this.estado = e; }
    public void setObservacoes(String obs)         { this.observacoesAdmin = obs; }
    public String getObservacoes()                 { return observacoesAdmin; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String obs = (observacoesAdmin != null && !observacoesAdmin.isBlank())
                     ? observacoesAdmin : "(sem observações)";
        return String.format(
            "┌──────────────────────────────────────────────────┐\n" +
            "│  ID          : %-33d│\n" +
            "│  Nome        : %-33s│\n" +
            "│  NIF         : %-33s│\n" +
            "│  Email       : %-33s│\n" +
            "│  Telefone    : %-33s│\n" +
            "│  Idade       : %-33d│\n" +
            "│  Morada      : %-33s│\n" +
            "│  Motivação   : %-33s│\n" +
            "│  Estado      : %-33s│\n" +
            "│  Observações : %-33s│\n" +
            "│  Registado   : %-33s│\n" +
            "│  Por         : %-33s│\n" +
            "└──────────────────────────────────────────────────┘",
            id, nome, nif, email, telefone, idade,
            truncar(morada, 33), truncar(motivacao, 33),
            estado, truncar(obs, 33),
            dataCriacao.format(fmt), criadoPor);
    }

    private String truncar(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max - 3) + "..." : s;
    }
}

// ============================================================
//  MODELO — Registo de Auditoria (REQ-009)
// ============================================================
class Auditoria {
    private static int seq = 1;
    private int    id;
    private String utilizador;
    private String acao;
    private String detalhe;
    private String ip;          // simulado
    private LocalDateTime ts;

    public Auditoria(String utilizador, String acao, String detalhe) {
        this.id         = seq++;
        this.utilizador = utilizador;
        this.acao       = acao;
        this.detalhe    = detalhe;
        this.ip         = "127.0.0.1";  // numa app real viria do contexto
        this.ts         = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("  #%-4d [%s] [%-15s] %-30s → %s",
            id, ts.format(fmt), utilizador, acao, detalhe);
    }
}

// ============================================================
//  VALIDAÇÃO (REQ-002)
// ============================================================
class Validacao {

    public static List<String> validarBeneficiario(String nome, String nif, String email,
            String telefone, int idade, String morada, String motivacao) {
        List<String> erros = new ArrayList<>();

        if (nome == null || nome.trim().length() < 3)
            erros.add("Nome deve ter pelo menos 3 caracteres.");

        if (!Pattern.matches("\\d{9}", nif == null ? "" : nif.trim()))
            erros.add("NIF inválido (9 dígitos numéricos).");

        if (!Pattern.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$",
                             email == null ? "" : email.trim()))
            erros.add("Email inválido.");

        if (!Pattern.matches("[239]\\d{8}", telefone == null ? "" : telefone.trim()))
            erros.add("Telefone inválido (9 dígitos, começa em 2, 3 ou 9).");

        if (idade < 18 || idade > 120)
            erros.add("Idade deve estar entre 18 e 120 anos.");

        if (morada == null || morada.trim().length() < 5)
            erros.add("Morada deve ter pelo menos 5 caracteres.");

        if (motivacao == null || motivacao.trim().length() < 20)
            erros.add("Motivação deve ter pelo menos 20 caracteres.");

        return erros;
    }
}

// ============================================================
//  APLICAÇÃO PRINCIPAL
// ============================================================
public class DoacaoApp2 {

    // ── Estado global ──────────────────────────────────────
    static final Map<String, Utilizador> utilizadores  = new LinkedHashMap<>();
    static final List<Beneficiario>      beneficiarios = new ArrayList<>();
    static final List<Auditoria>         auditLog      = new ArrayList<>();
    static Utilizador                    sessao        = null;
    static final Scanner                 sc            = new Scanner(System.in);

    // ── ANSI ───────────────────────────────────────────────
    static final String R  = "\u001B[0m";
    static final String V  = "\u001B[32m";  // verde
    static final String RM = "\u001B[31m";  // vermelho
    static final String AM = "\u001B[33m";  // amarelo
    static final String AZ = "\u001B[34m";  // azul
    static final String CI = "\u001B[36m";  // ciano
    static final String NG = "\u001B[1m";   // negrito

    // ──────────────────────────────────────────────────────
    public static void main(String[] args) {
        inicializar();
        banner();
        loopLogin();
    }

    // ============================================================
    //  INICIALIZAÇÃO
    // ============================================================
    static void inicializar() {
        // Admin pré-configurado (password: Admin@2024!)
        utilizadores.put("admin",
            new Utilizador("admin", "Admin@2024!", Utilizador.Perfil.ADMIN));
        // Marca como não sendo primeiro login para não forçar mudança
        utilizadores.get("admin").resetarTentativas();

        // Operador pré-configurado (vai precisar de mudar a pass no 1.º login)
        utilizadores.put("operador",
            new Utilizador("operador", "Oper@0001!", Utilizador.Perfil.OPERADOR));
    }

    // ============================================================
    //  BANNER
    // ============================================================
    static void banner() {
        System.out.println(AZ + NG);
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║       🤝  SISTEMA DE DOAÇÕES SEGURO — v2.0  🤝       ║");
        System.out.println("║          Gestão de Beneficiários e Candidaturas       ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println(R);
    }

    // ============================================================
    //  LOGIN (REQ-008 reforçado)
    // ============================================================
    static void loopLogin() {
        while (true) {
            System.out.println(CI + "\n══ AUTENTICAÇÃO " + "═".repeat(38) + R);
            System.out.print("  Utilizador : ");
            String user = sc.nextLine().trim();
            System.out.print("  Password   : ");
            String pass = sc.nextLine().trim();

            Utilizador u = utilizadores.get(user);

            if (u == null) {
                log("ANONIMO", "LOGIN_FALHA", "Utilizador inexistente: " + user);
                System.out.println(RM + "  ✘ Credenciais inválidas." + R);
                pausar(1500);
                continue;
            }

            if (u.isBloqueado()) {
                log("ANONIMO", "LOGIN_BLOQUEADO", "Conta bloqueada: " + user);
                System.out.println(RM + "  ⛔ Conta bloqueada. Contacte o administrador." + R);
                pausar(2000);
                continue;
            }

            if (!u.autenticar(pass)) {
                u.registarTentativaFalhada();
                log(user, "LOGIN_FALHA",
                    "Tentativa " + u.getTentativasFalhadas() + "/3");
                if (u.isBloqueado()) {
                    System.out.println(RM + "  ⛔ Conta BLOQUEADA após 3 tentativas falhadas." + R);
                } else {
                    System.out.println(RM + "  ✘ Credenciais inválidas. ("
                        + (3 - u.getTentativasFalhadas()) + " tentativa(s) restante(s))" + R);
                }
                pausar(1500);
                continue;
            }

            // Login OK
            u.resetarTentativas();
            u.setUltimoLogin(LocalDateTime.now());
            sessao = u;
            log(user, "LOGIN_OK", "Perfil: " + u.getPerfil());
            System.out.println(V + "\n  ✔ Bem-vindo/a, " + user + "! Perfil: " + u.getPerfil() + R);

            // Forçar mudança de password no 1.º login
            if (u.isPrimeiroLogin()) {
                System.out.println(AM + "\n  ⚠ Primeiro acesso detectado — defina uma nova password." + R);
                fluxoMudarPassword(u, true);
            }

            menuPrincipal();
        }
    }

    // ============================================================
    //  MENU PRINCIPAL
    // ============================================================
    static void menuPrincipal() {
        while (true) {
            System.out.println(AZ + "\n╔══════════════════════════════════════════════════════╗");
            System.out.printf( "║  Sessão: %-15s  Perfil: %-16s║%n",
                sessao.getUsername(), sessao.getPerfil());
            System.out.println("╠══════════════════════════════════════════════════════╣" + R);

            // Opções comuns
            System.out.println("  1. Registar e Submeter Candidatura");
            System.out.println("  2. Consultar Candidaturas");
            System.out.println("  3. Alterar a minha password");

            // Opções ADMIN / OPERADOR
            if (podeGerirCandidaturas()) {
                System.out.println("  4. Gerir Candidaturas (avaliar)");
            }
            if (sessao.getPerfil() == Utilizador.Perfil.ADMIN) {
                System.out.println("  5. Gestão de Utilizadores");
                System.out.println("  6. Auditoria completa");
            }
            System.out.println("  0. Terminar sessão");
            System.out.println(AZ + "╚══════════════════════════════════════════════════════╝" + R);
            System.out.print("  Opção: ");

            switch (sc.nextLine().trim()) {
                case "1" -> registarESubmeter();
                case "2" -> consultarCandidaturas();
                case "3" -> fluxoMudarPassword(sessao, false);
                case "4" -> { if (podeGerirCandidaturas()) avaliarCandidatura(); else invalido(); }
                case "5" -> { if (isAdmin()) gestaoUtilizadores(); else invalido(); }
                case "6" -> { if (isAdmin()) verAuditoria();       else invalido(); }
                case "0" -> {
                    log(sessao.getUsername(), "LOGOUT", "Sessão terminada.");
                    System.out.println(AM + "\n  Até breve, " + sessao.getUsername() + "! 👋" + R);
                    sessao = null;
                    return;
                }
                default  -> invalido();
            }
        }
    }

    // ============================================================
    //  REQ-001 + REQ-003 — REGISTAR E SUBMETER (fluxo único)
    // ============================================================
    static void registarESubmeter() {
        // VISUALIZADOR não pode registar
        if (sessao.getPerfil() == Utilizador.Perfil.VISUALIZADOR) {
            System.out.println(RM + "  ⛔ O seu perfil não permite registar candidaturas." + R);
            log(sessao.getUsername(), "ACESSO_NEGADO", "Tentou registar candidatura");
            return;
        }

        System.out.println(CI + "\n══ REGISTAR E SUBMETER CANDIDATURA " + "═".repeat(19) + R);
        System.out.println(AM + "  Preencha todos os campos. A candidatura é submetida automaticamente." + R);

        String nome     = ler("Nome completo do beneficiário");
        String nif      = ler("NIF (9 dígitos)");
        String email    = ler("Email");
        String telefone = ler("Telefone (9 dígitos)");
        int    idade    = lerInt("Idade");
        String morada   = ler("Morada completa");

        System.out.println(CI + "\n  Descreva a situação/necessidade (mín. 20 caracteres):" + R);
        System.out.print("  > ");
        String motivacao = sc.nextLine().trim();

        // REQ-002 — Validar
        List<String> erros = Validacao.validarBeneficiario(
            nome, nif, email, telefone, idade, morada, motivacao);

        // Verificar NIF duplicado
        boolean nifDup = beneficiarios.stream().anyMatch(b -> b.getNif().equals(nif.trim()));
        if (nifDup) erros.add("Já existe um beneficiário com o NIF " + nif.trim() + ".");

        if (!erros.isEmpty()) {
            System.out.println(RM + "\n  ✘ Dados inválidos:" + R);
            erros.forEach(e -> System.out.println(RM + "    • " + e + R));
            log(sessao.getUsername(), "REGISTO_FALHA", "Dados inválidos — NIF: " + nif);
            return;
        }

        // Confirmação antes de submeter
        System.out.println(AM + "\n  Confirma a submissão da candidatura para " + nome.trim() + "? (s/n): " + R);
        System.out.print("  > ");
        if (!sc.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.println("  Operação cancelada.");
            return;
        }

        Beneficiario b = new Beneficiario(
            nome.trim(), nif.trim(), email.trim(), telefone.trim(),
            idade, morada.trim(), motivacao.trim(), sessao.getUsername());
        beneficiarios.add(b);

        log(sessao.getUsername(), "CANDIDATURA_SUBMETIDA",
            "ID=" + b.getId() + " NIF=" + nif.trim() + " Nome=" + nome.trim());

        System.out.println(V + "\n  ✔ Candidatura submetida com sucesso! ID: " + b.getId() + R);
        System.out.println(b);
    }

    // ============================================================
    //  CONSULTAR CANDIDATURAS
    // ============================================================
    static void consultarCandidaturas() {
        System.out.println(CI + "\n══ CONSULTAR CANDIDATURAS " + "═".repeat(29) + R);

        if (beneficiarios.isEmpty()) {
            System.out.println("  (Sem candidaturas registadas.)");
            return;
        }

        System.out.println("  Filtrar por estado:");
        System.out.println("  1. Todas   2. Submetidas   3. Em análise   4. Aprovadas   5. Rejeitadas   0. Voltar");
        System.out.print("  Opção: ");
        String op = sc.nextLine().trim();

        List<Beneficiario> lista = switch (op) {
            case "1" -> new ArrayList<>(beneficiarios);
            case "2" -> beneficiarios.stream().filter(b -> b.getEstado() == Beneficiario.EstadoCandidatura.SUBMETIDA).toList();
            case "3" -> beneficiarios.stream().filter(b -> b.getEstado() == Beneficiario.EstadoCandidatura.EM_ANALISE).toList();
            case "4" -> beneficiarios.stream().filter(b -> b.getEstado() == Beneficiario.EstadoCandidatura.APROVADA).toList();
            case "5" -> beneficiarios.stream().filter(b -> b.getEstado() == Beneficiario.EstadoCandidatura.REJEITADA).toList();
            default  -> new ArrayList<>();
        };

        if (lista.isEmpty()) {
            System.out.println("  (Nenhum resultado para o filtro selecionado.)");
        } else {
            lista.forEach(System.out::println);
            System.out.println(AZ + "\n  Total: " + lista.size() + " candidatura(s)." + R);
        }

        log(sessao.getUsername(), "CONSULTA_CANDIDATURAS", "Filtro=" + op + " Resultados=" + lista.size());
    }

    // ============================================================
    //  AVALIAR CANDIDATURA (ADMIN / OPERADOR)
    // ============================================================
    static void avaliarCandidatura() {
        System.out.println(CI + "\n══ GERIR CANDIDATURAS " + "═".repeat(33) + R);

        List<Beneficiario> pendentes = beneficiarios.stream()
            .filter(b -> b.getEstado() == Beneficiario.EstadoCandidatura.SUBMETIDA
                      || b.getEstado() == Beneficiario.EstadoCandidatura.EM_ANALISE)
            .toList();

        if (pendentes.isEmpty()) {
            System.out.println("  (Sem candidaturas para avaliar.)");
            return;
        }

        System.out.println("  Candidaturas por avaliar:");
        pendentes.forEach(b -> System.out.printf("  [%d] %-25s Estado: %s%n",
            b.getId(), b.getNome(), b.getEstado()));

        System.out.print("\n  ID a avaliar (0=voltar): ");
        int id = lerIntRaw();
        if (id == 0) return;

        Beneficiario ben = porId(id);
        if (ben == null) { System.out.println(RM + "  ID não encontrado." + R); return; }

        System.out.println("\n" + ben);
        System.out.println("\n  Decisão:");
        System.out.println("  1. Marcar Em Análise   2. Aprovar   3. Rejeitar   0. Cancelar");
        System.out.print("  Opção: ");
        String dec = sc.nextLine().trim();

        if (dec.equals("0")) { System.out.println("  Cancelado."); return; }

        System.out.print("  Observações (opcional): ");
        String obs = sc.nextLine().trim();

        switch (dec) {
            case "1" -> {
                ben.setEstado(Beneficiario.EstadoCandidatura.EM_ANALISE);
                ben.setObservacoes(obs);
                System.out.println(AM + "  ➡ Estado atualizado para EM ANÁLISE." + R);
                log(sessao.getUsername(), "CANDIDATURA_EM_ANALISE", "ID=" + id);
            }
            case "2" -> {
                ben.setEstado(Beneficiario.EstadoCandidatura.APROVADA);
                ben.setObservacoes(obs);
                System.out.println(V + "  ✔ Candidatura APROVADA." + R);
                log(sessao.getUsername(), "CANDIDATURA_APROVADA", "ID=" + id + " Nome=" + ben.getNome());
            }
            case "3" -> {
                ben.setEstado(Beneficiario.EstadoCandidatura.REJEITADA);
                ben.setObservacoes(obs.isBlank() ? "Sem justificação fornecida." : obs);
                System.out.println(RM + "  ✘ Candidatura REJEITADA." + R);
                log(sessao.getUsername(), "CANDIDATURA_REJEITADA", "ID=" + id + " Nome=" + ben.getNome());
            }
            default -> System.out.println("  Opção inválida, operação cancelada.");
        }
    }

    // ============================================================
    //  ALTERAR PASSWORD
    // ============================================================
    static void fluxoMudarPassword(Utilizador u, boolean obrigatorio) {
        System.out.println(CI + "\n══ ALTERAR PASSWORD " + "═".repeat(35) + R);
        if (!obrigatorio) {
            System.out.print("  Password atual: ");
            String atual = sc.nextLine().trim();
            if (!u.autenticar(atual)) {
                System.out.println(RM + "  ✘ Password atual incorreta." + R);
                log(u.getUsername(), "ALTERAR_PASSWORD_FALHA", "Password atual incorreta.");
                return;
            }
        }

        for (int t = 0; t < 3; t++) {
            System.out.print("  Nova password      : ");
            String nova = sc.nextLine().trim();
            System.out.print("  Confirmar password : ");
            String conf = sc.nextLine().trim();

            if (!nova.equals(conf)) {
                System.out.println(RM + "  ✘ As passwords não coincidem." + R);
                continue;
            }

            List<String> erros = SegurancaUtils.validarForcaPassword(nova);
            if (!erros.isEmpty()) {
                System.out.println(RM + "  ✘ Password fraca:" + R);
                erros.forEach(e -> System.out.println(RM + "    • " + e + R));
                continue;
            }

            u.atualizarPassword(nova);
            System.out.println(V + "  ✔ Password atualizada com sucesso." + R);
            log(u.getUsername(), "ALTERAR_PASSWORD_OK", "Password atualizada.");
            return;
        }

        if (obrigatorio) {
            System.out.println(RM + "  ⛔ Não foi possível definir a password. Sessão terminada." + R);
            log(u.getUsername(), "LOGOUT_FORCADO", "Falhou a definição de password obrigatória.");
            sessao = null;
            System.exit(0);
        }
    }

    // ============================================================
    //  GESTÃO DE UTILIZADORES (ADMIN)
    // ============================================================
    static void gestaoUtilizadores() {
        System.out.println(CI + "\n══ GESTÃO DE UTILIZADORES " + "═".repeat(29) + R);
        System.out.println("  1. Listar utilizadores");
        System.out.println("  2. Criar utilizador");
        System.out.println("  3. Desbloquear utilizador");
        System.out.println("  4. Alterar perfil de utilizador");
        System.out.println("  0. Voltar");
        System.out.print("  Opção: ");

        switch (sc.nextLine().trim()) {
            case "1" -> listarUtilizadores();
            case "2" -> criarUtilizador();
            case "3" -> desbloquearUtilizador();
            case "4" -> alterarPerfil();
            case "0" -> {}
            default  -> invalido();
        }
    }

    static void listarUtilizadores() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println(CI + "\n  Lista de utilizadores:" + R);
        System.out.printf("  %-12s %-12s %-9s %-8s %-20s%n",
            "Username","Perfil","Bloqueado","Tentativas","Último Login");
        System.out.println("  " + "─".repeat(65));
        for (Utilizador u : utilizadores.values()) {
            String ultimo = u.getUltimoLogin() != null
                ? u.getUltimoLogin().format(fmt) : "(nunca)";
            System.out.printf("  %-12s %-12s %-9s %-8d %-20s%n",
                u.getUsername(), u.getPerfil(),
                u.isBloqueado() ? RM+"SIM"+R : V+"NÃO"+R,
                u.getTentativasFalhadas(), ultimo);
        }
        log(sessao.getUsername(), "LISTAR_UTILIZADORES", "Listagem consultada.");
    }

    static void criarUtilizador() {
        System.out.println(CI + "\n  Criar novo utilizador:" + R);
        System.out.print("  Username : ");
        String user = sc.nextLine().trim();

        if (utilizadores.containsKey(user)) {
            System.out.println(RM + "  ✘ Username já existe." + R);
            return;
        }

        System.out.println("  Perfil: 1.ADMIN  2.OPERADOR  3.VISUALIZADOR");
        System.out.print("  Opção: ");
        Utilizador.Perfil perfil = switch (sc.nextLine().trim()) {
            case "1" -> Utilizador.Perfil.ADMIN;
            case "2" -> Utilizador.Perfil.OPERADOR;
            default  -> Utilizador.Perfil.VISUALIZADOR;
        };

        // Gera password temporária aleatória segura
        String tempPass = "Temp@" + (100000 + new SecureRandom().nextInt(900000)) + "!";
        utilizadores.put(user, new Utilizador(user, tempPass, perfil));
        System.out.println(V + "  ✔ Utilizador criado! Password temporária: " + AM + tempPass + R);
        System.out.println(AM + "  ⚠ O utilizador será obrigado a mudar a password no 1.º login." + R);
        log(sessao.getUsername(), "CRIAR_UTILIZADOR", "Username=" + user + " Perfil=" + perfil);
    }

    static void desbloquearUtilizador() {
        System.out.print("  Username a desbloquear: ");
        String user = sc.nextLine().trim();
        Utilizador u = utilizadores.get(user);
        if (u == null) { System.out.println(RM + "  Utilizador não encontrado." + R); return; }
        if (!u.isBloqueado()) { System.out.println("  Utilizador não está bloqueado."); return; }
        u.desbloquear();
        System.out.println(V + "  ✔ Utilizador desbloqueado." + R);
        log(sessao.getUsername(), "DESBLOQUEAR_UTILIZADOR", "Username=" + user);
    }

    static void alterarPerfil() {
        System.out.print("  Username: ");
        String user = sc.nextLine().trim();
        Utilizador u = utilizadores.get(user);
        if (u == null) { System.out.println(RM + "  Utilizador não encontrado." + R); return; }

        System.out.println("  Novo perfil: 1.ADMIN  2.OPERADOR  3.VISUALIZADOR");
        System.out.print("  Opção: ");
        Utilizador.Perfil novo = switch (sc.nextLine().trim()) {
            case "1" -> Utilizador.Perfil.ADMIN;
            case "2" -> Utilizador.Perfil.OPERADOR;
            default  -> Utilizador.Perfil.VISUALIZADOR;
        };
        u.setPerfil(novo);
        System.out.println(V + "  ✔ Perfil atualizado para " + novo + R);
        log(sessao.getUsername(), "ALTERAR_PERFIL", "Username=" + user + " NovoPerfil=" + novo);
    }

    // ============================================================
    //  AUDITORIA (ADMIN)
    // ============================================================
    static void verAuditoria() {
        System.out.println(CI + "\n══ AUDITORIA COMPLETA " + "═".repeat(33) + R);
        System.out.println("  Filtrar por: 1.Todas  2.Por utilizador  0.Voltar");
        System.out.print("  Opção: ");

        switch (sc.nextLine().trim()) {
            case "1" -> {
                auditLog.forEach(System.out::println);
                System.out.println(AZ + "\n  Total: " + auditLog.size() + " registo(s)." + R);
            }
            case "2" -> {
                System.out.print("  Username: ");
                String filtro = sc.nextLine().trim();
                List<Auditoria> filtrados = auditLog.stream()
                    .filter(a -> a.toString().contains(filtro))
                    .toList();
                filtrados.forEach(System.out::println);
                System.out.println(AZ + "\n  Total: " + filtrados.size() + " registo(s)." + R);
            }
            default -> {}
        }
        log(sessao.getUsername(), "CONSULTA_AUDITORIA", "Auditoria consultada.");
    }

    // ============================================================
    //  HELPERS
    // ============================================================
    static void log(String user, String acao, String detalhe) {
        auditLog.add(new Auditoria(user, acao, detalhe));
    }

    static boolean isAdmin() {
        return sessao != null && sessao.getPerfil() == Utilizador.Perfil.ADMIN;
    }

    static boolean podeGerirCandidaturas() {
        return sessao != null && (
            sessao.getPerfil() == Utilizador.Perfil.ADMIN ||
            sessao.getPerfil() == Utilizador.Perfil.OPERADOR);
    }

    static void invalido() {
        System.out.println(RM + "  Opção inválida ou sem permissão." + R);
    }

    static String ler(String label) {
        System.out.print("  " + label + ": ");
        return sc.nextLine().trim();
    }

    static int lerInt(String label) {
        while (true) {
            System.out.print("  " + label + ": ");
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println(RM + "  Valor numérico inválido." + R);
            }
        }
    }

    static int lerIntRaw() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println(RM + "  Valor inválido, tente novamente: " + R);
            }
        }
    }

    static Beneficiario porId(int id) {
        return beneficiarios.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    static void pausar(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}