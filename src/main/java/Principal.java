
import javax.swing.JOptionPane;

public class Principal {

    //Constante para tamanho limite dos vetores
    static int n = 100;

    //Vetores e contador declarados fora das subrotinas
    //Registra em vetores todos os documentos cadastrados
    static int documentos[] = new int[n];

    //Registra em vetores todos os beneficiarios cadastrados
    static String beneficiarios[] = new String[n];

    //Registra em vetores as descrições de cada conta cadastrada
    static String descs[] = new String[n];

    //Registra em vetores todos os valores cadastrados
    static double valores[] = new double[n];

    //Registra em vetores todas as datas de vencimento cadastradas
    static String vencimentos[] = new String[n];

    //Controla e registra o número de contas cadastradas
    static int total = 0;

    //Variavel de verificação se um dado é ou não valido
    static boolean valido = false;

    //Variavel que define o número do documento
    static int documento;

    //Variavel reponsavel por confirmar(S/N) um procedimento
    static char resposta = 0;

    //Variaveis responsaveis pela escolha de opçoes nos menus
    static int menuprincipal, cadcontas;

    public static void main(String[] args) {

        // Variaveis:
        int baicontas, relatorios;
        // baicontas: baixa de conta

        //Controla e registra o número de contas cadastradas
        do {

            menuPrincipal();

            // Verificação da opção escolhida
            switch (menuprincipal) {
                case 1: {
                    do {

                        menuCadContas();

                        // Verificação da opção escolhida para o menu de Cadastro de Contas
                        switch (cadcontas) {
                            case 1: {
                                incluirConta();
                                break;
                            }
                            case 2: {
                                alterarConta();
                                break;
                            }
                            case 3: {
                                Biblioteca.consultarConta(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 4: {
                                Biblioteca.ExcConta(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }

                            case 0: {
                                JOptionPane.showMessageDialog(null, "Retornando...");
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Opção Inexistente!");
                                break;
                            }
                        }

                    } while (cadcontas != 0);
                }
                break;

                case 2: {
                    baixaconta();
                    break;
                }
                case 3: {

                    // Menu de Relatorio de Contas
                    relatorios = -1;
                    do {
                        String entradaRel = JOptionPane.showInputDialog("XYZ COMÉRCIO DE PRODUTOS LTDA.\n"
                                + "SISTEMA DE CONTAS A PAGAR\n\n"
                                + "RELATÓRIOS\n\n"
                                + "1 - Contas a pagar\n"
                                + "2 - Contas atrasadas\n"
                                + "3 - Balanço de despesas\n"
                                + "0 - Retornar\n\n"
                                + "Digite a opção desejada:");

                        if (entradaRel == null) {
                            relatorios = 0;
                        } else {
                            entradaRel = entradaRel.trim();
                            valido = !entradaRel.isEmpty();

                            for (int i = 0; i < entradaRel.length(); i++) {
                                if (!Character.isDigit(entradaRel.charAt(i))) {
                                    valido = false;
                                    break;
                                }
                            }

                            if (valido) {
                                relatorios = Integer.parseInt(entradaRel);
                            } else {
                                relatorios = -1;
                            }
                        }

                        // Verificação da opção escolhida para o menu de Relatorio de Contas
                        switch (relatorios) {
                            case 1: {
                                Biblioteca.relatorioContasAPagar(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 2: {
                                Biblioteca.relatorioContasVencidas(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 3: {
                                Biblioteca.relatorioBalancoDespesas(beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 0: {
                                relatorios = 0;
                                JOptionPane.showMessageDialog(null, "Retornando...");
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Opção Inexistente!");
                                break;
                            }
                        }
                    } while (relatorios != 0);
                    break;
                }

                // Outras Opções
                case 0: {
                    JOptionPane.showMessageDialog(null, "Saindo do Programa.");
                    break;
                }

                default: {
                    JOptionPane.showMessageDialog(null, "Opção Inexistente!");
                    break;
                }
            }
            // Fim de Repetição
        } while (menuprincipal != 0);

    }

    /**
     * Gerencia o fluxo completo de inclusão de novas contas a pagar (Tela
     * 1.1.1), coordenando a leitura, validação, confirmação e armazenamento nos
     * vetores.
     */
    public static void incluirConta() {
        char novocad = 0;
        do {
            documento = lerDoc();
            if (documento == -1) {
                break;
            }

            String desc = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "INCLUSÃO DE CONTA\n\n"
                    + "Adicione uma descrição a conta:");
            if (desc == null) {
                break;
            }

            String beneficiario = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "INCLUSÃO DE CONTA\n\n"
                    + "Informe o nome do beneficiário:");
            if (beneficiario == null) {
                break;
            }

            double valor = lerValorValido();
            if (valor == -1) {
                break;
            }

            String vencimento = lerDataValida();
            if (vencimento == null) {
                break;
            }

            if (confirmaInclusao(documento, desc, beneficiario, valor, vencimento)) {
                salvaConta(documento, desc, beneficiario, valor, vencimento);
                JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Inclusão cancelada, os dados não foram salvos!");
            }

            String confirmanovocad = JOptionPane.showInputDialog("Deseja cadastrar outra conta? (S/N)");
            if (confirmanovocad != null && !confirmanovocad.trim().isEmpty()) {
                novocad = confirmanovocad.charAt(0);
            }

        } while (novocad == 's' || novocad == 'S');
    }

    /**
     * Pesquisa um número de documento no vetor de documentos cadastrados.
     *
     * @param pesquisaDoc O número do documento que se deseja localizar.
     * @return O índice (posição) do documento no vetor, ou -1 caso não seja
     * encontrado.
     */
    public static int buscaDoc(int pesquisaDoc) {
        for (int i = 0; i < total; i++) {
            if (documentos[i] == pesquisaDoc) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Solicita ao usuário o número do documento via caixa de diálogo, validando
     * se a entrada contém apenas números e se o documento ainda não existe.
     *
     * @return O número do documento validado, ou -1 caso o usuário cancele a
     * operação.
     */
    public static int lerDoc() {
        documento = -1;
        valido = false;

        while (valido == false) {
            String entradadoc = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "INCLUSÃO DE CONTA\n\n"
                    + "Informe o número do Documento:");
            if (entradadoc == null) {
                return -1;
            }

            entradadoc = entradadoc.trim();
            if (!entradadoc.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Digite um número de documento válido!");
                continue;
            }

            documento = Integer.parseInt(entradadoc);

            if (buscaDoc(documento) != -1) {
                JOptionPane.showMessageDialog(null, "Documento já cadastrado, tente outro.");
                continue;
            }
            valido = true;
        }
        return documento;
    }

    /**
     * Solicita e valida o valor monetário da conta, garantindo que seja um
     * número válido (aceitando ponto ou vírgula) e maior que zero.
     *
     * @return O valor da conta validado, ou -1 caso o usuário cancele a
     * operação.
     */
    public static double lerValorValido() {
        double valor = -1;
        valido = false;
        while (valido == false) {
            String entradavalor = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "INCLUSÃO DE CONTA\n\n"
                    + "Informe o valor (R$):  ");
            if (entradavalor == null) {
                return -1;
            }

            entradavalor = entradavalor.trim();

            if (!entradavalor.matches("\\d+([\\.,]\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Digite um valor válido!");
                continue;
            }

            valor = Double.parseDouble(entradavalor.replace(",", "."));

            if (valor <= 0) {
                JOptionPane.showMessageDialog(null, "O valor deve ser maior que R$0.00");
                continue;
            }
            valido = true;
        }
        return valor;
    }

    /**
     * Solicita e valida a data de vencimento da conta no formato DD/MM/AAAA,
     * verificando os limites válidos de dia, mês e ano.
     *
     * @return A data de vencimento formatada e validada, ou null caso o usuário
     * cancele.
     */
    public static String lerDataValida() {
        String vencimento = "";
        valido = false;

        while (valido == false) {
            vencimento = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "INCLUSÃO DE CONTA\n\n"
                    + "Informe a data de vencimento:\n"
                    + "(DD/MM/AAAA):");

            if (vencimento == null) {
                return null;
            }

            vencimento = vencimento.trim();
            if (!vencimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(null, "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                        + "INCLUSÃO DE CONTA\n\n"
                        + "Informe uma data válida no formato (DD/MM/AAAA).");
                continue;
            }

            int dia = Integer.parseInt(vencimento.substring(0, 2));
            int mes = Integer.parseInt(vencimento.substring(3, 5));
            int ano = Integer.parseInt(vencimento.substring(6, 10));

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 1900 || ano > 2100) {
                JOptionPane.showMessageDialog(null, "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                        + "INCLUSÃO DE CONTA\n\n"
                        + "Data inválida! Verifique o dia, mês e ano.");
                continue;
            }
            valido = true;
        }
        return vencimento;
    }

    /**
     * Exibe um resumo com todos os dados preenchidos da conta e solicita a
     * confirmação do usuário (S/N) antes de efetivar o cadastro.
     *
     * @param documento Número do documento da conta.
     * @param desc Descrição da despesa.
     * @param beneficiario Nome do beneficiário do pagamento.
     * @param valor Valor da despesa.
     * @param vencimento Data de vencimento no formato DD/MM/AAAA.
     * @return true se o usuário confirmar com 'S', ou false caso recuse ou
     * cancele.
     */
    public static boolean confirmaInclusao(int documento, String desc, String beneficiario, double valor, String vencimento) {
        String msgconfirma = String.format("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + "INCLUSÃO DE CONTA\n\n"
                + "Documento: %d\n"
                + "Descrição: %s\n"
                + "Beneficiário: %s\n"
                + "Valor: R$ %.2f\n"
                + "Vencimento: %s\n\n"
                + "CONFIRMA INCLUSÃO? (S/N)", documento, desc, beneficiario, valor, vencimento);

        String confirmaconta = JOptionPane.showInputDialog(msgconfirma);

        if (confirmaconta != null && !confirmaconta.trim().isEmpty()) {
            char confirmainc = Character.toUpperCase(confirmaconta.trim().charAt(0));
            return confirmainc == 'S';
        }
        return false;
    }

    /**
     *
     * @param documento Número do documento da conta.
     * @param desc Descrição da despesa.
     * @param beneficiario Nome do beneficiário do pagamento.
     * @param valor Valor da despesa.
     * @param vencimento Data de vencimento no formato DD/MM/AAAA.
     */
    public static void salvaConta(int documento, String desc, String beneficiario, double valor, String vencimento) {
        documentos[total] = documento;
        descs[total] = desc;
        beneficiarios[total] = beneficiario;
        valores[total] = valor;
        vencimentos[total] = vencimento;
        total++;
    }

    /**
     * Solicita ao usuário o número do documento para Baixar a conta via caixa
     * de diálogo, validando se a entrada contém apenas dígitos numéricos.
     *
     * @return O número do documento validado, ou 'N' caso o usuário cancele a
     * operação.
     */
    public static void baixaconta() {
        char NovaBaixa = 'N';
        do {
            String baixacontas = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "SISTEMA DE CONTAS A PAGAR\n\n"
                    + "MOVIMENTAÇÃO - BAIXA DE CONTA\n\n"
                    + "Informe o número do documento a ser movimentado:");
            if (baixacontas == null) {
                break;
            }
            baixacontas = baixacontas.trim();
            valido = !baixacontas.isEmpty();

            for (int i = 0; i < baixacontas.length(); i++) {
                if (!Character.isDigit(baixacontas.charAt(i))) {
                    valido = false;
                    break;
                }
            }
            if (!valido) {
                JOptionPane.showMessageDialog(null, "Por favor, digite um número de documento válido.");
                NovaBaixa = 'S';
                continue;
            }

            int contabaixa = Integer.parseInt(baixacontas);
            int indiceencontrado = -1;
            for (int i = 0; i < total; i++) {
                if (documentos[i] == contabaixa) {
                    indiceencontrado = i;
                    break;
                }
            }
            double valorfinal = 0;
            if (indiceencontrado != -1) {
                String msg = ("XYZ COMERCIO DE PRODUTOS LTDA.\n")
                        + "SISTEMA DE CONTAS A PAGAR\n\n"
                        + "MOVIMENTAÇÃO - BAIXA DE CONTA\n\n"
                        + "Documento: " + documentos[indiceencontrado] + "\n"
                        + "Descrição: " + descs[indiceencontrado] + "\n"
                        + "Beneficiário: " + beneficiarios[indiceencontrado] + "\n"
                        + "Valor atual: R$" + String.format("%.2f", valores[indiceencontrado]) + "\n";

                double valordiminuir = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a diminuir:"));
                JOptionPane.showMessageDialog(null, msg);
                char MovimentacaoConta = JOptionPane.showInputDialog(null, "Você confirma essa ação? (S/N):").charAt(0);
                if (MovimentacaoConta == 'S' || MovimentacaoConta == 's') {
                    for (int i = indiceencontrado; i < total; i++) {
                        valorfinal = valores[indiceencontrado] - valordiminuir;
                        valores[indiceencontrado] = valorfinal;
                        JOptionPane.showMessageDialog(null, "Conta baixada com sucesso!");
                    }
                } else {
                    if (MovimentacaoConta == 'N' || MovimentacaoConta == 'n') {
                        JOptionPane.showMessageDialog(null, "Baixa de conta cancelada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Documento não encontrado!"
                        + "");
            }
            String resp = JOptionPane.showInputDialog("Deseja fazer outra baixa? (S/N)");
            if (resp != null && !resp.trim().isEmpty()) {
                NovaBaixa = Character.toUpperCase(resp.trim().charAt(0));
            } else {
                NovaBaixa = 'N';
            }
        } while (NovaBaixa == 'S');
    }

    /**
     * Exibe o menu principal do sistema de contas a pagar, permitindo ao
     * usuário selecionar entre as opções de cadastro de contas, baixa de conta,
     * relatórios ou saída do programa.
     *
     * @return A opção escolhida pelo usuário.
     */
    public static int menuPrincipal() {
        // Menu Principal
        menuprincipal = -1;

        menuprincipal = Integer.parseInt(JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + "MENU PRINCIPAL\n\n"
                + "1 - Cadastro de Contas\n"
                + "2 - Baixa de Conta\n"
                + "3 - Relatórios\n"
                + "0 - Sair do Programa\n\n"
                + "Digite a opção desejada:"));
        return (menuprincipal);
    }

    /**
     * Exibe o menu de cadastro de contas, permitindo ao usuário selecionar
     * entre as opções de inclusão, alteração, consulta, exclusão de contas ou
     * retorno ao menu principal.
     *
     * @return A opção escolhida pelo usuário.
     */
    public static int menuCadContas() {
        // Menu de Cadastro de Contas
        cadcontas = -1;

        cadcontas = Integer.parseInt(JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + "CADASTRO DE CONTAS\n\n"
                + "1 - Inclusão\n"
                + "2 - Alteração\n"
                + "3 - Consulta\n"
                + "4 - Exclusão\n"
                + "0 - Retornar\n\n"
                + "Digite a opção desejada:"));
        return (cadcontas);
    }

    /**
     * Gerencia o processo de alteração de contas a pagar, solicitando o número
     * do documento, localizando a conta cadastrada, permitindo a modificação de
     * seus dados e solicitando a confirmação do usuário antes de salvar as
     * alterações.
     */
    public static void alterarConta() {
        char novaAlteracao = 'N';

        do {
            String entradaDoc = JOptionPane.showInputDialog(
                    "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "SISTEMA DE CONTAS A PAGAR\n\n"
                    + "ALTERAÇÃO DE CONTA\n\n"
                    + "Informe o número do documento da conta:");

            if (entradaDoc == null) {
                break;
            }

            entradaDoc = entradaDoc.trim();

            // Validação do documento
            valido = true;

            for (int i = 0; i < entradaDoc.length(); i++) {
                if (Character.isDigit(entradaDoc.charAt(i)) == false) {
                    valido = false;
                    break;
                }
            }

            if (valido == false) {
                JOptionPane.showMessageDialog(null,
                        "Por favor, digite um número de documento válido!");
                continue;
            }

            documento = Integer.parseInt(entradaDoc);

            // Busca da conta cadastrada
            int indice = buscaDoc(documento);

            if (indice == -1) {
                JOptionPane.showMessageDialog(null,
                        "Documento não encontrado.");
            } else {

                // Exibe os dados atuais da conta
                Biblioteca.exibirConta(documentos, descs, beneficiarios, valores, vencimentos, indice);

                // Solicita os novos dados
                String novaDesc = JOptionPane.showInputDialog(
                        "Informe a nova descrição:",
                        descs[indice]);

                if (novaDesc == null) {
                    break;
                }

                String novoBeneficiario = JOptionPane.showInputDialog(
                        "Informe o novo beneficiário:",
                        beneficiarios[indice]);

                if (novoBeneficiario == null) {
                    break;
                }

                double novoValor = lerValorValido();

                if (novoValor == -1) {
                    break;
                }

                String novoVencimento = lerDataValida();

                if (novoVencimento == null) {
                    break;
                }

                // Exibe os novos dados para confirmação
                String mensagem = String.format(
                        "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                        + "SISTEMA DE CONTAS A PAGAR\n\n"
                        + "ALTERAÇÃO DE CONTA\n\n"
                        + "Documento: %d\n"
                        + "Descrição: %s\n"
                        + "Beneficiario: %s\n"
                        + "Valor: R$ %.2f\n"
                        + "Vencimento: %s\n\n"
                        + "CONFIRMA A ALTERAÇÃO? (S/N)",
                        documentos[indice],
                        novaDesc,
                        novoBeneficiario,
                        novoValor,
                        novoVencimento);

                String resposta = JOptionPane.showInputDialog(mensagem);

                if (resposta != null && !resposta.trim().isEmpty()
                        && Character.toUpperCase(resposta.trim().charAt(0)) == 'S') {

                    // Atualiza os dados nos vetores
                    descs[indice] = novaDesc;
                    beneficiarios[indice] = novoBeneficiario;
                    valores[indice] = novoValor;
                    vencimentos[indice] = novoVencimento;

                    JOptionPane.showMessageDialog(null,
                            "Conta alterada com sucesso!");

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Alteração cancelada! Os dados não foram modificados.");
                }
            }

            // Verifica se deseja alterar outra conta
            String resp = JOptionPane.showInputDialog(
                    "Deseja alterar outra conta? (S/N)");

            if (resp != null && !resp.trim().isEmpty()) {
                novaAlteracao = Character.toUpperCase(
                        resp.trim().charAt(0));
            } else {
                novaAlteracao = 'N';
            }

        } while (novaAlteracao == 'S');
    }
}
