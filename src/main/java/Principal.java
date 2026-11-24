
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
                                Biblioteca.alterarConta(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 3: {
                                Biblioteca.consultarConta(documentos, descs, beneficiarios, valores, vencimentos, total);
                                break;
                            }
                            case 4: {
                                total = Biblioteca.ExcConta(documentos, descs, beneficiarios, valores, vencimentos, total);
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
}
