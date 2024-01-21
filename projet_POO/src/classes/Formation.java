package classes;
//import java.util.Date;

public class Formation 
{
	private int id_formation;
	private String nomFormation;
	private int idPromotion;
  
	public Formation(){;
		this.id_formation = 0;
		this.nomFormation="";
		this.idPromotion= 0;
	}
  
	public Formation(int id_formation, String nomFormation, int idPromotion){
		this.id_formation = id_formation;
		this.nomFormation = nomFormation;
		this.idPromotion = idPromotion;
	}

	public int getId_Formation(){
		return this.id_formation;
	}
	
    public String getNomFormation(){
    	return this.nomFormation;
    }
  
    public int getIdPromotion(){
    	return this.idPromotion;
    }
    
    public void setId_Formation(int id_formation) {
    	this.id_formation = id_formation;
    }

    public void setNomFormation(String nomFormation){
    	this.nomFormation = nomFormation;
    }
    public void setPromotion(int idPromotion){
    	this.idPromotion = idPromotion;
    }

  //toString
	public String toString() {
		return this.nomFormation;
	 }
}
  