import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
   public static void main(String args[]) throws IOException {
      Scanner fetch = new Scanner(System.in);
      CRUD crud = new CRUD("pokemonDB");
      String basefile = "pokemonSample.csv";
      // ArvoreB arv = new ArvoreB(8);
      // HashExtensivel hash= new HashExtensivel(1);

      // Criar lista invertida para gêneros
      // ListaInvertida listaGeneros = new ListaInvertida("listaGeneros");

      // Criar lista invertida para nome
      // ListaInvertida listaNomes = new ListaInvertida("listaNomes");
      System.out.println("Deseja carregar o arquivo?");
      System.out.println("1-Sim \n2-Nao");

      if (fetch.nextInt() == 1) {
         try {
            FileInputStream fs = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));

            String csvLine = br.readLine();
            while ((csvLine = br.readLine()) != null) {
               Pokemon pokemon = new Pokemon();
               pokemon.parseCSV(csvLine);
               crud.create(pokemon);
               long pos = crud.getIndex(pokemon);
               // arv.insere(m.getId(), pos);
               // hash.insert(m.getId(), pos);
               // listaGeneros.addDocument((int) pos, m.getGenres());
               // listaNomes.addDocument((int) pos, m.getReleaseName());
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      int choice = 0;
      while (choice != 5) {
         System.out.println("Escolha uma operação: ");
         System.out.println("1- Create");
         System.out.println("2- Read");
         System.out.println("3- Update");
         System.out.println("4- Delete");
         System.out.println("5- Sair");
         choice = fetch.nextInt();

         switch (choice) {

            // CREATE
            case 1:
               try {
                  System.out.println("Digite o ID: ");
                  int readID = fetch.nextInt();
                  if (crud.search(readID)) {
                     // Personalizar para meu CSV e campos de Pokemon
                     System.out.println("Digite o nome do Pokemon: ");
                     fetch.nextLine();
                     String pokemonName = fetch.nextLine();
                     System.out.println("Digite o número da pokedex: ");
                     int pokedexID = fetch.nextInt();
                     System.out.println("Escreva o número da geração: ");
                     String generation = fetch.nextLine();
                     System.out.println("Escreva o nome da especie de Pokemon: ");
                     String specie = fetch.nextLine();
                     System.out.println("Escreva o nome da habilidade escodida: ");
                     String hiddenAbility = fetch.nextLine();
                     // data
                     System.out.println("Digite a data de lançamento do Pokemon: ");
                     System.out.println("Ex.: yyyy-MM-dd");
                     String releaseDate = fetch.nextLine();

                     // arrays types
                     System.out.println("Escreva os tipos do pokemon (separado por virgulas)");
                     System.out.println("Ex: Fire, Rock");
                     String typesStr = fetch.nextLine();
                     String[] types = typesStr.split(",");
                     ArrayList<String> typesAL = new ArrayList();
                     for (int i = 0; i < types.length; i++)
                        typesAL.add(types[i]);

                     // abilities
                     System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
                     System.out.println("Keen Eye, Tangled Feet");
                     String abilitiesStr = fetch.nextLine();
                     String[] abilities = abilitiesStr.split(",");
                     ArrayList<String> abilitiesAL = new ArrayList();
                     for (int i = 0; i < abilities.length; i++)
                        abilitiesAL.add(types[i]);

                     Pokemon pokemon = new Pokemon();
                     String line = readID + "," + pokedexID + "," + pokemonName + "," + generation + "," + specie + ","
                           + hiddenAbility + ",\"" + typesStr + "\",\"" + abilitiesStr + "\"";
                     pokemon.parseCSV(line);
                     crud.create(pokemon);

                     long pos = crud.getIndex(pokemon);
                     // arv.insere(readID, pos);
                     // hash.insert(readID, choice);
                     // listaGeneros.addDocument((int) pos, mus.getGenres());
                     // listaNomes.addDocument((int) pos, mus.getReleaseName());
                     System.out.println("\nArquivo atualizado!\n");

                     System.out.print(
                           "ID: " + crud.read(readID).getIndex() + ", Pokedex ID: " + crud.read(readID).getPokedexNum()
                                 + ", Nome do Pokemon: " + crud.read(readID).getName()
                                 + ", Geração: " + crud.read(readID).getGeneration()
                                 + ", Especie: " + crud.read(readID).getSpecie()
                                 + ", Hidden Hability: " + crud.read(readID).getHiddenAbility() + ", ");

                     System.out.print(" Tipos: ");
                     for (int i = 0; i < crud.read(readID).getTypes().size(); i++) {
                        System.out.print(crud.read(readID).getTypes().get(i));
                        if (i != crud.read(readID).getTypes().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < crud.read(readID).getAbilities().size(); i++) {
                        System.out.print(crud.read(readID).getAbilities().get(i));
                        if (i != crud.read(readID).getAbilities().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.println("");
                  } else
                     System.out.println("ID já existente.");
               } catch (Exception e) {
                  System.out.println("Erro ao criar Arquivo.");
               }

               break;

            // Read
            /*
             * case 2:
             * System.out.print("Digite o ID do game que deseja pesquisar no arquivo: ");
             * int readID = sc.nextInt();
             * try{
             * fileAF.Read(readID).getAppId(); // Teste para ver se game existe (teste de
             * pointer)
             * System.out.println("\nArquivo encontrado!\n");
             * System.out.println(fileAF.Read(readID).getAppId() +" "+
             * fileAF.Read(readID).getName() + " " + fileAF.Read(readID).getPrice()+" "+
             * fileAF.Read(readID).getReleaseDate());
             * for(int i=0; i<fileAF.Read(readID).getGenres().size(); i++){
             * System.out.print(fileAF.Read(readID).getGenres().get(i));
             * if(i!=fileAF.Read(readID).getGenres().size()-1){
             * System.out.print(", ");
             * }
             * }
             * System.out.println();
             * } catch(Exception e){
             * System.out.println("\nArquivo não encontrado!");
             * }
             * 
             * break;
             */
            case 2:
               try {
                  System.out.println("Digite o ID: ");
                  int readID = fetch.nextInt();
                  crud.read(readID).getIndex(); // Teste para ver se pokemon existe (teste de pointer)
                  if (crud.read(readID).lapide) {
                     System.out.println("\nArquivo encontrado!\n");
                     System.out.print(
                           "ID: " + crud.read(readID).getIndex() + ", Pokedex ID: " + crud.read(readID).getPokedexNum()
                                 + ", Nome do Pokemon: " + crud.read(readID).getName()
                                 + ", Geração: " + crud.read(readID).getGeneration()
                                 + ", Especie: " + crud.read(readID).getSpecie()
                                 + ", Hidden Hability: " + crud.read(readID).getHiddenAbility() + ", ");

                     System.out.print(" Tipos: ");
                     for (int i = 0; i < crud.read(readID).getTypes().size(); i++) {
                        System.out.print(crud.read(readID).getTypes().get(i));
                        if (i != crud.read(readID).getTypes().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < crud.read(readID).getAbilities().size(); i++) {
                        System.out.print(crud.read(readID).getAbilities().get(i));
                        if (i != crud.read(readID).getAbilities().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.println("");
                  } else
                     System.out.println("Arquivo não Encontrado!");
                  ;
               } catch (Exception e) {
                  System.out.println("\nArquivo não encontrado!");
               }

               break;

            // Update
            case 3:
               try {
                  System.out.println("Infome o ID: ");
                  int readID = fetch.nextInt();
                  boolean resp = crud.getDeletedIndex(readID);
                  if (resp == false) {
                     System.out.println("ID já cadastrado");
                     break;
                  }
                  System.out.println("Digite o ID atualizado: ");
                  int novoID = fetch.nextInt();
                  if ((crud.search(novoID))) {
                     // Personalizar para meu CSV e campos de Pokemon
                     System.out.println("Digite o nome do Pokemon: ");
                     fetch.nextLine();
                     String pokemonName = fetch.nextLine();
                     System.out.println("Digite o número da pokedex: ");
                     int pokedexID = fetch.nextInt();
                     System.out.println("Digite o número da geração: ");
                     int generation = fetch.nextInt();
                     System.out.println("Escreva o nome da especie de Pokemon: ");
                     String specie = fetch.nextLine();
                     System.out.println("Escreva o nome da habilidade escodida: ");
                     String hiddenAbility = fetch.nextLine();
                     // data
                     System.out.println("Digite a data de lançamento do Pokemon: ");
                     System.out.println("Ex.: yyyy-MM-dd");
                     String releaseDate = fetch.nextLine();

                     // arrays types
                     System.out.println("Escreva os tipos do pokemon (separado por virgulas)");
                     System.out.println("Ex: Fire, Rock");
                     String typesStr = fetch.nextLine();
                     String[] types = typesStr.split(",");
                     ArrayList<String> typesAL = new ArrayList();
                     for (int i = 0; i < types.length; i++)
                        typesAL.add(types[i]);

                     // abilities
                     System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
                     System.out.println("Keen Eye, Tangled Feet");
                     String abilitiesStr = fetch.nextLine();
                     String[] abilities = abilitiesStr.split(",");
                     ArrayList<String> abilitiesAL = new ArrayList();
                     for (int i = 0; i < abilities.length; i++)
                        abilitiesAL.add(types[i]);

                     Pokemon pokemon = new Pokemon();
                     String line = readID + "," + pokedexID + "," + pokemonName + "," + generation + "," + specie + ","
                           + hiddenAbility + ",\"" + typesStr + "\",\"" + abilitiesStr + "\"";
                     pokemon.parseCSV(line);
                     crud.delete(readID);
                     crud.create(pokemon);
                     System.out.println("\nArquivo atualizado!\n");

                     System.out.print(
                           "ID: " + crud.read(readID).getIndex() + ", Pokedex ID: " + crud.read(readID).getPokedexNum()
                                 + ", Nome do Pokemon: " + crud.read(readID).getName()
                                 + ", Geração: " + crud.read(readID).getGeneration()
                                 + ", Especie: " + crud.read(readID).getSpecie()
                                 + ", Hidden Hability: " + crud.read(readID).getHiddenAbility() + ", ");

                     System.out.print(" Tipos: ");
                     for (int i = 0; i < crud.read(readID).getTypes().size(); i++) {
                        System.out.print(crud.read(readID).getTypes().get(i));
                        if (i != crud.read(readID).getTypes().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < crud.read(readID).getAbilities().size(); i++) {
                        System.out.print(crud.read(readID).getAbilities().get(i));
                        if (i != crud.read(readID).getAbilities().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.println("");
                  } else {
                     System.out.println("ID já cadastrado");
                  }
               } catch (Exception e) {
                  System.out.println("Erro ao Atualizar o arquivo.");
               }

               break;

            // Delete

            case 4:
               try {
                  System.out.println("Informe o ID: ");
                  int readID = fetch.nextInt();
                  crud.read(readID).getIndex(); // Teste para ver se game existe (teste de pointer)
                  if (crud.read(readID).lapide) {
                     System.out.println("\nArquivo encontrado!\n");
                     System.out.print(
                           "ID: " + crud.read(readID).getIndex() + ", Pokedex ID: " + crud.read(readID).getPokedexNum()
                                 + ", Nome do Pokemon: " + crud.read(readID).getName()
                                 + ", Geração: " + crud.read(readID).getGeneration()
                                 + ", Especie: " + crud.read(readID).getSpecie()
                                 + ", Hidden Hability: " + crud.read(readID).getHiddenAbility() + ", ");

                     System.out.print(" Tipos: ");
                     for (int i = 0; i < crud.read(readID).getTypes().size(); i++) {
                        System.out.print(crud.read(readID).getTypes().get(i));
                        if (i != crud.read(readID).getTypes().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < crud.read(readID).getAbilities().size(); i++) {
                        System.out.print(crud.read(readID).getAbilities().get(i));
                        if (i != crud.read(readID).getAbilities().size() - 1) {
                           System.out.print(", ");
                        }
                     }
                     System.out.println("");
                     crud.delete(readID);
                     System.out.println(" ");
                     System.out.println("Arquivo deletado com sucesso!");
                     System.out.println(" ");
                  } else
                     System.out.println("Arquivo não Encontrado");
               } catch (Exception e) {
                  System.out.println("Erro ao deletar Arquivo.");
               }

               break;

            // Sair

            case 5:

               break;
         }

      }
      fetch.close();
   }
}
