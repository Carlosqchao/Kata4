package software.ulpgc.kata4.model;

public record Pokedex(int id, int pokedex_number, String name, int generation,boolean isLegendary, Types firstType, Types secondType,int abilities, double weight) {
}
