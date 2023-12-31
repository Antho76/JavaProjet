package classes;
//import java.util.Date;

public class Batiment 
{
  private int numeroBatiment;
  private String ville;
  private String nomBatiment;
  private int nbSalles;
  private Salle[] tabSalles;

  public Batiment() {
    this.numeroBatiment = 0;
    this.ville = "";
    this.nomBatiment = "";
    this.nbSalles = 0;
    this.tabSalles = new Salle[0];
  }

  public Batiment(int numeroBatiment, String ville, String nomBatiment, int nbSalles, Salle[] tabSalles) {
    this.numeroBatiment = numeroBatiment;
    this.ville = ville;
    this.nomBatiment = nomBatiment;
    this.nbSalles = nbSalles;
    this.tabSalles = tabSalles;
  }

  public int getNumeroBatiment() {
    return this.numeroBatiment;
  }

  public String getVille() {
    return this.ville;
  }

  public String getNomBatiment() {
    return this.nomBatiment;
  }

  public int getNbSalles() {
    return this.nbSalles;
  }

  public Salle[] getTabSalles() {
    return this.tabSalles;
  }

  public void setNumeroBatiment(int numeroBatiment) {
    this.numeroBatiment = numeroBatiment;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setNomBatiment(String nomBatiment) {
    this.nomBatiment = nomBatiment;
  }

  public void setNbSalles(int nbSalles) {
    this.nbSalles = nbSalles;
  }

  public void setTabSalles(Salle[] tabSalles) {
    this.tabSalles = tabSalles;
  }
}



