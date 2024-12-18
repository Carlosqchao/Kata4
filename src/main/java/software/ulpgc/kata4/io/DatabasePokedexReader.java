package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Pokedex;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

import static software.ulpgc.kata4.io.CsvPokedexDeserializer.toType;

public class DatabasePokedexReader implements PokedexReader,AutoCloseable {
    static Random random =  new Random();
    private final Connection connection;

    public DatabasePokedexReader(File file) throws SQLException {
        this(connectionFor(file));
    }

    private static int generateRandomNum(Random random){
        return random.nextInt(890)+1;
    }

    private void initDatabase(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(SelectRecordStatement());
        connection.prepareStatement(SelectRecordStatement());
    }

    public DatabasePokedexReader(String connection) throws SQLException{
        this.connection = DriverManager.getConnection(connection);
        this.connection.setAutoCommit(false);
        initDatabase(this.connection);
    }
    private static String connectionFor(File file){
        return "jdbc:sqlite:" + file.getAbsolutePath();
    }
    @Override
    public Pokedex read() throws IOException{
        try(Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(SelectRecordStatement());
            return new Pokedex(
                    result.getInt("Id"),
                    result.getInt("Pokedex_number"),
                    result.getString("Name"),
                    result.getInt("Generation"),
                    result.getInt("Is_Legendary")==1,
                    toType(result.getString("Type_1")),
                    toType(result.getString("Type_2")),
                    result.getInt("Abilities"),
                    result.getDouble("Weight_KG")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String SelectRecordStatement() {
        return """
                SELECT * FROM POKEDEX WHERE Id=""" + generateRandomNum(random);
    }

    @Override
    public void close() throws Exception{
        this.connection.commit();
    }
}
