package classes; 
//import java.util.Date;

public class Etudiant extends Personne
  {
    private Formation formation;

    public Etudiant(String nom, String prenom, String dateNaissance,Formation formation, String login, String password)
    {
      super(nom, prenom, dateNaissance,login, password);
      this.formation = formation;
    }

    public Formation getFormation()
    {
      return this.formation;
    }

    public void setFormation(Formation formation)
    {
      this.formation = formation;
    }

    public String toString()
    {
      return super.toString() + " etudiant de la formation " + this.formation.toString();
    }
    
  }