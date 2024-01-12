package controller;
import classes.Etudiant;
import classes.Enseignant;
import classes.Personnel;


public class ConnexionController {
	private boolean islogged = false;
	
	public boolean checkConnexionEtudiant(String login, String password) {
		/*List<Etudiant> listeEtudiant = 
		 * for etudiant in listeEtudiant :
		 * 		if etudiant.getLogin().equals(login) && etudiant.getPassword().equals(password)
		 * 			islogged = true;
		 * 			return islogged;*/
		 return islogged;
	}
	public boolean checkConnexionEnseignant(String login, String password) {
		/*List<Enseignant> listeEnseignant = 
		 * for enseignant in listeEnseignant :
		 * 		if enseignant.getLogin().equals(login) && enseignant.getPassword().equals(password)
		 * 			islogged = true;
		 * 			return islogged;*/
		return islogged;
	}
	public boolean checkConnexionPersonnel(String login, String password) {
		/*List<Personnel> listePersonnel = 
		 * for personne in listePersonnel :
		 * 		if personne.getLogin().equals(login) && personne.getPassword().equals(password)
		 * 			islogged = true;
		 * 			return islogged;*/
		return islogged;
	}
}