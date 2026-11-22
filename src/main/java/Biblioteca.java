import javax.swing.JOptionPane;
import java.time.LocalDate;

public class Biblioteca {

    public static int menuCadContas() {

        int cadcontas = -1;
        
    return (cadcontas);
    }
    /**
     * Converte uma data no formato DD/MM/AAAA para um inteiro AAAAMMDD,
     * para que as datas mais recentes sejam maiores que datas mais antigas evitando conflito.
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
     * Obtém a data atual do sistema convertida no formato inteiro AAAAMMDD para que a data atual seja maior que alguma data passada.
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
     * @return A data validada no formato DD/MM/AAAA, ou null caso o usuário cancele.
     */
    public static String lerData(String mensagem) {
        String data = "";
        boolean valido = false;

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
     * Ordena os vetores de contas em ordem crescente de data
     * de vencimento usando metodo bubble sort ensinado em sala.
     *
     * @param documentos    Vetor com os números dos documentos.
     * @param descs         Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores       Vetor com os valores das contas.
     * @param vencimentos   Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total         Quantidade de contas cadastradas.
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
     * Exibe o relatório de contas a pagar por período em ordem crescente
     * de data de vencimento, considerando apenas contas com valor final
     * maior que zero.
     *
     * @param documentos    Vetor com os números dos documentos.
     * @param descs         Vetor com as descrições das contas.
     * @param beneficiarios Vetor com os beneficiários.
     * @param valores       Vetor com os valores atuais das contas.
     * @param vencimentos   Vetor com as datas de vencimento (DD/MM/AAAA).
     * @param total         Quantidade de contas cadastradas.
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
}
