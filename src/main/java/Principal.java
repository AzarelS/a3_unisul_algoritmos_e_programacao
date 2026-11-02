
import javax.swing.JOptionPane;

public class Principal {

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
        //Variaveis vetores:
        int documentos[] = new int[100];            //Registra em vetores todos os documentos cadastrados
        String beneficiarios[] = new String[100];   //Registra em vetores todos os beneficiarios cadastrados
        String descs[] = new String[100];           //Registra em vetores as descrições de cada conta cadastrada
        double valores[] = new double[100];         //Registra em vetores todos os valores cadastrados
        String[] vencimentos = new String[100];     //Registra em vetores todas as datas de vencimento cadastradas
        int total = 0;                              //Controla e registra o número de contas cadastradas
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
                                char novocad = 0;
                                do {
                                    String entradadoc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                            + "--Inclusão de conta--\n\n"
                                            + "Informe o número do Documento: ");
                                    if (entradadoc == null){
                                        JOptionPane.showMessageDialog(null, "Digite um número de documento válido.");
                                        continue;                                    
                                    }
                                    
                                    int documento = Integer.parseInt(entradadoc);
                                    
                                    boolean docexiste = false;
                                    for (int i = 0; i < total; i++) {
                                        if (documentos[i] == documento) {
                                            docexiste = true;
                                            break;
                                        }
                                    }
                                    if (docexiste == true) {
                                        JOptionPane.showMessageDialog(null, "Documento ja cadastrado, tente outro.");
                                        continue;
                                    }

                                    String desc = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                            + "--Inclusão de conta--\n\n"
                                            + "Adicione uma descrição a conta: ");
                                    if (desc == null){
                                        continue;
                                    }

                                    String beneficiario = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                            + "--Inclusão de conta--\n\n"
                                            + "Informe o beneficiário: ");
                                    if (beneficiario == null){
                                        continue;
                                    }
                                    
                                    String entradavalor = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                            + "--Inclusão de conta--\n\n"
                                            + "Informe o valor: ");
                                    if (entradavalor == null) {
                                        continue;
                                    }
                                    entradavalor = entradavalor.trim();

                                    if (!entradavalor.matches("\\d+([\\.,]\\d+)?")) {
                                        JOptionPane.showMessageDialog(null, "Digite um valor válido.");
                                        continue;
                                    }
                                    double valor = Double.parseDouble(entradavalor.replace(",", "."));

                                    if (valor <= 0) {
                                        JOptionPane.showMessageDialog(null, "O valor deve ser maior que R$0.00");
                                        continue;
                                    }

                                    String vencimento = JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                            + "--Inclusão de conta--\n\n"
                                            + "Informe a data de vencimento\n"
                                            + "(DD/MM/AAAA): ");
                                    if (vencimento == null) {
                                        continue;
                                    }
                                    vencimento.trim();
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

                                    char confirmainc = confirmaconta.charAt(0);

                                    if (confirmainc == 'S' || confirmainc == 's') {
                                        //Define os valores de entrada ja válidados em seus respectivos vetores.
                                        documentos[total] = documento;
                                        descs[total] = desc;
                                        beneficiarios[total] = beneficiario;
                                        valores[total] = valor;
                                        vencimentos[total] = vencimento;
                                        total++;

                                        JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!");

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Inlclusão cancelada, os dados não foram salvos.");
                                    }

                                    String confirmanovocad = JOptionPane.showInputDialog("Deseja cadastrar outra conta: ( S/N ) ?");
                                    if (confirmanovocad != null) {
                                        novocad = confirmanovocad.charAt(0);
                                    }

                                } while (novocad == 's' || novocad == 'S');

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
                                            +"SISTEMA DE CONTAS A PAGAR\n\n"
                                            +"CONSULTA DE CONTA\n\n"
                                            +"Informe o número do documento para consulta:");
                                    
                                    if (entradadoc == null){
                                        break;
                                    }
                                    
                                    entradadoc = entradadoc.trim();
                                    
                                    boolean ehNumero = !entradadoc.isEmpty();
                                    
                                    for (int i=0; i<entradadoc.length(); i++){
                                        if (!Character.isDigit(entradadoc.charAt(i))){
                                            ehNumero = false;
                                        break;
                                        }
                                    }
                                    
                                    if(!ehNumero){
                                        JOptionPane.showMessageDialog(null, "Por favor, digite um número de documento válido.");
                                        novaconsulta = 'S';
                                        continue;
                                    }
                                    
                                    int docConsulta = Integer.parseInt(entradadoc);
                                    
                                    int indiceEncontrado = -1;
                                    
                                    for (int i=0; i<total; i++){
                                        if(documentos[i] == docConsulta){
                                            indiceEncontrado = i;
                                        break;
                                        }
                                    }
                                    
                                    if (indiceEncontrado != -1) {
                                        String msg = ("\t\t XYZ COMERCIO DE PRODUTOS LTDA.\n")
                                                +"\tSISTEMA DE CONTAS A PAGAR\n\n"
                                                +"\tCONSULTA DE CONTA\n\n"
                                                +"Documento: " + documentos[indiceEncontrado] + "\n"
                                                +"Descrição: " + descs[indiceEncontrado] + "\n"
                                                +"Beneficiário: " + beneficiarios[indiceEncontrado] + "\n"
                                                +"Valor: R$ " + valores[indiceEncontrado] + "\n"
                                                +"Vencimento: " + vencimentos[indiceEncontrado] + "\n";
                                        JOptionPane.showMessageDialog(null, msg);
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Documento não encontrado");
                                    }
                                    
                                    String resp = JOptionPane.showInputDialog("Deseja fazer uma nova consulta? (S/N)?");
                                    
                                    if (resp != null && !resp.trim().isEmpty()){
                                        novaconsulta = Character.toUpperCase(resp.trim().charAt(0));
                                    }else{
                                        novaconsulta = 'N';
                                    }
                                    
                                } while (novaconsulta == 'S');
                                
                                break;
                            }
                            
                            case 4: {
                                excontas = Integer.parseInt(JOptionPane.showInputDialog("\t XYZ COMERCIO DE PRODUTOS LTDA. \n"
                                        + "Sistema de Contas a Pagar\n"
                                        + "\n"
                                        + "Exclusão de Contas\n"
                                        + "\n"
                                        + "Documento: \n"
                                        + "Descrição: \n"
                                        + "Beneficiário: \n"
                                        + "Valor: \n"
                                        + "Vencimento \n"
                                        + "\n"
                                        + "Confirmar exclusão (S/N)? __\n"
                                        + "\n"
                                        + "Nova exclusão (S/N)? __\n"));
                                break;
                            }

                            case 0: {
                                cadcontas = 0;
                                JOptionPane.showMessageDialog(null, "Retornando...");
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
}
