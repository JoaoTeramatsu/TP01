import java.io.RandomAccessFile;
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
   //Fazer Método READ
   public void read(int pokemonIndex) throws IOException{
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
                  //convertTo
                  break;
               }catch(Exception e){e.printStackTrace();}
            }else{
               //entender esse -5
               file.skipBytes(pokemonIndex - 5);
            }
         }
      }catch(IOException e){e.printStackTrace();}
   }
   
   public Pokemon binToPokemon(RandomAccessFile file, long pos){
      Pokemon pokemon = new Pokemon();
      return pokemon;
   }
}
