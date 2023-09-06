import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

public class CRUD {
   private RandomAccessFile file;

   CRUD(String filename) throws IOException {
      this.file = new RandomAccessFile(filename, "rw");
   }

   public void create(RandomAccessFile file, Pokemon pokemon) throws IOException {
      file.seek(0);
      file.writeInt(pokemon.getIndex());
      System.out.println(file.length());
      file.seek(file.length());

      byte[] byteArr = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArr);
   }

   public void create(Pokemon pokemon) throws IOException {
      create(this.file, pokemon);
   }

   // Fazer Método READ
   public Pokemon read(int pokemonIndex) throws IOException{
      long pos;
      int bytes, id;
      boolean isAlive;

      Pokemon pokemon = null;

      file.seek(0);
      file.readInt(); //Lê o index do registro x
      try{
         while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer();
            bytes = file.readInt();
            isAlive = file.readBoolean();
            if(file.readInt() == pokemonIndex && isAlive){
               try{
                  pokemon = binToPokemon(file, pos);
                  break;
               }catch(Exception e){e.printStackTrace();}
            }else{
               //entender esse -5
               file.skipBytes(pokemonIndex - 5);
            }
         }
      }catch(IOException e){e.printStackTrace();}
      return pokemon;
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
}
