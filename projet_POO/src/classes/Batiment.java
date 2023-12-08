package classes;
import java.util.Date;

public class Batiment 
{
  private int id;
  private String ville;
  private String nom;
  private int nbSalles;
  private Salle[] tabSalles;

  public Batiment() {
    this.id = 0;
    this.ville = "";
    this.nom = "";
    this.nbSalles = 0;
    this.tabSalles = new Salle[0];
  }

  public Batiment(int id, String ville, String nom, int nbSalles, Salle[] tabSalles) {
    this.id = id;
    this.ville = ville;
    this.nom = nom;
    this.nbSalles = nbSalles;
    this.tabSalles = tabSalles;
  }

  public int getId() {
    return this.id;
  }

  public String getVille() {
    return this.ville;
  }

  public String getNom() {
    return this.nom;
  }

  public int getNbSalles() {
    return this.nbSalles;
  }

  public Salle[] getTabSalles() {
    return this.tabSalles;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setNbSalles(int nbSalles) {
    this.nbSalles = nbSalles;
  }

  public void setTabSalles(Salle[] tabSalles) {
    this.tabSalles = tabSalles;
  }
}



