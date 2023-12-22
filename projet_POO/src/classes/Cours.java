package classes;
import java.util.Date;

public class Cours{ 
  private int nbEtudiant;
  private Etudiant[] tabEtudiants;
  private Enseignant enseignant;
  private Date date;
  private int heure;
  private Matiere matiere;
  private Salle salle;

  public Cours(){
    this.nbEtudiant = 0;
    this.tabEtudiants = new Etudiant[nbEtudiant];
    this.enseignant = new Enseignant();
    this.date = new Date();
    this.heure = 0;
    this.matiere = new Matiere();
    this.salle = new Salle();
  }
  
  public Cours(int nbEtudiant, Etudiant[] tabEtudiants, Enseignant       
  enseignant, Date date, int heure,Matiere matiere, Salle salle){
    this.nbEtudiant = nbEtudiant;
    this.tabEtudiants = tabEtudiants;
    this.enseignant = enseignant;
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

  public Enseignant getEnseignant(){
    return this.enseignant;
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
  
