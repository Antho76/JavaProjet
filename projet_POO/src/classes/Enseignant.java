package classes;
//import java.util.Date;

public class Enseignant {
	private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;
    private String login;
    private String password;
	private Matiere matiere;

  public Enseignant(String nom, String prenom, String dateNaissance, Matiere matiere, String login, String password){
	  this.nom = nom;
      this.prenom = prenom;
      this.dateNaissance = dateNaissance;
      this.login = login;
      this.password = password;    
      this.matiere = matiere;
  }

  public Enseignant(){
    super();
    this.matiere = new Matiere();
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

