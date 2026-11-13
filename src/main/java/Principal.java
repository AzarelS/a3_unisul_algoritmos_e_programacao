
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

    public static void main(String[] args) {

        // Variaveis:
        int menuprincipal, cadcontas, inccontas, altcontas, concontas, excontas, baicontas, relatorios;
        char resposta;
        // resposta = variavel tipo char para responder S/N
        // cadcontas: cadastro de  contas
        // inccontas: inclusão de conta
        // altcontas: alteração de conta
        // concontas: consulta de conta
        // excontas: exclusão de conta
        // baicontas: baixa de conta

        //Controla e registra o número de contas cadastradas
        menuprincipal = -1;

        do {

            // Menu Principal
            menuprincipal = Integer.parseInt(JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "Sistema de Contas a Pagar\n"
                    + "\n"
                    + "Menu Principal\n"
                    + "\n"
                    + "1 - Cadastro de Contas\n"
                    + "2 - Baixa de Conta\n"
                    + "3 - Relatótios\n"
                    + "0 - Sair de Programa\n"
                    + "\n"
                    + "Digite a opção desejada: \n"));

            // Verificação da opção escolhida
            switch (menuprincipal) {
                case 1: {
                    do {
                        // Menu de Cadastro de Contas
                        cadcontas = -1;

                        cadcontas = Integer.parseInt(JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                + "Sistema de Contas a Pagar\n"
                                + "\n"
                                + "Cadastro de Contas\n"
                                + "\n"
                                + "1 - Inclusão\n"
                                + "2 - Alteração\n"
                                + "3 - Consulta\n"
                                + "4 - Exclusão\n"
                                + "0 - Retornar\n"
                                + "\n"
                                + "Digite a opção desejada: \n"));

                        // Verificação da opção escolhida para o menu de Cadastro de Contas
                        switch (cadcontas) {
                            case 1: {
                                incluirConta();
                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                // CONSULTA DE CONTA
                                char novaconsulta = 'N';
                                do {
                                    String entradadoc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA.\n"
                                            + "SISTEMA DE CONTAS A PAGAR\n\n"
                                            + "CONSULTA DE CONTA\n\n"
                                            + "Informe o número do documento para consulta:");

                                    if (entradadoc == null) {
                                        break;
                                    }

                                    entradadoc = entradadoc.trim();

                                    boolean ehNumero = !entradadoc.isEmpty();

                                    for (int i = 0; i < entradadoc.length(); i++) {
                                        if (!Character.isDigit(entradadoc.charAt(i))) {
                                            ehNumero = false;
                                            break;
                                        }
                                    }

                                    if (!ehNumero) {
                                        JOptionPane.showMessageDialog(null, "Por favor, digite um número de documento válido.");
                                        novaconsulta = 'S';
                                        continue;
                                    }

                                    int docConsulta = Integer.parseInt(entradadoc);

                                    int indiceEncontrado = -1;

                                    for (int i = 0; i < total; i++) {
                                        if (documentos[i] == docConsulta) {
                                            indiceEncontrado = i;
                                            break;
                                        }
                                    }

                                    if (indiceEncontrado != -1) {
                                        String msg = ("\t\t XYZ COMERCIO DE PRODUTOS LTDA.\n")
                                                + "\tSISTEMA DE CONTAS A PAGAR\n\n"
                                                + "\tCONSULTA DE CONTA\n\n"
                                                + "Documento: " + documentos[indiceEncontrado] + "\n"
                                                + "Descrição: " + descs[indiceEncontrado] + "\n"
                                                + "Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                                                + "Valor: R$ " + valores[indiceEncontrado] + "\n"
                                                + "Vencimento: " + vencimentos[indiceEncontrado] + "\n";
                                        JOptionPane.showMessageDialog(null, msg);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Documento não encontrado");
                                    }

                                    String resp = JOptionPane.showInputDialog("Deseja fazer uma nova consulta? (S/N)?");

                                    if (resp != null && !resp.trim().isEmpty()) {
                                        novaconsulta = Character.toUpperCase(resp.trim().charAt(0));
                                    } else {
                                        novaconsulta = 'N';
                                    }

                                } while (novaconsulta == 'S');

                                break;
                            }

                            case 4: {
                                char NovaExc = 'N';
                                do {
                                    String ContaExc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA.\n"
                                            + "SISTEMA DE CONTAS A PAGAR\n"
                                            + "EXCLUIR CONTA\n"
                                            + "INFORME O NUMERO DO DOCUMENTO A SER EXCLUIDA: ");
                                    if (ContaExc == null) {
                                        break;
                                    }
                                    ContaExc = ContaExc.trim();

                                    boolean ehNumero = !ContaExc.isEmpty();

                                    for (int i = 0; i < ContaExc.length(); i++) {
                                        if (!Character.isDigit(ContaExc.charAt(i))) {
                                            ehNumero = false;
                                            break;
                                        }
                                    }

                                    if (!ehNumero) {
                                        JOptionPane.showMessageDialog(null, "Por favor, digite um número de documento válido.");
                                        NovaExc = 'S';
                                        continue;
                                    }

                                    int docExc = Integer.parseInt(ContaExc);

                                    int indiceEncontrado = -1;

                                    for (int i = 0; i < total; i++) {
                                        if (documentos[i] == docExc) {
                                            indiceEncontrado = i;
                                            break;
                                        }
                                    }

                                    if (indiceEncontrado != -1) {
                                        String msg = ("\t\t XYZ COMERCIO DE PRODUTOS LTDA.\n")
                                                + "\tSISTEMA DE CONTAS A PAGAR\n\n"
                                                + "\tEXCLUSÃO DE CONTA\n\n"
                                                + "Documento: " + documentos[indiceEncontrado] + "\n"
                                                + "Descrição: " + descs[indiceEncontrado] + "\n"
                                                + "Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                                                + "Valor: R$ " + valores[indiceEncontrado] + "\n"
                                                + "Vencimento: " + vencimentos[indiceEncontrado] + "\n";
                                        JOptionPane.showMessageDialog(null, msg);
                                        char DelConta = JOptionPane.showInputDialog(null, "CERTEZA EM EXCLUIR CONTA S/N: ").charAt(0);
                                        if (DelConta == 'S' || DelConta == 's') {
                                            for (int i = indiceEncontrado; i < total - 1; i++) {
                                                documentos[i] = documentos[i + 1];
                                                descs[i] = descs[i + 1];
                                                beneficiarios[i] = beneficiarios[i + 1];
                                                valores[i] = valores[i + 1];
                                                vencimentos[i] = vencimentos[i + 1];
                                            }
                                            total--;
                                            JOptionPane.showMessageDialog(null, "CONTA EXCLUÍDA COM SUCESSO!");
                                        } else {
                                            if (DelConta == 'N' || DelConta == 'n') {
                                                JOptionPane.showMessageDialog(null, "EXCLUÇÃO CANCELADA");
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Opção invalida");
                                            }
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Documento não encontrado");
                                    }

                                    String resp = JOptionPane.showInputDialog("Deseja fazer uma outra Exclução? (S/N)?");

                                    if (resp != null && !resp.trim().isEmpty()) {
                                        NovaExc = Character.toUpperCase(resp.trim().charAt(0));
                                    } else {
                                        NovaExc = 'N';
                                    }

                                } while (NovaExc == 'S');
                                break;
                            }

                            case 0: {
                                cadcontas = 0;
                                JOptionPane.showMessageDialog(null, "Voltando");
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Opção Inexistente");
                                break;
                            }
                        }

                    } while (cadcontas != 0);
                }
                break;

                case 2: {
                    // Menu de Baixa de Contas
                    baicontas = -1;
                    do {
                        baicontas = Integer.parseInt(JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                + "Sistema de Contas a Pagar\n"
                                + "\n"
                                + "Movimentação - Baixa de Conta\n"
                                + "\n"
                                + "Documento :\n"
                                + "Valor atual :\n"
                                + "\n"
                                + "Valor pago :\n"
                                + "\n"
                                + "Valor final :\n"
                                + "\n"
                                + "Confirmar baixa (S/N)? __\n"
                                + "\n"
                                + "Nova baixa (S/N)? __\n"));

                    } while (baicontas != 0);

                    break;
                }
                case 3: {

                    // Menu de Cadastro de Contas
                    relatorios = -1;
                    do {
                        relatorios = Integer.parseInt(JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                + "Sistema de Contas a Pagar\n"
                                + "\n"
                                + "Relatórios\n"
                                + "\n"
                                + "1 - Contas a pagar\n"
                                + "2 - Contas atrasadas\n"
                                + "3 - Balanço de despesas\n"
                                + "0 - Retornar\n"
                                + "\n"
                                + "Digite a opção desejada: \n"));

                        // Verificação da opção escolhida para o menu de Cadastro de Contas
                        switch (relatorios) {
                            case 1: {
                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 0: {
                                relatorios = 0;
                                JOptionPane.showMessageDialog(null, "Retornando...");
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Opção Inexistente");
                                break;
                            }
                        }
                    } while (relatorios != 0);

                }
                break;

                // Outras Opções
                case 0: {
                    JOptionPane.showMessageDialog(null, "Saindo do Programa");
                    break;
                }

                default: {
                    JOptionPane.showMessageDialog(null, "Opção Inexistente");
                    break;
                }

            }

            // Fim de Repetição
        } while (menuprincipal != 0);

    }

    /**
     * Gerencia o fluxo completo de inclusão de novas contas a pagar (Tela
     * 1.1.1), coordenando a leitura, validação, confirmação e armazenamento nos vetores.
     */
    public static void incluirConta() {
        char novocad = 0;
        do {
            int documento = lerDoc();
            if (documento == -1) {
                break;
            }

            String desc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "--Inclusão de conta--\n\n"
                    + "Adicione uma descrição a conta: ");
            if (desc == null) {
                break;
            }

            String beneficiario = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "--Inclusão de conta--\n\n"
                    + "Informe o beneficiário: ");
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
                JOptionPane.showMessageDialog(null, "Inclusão cancelada, os dados não foram salvos.");
            }

            String confirmanovocad = JOptionPane.showInputDialog("Deseja cadastrar outra conta: ( S/N ) ?");
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
        int documento = -1;
        boolean valido = false;

        while (valido == false) {
            String entradadoc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "--Inclusão de conta--\n\n"
                    + "Informe o número do Documento: ");
            if (entradadoc == null) {
                return -1;
            }

            entradadoc = entradadoc.trim();
            if (!entradadoc.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Digite um número de documento válido.");
                continue;
            }

            documento = Integer.parseInt(entradadoc);

            if (buscaDoc(documento) != -1) {
                JOptionPane.showMessageDialog(null, "Documento ja cadastrado, tente outro.");
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
        boolean valido = false;
        while (valido == false) {
            String entradavalor = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "--Inclusão de conta--\n\n"
                    + "Informe o valor: ");
            if (entradavalor == null) {
                return -1;
            }

            entradavalor = entradavalor.trim();

            if (!entradavalor.matches("\\d+([\\.,]\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Digite um valor válido.");
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
        boolean valido = false;

        while (valido == false) {
            vencimento = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "--Inclusão de conta--\n\n"
                    + "Informe a data de vencimento\n"
                    + "(DD/MM/AAAA): ");

            if (vencimento == null) {
                return null;
            }

            vencimento = vencimento.trim();
            if (!vencimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(null, "\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                        + "--Inclusão de conta--\n\n"
                        + "Informe uma data válida no formato (DD/MM/AAAA).");
                continue;
            }

            int dia = Integer.parseInt(vencimento.substring(0, 2));
            int mes = Integer.parseInt(vencimento.substring(3, 5));
            int ano = Integer.parseInt(vencimento.substring(6, 10));

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 1900 || ano > 2100) {
                JOptionPane.showMessageDialog(null, "\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                        + "--Inclusão de conta--\n\n"
                        + "Data inválida verifique o dia mês e ano.");
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
        String msgconfirma = String.format("\t XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n"
                + "INCLUSÃO DE CONTA\n"
                + " DOCUMENTO : %d\n"
                + " DESCRIÇÃO : %s\n"
                + " BENEFICIÁRIO : %s\n"
                + " VALOR : %.2f\n"
                + " VENCIMENTO : %s\n\n"
                + "CONFIRMA INCLUSÃO ( S/N ) ?", documento, desc, beneficiario, valor, vencimento);

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
}
