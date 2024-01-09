package database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseManager {
    private static final String DB_FILE_NAME = "myDb.db"; // Remplacez par le nom souhaité
    private static final String URL = "jdbc:sqlite:" + DB_FILE_NAME;

    // Méthode pour établir une connexion à la base de données
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour créer la base de données si elle n'existe pas
    public static void createDatabaseIfNotExists() {
        File dbFile = new File(DB_FILE_NAME);
        
        if (!dbFile.exists()) {
            try {
            	
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_NAME);
                System.out.println("Bouton cliqué !");
                String createTableQuery = "CREATE TABLE IF NOT EXISTS exemple_table (id INTEGER PRIMARY KEY, nom TEXT)";
                PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery);
                preparedStatement.executeUpdate();
                connection.close();
                System.out.println("Base de données créée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour fermer la connexion à la base de données
    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        createDatabaseIfNotExists();
    }
}
