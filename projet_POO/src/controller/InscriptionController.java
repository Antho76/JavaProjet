package controller;
import classes.Etudiant;
import classes.*;
import database.DatabaseManager;
import java.util.ArrayList;
import java.util.List;


public class InscriptionController {
	DatabaseManager dbManager = new DatabaseManager();
	List<Personnel> listePersonnel = new ArrayList<Personnel>();
	List<Enseignant> listeEnseignant = new ArrayList<Enseignant>();
	List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	private boolean already_exist = false;
	
	public void inscriptionEtudiant(String nom, String prenom, int promotion, String dateNaissance,int numeroEtudiant, int formation, String login, String password) {
		for (Etudiant etudiant : listeEtudiant) {
			if (etudiant.getLogin().equals(login) && etudiant.getPassword().equals(password)) {
				already_exist = true;
			}
		}
		if (!already_exist) {
			Etudiant newEtudiant = new Etudiant(nom,prenom,promotion,dateNaissance,formation,login,password);
			listeEtudiant.add(newEtudiant);
			DatabaseManager.insertEtudiant(newEtudiant);
		}	
	}
	public void inscriptionEnseignant(String nom, String prenom, String dateNaissance, int matiere, String login, String password) {
		for (Enseignant enseignant : listeEnseignant) {
			if (enseignant.getLogin().equals(login) && enseignant.getPassword().equals(password)) {
				already_exist = true;
			}
		}
		if (!already_exist) {
			Enseignant newEnseignant = new Enseignant(nom,prenom,dateNaissance,matiere,login,password);
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
			Personnel newPersonnel = new Personnel(nom,prenom,dateNaissance,metier,login,password);
			listePersonnel.add(newPersonnel);
			DatabaseManager.insertPersonnel(newPersonnel);
		}	 
	}
}