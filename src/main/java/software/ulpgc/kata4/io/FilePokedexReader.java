package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

import java.io.*;

public class FilePokedexReader implements AutoCloseable, PokedexReader {

    private final PokedexDeserializer deserializer;
    private final BufferedReader reader;

    public FilePokedexReader(File file, PokedexDeserializer deserializer) throws IOException {
        this.deserializer = deserializer;
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)
                )
        );
        this.reader.readLine();
    }

    @Override
    public Pokedex read() throws IOException{
        return deserializer(reader.readLine());
    }

    private Pokedex deserializer(String line){
        return line != null ?
                deserializer.deserialize(line) :
                null;
    }

    @Override
    public void close() throws Exception {
    }
}
