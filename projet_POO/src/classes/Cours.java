package classes;
import java.time.*;

public class Cours{
	private int idCours;
	private int nbEtudiant;
	private String tabEtudiants;
	private int idEnseignant;
	private LocalDate date;
	private int heure;
	private int idMatiere;
	private int idSalle;

	public Cours(){
	    this.nbEtudiant = 0;
	    this.tabEtudiants = "";
	    this.idEnseignant = 0;
	    this.date = LocalDate.of(2024, 1, 23);;
	    this.heure = 0;
	    this.idMatiere = 0;
	    this.idSalle = 0;
	}
  
	public Cours(int idCours,int nbEtudiant, String tabEtudiants, int enseignant, LocalDate date, int heure,int idMatiere, int idSalle){  
		this.idCours = idCours;
	    this.nbEtudiant = nbEtudiant;
	    this.tabEtudiants = tabEtudiants;
	    this.idEnseignant = enseignant;
	    this.date = date;
	    this.heure = heure;
	    this.idMatiere = idMatiere;
	    this.idSalle = idSalle;
	}
	public int getId() {
		return this.idCours;
	}
	public int getNbEtudiant(){
		return this.nbEtudiant;
	}

	public String getTabEtudiants(){
		return this.tabEtudiants;
	}

	public int getEnseignant(){
		return this.idEnseignant;
	}

	public LocalDate getDate(){
		return this.date;
	}

  
	public int getHeure() {
		return this.heure;
	}
  
	public int getMatiere() {
		return this.idMatiere;
	}
	
  
	public int getSalle() {
		return this.idSalle;
	}
	
	public void setId(int idCours) {
		this.idCours = idCours;
	}
	
	public void setNbEtudiant(int nbEtudiant){
		this.nbEtudiant = nbEtudiant;
	}
  
	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}
  
	public void setDate(LocalDate date) {
		this.date = date;
	}
  
 	public void setHeure(int heure) {
 		this.heure = heure;
 	}
  
 	public void setMatiere(int idMatiere) {
 		this.idMatiere = idMatiere;
 	}
  
 	public void setSalle(int idSalle) {
 		this.idSalle = idSalle;
 	}
}
  
