package interfaces;
import javax.swing.*;

import classes.Etudiant;
import classes.Cours;
import classes.Salle;
import classes.Enseignant;
import classes.Matiere;
import database.DatabaseManager;

import java.awt.*;
import java.util.List;

public class ShowPageCours extends JFrame {
    private JList<String> coursList;

    public ShowPageCours(List<Cours> Cours) {
        // Créer un tableau de chaînes avec les informations à afficher
        String[] coursArray = new String[Cours.size()];
        Enseignant enseignantCours = null;
        for (int i = 0; i < Cours.size(); i++) {
        	Cours cours = Cours.get(i);
        	List<Enseignant> listeEnseignants = DatabaseManager.getEnseignants();
        	int idEnseignant = cours.getEnseignant();
		    for (Enseignant enseignant : listeEnseignants) {
		        if (enseignant.getId() == idEnseignant) {
		        	enseignantCours = enseignant;
		        }
		    }
		    int idMatiere = cours.getMatiere();
        	Matiere matiereCours = null;
        	List<Matiere> listeMatiere = DatabaseManager.getMatiere();
		    for (Matiere matiere : listeMatiere) {
		        if (matiere.getNumeroMatiere() == idMatiere) {
		        	matiereCours = matiere;
		        }
		    }
		    
		    int idSalle = cours.getSalle();
        	Salle salleCours = null;
        	List<Salle> listeSalle = DatabaseManager.getSalle();
		    for (Salle salle : listeSalle) {
		        if (salle.getNumeroSalle() == idSalle) {
		        	salleCours = salle;
		        }
		    }
		    
		    if (enseignantCours != null) {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " avec " + enseignantCours.getNom() + " " + enseignantCours.getPrenom() +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            } else {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " (Enseignant non trouvé)" +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            }
            
        }

        // Créer une liste déroulante pour afficher les étudiants
        coursList = new JList<>(coursArray);

        // Mettre la liste dans un JScrollPane pour la faire défiler si nécessaire
        JScrollPane scrollPane = new JScrollPane(coursList);

        // Ajouter le JScrollPane à la fenêtre
        add(scrollPane);

        // Configurer la fenêtre
        setTitle("Liste des cours");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Assurez-vous d'appeler createDatabaseIfNotExists pour créer la base de données si elle n'existe pas
        DatabaseManager.createDatabaseIfNotExists();

        // Récupérer la liste d'étudiants depuis le DatabaseManager
        List<Cours> Cours = DatabaseManager.getCours();

        // Créer une instance de la page d'affichage des étudiants
        SwingUtilities.invokeLater(() -> new ShowPageCours(Cours));
    }
}
