package classes;
//import java.util.Date;

public class Enseignant extends Personne{
  private int numeroEnseignant;
  private Matiere matiere;

  public Enseignant(String nom, String prenom, int age, String dateNaissance,int numeroEnseignant, Matiere matiere){
    super(nom, prenom, age, dateNaissance);
    this.numeroEnseignant = numeroEnseignant;
    this.matiere = matiere;
  }

  public Enseignant(){
    super();
    this.numeroEnseignant = 0;
    this.matiere = new Matiere();
  }
    
  public int getNumeroEnseignant(){
    return this.numeroEnseignant;
  }

  public Matiere getMatiere(){
    return this.matiere;
  }

  public void setNumeroEnseignant(int numeroEnseignant){
    this.numeroEnseignant = numeroEnseignant;
  }

  public void setMatiere(Matiere matiere){
    this.matiere = matiere;
  }

  public String toString() 
  {
      return super.toString() + "enseignant de la matière "        + this.matiere.toString();
  }
  
}

