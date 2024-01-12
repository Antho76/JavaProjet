package classes;
//import java.util.Date;

public class Promotion
{
  private int numeroPromotion;
  private int annee;

  public Promotion(int numeroPromotion, int annee){
    this.numeroPromotion = numeroPromotion;
    this.annee = annee;
  }
  
  public Promotion(){
    this.numeroPromotion = 0;
    this.annee = 0;
  }

  public int getId(){
    return this.numeroPromotion;
  }

  public int getAnnee(){
    return this.annee;
  }



  public void setId(int numeroPromotion){
    this.numeroPromotion = numeroPromotion;
  }

  public void setAnnee(int annee){
    this.annee = annee;
  }
  
  public String toString() {
      return "Promotion " + this.annee;
  }

}