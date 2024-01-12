package classes; 
//import java.util.Date;

public class Etudiant extends Personne
  {
    private int numeroEtudiant;
    private Formation formation;
    private int promotion;

    public Etudiant(String nom, String prenom, int age,int promotion, String dateNaissance,int numeroEtudiant, Formation formation, String login, String password)
    {
      super(nom, prenom, age, dateNaissance,login, password);
      this.numeroEtudiant = numeroEtudiant;
      this.formation = formation;
    }
    public int getNumeroEtudiant()
    {
      return this.numeroEtudiant;
    }

    public Formation getFormation()
    {
      return this.formation;
    }
    public void setNumeroEtudiant(int numeroEtudiant)
    {
      this.numeroEtudiant = numeroEtudiant;
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