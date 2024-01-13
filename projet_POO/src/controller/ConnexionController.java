// ConnexionController.java
package controller;

import javax.swing.JOptionPane;
import classes.Enseignant;
import classes.Etudiant;
import classes.Personnel;
import database.DatabaseManager;
import java.util.List;

public class ConnexionController {
    public boolean checkConnexionEtudiant(String login, String password) {
        List<Etudiant> listeEtudiant = DatabaseManager.getStudents();
        boolean isLogged = false;

        for (Etudiant etudiant : listeEtudiant) {
            if (etudiant.getLogin().equals(login) && etudiant.getPassword().equals(password)) {
                isLogged = true;
                return isLogged;
            }
        }

        return isLogged;
    }

    public boolean checkConnexionEnseignant(String login, String password) {
        List<Enseignant> listeEnseignant = DatabaseManager.getEnseignants();
        boolean isLogged = false;

        for (Enseignant enseignant : listeEnseignant) {
            if (enseignant.getLogin().equals(login) && enseignant.getPassword().equals(password)) {
                isLogged = true;
                return isLogged;
            }
        }

        return isLogged;
    }

    public boolean checkConnexionPersonnel(String login, String password) {
    	
        List<Personnel> listePersonnel = DatabaseManager.getPersonnel();
        boolean isLogged = false;
        	
        for (Personnel personne : listePersonnel) {
            if (personne.getLogin().equals(login) && personne.getPassword().equals(password)) {
                isLogged = true;
                return isLogged;
            }
        }

        return isLogged;
    }

}