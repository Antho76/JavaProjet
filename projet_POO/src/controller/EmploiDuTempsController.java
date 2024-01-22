package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import classes.Cours;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseManager;
import java.time.*;

public class EmploiDuTempsController {
	
	private static final String DB_FILE_NAME = "myDb.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE_NAME;
	public static Connection connect() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String getNomMatiere(int idMatiere) {
	    String nomMatiere = "";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = connect(); // Assurez-vous que cette méthode établit une connexion à votre base de données
	        String query = "SELECT nom FROM matiere WHERE id = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, idMatiere);

	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            nomMatiere = resultSet.getString("nom");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Fermeture des ressources
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return nomMatiere;
	}


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public String[][] getCoursPourSemaineInterface(LocalDate dateDebutSemaine, LocalDate dateFinSemaine) {
        LocalDate dateDebut = dateDebutSemaine;
        LocalDate dateFin = dateFinSemaine;

        // Appel à DatabaseManager pour récupérer les cours
        List<Cours> coursList = DatabaseManager.getCoursPourSemaine(dateDebut, dateFin);

        // Traitement des données pour les mettre dans le format requis par le frontend
        String[][] semaine = new String[10][6]; // Exemple de tableau avec 10 créneaux horaires et 5 jours de la semaine
        // Initialisation du tableau
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                semaine[i][j] = ""; // Initialise avec des chaînes vides
            }
        }
        // Remplissage du tableau avec les cours
        for (Cours cours : coursList) {
            int jour = cours.getDate().getDayOfWeek().getValue() - 1; // Lundi = 1, Dimanche = 7
            int creneau = cours.getHeure(); // Supposer que l'heure correspond à un créneau
            int id = cours.getMatiere();
            String nomMatiere = getNomMatiere(id);
            semaine[creneau][jour] = nomMatiere; // Supposer que vous avez une méthode getNomMatiere() dans Cours
        }

        return semaine;
    }
}

