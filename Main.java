import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
   public static void main(String args[]){
      try{
         String basefile = "./pokemon.csv";
         FileInputStream fs = new FileInputStream(basefile);
         BufferedReader br = new BufferedReader(new InputStreamReader(fs));

         String csvLine;
         while((csvLine = br.readLine()) != null){
            Pokemon pokemon = new Pokemon();
            pokemon.parseCSV(csvLine); 
         }

      }catch(Exception e){System.out.println("Erro: " + e);}
   }
}
