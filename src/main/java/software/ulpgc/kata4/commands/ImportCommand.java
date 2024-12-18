package software.ulpgc.kata4.commands;

import software.ulpgc.kata4.io.*;
import software.ulpgc.kata4.model.Pokedex;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ImportCommand implements Command{

    @Override
    public String executeReading() throws Exception {
        File output = getOutputFile();
        Pokedex pokemon = doExecuteReading(output);
        return pokemon.toString();
    }

    @Override
    public void executeWriting(){
        try{
            File input = getInputFile();
            File output = getOutputFile();
            doExecuteWriting(input,output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private File getOutputFile() {
        return new File("Pokedex.db");
    }

    private File getInputFile(){
        return new File("pokedex_(Update.04.20).csv");
    }

    private void doExecuteWriting(File input, File output) throws Exception{
        try (PokedexReader reader = createPokedexReader(input);
             PokedexWriter writer = createPokedexWriter(output)) {
            while (true){
                Pokedex pokedex = reader.read();
                if (pokedex==null) break;
                writer.write(pokedex);
            }
        }
    }
    private Pokedex doExecuteReading(File input) throws Exception{
        input = getOutputFile();
        try (DatabasePokedexReader reader = Reader(input)) {
            return reader.read();
        }

    }

    private static FilePokedexReader createPokedexReader(File input) throws IOException {
        PokedexDeserializer deserializer = new CsvPokedexDeserializer();
        return new FilePokedexReader(input,deserializer);
    }

    private static DatabasePokedexWriter createPokedexWriter(File output) throws SQLException {
        return new DatabasePokedexWriter(deleteIfExists(output));
    }

    private static DatabasePokedexReader Reader(File output) throws SQLException {
        return new DatabasePokedexReader(output);
    }

    private static File deleteIfExists(File file) {
        if (file.exists()) file.delete();
        return file;
    }



}
