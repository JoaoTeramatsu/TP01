import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

public class CRUD {
   private RandomAccessFile file;

   CRUD(String filename) throws IOException {
      this.file = new RandomAccessFile(filename, "rw");
   }

   public long getIndex(RandomAccessFile file, Pokemon pokemon) throws IOException {
      file.seek(0); // Ponteiro vai para o inicio do arquivo
      file.writeInt(pokemon.getIndex()); // Escreve no inicio do registro o seu ID
      file.seek(file.length()); // Ponteiro vai para o final do arquivo
      long index = file.getFilePointer();
      byte[] byteArr = pokemon.toByteArray(); // Vetor de Bytes populado com os dados do CSV já filtrados
      file.writeInt(pokemon.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
      file.write(byteArr); // Escreve o vetor de Bytes
      return index;
   }

   public Boolean getDeletedIndex(int entryIndex) throws IOException {
      long index;
      int qntBytesInic, id;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            index = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a
                                           // quantidade
            // de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Game específico
            id = file.readInt(); // Armazena o ID do registro Game específico
            if (id == entryIndex) { // Verifica se o id é o mesmo que o selecionado
               return false;
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return true;
   }

   public long getIndex(Pokemon pokemon) throws IOException {
      return getIndex(file, pokemon);
   }

   public void create(RandomAccessFile file, Pokemon pokemon) throws IOException {
      file.seek(0);
      file.writeInt(pokemon.getIndex());
      file.seek(file.length());

      byte[] byteArr = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArr);
   }

   public void create(Pokemon pokemon) throws IOException {
      create(this.file, pokemon);
   }

   // Fazer Método READ
   public Pokemon read(int pokemonIndex) throws IOException {
      long pos;
      int bytes, id;
      boolean isAlive;

      Pokemon pokemon = null;

      file.seek(0);
      file.readInt(); // Lê o index do registro x
      try {
         while (file.getFilePointer() < file.length()) {
            pos = file.getFilePointer();
            bytes = file.readInt();
            isAlive = file.readBoolean();
            if (file.readInt() == pokemonIndex && isAlive) {
               try {
                  pokemon = binToPokemon(file, pos);
                  break;
               } catch (Exception e) {
                  e.printStackTrace();
               }
            } else {
               // entender esse -5
               file.skipBytes(pokemonIndex - 5);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return pokemon;
   }

   public Pokemon Read(int pokemonIndex) throws IOException {
      long pos;
      int qntBytesInic, id;
      Pokemon pokemon = null;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade
                                         // de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Pokemon específico
            id = file.readInt(); // Armazena o ID do registro Pokemon específico
            if (id == pokemonIndex) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  try {
                     pokemon = binToPokemon(file, pos); // Gera uma instância de Game e popula com as informações do
                                                        // banco
                     // de dados
                     break;
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               } else {
                  return pokemon;
               }
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
         pokemon = null;
      }

      return pokemon;
   }

   public Pokemon delete(int pokemonID) throws IOException {
      long pos;
      int bytes, id;
      Pokemon pokemon = null;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade
                                         // de bytes no registo).
            bytes = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Game específico
            id = file.readInt();
            if (id == pokemonID) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  try {
                     file.seek(pos); // Volta para começo do registro
                     file.readInt(); // Pula primeira parte do registro
                     file.writeBoolean(false); // Acessa posição da lápide e deixa ela falsa
                     break;
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               } else {
                  file.skipBytes(bytes - 5); // Pula para o próximo registro
               }
            } else {
               file.skipBytes(bytes - 5); // Pula para o próximo registro
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
         pokemon = null;
      }

      return pokemon;
   }

   public void update(Pokemon pokemon) throws IOException, ParseException {
      file.seek(0);
      file.writeInt(pokemon.getIndex());
      file.seek(file.length());
      byte[] byteArray = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArray);
   }

   public Pokemon binToPokemon(RandomAccessFile file, long pos) throws IOException {
      Pokemon pokemon = new Pokemon();

      ArrayList<String> typeArrList = new ArrayList<String>();
      ArrayList<String> abilitiesArrList = new ArrayList<String>();

      file.seek(pos);
      file.readInt(); // pula id do registro
      pokemon.lapide = file.readBoolean();
      pokemon.setIndex(file.readInt());
      pokemon.setPokedexNum(file.readInt());
      file.readInt(); // pula tam. do nome
      pokemon.setName(file.readUTF());
      file.readInt(); // pula tam. da geração
      pokemon.setGeneration(file.readUTF());
      file.readInt(); // pula tam. da especie
      pokemon.setSpecie(file.readUTF());
      file.readInt(); // pula tam. da hidden ability
      pokemon.setHiddenAbility(file.readUTF());
      pokemon.setReleaseDate(file.readLong());

      for (int i = 0; i < file.readInt(); i++) {
         file.readInt(); // pula tam. de cada tipo
         typeArrList.add(file.readUTF());
      }
      pokemon.setTypes(typeArrList);
      for (int i = 0; i < file.readInt(); i++) {
         file.readInt(); // pula tam. de cada habilidade
         abilitiesArrList.add(file.readUTF());
      }
      pokemon.setTypes(abilitiesArrList);

      return pokemon;
   }

   public Boolean search(int entradaID) throws IOException {
      long index;
      int qntBytesInic, id;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            index = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a
                                           // quantidade
                                           // de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean();
            id = file.readInt();
            if (id == entradaID) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  return false;
               } else {
                  file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
               }
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return true;
   }
}
