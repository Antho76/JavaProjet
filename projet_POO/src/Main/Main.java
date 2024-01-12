package Main;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
public class Main {
	

	
    public static void main(String[] args) {
    	
    	
        // Création de la base de données si elle n'existe pas
        DatabaseManager.createDatabaseIfNotExists();
        AfficherTablesSQLite afficherTables = new AfficherTablesSQLite();
        afficherTables.afficherTablesSQLite();

}
}