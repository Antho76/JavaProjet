package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import classes.Cours;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseManager;
import java.time.*;
import classes.Enseignant;
import classes.Salle;
import classes.Batiment;

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
	        connection = connect(); 
	        String query = "SELECT nomMatiere FROM matiere WHERE numeroMatiere = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, idMatiere);

	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            nomMatiere = resultSet.getString("nomMatiere");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	       
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
	public Enseignant getEnseignantById(int id) {
       
        return DatabaseManager.getEnseignantById(id);
    }
	
	public Salle getSalleByIdInterface(int id) {
       
        return DatabaseManager.getSalleById(id);
    }
	
	public Batiment getBatimentByIdInterface(int id) {
		return DatabaseManager.getBatimentById(id);
	}

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String[][] getCoursPourSemaineInterface(LocalDate dateDebutSemaine, LocalDate dateFinSemaine, int idEtudiant ) {
    	List<Cours> coursList = filtrerCoursParEtudiant(DatabaseManager.getCoursPourSemaine(dateDebutSemaine, dateFinSemaine),idEtudiant);
        LocalDate dateDebut = dateDebutSemaine;
        LocalDate dateFin = dateFinSemaine;

     
        String[][] semaine = new String[10][6]; 
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                semaine[i][j] = "";
            }
        }
        for (Cours cours : coursList) {
            int jour = cours.getDate().getDayOfWeek().getValue() - 1;
            int creneau = cours.getHeure(); 
            int id = cours.getMatiere();
            int idEnseignant = cours.getEnseignant();
            int idSalle= cours.getSalle();
            Salle salle = getSalleByIdInterface(idSalle);
            int idBatiment = salle.getIdBatiment();
            Batiment batiment = getBatimentByIdInterface(idBatiment);
            String nomBatiment = batiment.getNomBatiment();
            Enseignant enseignant = getEnseignantById(idEnseignant);
            String nomEnseignant=enseignant.getNom();
            String nomMatiere = getNomMatiere(id);
            if(creneau<8) {
            	int temp=8-creneau;
            	creneau+=temp;
            }
            semaine[creneau-8][jour] = nomMatiere+"\n enseignant : "+nomEnseignant+ "\n Batiment : "+nomBatiment+"\n salle : "+idSalle; 
        }

        return semaine;
    }
    
    private List<Cours> filtrerCoursParEtudiant(List<Cours> coursList, int idEtudiant) {
        return coursList.stream()
                .filter(cours -> Arrays.asList(cours.getTabEtudiants().split(",")).contains(String.valueOf(idEtudiant)))
                .collect(Collectors.toList());
    }
    
    public String[][] getCoursPourSemaineInterfaceEnseignant(LocalDate dateDebutSemaine, LocalDate dateFinSemaine, int idEnseignant ) {
    	List<Cours> coursList = filtrerCoursParEnseignant(DatabaseManager.getCoursPourSemaine(dateDebutSemaine, dateFinSemaine),idEnseignant);
        LocalDate dateDebut = dateDebutSemaine;
        LocalDate dateFin = dateFinSemaine;

       
        String[][] semaine = new String[10][6];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                semaine[i][j] = "";
            }
        }
        for (Cours cours : coursList) {
            int jour = cours.getDate().getDayOfWeek().getValue() - 1;
            int creneau = cours.getHeure();
            int id = cours.getMatiere();
            int idSalle= cours.getSalle();
            Salle salle = getSalleByIdInterface(idSalle);
            int idBatiment = salle.getIdBatiment();
            Batiment batiment = getBatimentByIdInterface(idBatiment);
            String nomBatiment = batiment.getNomBatiment();
            Enseignant enseignant = getEnseignantById(idEnseignant);
            String nomEnseignant=enseignant.getNom();
            String nomMatiere = getNomMatiere(id);
            if(creneau<8) {
            	int temp=8-creneau;
            	creneau+=temp;
            }
            semaine[creneau-8][jour] = nomMatiere+"\n enseignant : "+nomEnseignant+ "\n Batiment : "+nomBatiment+"\n salle : "+idSalle; 
        }

        return semaine;
    }
    
    private List<Cours> filtrerCoursParEnseignant(List<Cours> coursList, int idEnseignant) {
        return coursList.stream()
                .filter(cours -> cours.getEnseignant() == idEnseignant)
                .collect(Collectors.toList());
    }


}

