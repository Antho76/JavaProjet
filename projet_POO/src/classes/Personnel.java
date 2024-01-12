package classes;
//import java.util.Date;

public class Personnel extends Personne
{
  private int numeroPersonnel;
  private String metier;

  public Personnel(String nom, String prenom, int age, String dateNaissance, int numeroPersonnel,String metier)
  {
    super(nom, prenom, age, dateNaissance);
    this.numeroPersonnel = numeroPersonnel;
    this.metier = metier;
  }
  public int getNumeroPersonnel()
  {
    return this.numeroPersonnel;
  }

  public String getMetier()
  {
    return this.metier;
  }
  public void setNumeroPersonnel(int numeroPersonnel)
  {
    this.numeroPersonnel = numeroPersonnel;
  }

  public void setMetier(String metier)
  {
    this.metier = metier;
  }

  public String toString()
  {
    return super.toString() + " personnel de metier " + this.metier;
  }
}