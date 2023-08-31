import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Pokemon {
    private String name, generation, specie, hiddenAbility;
    private int index, pokedexNum;
    private ArrayList<String> types, abilities;
    private Date releaseDate;

    // Seters
    public void setName(String name) {
        this.name = name;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public void setHiddenAbility(String hiddenAbility) {
        this.hiddenAbility = hiddenAbility;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPokedexNum(int pokedexNum) {
        this.pokedexNum = pokedexNum;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getGeneration() {
        return this.generation;
    }

    public String getSpecie() {
        return this.specie;
    }

    public String getHiddenAbility() {
        return this.hiddenAbility;
    }

    public int getIndex() {
        return this.index;
    }

    public int getPokedexNum() {
        return this.pokedexNum;
    }

    public ArrayList<String> getTypes(){return this.types;}
    public ArrayList<String> getAbilities(){return this.abilities;}
    public Date getReleaseDate(){return this.releaseDate;}

    // Empty Constructor
    public Pokemon() {
        this.name = this.generation = this.specie = hiddenAbility = null;
        this.index = this.pokedexNum = -1;
        this.types = abilities = new ArrayList<String>();
        this.releaseDate = null;
    }

    // Create a parseCSV or parseLine
    // Important REGEX: ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"
    // CSV: [id, pokedexId, name, generation, specie, abilityHidden, releaseDate, types, abilities]
    public void parseCSV(String csvLine){
        String[] cells = csvLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        this.setIndex(Integer.parseInt(cells[0]));
        this.setPokedexNum(Integer.parseInt(cells[1]));
        this.setName(cells[2]);
        this.setGeneration(cells[3]);
        this.setSpecie(cells[4]);
        this.setHiddenAbility(cells[5]);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{this.setReleaseDate(format.parse(cells[6]));}catch(Exception e){}
        //Criar função formatarArray
        
        this.setTypes(formatArray(cells[7]));
        this.setAbilities(formatArray(cells[8]));
    }
    public ArrayList<String> formatArray(String arrayField){
        String[] array = (arrayField.replaceAll("\"" , "")).split(",");
        ArrayList<String> arrayList = new ArrayList<String>();
        try{
            arrayList.add(array[0]);
            if(array[1] != " "){
                arrayList.add(array[1]);
            }
        }catch(Exception e){System.out.println(e);}
        return arrayList;
    }
}