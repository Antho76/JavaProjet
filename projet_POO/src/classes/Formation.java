package classes;
//import java.util.Date;

public class Formation 
{
	private static int dernierIdFormation;
	private int id_formation;
	private String nomFormation;
	private Promotion promotion;
  
	public Formation(){
		dernierIdFormation++;
		this.id_formation = dernierIdFormation;
		this.nomFormation="";
		this.promotion= new Promotion();
	}
  
	public Formation(String nomFormation, Promotion promotion){
		dernierIdFormation++;
		this.id_formation = dernierIdFormation;
		this.nomFormation = nomFormation;
		this.promotion = promotion;
	}

	public int getId_Formation(){
		return this.id_formation;
	}
	
    public String getNomFormation(){
    	return this.nomFormation;
    }
  
    public Promotion getPromotion(){
    	return this.promotion;
    }
    
    public void setId_Formation(int id_formation) {
    	this.id_formation = id_formation;
    }

    public void setNomFormation(String nomFormation){
    	this.nomFormation = nomFormation;
    }
    public void setPromotion(Promotion promotion){
    	this.promotion = promotion;
    }

  //toString
	public String toString() {
		return this.nomFormation;
	 }
}
  