package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

public interface PokedexDeserializer {
    Pokedex deserialize(String line);
}
