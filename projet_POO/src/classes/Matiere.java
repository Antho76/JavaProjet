package classes;

public class Matiere 
{
	private int numeroMatiere;
	private String nomMatiere;
	private int coefficient;


	public Matiere(){
		this.numeroMatiere = 0;
		this.nomMatiere = "" ;
		this.coefficient = 0;
	}
  
	public Matiere(int numeroMatiere, String nomMatiere, int coefficient) {
		this.numeroMatiere = numeroMatiere;
		this.nomMatiere = nomMatiere;
		this.coefficient = coefficient;
	}
	
	public int getNumeroMatiere() {
		return this.numeroMatiere;
	}

	public String getNomMatiere(){
		return this.nomMatiere;
	}

	public int getCoefficient(){
		return this.coefficient;
	}
	
	public void setNumeroMatiere(int numeroMatiere) {
		this.numeroMatiere = numeroMatiere;
	}

	public void setNomMatiere(String nomMatiere){
		this.nomMatiere = nomMatiere;
	}

	public void setCoefficient(int coefficient){
		this.coefficient = coefficient;
	}

  
	public String toString() {
		return this.nomMatiere;
	}
}

