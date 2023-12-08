package classes;
//import java.util.Date;

public class Salle
{
  private int nbPlaces;
  private int nbEtudiants;

  public Salle(){
    this.nbPlaces = 0;
    this.nbEtudiants = 0;
  }

  public Salle(int nbPlaces, int nbEtudiants){
    this.nbPlaces = nbPlaces;
    this.nbEtudiants = nbEtudiants;
  }

  public int getNbPlaces(){
    return this.nbPlaces;
  }

  public int getNbEtudiants(){
    return this.nbEtudiants;
  }

  public void setNbPlaces(int nbPlaces){
    this.nbPlaces = nbPlaces;
  }

  public void setNbEtudiants(int nbEtudiants){
    this.nbEtudiants = nbEtudiants;
  }
}


