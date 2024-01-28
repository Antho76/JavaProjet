package controller;
import classes.Etudiant;
import classes.*;
import database.DatabaseManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class InscriptionController {
	DatabaseManager dbManager = new DatabaseManager();
	List<Personnel> listePersonnel = new ArrayList<Personnel>();
	List<Enseignant> listeEnseignant = new ArrayList<Enseignant>();
	List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	private boolean already_exist = false;
	
	public void inscriptionEtudiant(String nom, String prenom, int promotion, String dateNaissance, int formation, String login, String password) {
		int maxEtudiantId = DatabaseManager.getMaxEtudiantId();
		Etudiant newEtudiant = new Etudiant(maxEtudiantId +1,nom,prenom,promotion,dateNaissance,formation,login,password);
		if (DatabaseManager.insertEtudiant(newEtudiant)) {
		    JOptionPane.showMessageDialog(null, "Étudiant ajouté avec succès.");
		} else {
		    JOptionPane.showMessageDialog(null, "Étudiant déjà existant. L'ajout n'a pas été effectué.");
		}
	}

	
	public void inscriptionEnseignant(String nom, String prenom, String dateNaissance, int matiere, String login, String password) {
	    int maxEnseignantId = DatabaseManager.getMaxEnseignantId();
	    Enseignant newEnseignant = new Enseignant(maxEnseignantId + 1, nom, prenom, dateNaissance, matiere, login, password);

	    if (DatabaseManager.insertEnseignant(newEnseignant)) {
	        JOptionPane.showMessageDialog(null, "Enseignant ajouté avec succès.");
	    } else {
	        JOptionPane.showMessageDialog(null, "Enseignant déjà existant. L'ajout n'a pas été effectué.");
	    }
	}
	public void inscriptionPersonnel(String nom, String prenom, String dateNaissance, String metier, String login, String password) {
	    int maxPersonnelId = DatabaseManager.getMaxPersonnelId();
	    Personnel newPersonnel = new Personnel(maxPersonnelId + 1, nom, prenom, dateNaissance, metier, login, password);

	    if (DatabaseManager.insertPersonnel(newPersonnel)) {
	        JOptionPane.showMessageDialog(null, "Personnel ajouté avec succès.");
	    } else {
	        JOptionPane.showMessageDialog(null, "Personnel déjà existant. L'ajout n'a pas été effectué.");
	    }
	}
}