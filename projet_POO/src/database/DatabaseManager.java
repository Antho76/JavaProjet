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
                // Ajouter des classes à cette liste pour lesquelles vous voulez créer des tables
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
                    createTableQuery.setLength(createTableQuery.length() - 2); // Supprimer la virgule finale
                    createTableQuery.append(")");

                    try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery.toString())) {
                        preparedStatement.executeUpdate();
                    }
                }

                // Ajouter l'administrateur principal à la table Personnel
                insertAdminPrincipal(connection);
                insertPromoPrincipal(connection);
                insertFormationPrincipal(connection);
                insertElevePrincipal(connection);
                insertMatierePrincipale(connection);
                insertEnseignantPrincipal(connection);

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

    Promotion promotion = new Promotion(0,2023);
	
	String query = "INSERT INTO promotion (numeroPromotion, annee) VALUES (?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, promotion.getId());
        preparedStatement.setInt(2, promotion.getAnnee());

        preparedStatement.executeUpdate();
    }
     catch (SQLException e) {
        e.printStackTrace();
     }
}
    private static void insertFormationPrincipal(Connection connection) {

    Formation formation = new Formation(0,"formation",0);
	
	String query = "INSERT INTO formation (id_formation,nomFormation, promotion) VALUES (?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, formation.getId_Formation());
        preparedStatement.setString(2, formation.getNomFormation());
        preparedStatement.setInt(3, formation.getIdPromotion());

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
		
    

    private static String getSqlType(Class<?> type) {
        if (type == String.class) {
            return "STRING";
        } else if (type == int.class || type == Integer.class) {
            return "INTEGER";
        } else if (type == long.class || type == Long.class) {
            return "INTEGER"; // Utilisez INTEGER pour les types long
        } else if (type == double.class || type == Double.class || type == float.class || type == Float.class) {
            return "REAL"; // Utilisez REAL pour les types à virgule flottante
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN"; // Utilisez BOOLEAN pour les types booléens
        } else if (List.class.isAssignableFrom(type)) {
            // Si le champ est de type List, déterminez le type d'élément de la liste
            ParameterizedType parameterizedType = (ParameterizedType) type.getGenericSuperclass();
            Class<?> elementType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            return getSqlType(elementType) + "[]";
        } else {
            // Ajoutez d'autres types de données selon vos besoins
            return "TEXT";
        }
    }
    
    public static void insertEtudiant(Etudiant etudiant) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO etudiant (id, nom, prenom, idPromotion dateNaissance, idFormation, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, etudiant.getId());
                preparedStatement.setString(2, etudiant.getNom());
                preparedStatement.setString(3, etudiant.getPrenom());
                preparedStatement.setInt(4, etudiant.getPromotion());
                preparedStatement.setString(5, etudiant.getDateNaissance());
                preparedStatement.setInt(6, etudiant.getFormation());
                preparedStatement.setString(7, etudiant.getLogin());
                preparedStatement.setString(8, etudiant.getPassword());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertEnseignant(Enseignant enseignant) {
        try (Connection connection = connect()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertPersonnel(Personnel personnel) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO personnel (id, nom, prenom, dateNaissance, metier, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, personnel.getId());
                preparedStatement.setString(2, personnel.getNom());
                preparedStatement.setString(3, personnel.getPrenom());
                preparedStatement.setString(4, personnel.getDateNaissance());
                preparedStatement.setString(5, personnel.getMetier());;
                preparedStatement.setString(6, personnel.getLogin());
                preparedStatement.setString(7, personnel.getPassword());

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
    /*
    public static List<Cours> getCoursPourSemaine(int numeroSemaine) {
        List<Cours> coursList = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT * FROM cours WHERE semaine = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, numeroSemaine);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Remplissez l'objet Cours en supposant que les champs correspondent
                        Cours cours = new Cours();
                        cours.setNbEtudiant(resultSet.getInt("nbEtudiant"));
                        // cours.setTabEtudiants(...); // La gestion des étudiants n'est pas claire dans ce contexte
                        cours.setIdEnseignant(resultSet.getInt("idEnseignant"));
                        cours.setDate(resultSet.getDate("date"));
                        cours.setHeure(resultSet.getInt("heure"));

                        Matiere matiere = new Matiere(); // Vous devrez adapter selon la structure de votre classe Matiere
                        cours.setMatiere(matiere);

                        Salle salle = new Salle(); // Vous devrez adapter selon la structure de votre classe Salle
                        cours.setSalle(salle);

                        coursList.add(cours);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coursList;
    }
    */
    
    public static int getMaxEtudiantId() {
        int maxId = -1;

        try (Connection connection = connect()) {
            // Requête SQL pour obtenir le maximum de l'ID dans la table des étudiants
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
            // Requête SQL pour obtenir le maximum de l'ID dans la table des étudiants
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
            // Requête SQL pour obtenir le maximum de l'ID dans la table des étudiants
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
