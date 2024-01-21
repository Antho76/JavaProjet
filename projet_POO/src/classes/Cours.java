package classes;
import java.util.Date;

public class Cours{ 
  private int nbEtudiant;
  private Etudiant[] tabEtudiants;
  private int idEnseignant;
  private Date date;
  private int heure;
  private Matiere matiere;
  private Salle salle;

  public Cours(){
    this.nbEtudiant = 0;
    this.tabEtudiants = new Etudiant[nbEtudiant];
    this.idEnseignant = 0;
    this.date = new Date();
    this.heure = 0;
    this.matiere = new Matiere();
    this.salle = new Salle();
  }
  
  public Cours(int nbEtudiant, Etudiant[] tabEtudiants, int       
  enseignant, Date date, int heure,Matiere matiere, Salle salle){
    this.nbEtudiant = nbEtudiant;
    this.tabEtudiants = tabEtudiants;
    this.idEnseignant = enseignant;
    this.date = date;
    this.heure = heure;
    this.matiere = matiere;
    this.salle = salle;
  
  }
  public int getNbEtudiant(){
    return this.nbEtudiant;
  }

  public Etudiant[] getTabEtudiants(){
    return this.tabEtudiants;
  }

  public int getEnseignant(){
    return this.idEnseignant;
  }

  public Date getDate(){
    return this.date;
  }

  
  public int getHeure() {
      return this.heure;
  }
  
  public Matiere getMatiere() {
      return this.matiere;
  }
  
  public Salle getSalle() {
      return this.salle;
  }
  
  public void setDate(Date date) {
	  this.date=date;
  }

  public void setIdEnseignant(int idEnseignant) {
	  this.idEnseignant=idEnseignant;
  }
  /*public void setNbPlaces*/
  public void setHeure(int heure) {
      this.heure = heure;
  }
  
  public void setMatiere(Matiere matiere) {
      this.matiere = matiere;
  }
  
  public void setSalle(Salle salle) {
      this.salle = salle;
  }
}
  
