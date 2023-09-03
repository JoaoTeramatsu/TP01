import java.io.RandomAccessFile;
import java.io.IOException;

public class CRUD {
   private RandomAccessFile file;

   CRUD(String filename) throws IOException{
      this.file = new RandomAccessFile(filename, "rw");
   }

}
