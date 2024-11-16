package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

import java.io.IOException;

public interface PokedexReader extends AutoCloseable {
    Pokedex read() throws IOException;
}
