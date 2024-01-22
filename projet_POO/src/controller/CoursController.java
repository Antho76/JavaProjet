package controller;
import classes.*;
import database.DatabaseManager;
import java.util.ArrayList;
import java.util.List;
import java.time.*;


public class CoursController {
	DatabaseManager dbManager = new DatabaseManager();
	List<Cours> listCours =  DatabaseManager.getCours();;
	
	private boolean already_exist = false;
	
	public void ajoutCours(int idCours, int nbEtudiant, String tabEtudiant, int idEnseignant, LocalDate date, int heure, int idMatiere, int idSalle) {
		for (Cours cours : listCours) {
			if (cours.getId() == idCours) {
				already_exist = true;
			}
		}
		if (!already_exist) {
			
			Cours newCours = new Cours(idCours,nbEtudiant,tabEtudiant,idEnseignant,date,heure,idMatiere,idSalle);
			listCours.add(newCours);
			DatabaseManager.insertCours(newCours);
		}	
		else {
			System.out.println("BAHAHAHA TU PUES");
		}
	}
	
	
}