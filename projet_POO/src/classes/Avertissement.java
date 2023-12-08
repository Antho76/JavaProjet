package classes;
//import java.util.Date;

public class Avertissement 
{
  private String nom;
  private Etudiant etudiant;
  private Enseignant enseignant;

  public Avertissement(){
    this.nom = "";
    this.etudiant = null;
    this.enseignant = null;
  }

  public Avertissement(String nom, Etudiant etudiant, Enseignant enseignant){
    this.nom = nom;
    this.etudiant = etudiant;
    this.enseignant = enseignant;
  }

  public String getNom() {
    return this.nom;
  }

  public Etudiant getEtudiant() {
    return this.etudiant;
  }

  public Enseignant getEnseignant() {
    return this.enseignant;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setEtudiant(Etudiant etudiant) {
    this.etudiant = etudiant;
  }

  public void setEnseignant(Enseignant enseignant) {
    this.enseignant = enseignant;
  }
}

