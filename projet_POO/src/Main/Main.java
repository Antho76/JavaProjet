package Main;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import database.AfficherTablesSQLite;
import database.DatabaseManager;
import interfaces.*;

public class Main {
	

    public static void main(String[] args) {
    	
        DatabaseManager.createDatabaseIfNotExists();

    	/*ConnectionInterface connectionInterface = new ConnectionInterface();
        connectionInterface.afficherInterface();*/
    	
    	AccueilEtudiant accueilEtu = new AccueilEtudiant();
    	accueilEtu.afficherInterface();
    	
    	/*Inscription inscription = new Inscription();
    	inscription.afficherInterface();*/
    }
    
}