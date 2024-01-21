package classes;
//import java.util.Date;

public class Salle
{
	private static int dernierNumeroSalle = 0;
	private int numeroSalle;
	private int nbPlaces;
	private int nbEtudiants;
	private boolean equipInfo;

	public Salle(){
		dernierNumeroSalle++;
		this.numeroSalle = dernierNumeroSalle;
		this.nbPlaces = 0;
		this.nbEtudiants = 0;
		this.equipInfo = false;
	}

	public Salle(int numeroSalle, int nbPlaces, int nbEtudiants,boolean equipInfo){
		this.numeroSalle = numeroSalle;
		this.nbPlaces = nbPlaces;
		this.nbEtudiants = nbEtudiants;
		this.equipInfo = equipInfo;
	}
	
	public int getNumeroSalle() {
        return this.numeroSalle;
    }

	public int getNbPlaces(){
		return this.nbPlaces;
	}

	public int getNbEtudiants(){
		return this.nbEtudiants;
	}
  
	public boolean getEquipInfo() {
		return this.equipInfo;
	}	

	public void setNbPlaces(int nbPlaces){
		this.nbPlaces = nbPlaces;
	}

	public void setNbEtudiants(int nbEtudiants){
		this.nbEtudiants = nbEtudiants;
	}
  
	public void setEquipInfo(boolean equipInfo) {
		this.equipInfo = equipInfo;
	}
}


