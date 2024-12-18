package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

import java.io.IOException;

public interface PokedexWriter extends AutoCloseable{
    void write(Pokedex pokedex) throws IOException;
}
