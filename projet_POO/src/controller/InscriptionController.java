package controller;
import classes.Etudiant;
import classes.Formation;
import classes.Matiere;
import classes.Enseignant;
import classes.Personnel;
import database.DatabaseManager;
import java.util.ArrayList;
import java.util.List;


public class InscriptionController {
	DatabaseManager dbManager = new DatabaseManager();
	List<Personnel> listePersonnel = new ArrayList<Personnel>();
	private boolean already_exist = false;
	
	public void inscriptionEtudiant(String nom, String prenom, int age, String dateNaissance,int numeroEtudiant, Formation formation, String login, String password) {
		/*List<Etudiant> listeEtudiant = 
		 * for etudiant in listeEtudiant :
		 * 		if etudiant.getLogin().equals(login) && etudiant.getPassword().equals(password)
		 * 			already_exist = true;
		 * if !already_exist :
		 * 		listeEtudiant.add(new Etudiant(nom,prenom,age,dateNaissance,numeroEtudiant,formation,login,password)); */
	}
	public void inscriptionEnseignant(String nom, String prenom, int age, String dateNaissance,int numeroEnseignant, Matiere matiere, String login, String password) {
		/*List<Enseignant> listeEnseignant = 
		 * for enseignant in listeEnseignant :
		 * 		if enseignant.getLogin().equals(login) && enseignant.getPassword().equals(password)
		 * 			already_exist = true;
		 * if !already_exist :
		 * 		listeEnseignant.add(new Enseignant(nom,prenom,age,dateNaissance,numeroEnseignant,matiere,login,password));
		 * */
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