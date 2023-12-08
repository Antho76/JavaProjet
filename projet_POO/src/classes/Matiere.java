package classes;
//import java.util.Date;

public class Matiere 
{
  private String nom;
  private int coefficient;


  public Matiere(){
    this.nom = "" ;
    this.coefficient = 0;
  }
  
  public Matiere(String nom, int coefficient) {
    this.nom = nom;
    this.coefficient = coefficient;
  }

  public String getNom(){
    return this.nom;
  }

  public int getCoefficient(){
    return this.coefficient;
  }

  public void setNom(String nom){
    this.nom = nom;
  }

  public void setCoefficient(int coefficient){
    this.coefficient = coefficient;
  }

  
  public String toString() {
      return this.nom;
  }
}

