package database;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

                System.out.println("Tables créées avec succès!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                preparedStatement.setInt(4, etudiant.getPromotion().getId());
                preparedStatement.setString(5, etudiant.getDateNaissance());
                preparedStatement.setInt(6, etudiant.getFormation().getId_Formation());
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
                preparedStatement.setInt(5, enseignant.getMatiere().getNumeroMatiere());
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
