package classes;

public class Formation 
{
	private int id_formation;
	private String nomFormation;
  
	public Formation(){;
		this.id_formation = 0;
		this.nomFormation="";
	}
  
	public Formation(int id_formation, String nomFormation){
		this.id_formation = id_formation;
		this.nomFormation = nomFormation;
	}

	public int getId_Formation(){
		return this.id_formation;
	}
	
    public String getNomFormation(){
    	return this.nomFormation;
    }

    
    public void setId_Formation(int id_formation) {
    	this.id_formation = id_formation;
    }

    public void setNomFormation(String nomFormation){
    	this.nomFormation = nomFormation;
    }


	public String toString() {
		return this.nomFormation;
	 }
}
  