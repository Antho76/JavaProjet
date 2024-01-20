package interfaces;
import javax.swing.*;

import classes.Etudiant;
import database.DatabaseManager;

import java.awt.*;
import java.util.List;

public class ShowEtudiantPage extends JFrame {
    private JList<String> etudiantList;

    public ShowEtudiantPage(List<Etudiant> etudiants) {
        // Créer un tableau de chaînes avec les informations à afficher
        String[] etudiantInfoArray = new String[etudiants.size()];
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            etudiantInfoArray[i] = etudiant.getNom() + " " + etudiant.getPrenom() + " - Promotion " + etudiant.getPromotion();
        }

        // Créer une liste déroulante pour afficher les étudiants
        etudiantList = new JList<>(etudiantInfoArray);

        // Mettre la liste dans un JScrollPane pour la faire défiler si nécessaire
        JScrollPane scrollPane = new JScrollPane(etudiantList);

        // Ajouter le JScrollPane à la fenêtre
        add(scrollPane);

        // Configurer la fenêtre
        setTitle("Liste des étudiants");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Assurez-vous d'appeler createDatabaseIfNotExists pour créer la base de données si elle n'existe pas
        DatabaseManager.createDatabaseIfNotExists();

        // Récupérer la liste d'étudiants depuis le DatabaseManager
        List<Etudiant> etudiants = DatabaseManager.getStudents();

        // Créer une instance de la page d'affichage des étudiants
        SwingUtilities.invokeLater(() -> new ShowEtudiantPage(etudiants));
    }
}
