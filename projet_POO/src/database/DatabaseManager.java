package database;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import classes.Cours;
import java.time.*;

import classes.*;

public class DatabaseManager {
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

    public static void createDatabaseIfNotExists() {
        File dbFile = new File(DB_FILE_NAME);

        if (!dbFile.exists()) {
            try (Connection connection = connect()) {
                Class<?>[] classesToCreateTablesFor = {
                        Avertissement.class, Batiment.class, Cours.class, Enseignant.class,
                        Etudiant.class, Evaluation.class, Formation.class, Matiere.class,
                        Personnel.class, Promotion.class, Salle.class
                };

                for (Class<?> clazz : classesToCreateTablesFor) {
                    String tableName = clazz.getSimpleName().toLowerCase();
                    StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                            .append(tableName)
                            .append(" (");

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        createTableQuery.append(field.getName()).append(" ")
                                .append(getSqlType(field.getType())).append(", ");
                    }
                    createTableQuery.setLength(createTableQuery.length() - 2); 
                    createTableQuery.append(")");

                    try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery.toString())) {
                        preparedStatement.executeUpdate();
                    }
                }

                insertAdminPrincipal(connection);
                insertPromoPrincipal(connection);
                insertFormationPrincipal(connection);
                insertElevePrincipal(connection);
                insertMatierePrincipale(connection);
                insertEnseignantPrincipal(connection);
                insertBatimentPrincipale(connection);
                insertSallePrincipale(connection);
                insertCoursPrincipal(connection);
                

                System.out.println("Tables créées avec succès!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertAdminPrincipal(Connection connection) {
		Personnel newPersonnel = new Personnel(0, "admin","admin","00/00/0000","admin","admin","adminpass");
		
    	String query = "INSERT INTO personnel (id, nom, prenom, dateNaissance, metier, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newPersonnel.getId());
            preparedStatement.setString(2, newPersonnel.getNom());
            preparedStatement.setString(3, newPersonnel.getPrenom());
            preparedStatement.setString(4, newPersonnel.getDateNaissance());
            preparedStatement.setString(5, newPersonnel.getMetier());;
            preparedStatement.setString(6, newPersonnel.getLogin());
            preparedStatement.setString(7, newPersonnel.getPassword());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
   }
    
    private static void insertElevePrincipal(Connection connection) {
		Etudiant newEtudiant = new Etudiant(0,"etudiant","etudiant",0,"00/00/0000", 0,"etudiant","etudiantpass");
		
    	String query = "INSERT INTO etudiant (id, nom, prenom, dateNaissance, login, password, idFormation, idPromotion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newEtudiant.getId());
            preparedStatement.setString(2, newEtudiant.getNom());
            preparedStatement.setInt(7, newEtudiant.getFormation());
            preparedStatement.setString(3, newEtudiant.getPrenom());
            preparedStatement.setString(4, newEtudiant.getDateNaissance());
            preparedStatement.setString(5, newEtudiant.getLogin());;
            preparedStatement.setString(6, newEtudiant.getPassword());
            preparedStatement.setInt(8, newEtudiant.getPromotion());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
   }
    private static void insertEnseignantPrincipal(Connection connection) {
		Enseignant newEnseignant = new Enseignant(0,"enseignant","enseignant","00/00/0000",0,"enseignant","enseignantpass");
		
    	String query = "INSERT INTO enseignant (id, nom, prenom, dateNaissance, idMatiere, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newEnseignant.getId());
            preparedStatement.setString(2, newEnseignant.getNom());
            preparedStatement.setString(3, newEnseignant.getPrenom());
            preparedStatement.setString(4, newEnseignant.getDateNaissance());
            preparedStatement.setInt(5, newEnseignant.getMatiere());;
            preparedStatement.setString(6, newEnseignant.getLogin());;
            preparedStatement.setString(7, newEnseignant.getPassword());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
   } 
    
    private static void insertPromoPrincipal(Connection connection) {

	    Promotion promotion = new Promotion(0,2023, 0);
		
		String query = "INSERT INTO promotion (numeroPromotion, annee, idFormation) VALUES (?, ?, ?)";
	
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, promotion.getId());
	        preparedStatement.setInt(2, promotion.getAnnee());
	        preparedStatement.setInt(3, promotion.getidFormation());

	
	        preparedStatement.executeUpdate();
	    }
	     catch (SQLException e) {
	        e.printStackTrace();
	     }
    }
    private static void insertFormationPrincipal(Connection connection) {

    Formation formation = new Formation(0,"formation");
	

	String query = "INSERT INTO formation (id_formation,nomFormation) VALUES (?, ?)";


    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, formation.getId_Formation());
        preparedStatement.setString(2, formation.getNomFormation());

        preparedStatement.executeUpdate();
    }
     catch (SQLException e) {
        e.printStackTrace();
     }
}
    
    private static void insertMatierePrincipale(Connection connection) {

        Matiere matiere = new Matiere(0,"matiere",1);
    	
    	String query = "INSERT INTO matiere (numeroMatiere,nomMatiere, coefficient) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, matiere.getNumeroMatiere());
            preparedStatement.setString(2, matiere.getNomMatiere());
            preparedStatement.setInt(3, matiere.getCoefficient());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
    }
    
    private static void insertBatimentPrincipale(Connection connection) {
    	
        Batiment batiment = new Batiment(0,"Valenciennes","AB1");
    	
       	String query = "INSERT INTO batiment (numeroBatiment, ville, nomBatiment) VALUES ( ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, batiment.getNumeroBatiment());
            preparedStatement.setString(2, batiment.getVille());
            preparedStatement.setString(3, batiment.getNomBatiment());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
    }
    
    private static void insertSallePrincipale(Connection connection) {
    	
        Salle salle = new Salle(0,10,false,0);
    	
       	String query = "INSERT INTO salle (numeroSalle, nbPlaces, equipInfo, idBatiment) VALUES ( ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, salle.getNumeroSalle());
            preparedStatement.setInt(2, salle.getNbPlaces());
            preparedStatement.setBoolean(3, salle.getEquipInfo());
            preparedStatement.setInt(4, salle.getIdBatiment());

            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
    }
    
    private static void insertCoursPrincipal(Connection connection) {
    	LocalDate ld = LocalDate.of(2024, 1, 23);
        Cours cours = new Cours(0,0,"",0,ld,0,0,0);
    	
    	String query = "INSERT INTO cours (idCours,nbEtudiant, tabEtudiants, idEnseignant, date, heure, idMatiere, idSalle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cours.getId());
            preparedStatement.setInt(2, cours.getNbEtudiant());
            preparedStatement.setString(3, cours.getTabEtudiants());
            preparedStatement.setInt(4, cours.getEnseignant());
            preparedStatement.setObject(5, cours.getDate());
            preparedStatement.setInt(6, cours.getHeure());
            preparedStatement.setInt(7, cours.getMatiere());
            preparedStatement.setInt(8, cours.getSalle());
            
            preparedStatement.executeUpdate();
        }
         catch (SQLException e) {
            e.printStackTrace();
         }
    }
		
    

    private static String getSqlType(Class<?> type) {
        if (type == String.class) {
            return "STRING";
        } else if (type == int.class || type == Integer.class) {
            return "INTEGER";
        } else if (type == long.class || type == Long.class) {
            return "INTEGER"; 
        } else if (type == double.class || type == Double.class || type == float.class || type == Float.class) {
            return "REAL";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN"; 
        } else if (List.class.isAssignableFrom(type)) {
           
            ParameterizedType parameterizedType = (ParameterizedType) type.getGenericSuperclass();
            Class<?> elementType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return getSqlType(elementType) + "[]";
        } else {
           
            return "TEXT";
        }
    }
    
    public static void insertAvertissement(Avertissement avertissement) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO avertissement (nom, idEtudiant, idEnseignant) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, avertissement.getNom());
                preparedStatement.setInt(2, avertissement.getEtudiant());
                preparedStatement.setInt(3, avertissement.getEnseignant());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertBatiment(Batiment batiment) {
        try (Connection connection = connect()) {
        	int val = getMaxBatId();
            String query = "INSERT INTO batiment (numeroBatiment, ville, nomBatiment) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, val+1);
                preparedStatement.setString(2, batiment.getVille());
                preparedStatement.setString(3, batiment.getNomBatiment());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertCours(Cours cours) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO cours (idCours, nbEtudiant, tabEtudiants, idEnseignant, date, heure, idMatiere, idSalle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, cours.getId());
                preparedStatement.setInt(2, cours.getNbEtudiant());
                preparedStatement.setString(3, cours.getTabEtudiants());
                preparedStatement.setInt(4, cours.getEnseignant());
                preparedStatement.setObject(5, cours.getDate());
                preparedStatement.setInt(6, cours.getHeure());
                preparedStatement.setInt(7, cours.getMatiere());
                preparedStatement.setInt(8, cours.getSalle());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertEvaluation(Evaluation evaluation) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO evaluation (nom, idMatiere, idEnseignant, note, idEtudiant) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, evaluation.getNom());
                preparedStatement.setInt(2, evaluation.getMatiere());
                preparedStatement.setInt(3, evaluation.getEnseignant());
                preparedStatement.setInt(4, evaluation.getNote());
                preparedStatement.setInt(5, evaluation.getEtudiant());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertEtudiant(Etudiant etudiant) {
        try (Connection connection = connect()) {
            
            if (!etudiantExists(connection, etudiant)) {
                String query = "INSERT INTO etudiant (id, nom, prenom, idPromotion, dateNaissance, idFormation, login, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, etudiant.getId());
                    preparedStatement.setString(2, etudiant.getNom());
                    preparedStatement.setString(3, etudiant.getPrenom());
                    preparedStatement.setInt(4, etudiant.getPromotion());
                    preparedStatement.setString(5, etudiant.getDateNaissance());
                    preparedStatement.setInt(6, etudiant.getFormation());
                    preparedStatement.setString(7, etudiant.getLogin());
                    preparedStatement.setString(8, etudiant.getPassword());

                    int rowsAffected = preparedStatement.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        
                        return true;
                    }
                }
            } else {
               
                System.out.println("Étudiant déjà existant. L'ajout n'a pas été effectué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return false;
    }

   
    private static boolean etudiantExists(Connection connection, Etudiant etudiant) throws SQLException {
        String query = "SELECT COUNT(*) FROM etudiant WHERE nom = ? AND prenom = ? AND dateNaissance = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setString(3, etudiant.getDateNaissance());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    public static boolean insertEnseignant(Enseignant enseignant) {
        try (Connection connection = connect()) {
            if (!enseignantExists(connection, enseignant)) {
                String query = "INSERT INTO enseignant (id, nom, prenom, dateNaissance, idMatiere, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, enseignant.getId());
                    preparedStatement.setString(2, enseignant.getNom());
                    preparedStatement.setString(3, enseignant.getPrenom());
                    preparedStatement.setString(4, enseignant.getDateNaissance());
                    preparedStatement.setInt(5, enseignant.getMatiere());
                    preparedStatement.setString(6, enseignant.getLogin());
                    preparedStatement.setString(7, enseignant.getPassword());

                    preparedStatement.executeUpdate();
                    return true; 
                }
            } else {
                
                System.out.println("Enseignant déjà existant. L'ajout n'a pas été effectué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean enseignantExists(Connection connection, Enseignant enseignant) throws SQLException {
        String query = "SELECT COUNT(*) FROM enseignant WHERE nom = ? AND prenom = ? AND dateNaissance = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, enseignant.getNom());
            preparedStatement.setString(2, enseignant.getPrenom());
            preparedStatement.setString(3, enseignant.getDateNaissance());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    
    public static boolean insertFormation(String nomFormation) {
        try (Connection connection = connect()) {
            if (!formationExists(connection, nomFormation)) {
            	int value = getMaxFormationId();

                String query = "INSERT INTO formation (id_formation, nomFormation) VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, value+1);
                    preparedStatement.setString(2, nomFormation);
                    preparedStatement.executeUpdate();
                    return true; 
                }
            } else {
                System.out.println("La formation existe déjà. L'ajout n'a pas été effectué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; 
    }

    private static boolean formationExists(Connection connection, String nomFormation) throws SQLException {
        String query = "SELECT COUNT(*) FROM formation WHERE nomFormation = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nomFormation);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    
    public static void insertMatiere(Matiere matiere) {
        try (Connection connection = connect()) {
        	int val = getMaxMatiereId();
            String query = "INSERT INTO matiere (numeroMatiere, nomMatiere, coefficient) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, val +1 );
                preparedStatement.setString(2, matiere.getNomMatiere());
                preparedStatement.setInt(3, matiere.getCoefficient());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static boolean insertPersonnel(Personnel personnel) {
        try (Connection connection = connect()) {
            if (!personnelExists(connection, personnel)) {
                String query = "INSERT INTO personnel (id, nom, prenom, dateNaissance, metier, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, personnel.getId());
                    preparedStatement.setString(2, personnel.getNom());
                    preparedStatement.setString(3, personnel.getPrenom());
                    preparedStatement.setString(4, personnel.getDateNaissance());
                    preparedStatement.setString(5, personnel.getMetier());
                    preparedStatement.setString(6, personnel.getLogin());
                    preparedStatement.setString(7, personnel.getPassword());

                    preparedStatement.executeUpdate();
                    return true; 
                }
            } else {
               
                System.out.println("Personnel déjà existant. L'ajout n'a pas été effectué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; 
    }

    private static boolean personnelExists(Connection connection, Personnel personnel) throws SQLException {
        String query = "SELECT COUNT(*) FROM personnel WHERE nom = ? AND prenom = ? AND dateNaissance = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, personnel.getNom());
            preparedStatement.setString(2, personnel.getPrenom());
            preparedStatement.setString(3, personnel.getDateNaissance());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    public static void insertPromotion(int annee, int idFormation) {
        try (Connection connection = connect()) {
        	int val = getMaxPromotionId();
        	System.out.println(idFormation);
            String query = "INSERT INTO promotion (numeroPromotion, annee, idFormation ) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, val+1);
                preparedStatement.setInt(2, annee);
                preparedStatement.setInt(3, idFormation);

                

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSalle(Salle salle) {
        try (Connection connection = connect()) {
            int val = getMaxSalleId();
            String query = "INSERT INTO salle (numeroSalle, nbPlaces, equipInfo, idBatiment) VALUES ( ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, val + 1);
                preparedStatement.setInt(2, salle.getNbPlaces());
                preparedStatement.setBoolean(3, salle.getEquipInfo());
                preparedStatement.setInt(4, salle.getIdBatiment());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Etudiant> getStudents() {
    	List<Etudiant> etudiants = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT id, nom, prenom, dateNaissance, genre, login, password, idFormation, idPromotion FROM etudiant";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	Etudiant etudiant = new Etudiant(
                    			resultSet.getInt("id"),
                    		    resultSet.getString("nom"),
                    		    resultSet.getString("prenom"),
                    		    resultSet.getInt("idPromotion"),
                    		    resultSet.getString("dateNaissance"),
                    		    resultSet.getInt("idFormation"),
                    		    resultSet.getString("login"),
                    		    resultSet.getString("password")
                    		);                       
                    	etudiants.add(etudiant);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }
    
    public static List<Personnel> getPersonnel() {
        List<Personnel> personnels = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT id, nom, prenom, dateNaissance, metier, login, password, metier FROM personnel";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	Personnel personnel = new Personnel(
                    			resultSet.getInt("id"),
                                resultSet.getString("nom"),
                                resultSet.getString("prenom"),
                                resultSet.getString("dateNaissance"),
                                resultSet.getString("metier"),
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        );
                    	personnels.add(personnel);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnels;
    }
    
    public static List<Enseignant> getEnseignants() {
        List<Enseignant> enseignants = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT id, nom, prenom, dateNaissance, genre, login, password, idMatiere FROM enseignant";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	Enseignant enseignant = new Enseignant(
                                resultSet.getInt("id"),
                                resultSet.getString("nom"),
                                resultSet.getString("prenom"),
                                resultSet.getString("dateNaissance"),
                                resultSet.getInt("idMatiere"),
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        );
                        enseignants.add(enseignant);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enseignants;
    }
    
    public static Enseignant getEnseignantById(int id) {
        Enseignant enseignant = null;

        try (Connection connection = connect()) {
            String query = "SELECT * FROM enseignant WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        enseignant = new Enseignant(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("dateNaissance"),
                            resultSet.getInt("idMatiere"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enseignant;
    }
    
    public static Matiere getMatiereById(int id) {
        Matiere matiere = null;

        try (Connection connection = connect()) {
            String query = "SELECT * FROM matiere WHERE numeroMatiere = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        matiere = new Matiere(
                            resultSet.getInt("numeroMatiere"),
                            resultSet.getString("nomMatiere"),
                            resultSet.getInt("coefficient")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matiere;
    }
    
    
    public static Salle getSalleById(int id) {
        Salle salle = null;

        try (Connection connection = connect()) {
            String query = "SELECT * FROM salle WHERE numeroSalle = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        salle = new Salle(
                            resultSet.getInt("numeroSalle"),
                            resultSet.getInt("nbPlaces"),
                            resultSet.getBoolean("EquipInfo"),
                            resultSet.getInt("idBatiment")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salle;
    }

    
    public static List<Cours> getCoursPourSemaine(LocalDate dateDebut, LocalDate dateFin) {
    	if(dateDebut==null || dateFin==null) {
    		dateDebut=LocalDate.parse("2024-01-22");
    		dateFin=LocalDate.parse("2024-01-27");
    	}
        List<Cours> coursList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            String query = "SELECT * FROM cours WHERE date BETWEEN ? AND ? ";
            preparedStatement = connection.prepareStatement(query);
       
            preparedStatement.setObject(1, dateDebut);
            preparedStatement.setObject(2, dateFin);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate ld = resultSet.getObject("date", LocalDate.class);
                Cours cours = new Cours(
                    resultSet.getInt("idCours"),
                    resultSet.getInt("nbEtudiant"),
                    resultSet.getString("tabEtudiants"),
                    resultSet.getInt("idEnseignant"),
                    ld,
                    resultSet.getInt("heure"),
                    resultSet.getInt("idMatiere"),
                    resultSet.getInt("idSalle")
                );
                coursList.add(cours);
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

        return coursList;
    }
    
    
    

    public static List<Matiere> getMatiere() {
        List<Matiere> listMatiere = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT numeroMatiere, nomMatiere, coefficient FROM matiere";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	
                    	Matiere matiere = new Matiere(
                                resultSet.getInt("numeroMatiere"),
                                resultSet.getString("nomMatiere"),
                                resultSet.getInt("coefficient")

                               
                        );
                    	listMatiere.add(matiere);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listMatiere;
    }
    
    public static List<Salle> getSalle() {
        List<Salle> listSalle = new ArrayList<>();
        try (Connection connection = connect()) {
            String query = "SELECT numeroSalle, nbPlaces, equipInfo, idBatiment FROM salle";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Salle salle = new Salle(
                                resultSet.getInt("numeroSalle"),
                                resultSet.getInt("nbPlaces"),
                                resultSet.getBoolean("equipInfo"),
                                resultSet.getInt("idBatiment")
                        );
                        listSalle.add(salle);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listSalle;
    }

    
    public static List<Cours> getCours() {
        List<Cours> listCours = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT idCours, nbEtudiant, tabEtudiants, idEnseignant, date, heure, idMatiere, idSalle FROM cours";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	LocalDate ld = resultSet.getObject( "date" , LocalDate.class );
                    	Cours cours = new Cours(
                                resultSet.getInt("idCours"),
                                resultSet.getInt("nbEtudiant"),
                                resultSet.getString("tabEtudiants"),
                                resultSet.getInt("idEnseignant"),
                                ld,
                                resultSet.getInt("heure"),
                                resultSet.getInt("idMatiere"),
                                resultSet.getInt("idSalle") 
                        );
                    	listCours.add(cours);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listCours;
    }
    
    
    public static List<Avertissement> getAvertissement() {
        List<Avertissement> avertissements = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT nom, idEtudiant, idEnseignant  FROM avertissement";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                    	Avertissement avertissement = new Avertissement(
                    			resultSet.getString("nom"),
                                resultSet.getInt("idEtudiant"),
                                resultSet.getInt("idEnseignant")
                        );
                    	avertissements.add(avertissement);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avertissements;
    }
    
    public static List<Evaluation> getEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT nom, idMatiere, idEnseignant, note, idEtudiant FROM evaluation";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Evaluation evaluation = new Evaluation(
                                resultSet.getString("nom"),
                                resultSet.getInt("idMatiere"),
                                resultSet.getInt("idEnseignant"),
                                resultSet.getInt("note"),
                                resultSet.getInt("idEtudiant")
                        );
                        evaluations.add(evaluation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evaluations;
    }
    
    public static List<Promotion> getPromotions() {
        List<Promotion> promotions = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT numeroPromotion, annee, idFormation FROM promotion";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Promotion promotion = new Promotion(
                                resultSet.getInt("numeroPromotion"),
                                resultSet.getInt("annee"),
                                resultSet.getInt("idFormation")

                        );
                        promotions.add(promotion);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotions;
    }
    
    public static String getNomFormationById(int id) {
        try (Connection connection = connect()) {
            String query = "SELECT nomFormation FROM formation WHERE id_formation = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("nomFormation");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Formation inconnue";
    }
    
    public static List<Formation> getFormations() {
        List<Formation> formations = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT id_formation, nomFormation FROM formation";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Formation formation = new Formation(
                                resultSet.getInt("id_formation"),
                                resultSet.getString("nomFormation")
                        );
                        formations.add(formation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formations;
    }

    public static List<Batiment> getBatiments() {
        List<Batiment> batiments = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT numeroBatiment, ville, nomBatiment FROM batiment";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Batiment batiment = new Batiment(
                                resultSet.getInt("numeroBatiment"),
                                resultSet.getString("ville"),
                                resultSet.getString("nomBatiment")
                        );
                        batiments.add(batiment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batiments;
    }
    
    public static Batiment getBatimentById(int idBatiment) {
        Batiment batiment = null;

        try (Connection connection = connect()) {
            String query = "SELECT numeroBatiment, ville, nomBatiment FROM batiment WHERE numeroBatiment = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idBatiment);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        batiment = new Batiment(
                                resultSet.getInt("numeroBatiment"),
                                resultSet.getString("ville"),
                                resultSet.getString("nomBatiment")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batiment;
    }

    
    public static List<Avertissement> getAvertissementsByIdEtudiant(int idEtudiant) {
        List<Avertissement> avertissements = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT nom, idEtudiant, idEnseignant FROM avertissement WHERE idEtudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEtudiant);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Avertissement avertissement = new Avertissement(
                                resultSet.getString("nom"),
                                resultSet.getInt("idEtudiant"),
                                resultSet.getInt("idEnseignant")
                        );
                        avertissements.add(avertissement);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avertissements;
    }

    public static List<Evaluation> getEvaluationsByIdEtudiant(int idEtudiant) {
        List<Evaluation> evaluations = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT nom, idMatiere, idEnseignant, note, idEtudiant FROM evaluation WHERE idEtudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEtudiant);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Evaluation evaluation = new Evaluation(
                                resultSet.getString("nom"),
                                resultSet.getInt("idMatiere"),
                                resultSet.getInt("idEnseignant"),
                                resultSet.getInt("note"),
                                resultSet.getInt("idEtudiant")
                        );
                        evaluations.add(evaluation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evaluations;
    }


    
    public static int getMaxEtudiantId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(id) FROM etudiant";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    public static int getMaxPersonnelId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(id) FROM personnel";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    public static int getMaxEnseignantId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(id) FROM enseignant";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    

    public static int getMaxFormationId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(id_formation) FROM formation";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    
    public static int getMaxPromotionId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(numeroPromotion) FROM promotion";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    
    public static int getMaxCoursId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(idCours) FROM cours";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    
    public static int getMaxBatId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(numeroBatiment) FROM batiment";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    public static int getMaxSalleId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(numeroSalle) FROM salle";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    
    public static int getMaxMatiereId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            String query = "SELECT MAX(numeroMatiere) FROM matiere";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }
    
    public static void deleteCours(int idCours) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM cours WHERE idCours = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idCours);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteAvertissement(Avertissement avertissement) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM avertissement WHERE nom = ? AND idEtudiant = ? AND idEnseignant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, avertissement.getNom());
                preparedStatement.setInt(2, avertissement.getEtudiant());
                preparedStatement.setInt(3, avertissement.getEnseignant());
                
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteEvaluation(Evaluation evaluation) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM evaluation WHERE nom = ? AND idMatiere = ? AND idEnseignant = ? AND idEtudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, evaluation.getNom());
                preparedStatement.setInt(2, evaluation.getMatiere());
                preparedStatement.setInt(3, evaluation.getEnseignant());
                preparedStatement.setInt(4, evaluation.getEtudiant());
                
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateCours(Cours cours) {
        try (Connection connection = connect()) {
            String query = "UPDATE cours SET nbEtudiant=?, tabEtudiants=?, date=?, heure=? WHERE idCours=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, cours.getNbEtudiant());
                preparedStatement.setString(2, cours.getTabEtudiants());
                preparedStatement.setObject(3, cours.getDate());
                preparedStatement.setInt(4, cours.getHeure());
                preparedStatement.setInt(5, cours.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateEtudiant(Etudiant etudiant) {
        try (Connection connection = connect()) {
            String query = "UPDATE etudiant SET nom=?, prenom=?, idPromotion=?, dateNaissance=?, idFormation=?, login=?, password=? WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, etudiant.getNom());
                preparedStatement.setString(2, etudiant.getPrenom());
                preparedStatement.setInt(3, etudiant.getPromotion());
                preparedStatement.setString(4, etudiant.getDateNaissance());
                preparedStatement.setInt(5, etudiant.getFormation());
                preparedStatement.setString(6, etudiant.getLogin());
                preparedStatement.setString(7, etudiant.getPassword());
                preparedStatement.setInt(8, etudiant.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateEnseignant(Enseignant enseignant) {
        try (Connection connection = connect()) {
            String query = "UPDATE enseignant SET nom=?, prenom=?, dateNaissance=?, idMatiere=?, login=?, password=? WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, enseignant.getNom());
                preparedStatement.setString(2, enseignant.getPrenom());
                preparedStatement.setString(3, enseignant.getDateNaissance());
                preparedStatement.setInt(4, enseignant.getMatiere());
                preparedStatement.setString(5, enseignant.getLogin());
                preparedStatement.setString(6, enseignant.getPassword());
                preparedStatement.setInt(7, enseignant.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateFormation(Formation formation) {
        try (Connection connection = connect()) {
            String query = "UPDATE formation SET nomFormation=? WHERE id_formation=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, formation.getNomFormation());
                preparedStatement.setInt(2, formation.getId_Formation());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updatePromotion(int numeroPromotion, int annee, int idFormation) {
        try (Connection connection = connect()) {
            String query = "UPDATE promotion SET annee=?, idFormation=? WHERE numeroPromotion=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, annee);
                preparedStatement.setInt(2, idFormation);
                preparedStatement.setInt(3, numeroPromotion);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateBatiment(Batiment batiment) {
        try (Connection connection = connect()) {
            String query = "UPDATE batiment SET ville=?, nomBatiment=? WHERE numeroBatiment=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, batiment.getVille());
                preparedStatement.setString(2, batiment.getNomBatiment());
                preparedStatement.setInt(3, batiment.getNumeroBatiment());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateMatiere(Matiere matiere) {
        try (Connection connection = connect()) {
            String query = "UPDATE matiere SET nomMatiere = ?, coefficient = ? WHERE numeroMatiere = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, matiere.getNomMatiere());
                preparedStatement.setInt(2, matiere.getCoefficient());
                preparedStatement.setInt(3, matiere.getNumeroMatiere());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateSalle(Salle salle) {
        try (Connection connection = connect()) {
            String query = "UPDATE salle SET nbPlaces = ?, equipInfo = ?, idBatiment = ? WHERE numeroSalle = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, salle.getNbPlaces());
                preparedStatement.setBoolean(2, salle.getEquipInfo());
                preparedStatement.setInt(3, salle.getIdBatiment());
                preparedStatement.setInt(4, salle.getNumeroSalle());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void deleteEtudiantById(int etudiantId) {
    	deleteAvertissementByIdEtudiant(etudiantId);
    	deleteEvaluationByIdEtudiant(etudiantId);
        try (Connection connection = connect()) {
            String query = "DELETE FROM etudiant WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, etudiantId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
       
    }
    public static void deleteAvertissementByIdEtudiant(int idEtudiant) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM avertissement WHERE idEtudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEtudiant);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteEvaluationByIdEtudiant(int idEtudiant) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM evaluation WHERE idEtudiant = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEtudiant);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteEnseignantById(int idEnseignant) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM enseignant WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idEnseignant);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
