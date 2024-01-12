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
    	
    	/*ConnectionInterface connectionInterface = new ConnectionInterface();
        connectionInterface.afficherInterface();*/
    	
    	AccueilEtudiantInterface accueilEtu = new AccueilEtudiantInterface();
    	accueilEtu.afficherInterface();
    	
    	/*InscriptionInterface inscription = new InscriptionInterface();
    	inscription.afficherInterface();*/
    }
    
}