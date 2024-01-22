package interfaces;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import classes.Enseignant;
import classes.Matiere;
import database.DatabaseManager;

public class ShowProfesseurPage extends JFrame {
    private JList<String> professeurList;
    private JTextArea detailsTextArea;

    public ShowProfesseurPage(List<Enseignant> professeurs, List<Matiere> matieres) {
        initUI(professeurs, matieres);
    }

    private void initUI(List<Enseignant> professeurs, List<Matiere> matieres) {
        setTitle("Liste des professeurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal pour contenir la liste et les détails
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Créer un tableau de chaînes avec les informations à afficher
        String[] professeurInfoArray = new String[professeurs.size()];
        for (int i = 0; i < professeurs.size(); i++) {
            Enseignant professeur = professeurs.get(i);
            String matiereNom = findMatiereName(matieres, professeur.getMatiere());
            professeurInfoArray[i] = professeur.getNom() + " " + professeur.getPrenom() + " - matière " + matiereNom;
        }

        // Créer une liste déroulante pour afficher les professeurs
        professeurList = new JList<>(professeurInfoArray);
        professeurList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(professeurList);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));  // Ajustez la largeur ici

        // Panel pour afficher les détails des professeurs
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> dispose()); // Ferme la fenêtre actuelle

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierProfesseur(professeurs));

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        // Zone de texte pour afficher les détails des professeurs
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        professeurList.addListSelectionListener(e -> updateDetails(professeurs, matieres));

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void updateDetails(List<Enseignant> professeurs, List<Matiere> matieres) {
        int selectedIndex = professeurList.getSelectedIndex();
        if (selectedIndex != -1) {
            Enseignant selectedProfesseur = professeurs.get(selectedIndex);
            String matiereNom = findMatiereName(matieres, selectedProfesseur.getMatiere());
            String details = "Nom : " + selectedProfesseur.getNom() + "\n" +
                             "Prénom : " + selectedProfesseur.getPrenom() + "\n" +
                             "Matière : " + matiereNom;

            detailsTextArea.setText(details);
        } else {
            detailsTextArea.setText(""); // Effacer les détails si rien n'est sélectionné
        }
    }

    private void modifierProfesseur(List<Enseignant> professeurs) {
        int selectedIndex = professeurList.getSelectedIndex();
        if (selectedIndex != -1) {
            Enseignant selectedProfesseur = professeurs.get(selectedIndex);
            JOptionPane.showMessageDialog(this,
                    "Modifier un professeur : " + selectedProfesseur.getNom() + " " + selectedProfesseur.getPrenom(),
                    "Modifier un professeur", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un professeur avant de modifier.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String findMatiereName(List<Matiere> matieres, int matiereId) {
        for (Matiere matiere : matieres) {
            if (matiere.getNumeroMatiere() == matiereId) {
                return matiere.getNomMatiere();
            }
        }
        return "Matière inconnue";
    }

    public static void main(String[] args) {
        // Assurez-vous d'appeler createDatabaseIfNotExists pour créer la base de données si elle n'existe pas
        DatabaseManager.createDatabaseIfNotExists();

        // Récupérer la liste de professeurs depuis le DatabaseManager
        List<Enseignant> professeurs = DatabaseManager.getEnseignants();
        // Récupérer la liste des matières depuis le DatabaseManager
        List<Matiere> matieres = DatabaseManager.getMatiere();

        // Créer une instance de la page d'affichage des professeurs
        SwingUtilities.invokeLater(() -> new ShowProfesseurPage(professeurs, matieres).setVisible(true));
    }
}
