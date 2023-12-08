package classes;
//import java.util.Date;

public class Promotion
{
  private int id;
  private int annee;
  private Etudiant[] etudiants;  

  public Promotion(int id, int annee){
    this.id = id;
    this.annee = annee;
    this.etudiants = new Etudiant[100];
  }
  
  public Promotion(){
    this.id = 0;
    this.annee = 0;
    this.etudiants = new Etudiant[100];
  }

  public int getId(){
    return this.id;
  }

  public int getAnnee(){
    return this.annee;
  }

  public Etudiant[] getEtudiants(){
    return this.etudiants;
  }

  public void setId(int id){
    this.id = id;
  }

  public void setAnnee(int annee){
    this.annee = annee;
  }

  public void setEtudiants(Etudiant[] etudiants){
    this.etudiants = etudiants;
  }

  public void addEtudiant(Etudiant etudiant)
  {
    for(int i = 0; i < this.etudiants.length; i++)
    {
      if(this.etudiants[i] == null)
      {
        this.etudiants[i] = etudiant;
        break;
      }
    }
  }

  public void afficherEtudiants()
  {
    for(int i = 0; i < this.etudiants.length; i++)
    {
      if(this.etudiants[i] != null)
      {
        System.out.println(this.etudiants[i]);
      }
    }
  }

  
  public String toString() {
      return "Promotion " + this.annee;
  }

}