import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
   public static void main(String args[]) throws IOException {
      CRUD crud = new CRUD("pokemonDB");
      String basefile = "pokemonSample.csv";
      try {
         FileInputStream fs = new FileInputStream(basefile);
         BufferedReader br = new BufferedReader(new InputStreamReader(fs));
         String csvLine = br.readLine();
         while ((csvLine = br.readLine()) != null) {
            Pokemon pokemon = new Pokemon();
            System.out.println(csvLine);
            pokemon.parseCSV(csvLine);
            crud.create(pokemon);
         }

         br.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      Scanner fetch = new Scanner(System.in);
      fetch.close();
   }
}
