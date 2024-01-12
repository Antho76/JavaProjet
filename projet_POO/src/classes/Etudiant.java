package classes; 
//import java.util.Date;

public class Etudiant
  {
	private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;
    private String login;
    private String password;
    private Formation formation;

    private Promotion promotion;
  
    public Etudiant(String nom, String prenom, Promotion promotion, String dateNaissance, Formation formation, String login, String password) {
    	this.nom = nom;
    	this.prenom = prenom;
    	this.dateNaissance = dateNaissance;
    	this.login = login;
    	this.password = password;
    	this.formation = formation;
    	this.promotion = promotion;
    }
    
    public String getNom()
    {
      return this.nom;
    }

    public int getId()
    {
      return this.id;
    }
    
    public String getPrenom()
    {
      return this.prenom;
    }

    public String getDateNaissance()
    {
      return this.dateNaissance;
    }
    public String getGenre()
    {
      return this.genre;
    }
    public String getLogin()
    {
    	return this.login;
    }
    public String getPassword()
    {
    	return this.password;
    }
    
    public void setNom(String nom)
    {
      this.nom = nom;
    }
    
    public void setPrenom(String prenom)
    {
      this.prenom = prenom;
    }
    
    public void setDateNaissance(String dateNaissance)
    {
      this.dateNaissance = dateNaissance;
    }

    public void setGenre(String genre)
    {
      this.genre = genre;
    }
    
    public void setLogin(String login)
    {
    	this.login = login;
    }
    public void setPassword(String password)
    {
    	this.password = password;
    }

    public Formation getFormation()
    {
      return this.formation;
    }

    public void setFormation(Formation formation)
    {
      this.formation = formation;
    }
    public void setPromotion(Promotion promotion)
    {
      this.promotion = promotion;
    }

    public Promotion getPromotion()

    {
      return this.promotion;
    }

    public String toString()
    {
      return super.toString() + " etudiant de la formation " + this.formation.toString();
    }
    
  }