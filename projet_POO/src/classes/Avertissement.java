package classes;
//import java.util.Date;

public class Avertissement 
{
  private String nom;
  private int idEtudiant;
  private int idEnseignant;

  public Avertissement(){
    this.nom = "";
    this.idEtudiant = 0;
    this.idEnseignant = 0;
  }

  public Avertissement(String nom, int etudiant, int enseignant){
    this.nom = nom;
    this.idEtudiant = etudiant;
    this.idEnseignant = enseignant;
  }

  public String getNom() {
    return this.nom;
  }

  public int getEtudiant() {
    return this.idEtudiant;
  }

  public int getEnseignant() {
    return this.idEnseignant;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setEtudiant(int etudiant) {
    this.idEtudiant = etudiant;
  }

  public void setEnseignant(int enseignant) {
    this.idEnseignant = enseignant;
  }
}

