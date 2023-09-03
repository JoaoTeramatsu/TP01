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
      file.seek(file.length());

      byte[] byteArr = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArr);
   }

   public void create(Pokemon pokemon) throws IOException {
      create(this.file, pokemon);
   }

}
