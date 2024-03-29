package classes;

public class Personnel {

    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;
    private String login;
    private String password;
    private int numeroPersonnel;
    private String metier;

    public Personnel(int id, String nom, String prenom, String dateNaissance, String metier, String login, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.login = login;
        this.password = password;
        this.metier = metier;
    }

    public String getNom() {
        return this.nom;
    }

    public int getId() {
        return this.id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumeroPersonnel() {
        return this.numeroPersonnel;
    }

    public String getMetier() {
        return this.metier;
    }

    public void setNumeroPersonnel(int numeroPersonnel) {
        this.numeroPersonnel = numeroPersonnel;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String toString() {
        return super.toString() + " personnel de metier " + this.login+ this.password;
    }
}
