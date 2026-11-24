
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class Biblioteca {

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

    //Variavel reponsavel por confirmar(S/N) um procedimento
    static char resposta = 0;

    //Variavel que define o número do documento
    static int documento;

    /**
     * Converte uma data no formato DD/MM/AAAA para um inteiro AAAAMMDD, para
     * que as datas mais recentes sejam maiores que datas mais antigas evitando
     * conflito.
     *
     * @param data Data no formato DD/MM/AAAA.
     * @return Inteiro no formato AAAAMMDD.
     */
    public static int converterDataParaInt(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return (ano * 10000) + (mes * 100) + dia;
    }

    /**
     * Obtém a data atual do sistema convertida no formato inteiro AAAAMMDD para
     * que a data atual seja maior que alguma data passada.
     *
     * @return Data de hoje no formato AAAAMMDD.
     */
    public static int obterDataAtualInt() {
        LocalDate hoje = LocalDate.now();
        return (hoje.getYear() * 10000) + (hoje.getMonthValue() * 100) + hoje.getDayOfMonth();
    }

    /**
     * Solicita e valida uma data no formato DD/MM/AAAA exibindo uma mensagem
     * personalizada ao usuário conforme contexto necessário da solicitação.
     *
     * @param mensagem Texto exibido na caixa de diálogo.
     * @return A data validada no formato DD/MM/AAAA, ou null caso o usuário
     * cancele.
     */
    public static String lerData(String mensagem) {
        String data = "";
        valido = false;

        while (!valido) {
            data = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                    + "SISTEMA DE CONTAS A PAGAR\n\n"
                    + mensagem + "\n(DD/MM/AAAA): ");

            if (data == null) {
                return null;
            }

            data = data.trim();
            if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(null, "Informe uma data válida no formato (DD/MM/AAAA).");
                continue;
            }

            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, 10));

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 1900 || ano > 2100) {
                JOptionPane.showMessageDialog(null, "Data inválida, verifique o dia, mês e ano.");
                continue;
            }

            valido = true;
        }

        return data;
    }

    /**
     * Ordena os vetores de contas em ordem crescente de data de vencimento
     * usando metodo bubble sort ensinado em sala.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade de contas cadastradas.
     */
    public static void ordenarPorVencimento(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                int dataAtual = converterDataParaInt(vencimentos[j]);
                int dataProxima = converterDataParaInt(vencimentos[j + 1]);

                if (dataAtual > dataProxima) {
                    int auxDoc = documentos[j];
                    documentos[j] = documentos[j + 1];
                    documentos[j + 1] = auxDoc;

                    String auxDesc = descs[j];
                    descs[j] = descs[j + 1];
                    descs[j + 1] = auxDesc;

                    String auxBen = beneficiarios[j];
                    beneficiarios[j] = beneficiarios[j + 1];
                    beneficiarios[j + 1] = auxBen;

                    double auxVal = valores[j];
                    valores[j] = valores[j + 1];
                    valores[j + 1] = auxVal;

                    String auxVenc = vencimentos[j];
                    vencimentos[j] = vencimentos[j + 1];
                    vencimentos[j + 1] = auxVenc;
                }
            }
        }
    }

    /**
     * Exibe o relatório de contas a pagar por período em ordem crescente de
     * data de vencimento, considerando apenas contas com valor final maior que
     * zero.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores atuais das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade de contas cadastradas.
     */
    public static void relatorioContasAPagar(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada no sistema.");
            return;
        }

        String dataInicial = lerData("Informe a data INICIAL do período:");
        if (dataInicial == null) {
            return;
        }

        String dataFinal = lerData("Informe a data FINAL do período:");
        if (dataFinal == null) {
            return;
        }

        int dataInicialInt = converterDataParaInt(dataInicial);
        int dataFinalInt = converterDataParaInt(dataFinal);

        if (dataInicialInt > dataFinalInt) {
            JOptionPane.showMessageDialog(null, "A data inicial não pode ser maior que a data final.");
            return;
        }

        ordenarPorVencimento(documentos, descs, beneficiarios, valores, vencimentos, total);

        LocalDate hoje = LocalDate.now();
        String dataHoje = String.format("%02d/%02d/%04d", hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());

        String relatorio = "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n"
                + dataHoje + "   -   CONTAS A PAGAR POR PERÍODO (" + dataInicial + " a " + dataFinal + ")\n"
                + "----------------------------------------------------------------------------------------------------\n"
                + "VENCIMENTO  |  VALOR  |  DOCUMENTO  |  DESCRIÇÃO  |  BENEFICIÁRIO\n"
                + "----------------------------------------------------------------------------------------------------\n";

        double somaPeriodo = 0;
        int encontradas = 0;

        for (int i = 0; i < total; i++) {
            int dataVencInt = converterDataParaInt(vencimentos[i]);

            if (valores[i] > 0 && dataVencInt >= dataInicialInt && dataVencInt <= dataFinalInt) {
                relatorio += String.format("%s  |  R$ %.2f  |  %d  |  %s  |  %s\n",
                        vencimentos[i], valores[i], documentos[i], descs[i], beneficiarios[i]);
                somaPeriodo += valores[i];
                encontradas++;
            }
        }

        if (encontradas == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta a pagar encontrada no período informado.");
            return;
        }

        relatorio += "----------------------------------------------------------------------------------------------------\n"
                + String.format("TOTAL A PAGAR NO PERÍODO: R$ %.2f", somaPeriodo);

        JOptionPane.showMessageDialog(null, relatorio);
    }

    /**
     * Exibe o relatório de Contas Vencidas por Período em ordem crescente de
     * data de vencimento, considerando apenas contas com saldo devedor maior
     * que zero e com vencimento anterior à data atual.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores atuais das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade de contas cadastradas.
     */
    public static void relatorioContasVencidas(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada no sistema.");
            return;
        }

        String dataInicial = lerData("Informe a data INICIAL do período:");
        if (dataInicial == null) {
            return;
        }

        String dataFinal = lerData("Informe a data FINAL do período:");
        if (dataFinal == null) {
            return;
        }

        int dataIniInt = converterDataParaInt(dataInicial);
        int dataFimInt = converterDataParaInt(dataFinal);

        if (dataIniInt > dataFimInt) {
            JOptionPane.showMessageDialog(null, "A data inicial não pode ser maior que a data final.");
            return;
        }

        ordenarPorVencimento(documentos, descs, beneficiarios, valores, vencimentos, total);

        int dataHojeInt = obterDataAtualInt();
        LocalDate hoje = LocalDate.now();
        String dataHoje = String.format("%02d/%02d/%04d", hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());

        String relatorio = "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n"
                + dataHoje + "   -   CONTAS VENCIDAS POR PERÍODO (" + dataInicial + " a " + dataFinal + ")\n"
                + "----------------------------------------------------------------------------------------------------\n"
                + "VENCIMENTO  |  VALOR  |  DOCUMENTO  |  DESCRIÇÃO  |  BENEFICIÁRIO\n"
                + "----------------------------------------------------------------------------------------------------\n";

        double somaVencidas = 0;
        int encontradas = 0;

        for (int i = 0; i < total; i++) {
            int dataVencInt = converterDataParaInt(vencimentos[i]);

            if (valores[i] > 0 && dataVencInt < dataHojeInt && dataVencInt >= dataIniInt && dataVencInt <= dataFimInt) {
                relatorio += String.format("%s  |  R$ %.2f  |  %d  |  %s  |  %s\n",
                        vencimentos[i], valores[i], documentos[i], descs[i], beneficiarios[i]);
                somaVencidas += valores[i];
                encontradas++;
            }
        }

        if (encontradas == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta vencida encontrada no período informado.");
            return;
        }

        relatorio += "----------------------------------------------------------------------------------------------------\n"
                + String.format("TOTAL DE CONTAS VENCIDAS NO PERÍODO: R$ %.2f", somaVencidas);

        JOptionPane.showMessageDialog(null, relatorio);
    }

    /**
     * Exibe o relatório de Balanço de Despesas por Período, detalhando mês a
     * mês o total de despesas e o beneficiário com maior valor acumulado no
     * período.
     *
     * @param beneficiarios Vetor com os beneficiários das contas.
     * @param valores Vetor com os valores atuais das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade de contas cadastradas.
     */
    public static void relatorioBalancoDespesas(String[] beneficiarios, double[] valores,
            String[] vencimentos, int total) {

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada no sistema.");
            return;
        }

        String dataInicial = lerData("Informe a data INICIAL do período:");
        if (dataInicial == null) {
            return;
        }

        String dataFinal = lerData("Informe a data FINAL do período:");
        if (dataFinal == null) {
            return;
        }

        int dataIniInt = converterDataParaInt(dataInicial);
        int dataFimInt = converterDataParaInt(dataFinal);

        if (dataIniInt > dataFimInt) {
            JOptionPane.showMessageDialog(null, "A data inicial não pode ser maior que a data final.");
            return;
        }

        String[] nomesMeses = {
            "", "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO",
            "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"
        };

        int mesIni = Integer.parseInt(dataInicial.substring(3, 5));
        int anoIni = Integer.parseInt(dataInicial.substring(6, 10));
        int mesFim = Integer.parseInt(dataFinal.substring(3, 5));
        int anoFim = Integer.parseInt(dataFinal.substring(6, 10));

        String textoPeriodo = nomesMeses[mesIni] + "/" + anoIni + " A " + nomesMeses[mesFim] + "/" + anoFim;
        if (anoIni == anoFim) {
            textoPeriodo = nomesMeses[mesIni] + " A " + nomesMeses[mesFim] + " DE " + anoIni;
        }

        LocalDate hoje = LocalDate.now();
        String dataHoje = String.format("%02d/%02d/%04d", hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());

        String relatorio = "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + dataHoje + "          BALANÇO DE DESPESAS NO PERÍODO          PG 001\n"
                + "                    " + textoPeriodo + "\n"
                + "----------------------------------------------------------------------------------------------------\n"
                + "MÊS          |  TOTAL DE DESPESAS  |  MAIOR BENEFICIÁRIO\n"
                + "----------------------------------------------------------------------------------------------------\n";

        double somaGeral = 0;
        int mesAtual = mesIni;
        int anoAtual = anoIni;

        while (anoAtual < anoFim || (anoAtual == anoFim && mesAtual <= mesFim)) {
            double totalMes = 0;
            String maiorBeneficiario = "-";
            double maiorValor = 0;

            for (int i = 0; i < total; i++) {
                int dataVencInt = converterDataParaInt(vencimentos[i]);
                int mesConta = Integer.parseInt(vencimentos[i].substring(3, 5));
                int anoConta = Integer.parseInt(vencimentos[i].substring(6, 10));

                if (valores[i] > 0 && dataVencInt >= dataIniInt && dataVencInt <= dataFimInt
                        && mesConta == mesAtual && anoConta == anoAtual) {

                    totalMes += valores[i];

                    double somaBeneficiario = 0;
                    for (int j = 0; j < total; j++) {
                        int dataVencJ = converterDataParaInt(vencimentos[j]);
                        int mesJ = Integer.parseInt(vencimentos[j].substring(3, 5));
                        int anoJ = Integer.parseInt(vencimentos[j].substring(6, 10));

                        if (valores[j] > 0 && dataVencJ >= dataIniInt && dataVencJ <= dataFimInt
                                && mesJ == mesAtual && anoJ == anoAtual
                                && beneficiarios[j].equalsIgnoreCase(beneficiarios[i])) {
                            somaBeneficiario += valores[j];
                        }
                    }

                    if (somaBeneficiario > maiorValor) {
                        maiorValor = somaBeneficiario;
                        maiorBeneficiario = beneficiarios[i];
                    }
                }
            }

            String nomeLinhaMes = nomesMeses[mesAtual] + "/" + anoAtual;
            if (anoIni == anoFim) {
                nomeLinhaMes = nomesMeses[mesAtual];
            }

            relatorio += String.format("%s  |  R$ %.2f  |  %s\n", nomeLinhaMes, totalMes, maiorBeneficiario);
            somaGeral += totalMes;

            mesAtual++;
            if (mesAtual > 12) {
                mesAtual = 1;
                anoAtual++;
            }
        }

        relatorio += "----------------------------------------------------------------------------------------------------\n"
                + String.format("TOTAL GERAL NO PERÍODO: R$ %.2f", somaGeral);

        JOptionPane.showMessageDialog(null, relatorio);
    }

    /**
     * Pesquisa um número de documento no vetor de documentos cadastrados.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param total Quantidade de contas cadastradas.
     * @param pesquisaDoc O número do documento que se deseja localizar.
     * @return O índice (posição) do documento no vetor, ou -1 caso não seja
     * encontrado.
     */
    public static int buscaDocBiblioteca(int[] documentos, int total, int pesquisaDoc) {
        for (int i = 0; i < total; i++) {
            if (documentos[i] == pesquisaDoc) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Solicita ao usuário o número do documento para consulta via caixa de
     * diálogo, validando se a entrada contém apenas dígitos numéricos.
     *
     * @return O número do documento validado, ou -1 caso o usuário cancele a
     * operação.
     */
    public static int lerDocConsulta() {
        while (true) {
            String entradadoc = JOptionPane.showInputDialog("XYZ COMERCIO DE PRODUTOS LTDA.\n"
                    + "SISTEMA DE CONTAS A PAGAR\n\n"
                    + "CONSULTA DE CONTA\n\n"
                    + "Informe o número do documento para consulta:");

            if (entradadoc == null) {
                return -1;
            }

            entradadoc = entradadoc.trim();
            valido = !entradadoc.isEmpty();

            for (int i = 0; i < entradadoc.length(); i++) {
                if (!Character.isDigit(entradadoc.charAt(i))) {
                    valido = false;
                    break;
                }
            }

            if (!valido) {
                JOptionPane.showMessageDialog(null, "Por favor, digite um número de documento válido.");
                continue;
            }

            return Integer.parseInt(entradadoc);
        }
    }

    /**
     * Exibe uma caixa de diálogo com os dados completos de uma conta cadastrada
     * a partir de sua posição nos vetores.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento.
     * @param indiceEncontrado A posição (índice) da conta nos vetores.
     */
    public static void exibirConta(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int indiceEncontrado) {
        String msg = "XYZ COMERCIO DE PRODUTOS LTDA.\n"
                + "SISTEMA DE CONTAS A PAGAR\n\n"
                + "CONSULTA DE CONTA\n\n"
                + "Documento: " + documentos[indiceEncontrado] + "\n"
                + "Descrição: " + descs[indiceEncontrado] + "\n"
                + "Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                + "Valor: R$ " + String.format("%.2f", valores[indiceEncontrado]) + "\n"
                + "Vencimento: " + vencimentos[indiceEncontrado] + "\n";
        JOptionPane.showMessageDialog(null, msg);
    }

    /**
     * Gerencia o fluxo completo de consulta de contas a pagar, coordenando a
     * leitura do documento, busca nos vetores e exibição dos dados.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento.
     * @param total Quantidade de contas cadastradas.
     */
    public static void consultarConta(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma conta cadastrada no sistema.");
            return;
        }

        char novaconsulta = 'N';
        do {
            int docConsulta = lerDocConsulta();
            if (docConsulta == -1) {
                break;
            }

            int indiceEncontrado = buscaDocBiblioteca(documentos, total, docConsulta);

            if (indiceEncontrado != -1) {
                exibirConta(documentos, descs, beneficiarios, valores, vencimentos, indiceEncontrado);
            } else {
                JOptionPane.showMessageDialog(null, "Documento não encontrado!");
            }

            String resp = JOptionPane.showInputDialog("Deseja fazer uma nova consulta? (S/N)");

            if (resp != null && !resp.trim().isEmpty()) {
                novaconsulta = Character.toUpperCase(resp.trim().charAt(0));
            } else {
                novaconsulta = 'N';
            }

        } while (novaconsulta == 'S');
    }

    /**
     * Solicita ao usuário o número do documento para excluir a conta via caixa
     * de diálogo, validando se a entrada contém apenas dígitos numéricos.
     *
     * @return O número do documento validado, ou 'N' caso o usuário cancele a
     * operação.
     */
    public static void ExcConta(int documentos[], String[] descs, String[] beneficiarios, double[] valores, String[] vencimentos, int total) {
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
                String msg = ("XYZ COMERCIO DE PRODUTOS LTDA.\n")
                        + "SISTEMA DE CONTAS A PAGAR\n\n"
                        + "EXCLUSÃO DE CONTA\n\n"
                        + "Documento: " + documentos[indiceEncontrado] + "\n"
                        + "Descrição: " + descs[indiceEncontrado] + "\n"
                        + "Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                        + "Valor: R$" + String.format("%.2f", valores[indiceEncontrado]) + "\n"
                        + "Vencimento: " + vencimentos[indiceEncontrado] + "\n";
                JOptionPane.showMessageDialog(null, msg);
                char DelConta = JOptionPane.showInputDialog(null, "Você confirma essa ação? (S/N)").charAt(0);
                if (DelConta == 'S' || DelConta == 's') {
                    for (int i = indiceEncontrado; i < total - 1; i++) {
                        documentos[i] = documentos[i + 1];
                        descs[i] = descs[i + 1];
                        beneficiarios[i] = beneficiarios[i + 1];
                        valores[i] = valores[i + 1];
                        vencimentos[i] = vencimentos[i + 1];
                    }
                    total--;
                    JOptionPane.showMessageDialog(null, "Conta excluída com sucesso!");
                } else {
                    if (DelConta == 'N' || DelConta == 'n') {
                        JOptionPane.showMessageDialog(null, "EXCLUSÃO CANCELADA!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Documento não encontrado!");
            }

            String resp = JOptionPane.showInputDialog("Deseja fazer outra Exclusão? (S/N)?");

            if (resp != null && !resp.trim().isEmpty()) {
                NovaExc = Character.toUpperCase(resp.trim().charAt(0));
            } else {
                NovaExc = 'N';
            }

        } while (NovaExc == 'S');
    }

    /**
     * Solicita ao usuário o número do documento para Baixar a conta via caixa
     * de diálogo, validando se a entrada contém apenas dígitos numéricos.
     *
     * @return O número do documento validado, ou 'N' caso o usuário cancele a
     * operação.
     */
    public static void baixaconta(int documentos[], String[] descs, String[] beneficiarios, double[] valores, String[] vencimentos, int total) {
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
