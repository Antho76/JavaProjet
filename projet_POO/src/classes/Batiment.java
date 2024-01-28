package classes;

public class Batiment 
{
  private int numeroBatiment;
  private String ville;
  private String nomBatiment;


  public Batiment() {
    this.numeroBatiment = 0;
    this.ville = "";
    this.nomBatiment = "";

  }

  public Batiment(int numeroBatiment, String ville, String nomBatiment) {
    this.numeroBatiment = numeroBatiment;
    this.ville = ville;
    this.nomBatiment = nomBatiment;

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




  public void setNumeroBatiment(int numeroBatiment) {
    this.numeroBatiment = numeroBatiment;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setNomBatiment(String nomBatiment) {
    this.nomBatiment = nomBatiment;
  }


}



