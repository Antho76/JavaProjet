package classes; 
//import java.util.Date;

public class Etudiant extends Personne
  {
    private Formation formation;
    private int promotion;

    public Etudiant(String nom, String prenom, int promotion, String dateNaissance, Formation formation, String login, String password) {
      super(nom, prenom, dateNaissance,login, password);
      this.formation = formation;
      this.promotion = promotion;
    }

    public Formation getFormation()
    {
      return this.formation;
    }

    public void setFormation(Formation formation)
    {
      this.formation = formation;
    }
    public void setPromotion(int promotion)
    {
      this.promotion = promotion;
    }

    public void getPromotion(int promotion)
    {
      this.promotion = promotion;
    }

    public String toString()
    {
      return super.toString() + " etudiant de la formation " + this.formation.toString();
    }
    
  }