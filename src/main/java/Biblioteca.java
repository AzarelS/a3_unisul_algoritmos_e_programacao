
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class Biblioteca {

    //Variavel de verificação se um dado é ou não valido
    static boolean valido = false;
    
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
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade atual de contas cadastradas.
     * @return O número do documento validado, ou 'N' caso o usuário cancele a
     * operação.
     */
    public static int ExcConta(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {

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
     * Gerencia o fluxo completo de inclusão de novas contas a pagar (Tela
     * 1.1.1), coordenando a leitura, validação, confirmação e armazenamento nos
     * vetores paralelos.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade atual de contas cadastradas.
     * @return A quantidade atualizada de contas cadastradas após as inclusões.
     */
    public static int incluirConta(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total) {
        char novocad = 'N';
        do {
            documento = lerDoc(documentos, total);
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
                total = salvaConta(documentos, descs, beneficiarios, valores, vencimentos, total,
                        documento, desc, beneficiario, valor, vencimento);
                JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Inclusão cancelada, os dados não foram salvos!");
            }

            String confirmanovocad = JOptionPane.showInputDialog("Deseja cadastrar outra conta? (S/N)");
            if (confirmanovocad != null && !confirmanovocad.trim().isEmpty()) {
                novocad = Character.toUpperCase(confirmanovocad.trim().charAt(0));
            } else {
                novocad = 'N';
            }

        } while (novocad == 'S');

        return total;
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
    public static int buscaDoc(int[] documentos, int total, int pesquisaDoc) {
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
     * @param documentos Vetor com os números dos documentos já cadastrados.
     * @param total Quantidade atual de contas cadastradas.
     * @return O número do documento validado, ou -1 caso o usuário cancele a
     * operação.
     */
    public static int lerDoc(int[] documentos, int total) {
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

            if (buscaDoc(documentos, total, documento) != -1) {
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
     * Armazena os dados validados de uma nova conta na próxima posição
     * disponível dos vetores paralelos e incrementa o contador de registros.
     *
     * @param documentos Vetor com os números dos documentos.
     * @param descs Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores Vetor com os valores das contas.
     * @param vencimentos Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total Quantidade atual de contas cadastradas.
     * @param documento Número do documento da conta.
     * @param desc Descrição da despesa.
     * @param beneficiario Nome do beneficiário do pagamento.
     * @param valor Valor da despesa.
     * @param vencimento Data de vencimento no formato DD/MM/AAAA.
     * @return O novo total de contas cadastradas (total + 1).
     */
    public static int salvaConta(int[] documentos, String[] descs, String[] beneficiarios,
            double[] valores, String[] vencimentos, int total,
            int documento, String desc, String beneficiario, double valor, String vencimento) {

        documentos[total] = documento;
        descs[total] = desc;
        beneficiarios[total] = beneficiario;
        valores[total] = valor;
        vencimentos[total] = vencimento;
        return total + 1;
    }
}
