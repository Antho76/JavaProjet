package classes;
//import java.util.Date;

abstract class Personne
  {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String dateNaissance;
    private String genre;

    public Personne(String nom, String prenom, int age, String dateNaissance)
    {
      this.nom = nom;
      this.prenom = prenom;
      this.age = age;
      this.dateNaissance = dateNaissance;
    }
    public Personne(){
      this.nom = "";
      this.prenom = "";
      this.age = 0;
      this.dateNaissance = "";
    }
    
    public String getNom()
    {
      return this.nom;
    }

    public String getPrenom()
    {
      return this.prenom;
    }

    public int getAge()
    {
      return this.age;
    }

    public String getDateNaissance()
    {
      return this.dateNaissance;
    }
    public String getGenre()
    {
      return this.genre;
    }
    public void setNom(String nom)
    {
      this.nom = nom;
    }
    
    public void setPrenom(String prenom)
    {
      this.prenom = prenom;
    }
    
    public void setAge(int age)
    {
      this.age = age;
    }

    public void setDateNaissance(String dateNaissance)
    {
      this.dateNaissance = dateNaissance;
    }

    public void setGenre(String genre)
    {
      this.genre = genre;
    }

    public String toString()
    {
      return "Nom: " + this.nom + " Prénom: " + this.prenom;
    }
  }



