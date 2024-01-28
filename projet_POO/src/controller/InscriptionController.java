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
		// Exemple d'utilisation
		if (DatabaseManager.insertEtudiant(newEtudiant)) {
		    // Ajout réussi
		    JOptionPane.showMessageDialog(null, "Étudiant ajouté avec succès.");
		} else {
		    // Ajout échoué
		    JOptionPane.showMessageDialog(null, "Étudiant déjà existant. L'ajout n'a pas été effectué.");
		}
	}

	
	public void inscriptionEnseignant(String nom, String prenom, String dateNaissance, int matiere, String login, String password) {
		for (Enseignant enseignant : listeEnseignant) {
			if (enseignant.getLogin().equals(login) && enseignant.getPassword().equals(password)) {
				already_exist = true;
			}
		}
		if (!already_exist) {
			int maxEnseignantId = DatabaseManager.getMaxEnseignantId();

			Enseignant newEnseignant = new Enseignant(maxEnseignantId+1, nom,prenom,dateNaissance,matiere,login,password);
			listeEnseignant.add(newEnseignant);
			DatabaseManager.insertEnseignant(newEnseignant);
		}	
	}
	public void inscriptionPersonnel(String nom, String prenom, String dateNaissance, String metier, String login, String password) {
		for (Personnel personnel : listePersonnel) {
			if (personnel.getLogin().equals(login) && personnel.getPassword().equals(password)) {
				already_exist = true;
			}
		}
		if (!already_exist) {
			int maxPersonnelId = DatabaseManager.getMaxPersonnelId();

			Personnel newPersonnel = new Personnel(maxPersonnelId+1, nom,prenom,dateNaissance,metier,login,password);
			listePersonnel.add(newPersonnel);
			DatabaseManager.insertPersonnel(newPersonnel);
		}	 
	}
}