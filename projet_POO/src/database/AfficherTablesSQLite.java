package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class AfficherTablesSQLite {

    public void afficherTablesSQLite() {
        // Remplacez "jdbc:sqlite:chemin_vers_votre_base_de_donnees.db" par le chemin de votre base de données SQLite
        String url = "jdbc:sqlite:chemin_vers_votre_base_de_donnees.db";

        try (Connection connection = DriverManager.getConnection(url)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", null);

            System.out.println("Tables dans la base de données :");
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
