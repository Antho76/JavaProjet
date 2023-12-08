package classes;
//import java.util.Date;

public class Formation 
{
  private static int id_formation = 0;
  private String nom;
  private Promotion promotion;
  
  public Formation(){
    this.id_formation = id_formation++;
    this.nom="";
    this.promotion= new Promotion();
  }
  
  public Formation(String nom, Promotion promotion){
    this.id_formation = id_formation++;
    this.nom = nom;
    this.promotion = promotion;
  }

  public int getid(){
    return this.id_formation;
  }
    public String getnom(){
      return this.nom;
  }
  
    public Promotion getpromotion(){
      return this.promotion;
  }

    public void setnom(String nom){
      this.nom = nom;
  }
    public void setpromotion(Promotion promotion){
      this.promotion = promotion;
  }

  //toString
  public String toString() 
  {
    return this.nom;
  }
}
  