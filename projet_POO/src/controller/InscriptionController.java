package controller;
import classes.Etudiant;
import classes.Formation;
import classes.Matiere;
import classes.Enseignant;
import classes.Personnel;


public class InscriptionController {
	
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
	public void inscriptionPersonnel(String nom, String prenom, int age, String dateNaissance, int numeroPersonnel,String metier, String login, String password) {
		/*List<Personnel> listePersonnel = 
		 * for personnel in listePersonnel :
		 * 		if personnel.getLogin().equals(login) && personnel.getPassword().equals(password)
		 * 			already_exist = true;
		 * if !already_exist :
		 *		listePersonnel.add(new Personnel(nom,prenom,age,dateNaissance,numeroPersonnel,metier,login,password));
		 * */
	}
}