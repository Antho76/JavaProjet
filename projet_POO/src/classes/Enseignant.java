package classes;
//import java.util.Date;

public class Enseignant extends Personne{
  private Matiere matiere;

  public Enseignant(String nom, String prenom, String dateNaissance, Matiere matiere, String login, String password){
    super(nom, prenom, dateNaissance,login,password);
    this.matiere = matiere;
  }

  public Enseignant(){
    super();
    this.matiere = new Matiere();
  }
    
  public Matiere getMatiere(){
    return this.matiere;
  }


  public void setMatiere(Matiere matiere){
    this.matiere = matiere;
  }

  public String toString() 
  {
      return super.toString() + "enseignant de la matière "        + this.matiere.toString();
  }
  
}

