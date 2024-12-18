package software.ulpgc.kata4.model;

public enum Types {
    Grass,
    Fire,
    Water,
    Bug,
    Normal,
    Dark,
    Poison,
    Electric,
    Ground,
    Ice,
    Fairy,
    Steel,
    Fighting,
    Psychic,
    Rock,
    Ghost,
    Dragon,
    Flying,
    None;



    public static Types parseType(String field) {
        for (Types types : Types.values()) {
            if (field.equals(types.name())) return types;
        }
        return None;
    }
}
