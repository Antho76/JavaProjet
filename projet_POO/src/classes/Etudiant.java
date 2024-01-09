package classes; 
//import java.util.Date;

class Etudiant extends Personne
  {
    private int numeroEtudiant;
    private Formation formation;

    public Etudiant(String nom, String prenom, int age, String dateNaissance,int numeroEtudiant, Formation formation, String email, String mdp)
    {
      super(nom, prenom, age, dateNaissance,email, mdp);
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

    public String toString()
    {
      return super.toString() + " etudiant de la formation " + this.formation.toString();
    }
    
  }