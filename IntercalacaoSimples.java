import java.io.*;
import java.util.*;

public class IntercalacaoSimples {
    int arqTmp = 2;
    int bloco = 100;

    int qtdRegistros = 0;

    public void intercalacaoBalanceada() {
        qtdRegistros = contaRegistros("BancoDados.db");

        distribuicao("BancoDados.db");

        intercalacao();
    }

    public int contaRegistros(String enderecoDB) {
        int totalRegistros = 0;

        // Leitura dos registros
        try (RandomAccessFile arq = new RandomAccessFile(enderecoDB, "r")) {
            // Endereço do ponteiro de início
            long ponteiroBase = arq.getFilePointer();
            arq.seek(ponteiroBase + 4);

            while (ponteiroBase < arq.length()) {
                arq.readInt(); // Lê o ID

                boolean lapide = arq.readBoolean();

                if (lapide == false) {
                    totalRegistros++;
                }

                int tamanhoRegistro = arq.readInt();

                ponteiroBase = arq.getFilePointer();

                arq.seek(ponteiroBase + tamanhoRegistro);

                ponteiroBase = arq.getFilePointer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalRegistros;
    }

    public void distribuicao(String enderecoDB) {
        try {
            RandomAccessFile arq = new RandomAccessFile(enderecoDB, "r"); // Abre arquivo para leitura e escrita
            RandomAccessFile[] tmps = new RandomAccessFile[arqTmp];

            for (int i = 0; i < arqTmp; i++) {
                tmps[i] = new RandomAccessFile("tmps/tmp" + (i + 1) + ".db", "rw");
            }

            byte[] ba;
            int idRegistro;
            int len;
            boolean lapis;

            // Endereço do ponteiro de início
            long ponteiroBase = arq.getFilePointer();
            arq.seek(ponteiroBase + 4);

            int contadorTmp = 0;

            ArrayList<Pokemon> registros = new ArrayList<Pokemon>();

            while (ponteiroBase < arq.length()) {
                idRegistro = arq.readInt();
                len = arq.readInt();
                lapis = arq.readBoolean();

                ba = new byte[len];
                arq.read(ba);

                if (lapis == false) {
                    Pokemon registro = new Pokemon();
                    registro.toByteArray();

                    registros.add(registro);
                }

                ponteiroBase = arq.getFilePointer();
            }

            while (registros.size() > 0) {
                ArrayList<Pokemon> blocoOrdenavelTmp = new ArrayList<Pokemon>();

                for (int i = 0; i < bloco; i++) {
                    if (registros.size() > 0) {
                        blocoOrdenavelTmp.add(registros.get(0));
                        registros.remove(0);
                    }
                }

                Collections.sort(blocoOrdenavelTmp, new ComparadorPorId());

                for (Pokemon registro : blocoOrdenavelTmp) {
                    tmps[contadorTmp % arqTmp].seek(tmps[contadorTmp % arqTmp].length());
                    // tmps[contadorTmp % arqTmp].writeInt(registro.getIdRegistro());
                    // tmps[contadorTmp % arqTmp].writeBoolean(registro.isLapide());
                    // tmps[contadorTmp % arqTmp].writeInt(registro.getTamanho());
                    // tmps[contadorTmp % arqTmp].write(registro.getAnime().toByteArray());
                }

                contadorTmp++;
            }

            // escreverArquivoTXT("tmps/tmp1.db", "tmps/txt1.txt");
            // escreverArquivoTXT("tmps/tmp2.db", "tmps/txt2.txt");

            arq.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void intercalacao() {
        try {
            RandomAccessFile tmp1 = new RandomAccessFile("tmps/tmp1.db", "rw");
            RandomAccessFile tmp2 = new RandomAccessFile("tmps/tmp2.db", "rw");
            RandomAccessFile tmp3 = new RandomAccessFile("tmps/tmp3.db", "rw");
            RandomAccessFile tmp4 = new RandomAccessFile("tmps/tmp4.db", "rw");

            ArrayList<Pokemon> registrosTmp1 = new ArrayList<Pokemon>();
            ArrayList<Pokemon> registrosTmp2 = new ArrayList<Pokemon>();
            ArrayList<Pokemon> registrosTmp3 = new ArrayList<Pokemon>();
            ArrayList<Pokemon> registrosTmp4 = new ArrayList<Pokemon>();

            int numeroRodada = 0; // Número da rodada de intercalação

            byte[] ba;
            int idRegistro;
            int len;
            boolean lapis;

            long ponteiroBase;

            boolean arquivoOrdenado = false;

            while (arquivoOrdenado == false) {
                System.out.println("[ Intercalação Rodada " + (numeroRodada + 1) + " ]");
                System.out.print(".");
                System.out.print(".");
                System.out.print(".");
                System.out.print(".");
                System.out.println(".");

                if (numeroRodada % 2 == 0) {
                    do {
                        tmp1.seek(0);
                        ponteiroBase = tmp1.getFilePointer();

                        // Varre o arquivo temporário 1
                        while (ponteiroBase < tmp1.length()) {
                            idRegistro = tmp1.readInt();
                            lapis = tmp1.readBoolean();
                            len = tmp1.readInt();

                            ba = new byte[len];
                            tmp1.read(ba);

                            Pokemon registro = new Pokemon();
                            registro.toByteArray();

                            registrosTmp1.add(registro);

                            ponteiroBase = tmp1.getFilePointer();
                        }

                        tmp2.seek(0);
                        ponteiroBase = tmp2.getFilePointer();

                        // Varre o arquivo temporário 2
                        while (ponteiroBase < tmp2.length()) {
                            idRegistro = tmp2.readInt();
                            lapis = tmp2.readBoolean();
                            len = tmp2.readInt();

                            ba = new byte[len];
                            tmp2.read(ba);

                            Pokemon registro = new Pokemon();
                            registro.toByteArray();

                            registrosTmp2.add(registro);

                            ponteiroBase = tmp2.getFilePointer();
                        }

                        tmp1.setLength(0);
                        tmp2.setLength(0);

                        int rodadaArquivo = 0;

                        while (registrosTmp1.size() > 0 || registrosTmp2.size() > 0) {
                            ArrayList<Pokemon> blocoOrdenavelTmp = new ArrayList<Pokemon>();

                            for (int i = 0; i < (bloco * Math.pow(2, numeroRodada)); i++) {
                                if (registrosTmp1.size() > 0) {
                                    blocoOrdenavelTmp.add(registrosTmp1.get(0));
                                    registrosTmp1.remove(0);
                                }
                            }

                            for (int i = 0; i < (bloco * Math.pow(2, numeroRodada)); i++) {
                                if (registrosTmp2.size() > 0) {
                                    blocoOrdenavelTmp.add(registrosTmp2.get(0));
                                    registrosTmp2.remove(0);
                                }
                            }

                            Collections.sort(blocoOrdenavelTmp, new ComparadorPorId());

                            RandomAccessFile numeroArquivoEscrita;
                            numeroArquivoEscrita = (rodadaArquivo % 2 == 0) ? tmp3 : tmp4;

                            for (Pokemon registro : blocoOrdenavelTmp) {
                                numeroArquivoEscrita.seek(numeroArquivoEscrita.length());
                                // numeroArquivoEscrita.writeInt(registro.getId());
                                // numeroArquivoEscrita.writeBoolean(registro.getLapide());
                                // numeroArquivoEscrita.writeInt(registro.getTamanho());
                                // numeroArquivoEscrita.write(registro.getAnime().toByteArray());
                            }

                            if (blocoOrdenavelTmp.size() >= qtdRegistros) {
                                arquivoOrdenado = true;
                                gerarArquivoOrdenado(blocoOrdenavelTmp);
                                break;
                            }

                            blocoOrdenavelTmp.clear();

                            rodadaArquivo++;
                        }

                    } while (registrosTmp1.size() > 0 || registrosTmp2.size() > 0);
                } else {
                    do {
                        tmp3.seek(0);
                        ponteiroBase = tmp3.getFilePointer();

                        // Varre o arquivo temporário 3
                        while (ponteiroBase < tmp3.length()) {
                            idRegistro = tmp3.readInt();
                            lapis = tmp3.readBoolean();
                            len = tmp3.readInt();

                            ba = new byte[len];
                            tmp3.read(ba);

                            Pokemon registro = new Pokemon();
                            registro.toByteArray();

                            registrosTmp3.add(registro);

                            ponteiroBase = tmp3.getFilePointer();
                        }

                        tmp4.seek(0);
                        ponteiroBase = tmp4.getFilePointer();

                        // Varre o arquivo temporário 2
                        while (ponteiroBase < tmp4.length()) {
                            idRegistro = tmp4.readInt();
                            lapis = tmp4.readBoolean();
                            len = tmp4.readInt();

                            ba = new byte[len];
                            tmp4.read(ba);

                            Pokemon registro = new Pokemon();
                            registro.toByteArray();

                            registrosTmp4.add(registro);

                            ponteiroBase = tmp4.getFilePointer();
                        }

                        tmp3.setLength(0);
                        tmp4.setLength(0);

                        int rodadaArquivo = 0;

                        while (registrosTmp3.size() > 0 || registrosTmp4.size() > 0) {
                            ArrayList<Pokemon> blocoOrdenavelTmp = new ArrayList<Pokemon>();

                            for (int i = 0; i < (bloco * Math.pow(2, numeroRodada)); i++) {
                                if (registrosTmp3.size() > 0) {
                                    blocoOrdenavelTmp.add(registrosTmp3.get(0));
                                    registrosTmp3.remove(0);
                                }
                            }

                            for (int i = 0; i < (bloco * Math.pow(2, numeroRodada)); i++) {
                                if (registrosTmp4.size() > 0) {
                                    blocoOrdenavelTmp.add(registrosTmp4.get(0));
                                    registrosTmp4.remove(0);
                                }
                            }

                            Collections.sort(blocoOrdenavelTmp, new ComparadorPorId());

                            RandomAccessFile numeroArquivoEscrita;
                            numeroArquivoEscrita = (rodadaArquivo % 2 == 0) ? tmp1 : tmp2;

                            for (Pokemon registro : blocoOrdenavelTmp) {
                                // numeroArquivoEscrita.seek(numeroArquivoEscrita.length());
                                // numeroArquivoEscrita.writeInt(registro.getId());
                                // numeroArquivoEscrita.writeBoolean(registro.getLapide());
                                // numeroArquivoEscrita.writeInt(registro.getTamanho());
                                // numeroArquivoEscrita.write(registro.getAnime().toByteArray());
                            }

                            if (blocoOrdenavelTmp.size() >= qtdRegistros) {
                                arquivoOrdenado = true;
                                gerarArquivoOrdenado(blocoOrdenavelTmp);
                                break;
                            }

                            blocoOrdenavelTmp.clear();

                            rodadaArquivo++;
                        }

                    } while (registrosTmp3.size() > 0 || registrosTmp4.size() > 0);
                }

                numeroRodada++;
            }

            tmp1.close();
            tmp2.close();
            tmp3.close();
            tmp4.close();

            escreverArquivoTXT("tmps/tmp1.db", "tmps/txt1.txt");
            escreverArquivoTXT("tmps/tmp2.db", "tmps/txt2.txt");
            escreverArquivoTXT("tmps/tmp3.db", "tmps/txt3.txt");
            escreverArquivoTXT("tmps/tmp4.db", "tmps/txt4.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gerarArquivoOrdenado(ArrayList<Pokemon> listaOrdenada) throws Exception {
        try {
            RandomAccessFile arqFinal = new RandomAccessFile("ordenacao/intercalacao_comum.db", "rw");

            for (Pokemon registro : listaOrdenada) {
                // arqFinal.seek(arqFinal.length());
                // arqFinal.writeInt(registro.getId());
                // arqFinal.writeBoolean(registro.getLapide());
                // arqFinal.writeInt(registro.getTamanho());
                // arqFinal.write(registro.getAnime().toByteArray());
            }

            arqFinal.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void escreverArquivoTXT(String enderecoDB, String enderecoTXT) throws Exception {
        // Leitura dos registros
        RandomAccessFile arq = new RandomAccessFile(enderecoDB, "r");

        // Escrever os registros para impressão
        BufferedWriter writer = new BufferedWriter(new FileWriter(enderecoTXT));

        byte[] ba;
        int idTeste;
        int len;
        boolean lapis;

        // Endereço do ponteiro de início
        long ponteiroBase = arq.getFilePointer();
        arq.seek(ponteiroBase);

        while (ponteiroBase < arq.length()) {
            idTeste = arq.readInt();
            lapis = arq.readBoolean();
            len = arq.readInt();

            ba = new byte[len];
            arq.read(ba);

            Pokemon pokemon = new Pokemon();
            pokemon.toByteArray();

            ponteiroBase = arq.getFilePointer();

            // Escrever no arquivo de impressão
            // writer.write(pokemon.toString2(ba));
            writer.newLine();
        }

        writer.close();
        arq.close();
    }
}