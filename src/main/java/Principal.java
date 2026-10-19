
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {

        // Variaveis:
        int menuprincipal, cadcontas, inccontas, altcontas, concontas, excontas, baicontas, relatorios;

        // cadcontas: cadastro de  contas
        // inccontas: inclusão de conta
        // altcontas: alteração de conta
        // concontas: consulta de conta
        // excontas: exclusão de conta
        // baicontas: baixa de conta
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
                                break;
                            }
                            case 2: {
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                break;
                            }

                            case 0: {
                                JOptionPane.showMessageDialog(null, "Retornando...");
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Opção Inexistente");
                                break;
                            }
                        }

                        break;
                    } while (cadcontas != 0);
                }

                case 2: {
                    break;
                }
                case 3: {
                    break;
                }

                // Outras Opções
                case 0: {
                    JOptionPane.showMessageDialog(null, "Saindo do Programa.");
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
}
