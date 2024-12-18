package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;
import software.ulpgc.kata4.model.Types;

public class CsvPokedexDeserializer implements PokedexDeserializer {
    private static final int ID=0;
    private static final int POKEDEX_NUMBER=1;
    private static final int NAME=2;
    private static final int GENERATION=5;
    private static final int ISLEGENDARY=7;
    private static final int FIRST_TYPE=11;
    private static final int SECONDTYPE=12;
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
                toBoolean(fields[ISLEGENDARY]),
                toType(fields[FIRST_TYPE]),
                toType(fields[SECONDTYPE]),
                toInteger(fields[ABILITIES_NUMBER]),
                Double.parseDouble(fields[WEIGHT])

        );
    }

    public static Types toType(String field) {
        return Types.parseType(field);
    }

    private boolean toBoolean(String field) {
        return field.equals("1");
    }

    private int toInteger(String field) {
        try{
            return Integer.parseInt(field);
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
