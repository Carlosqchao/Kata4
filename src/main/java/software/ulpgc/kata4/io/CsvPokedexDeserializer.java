package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

public class CsvPokedexDeserializer implements PokedexDeserializer {
    private static final int ID=0;
    private static final int POKEDEX_NUMBER=1;
    private static final int NAME=2;
    private static final int GENERATION=5;
    private static final int WEIGHT=14;
    private static final int ABILITIES_NUMBER=15;

    @Override
    public Pokedex deserialize(String line){
        return read(line.split(";"));
    }

    private Pokedex read(String[] fields){
        return new Pokedex(
                toInteger(fields[ID]),
                toInteger(fields[POKEDEX_NUMBER]),
                fields[NAME],
                toInteger(fields[GENERATION]),
                toInteger(fields[ABILITIES_NUMBER]),
                Double.parseDouble(fields[WEIGHT])
        );
    }

    private int toInteger(String field) {
        try{
            return Integer.parseInt(field);
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
