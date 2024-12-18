package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DatabasePokedexWriter implements PokedexWriter {
    private final Connection connection;
    private final PreparedStatement insertStatement;
    private final static String CreateTableStatement = """
            CREATE TABLE IF NOT EXISTS POKEDEX (
                Id  INTEGER PRIMARY KEY,
                Pokedex_number  INTEGER,
                Name TEXT NOT NULL,
                Generation INTEGER,
                Is_Legendary BOOLEAN,
                Type_1 VARCHAR,
                Type_2 VARCHAR,
                Abilities INTEGER,
                Weight_KG DOUBLE
                )
            """;
    private static final String InsertRecordStatement = """
            INSERT INTO POKEDEX (Id, Pokedex_number, Name, Generation,Is_Legendary ,Type_1,Type_2,Abilities, Weight_KG)
            VALUES (?,?,?,?,?,?,?,?,?)
            """;


    public DatabasePokedexWriter(File file) throws SQLException{
        this(connectionFor(file));
    }

    public DatabasePokedexWriter(String connection) throws SQLException {
        this.connection = DriverManager.getConnection(connection);
        this.connection.setAutoCommit(false);
        this.insertStatement = initDatabase(this.connection);
    }

    private PreparedStatement initDatabase(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(CreateTableStatement);
        return connection.prepareStatement(InsertRecordStatement);
    }

    private static String connectionFor(File file){
        return "jdbc:sqlite:" + file.getAbsolutePath();
    }

    @Override
    public void write(Pokedex pokedex) throws IOException {
        try{
            updateInsertStatementWith(pokedex);
            insertStatement.execute();
        }catch (SQLException e){
            throw new IOException(e.getMessage());
        }
    }

    private void updateInsertStatementWith(Pokedex pokedex) throws SQLException {
        for(Parameter parameter : toParameter(pokedex))
            updateInsertStatementWith(parameter);
    }
    private void  updateInsertStatementWith(Parameter parameter) throws  SQLException{
        if (isNull(parameter.value))
            insertStatement.setNull(parameter.id, parameter.type);
        else
            insertStatement.setObject(parameter.id, parameter.value);
    }

    private boolean isNull(Object value){
        return value instanceof Integer && (Integer) value == -1;
    }

    private List<Parameter> toParameter(Pokedex pokedex){
        return List.of(
                new Parameter(1, pokedex.id(),Types.INTEGER),
                new Parameter(2, pokedex.pokedex_number(),Types.INTEGER),
                new Parameter(3, pokedex.name(),Types.LONGNVARCHAR),
                new Parameter(4, pokedex.generation(),Types.INTEGER),
                new Parameter(5,pokedex.isLegendary(),Types.BOOLEAN),
                new Parameter(6,pokedex.firstType().name(),Types.LONGNVARCHAR),
                new Parameter(7,pokedex.secondType().name(),Types.LONGNVARCHAR),
                new Parameter(8, pokedex.abilities(),Types.INTEGER),
                new Parameter(9,pokedex.weight(),Types.DOUBLE)

        );
    }

    private record Parameter(int id, Object value, int type){}

    @Override
    public void close() throws Exception{
        this.connection.commit();
    }


}
