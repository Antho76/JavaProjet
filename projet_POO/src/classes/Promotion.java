package classes;

public class Promotion
{
  private int numeroPromotion;
  private int annee;
  private int idFormation;

  public Promotion(int numeroPromotion, int annee, int idFormation){
    this.numeroPromotion = numeroPromotion;
    this.annee = annee;
    this.idFormation = idFormation;
  }
  
  public Promotion(){
    this.numeroPromotion = 0;
    this.annee = 0;
    this.idFormation = 0;

  }

  public int getId(){
    return this.numeroPromotion;
  }

  
  public int getAnnee(){
    return this.annee;
  }

  public int getidFormation(){
	    return this.idFormation;
	  }
  
  public int setidFormation(int id){
	    return this.idFormation = id;
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