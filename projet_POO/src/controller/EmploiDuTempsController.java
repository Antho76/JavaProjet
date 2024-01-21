/* package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import classes.Cours;
import database.DatabaseManager;

public class EmploiDuTempsController {

    public List<Cours> getCoursPourSemaine(int numeroSemaine) {
        List<Cours> listeCours = new ArrayList<>();
        String sql = "SELECT * FROM Cours WHERE semaine ="+numeroSemaine; 

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setInt(1, numeroSemaine);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cours cours = new Cours();
                // Remplissez l'objet cours avec des données de la base de données
                // Exemple : cours.setHeure(rs.getInt("heure"));
                listeCours.add(cours);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeCours;
    }
}
*/