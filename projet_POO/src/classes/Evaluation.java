package classes;
//import java.util.Date;

public class Evaluation 
{
  private String nom;
  private Matiere matiere;
  private Enseignant enseignant;
  private int note;
  private Etudiant etudiant;

  public void Evaulation(String nom,Matiere matiere,Enseignant enseignant,int note,Etudiant etudiant){
    this.nom = nom;
    this.matiere = matiere;
    this.enseignant = enseignant;
    this.note = note;
    this.etudiant = etudiant;
  }

  public String getNom() {
      return this.nom;
  }

  public Matiere getMatiere() {
      return this.matiere;
  }

  public Enseignant getEnseignant() {
      return this.enseignant;
  }

  public int getNote() {
      return this.note;
  }

  public Etudiant getEtudiant() {
      return this.etudiant;
  }

  public void setNom(String nom) {
      this.nom = nom;
  }

  public void setMatiere(Matiere matiere) {
      this.matiere = matiere;
  }

  public void setEnseignant(Enseignant enseignant) {
      this.enseignant = enseignant;
  }

  public void setNote(int note) {
      this.note = note;
  }

  public void setEtudiant(Etudiant etudiant) {
      this.etudiant = etudiant;
  }
}

