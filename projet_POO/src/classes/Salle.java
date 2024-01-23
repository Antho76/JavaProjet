package classes;
//import java.util.Date;

public class Salle
{
	private int numeroSalle;
	private int nbPlaces;
	private boolean equipInfo;
	private int idBatiment;

	public Salle(){
		this.numeroSalle = 0;
		this.nbPlaces = 0;
		this.equipInfo = false;
	}

	public Salle(int numeroSalle, int nbPlaces,boolean equipInfo, int idbatiment){
		this.numeroSalle = numeroSalle;
		this.nbPlaces = nbPlaces;
		this.equipInfo = equipInfo;
		this.idBatiment = idbatiment;
	}
	
	public int getNumeroSalle() {
        return this.numeroSalle;
    }

	public int getNbPlaces(){
		return this.nbPlaces;
	}


  
	public boolean getEquipInfo() {
		return this.equipInfo;
	}	

	public void setNbPlaces(int nbPlaces){
		this.nbPlaces = nbPlaces;
	}
	public int getIdBatiment() {
		return this.idBatiment;
	}	

	public void setIdBatiment(int idBatiment){
		this.idBatiment = idBatiment;
	}


  
	public void setEquipInfo(boolean equipInfo) {
		this.equipInfo = equipInfo;
	}
}


