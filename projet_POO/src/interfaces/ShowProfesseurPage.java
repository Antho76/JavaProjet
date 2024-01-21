package interfaces;
import javax.swing.*;

import classes.Enseignant;
import classes.Etudiant;
import database.DatabaseManager;

import java.awt.*;
import java.util.List;

public class ShowProfesseurPage extends JFrame {
    private JList<String> professeurList;

    public ShowProfesseurPage(List<Enseignant> professeurs) {
        // Créer un tableau de chaînes avec les informations à afficher
        String[] etudiantInfoArray = new String[professeurs.size()];
        for (int i = 0; i < professeurs.size(); i++) {
            Enseignant professeur = professeurs.get(i);
            etudiantInfoArray[i] = professeur.getNom() + " " + professeur.getPrenom() + " - matiére " + professeur.getMatiere();
        }

        // Créer une liste déroulante pour afficher les étudiants
        professeurList = new JList<>(etudiantInfoArray);

        // Mettre la liste dans un JScrollPane pour la faire défiler si nécessaire
        JScrollPane scrollPane = new JScrollPane(professeurList);

        // Ajouter le JScrollPane à la fenêtre
        add(scrollPane);

        // Configurer la fenêtre
        setTitle("Liste des professeurs");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Assurez-vous d'appeler createDatabaseIfNotExists pour créer la base de données si elle n'existe pas
        DatabaseManager.createDatabaseIfNotExists();

        // Récupérer la liste d'étudiants depuis le DatabaseManager
        List<Enseignant> professeurs = DatabaseManager.getEnseignants();

        // Créer une instance de la page d'affichage des étudiants
        SwingUtilities.invokeLater(() -> new ShowProfesseurPage(professeurs));
    }
}
