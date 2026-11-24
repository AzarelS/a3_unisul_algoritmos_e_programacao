
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
    
    //Registra se um determinado procedimento é valido
    static boolean valido = false;

    //Variavel que define o número do documento
    static int documento;

    //Variavel reponsavel por confirmar(S/N) um procedimento
    static char resposta = 0;
    
    //Variaveis responsaveis pela escolha de opçoes nos menus
    static int menuprincipal, cadcontas, relatorios;

    public static void main(String[] args) {

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
                                total = Biblioteca.incluirConta(documentos, descs, beneficiarios, valores, vencimentos, total);
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
                                ExcConta();
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
                    Biblioteca.baixaconta(documentos, descs, beneficiarios, valores, vencimentos, total);
                    break;
                }
                case 3: {

                    // Menu de Relatorio de Contas
                    relatorios = -1;
                    do {

                        menuRelatorios();

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
     * Exibe o menu de relatórios do sistema de contas a pagar, permitindo ao
     * usuário selecionar entre as opções de contas a pagar, contas atrasadas,
     * balanço de despesas ou retornar ao menu anterior.
     *
     * @return A opção escolhida pelo usuário.
     */
    public static int menuRelatorios() {
        relatorios = Integer.parseInt(JOptionPane.showInputDialog("XYZ COMÉRCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + "RELATÓRIOS\n\n"
                + "1 - Contas a pagar\n"
                + "2 - Contas atrasadas\n"
                + "3 - Balanço de despesas\n"
                + "0 - Retornar\n\n"
                + "Digite a opção desejada:"));
        return (relatorios);
    }
    /**
     * Gerencia o processo de alteração de contas a pagar, solicitando o número
     * do documento, localizando a conta cadastrada, permitindo a modificação de
     * seus dados e solicitando a confirmação do usuário antes de salvar as
     * alterações.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade de contas cadastradas.
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
            int indice = buscaDoc(documentos, total, documento);

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
    /**
     * Solicita ao usuário o número do documento para excluir a conta via caixa
     * de diálogo, validando se a entrada contém apenas dígitos numéricos.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade atual de contas cadastradas.
     * @return O número do documento validado, ou 'N' caso o usuário cancele a
     * operação.
     */
    public static int ExcConta() {

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada no sistema.");
            return total;
        }

        char NovaExc = 'N';
        do {
            String ContaExc = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "SISTEMA DE CONTAS A PAGAR\n\n"
                    + "EXCLUSÃO DE CONTA\n\n"
                    + "Informe o número do documento da conta a ser excluída:");
            if (ContaExc == null) {
                break;
            }
            ContaExc = ContaExc.trim();

            valido = !ContaExc.isEmpty();

            for (int i = 0; i < ContaExc.length(); i++) {
                if (!Character.isDigit(ContaExc.charAt(i))) {
                    valido = false;
                    break;
                }
            }

            if (!valido) {
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
                String msg = "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                        + "SISTEMA DE CONTAS A PAGAR\n\n"
                        + "EXCLUSÃO DE CONTA\n\n"
                        + "Documento: " + documentos[indiceEncontrado] + "\n"
                        + "Descrição: " + descs[indiceEncontrado] + "\n"
                        + "Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                        + "Valor: R$" + String.format("%.2f", valores[indiceEncontrado]) + "\n"
                        + "Vencimento: " + vencimentos[indiceEncontrado] + "\n";
                JOptionPane.showMessageDialog(null, msg);

                String confirmacao = JOptionPane.showInputDialog(null, "Você confirma essa ação? (S/N)");
                if (confirmacao != null && !confirmacao.trim().isEmpty()) {
                    char DelConta = Character.toUpperCase(confirmacao.trim().charAt(0));
                    if (DelConta == 'S') {
                        for (int i = indiceEncontrado; i < total - 1; i++) {
                            documentos[i] = documentos[i + 1];
                            descs[i] = descs[i + 1];
                            beneficiarios[i] = beneficiarios[i + 1];
                            valores[i] = valores[i + 1];
                            vencimentos[i] = vencimentos[i + 1];
                        }
                        total--;
                        JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");
                    } else if (DelConta == 'N') {
                        JOptionPane.showMessageDialog(null, "EXCLUSÃO CANCELADA!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EXCLUSÃO CANCELADA!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Documento não encontrado!");
            }

            if (total == 0) {
                break;
            }

            String resp = JOptionPane.showInputDialog("Deseja fazer outra Exclusão? (S/N)?");

            if (resp != null && !resp.trim().isEmpty()) {
                NovaExc = Character.toUpperCase(resp.trim().charAt(0));
            } else {
                NovaExc = 'N';
            }

        } while (NovaExc == 'S');
        return total;
    }
}
