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
}
