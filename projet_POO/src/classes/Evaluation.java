package classes;

public class Evaluation 
{
	private String nom;
	private int idMatiere;
	private int idEnseignant;
	private int note;
	private int idEtudiant;

	public Evaluation(String nom,int matiere,int enseignant,int note,int etudiant){
		this.nom = nom;
		this.idMatiere = matiere;
		this.idEnseignant = enseignant;
		this.note = note;
		this.idEtudiant = etudiant;
	}

	public String getNom() {
		return this.nom;
	}

	public int getMatiere() {
		return this.idMatiere;
	}

	public int getEnseignant() {
		return this.idEnseignant;
	}

	public int getNote() {
		return this.note;
	}

	public int getEtudiant() {
		return this.idEtudiant;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setMatiere(int matiere) {
		this.idMatiere = matiere;
	}

	public void setEnseignant(int enseignant) {
		this.idEnseignant = enseignant;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public void setEtudiant(int etudiant) {
		this.idEtudiant = etudiant;
	}
}

