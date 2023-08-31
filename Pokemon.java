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

    public void releaseDate(Date releaseDate) {
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

    // Empty Constructor
    public Pokemon() {
        this.name = this.generation = this.specie = hiddenAbility = null;
        this.index = this.pokedexNum = -1;
        this.types = abilities = new ArrayList<String>();
        this.releaseDate = null;
    }

    // Create a parseCSV or parseLine

    // Important REGEX: ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"
}