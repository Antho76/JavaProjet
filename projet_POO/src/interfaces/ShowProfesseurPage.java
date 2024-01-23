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

        // Zone de texte pour afficher les détails des professeurs
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        professeurList.addListSelectionListener(e -> updateDetails(professeurs, matieres));

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Boutons en bas de la colonne de gauche
        JButton retourButton = new JButton("Retour");
        JButton modifierButton = new JButton("Modifier");
        JButton supprimerButton = new JButton("Supprimer");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(retourButton);
        buttonsPanel.add(modifierButton);
        buttonsPanel.add(supprimerButton);

        // Écouteurs pour les boutons
        retourButton.addActionListener(e -> dispose());
        modifierButton.addActionListener(e -> modifierProfesseur(professeurs, matieres));
        supprimerButton.addActionListener(e -> supprimerProfesseur(professeurs));

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

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

    private void supprimerProfesseur(List<Enseignant> professeurs) {
        int selectedIndex = professeurList.getSelectedIndex();
        if (selectedIndex != -1) {
            Enseignant selectedProfesseur = professeurs.get(selectedIndex);

            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous vraiment supprimer le professeur sélectionné ?", "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                DatabaseManager.deleteEnseignantById(selectedProfesseur.getId());
                professeurs.remove(selectedIndex);
                // Mettre à jour la liste affichée
                updateProfesseurList(professeurs);
                detailsTextArea.setText(""); // Effacer les détails après la suppression
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un professeur avant de supprimer.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateProfesseurList(List<Enseignant> professeurs) {
        DefaultListModel<String> updatedModel = new DefaultListModel<>();
        for (Enseignant professeur : professeurs) {
            updatedModel.addElement(professeur.getNom() + " " + professeur.getPrenom());
        }
        professeurList.setModel(updatedModel);
    }

    private void modifierProfesseur(List<Enseignant> professeurs, List<Matiere> matieres) {
        int selectedIndex = professeurList.getSelectedIndex();
        if (selectedIndex != -1) {
            Enseignant selectedProfesseur = professeurs.get(selectedIndex);

            // Récupérer les noms de matières depuis la liste de matières
            String[] matiereNames = matieres.stream().map(Matiere::getNomMatiere).toArray(String[]::new);

            // Créer une JComboBox avec les noms de matières
            JComboBox<String> matiereComboBox = new JComboBox<>(matiereNames);

            // Sélectionner la matière actuelle de l'enseignant dans la JComboBox
            matiereComboBox.setSelectedItem(findMatiereName(matieres, selectedProfesseur.getMatiere()));

            // Créer une boîte de dialogue avec les composants nécessaires
            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Nom:"));
            panel.add(new JTextField(selectedProfesseur.getNom()));
            panel.add(new JLabel("Prénom:"));
            panel.add(new JTextField(selectedProfesseur.getPrenom()));
            panel.add(new JLabel("Date de naissance:"));
            panel.add(new JTextField(selectedProfesseur.getDateNaissance()));
            panel.add(new JLabel("Matière:"));
            panel.add(matiereComboBox);
            panel.add(new JLabel("Login:"));
            panel.add(new JTextField(selectedProfesseur.getLogin()));
            panel.add(new JLabel("Mot de passe:"));
            panel.add(new JPasswordField(selectedProfesseur.getPassword()));

            int result = JOptionPane.showConfirmDialog(null, panel, "Modifier le professeur",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Mettre à jour les informations du professeur avec les nouvelles valeurs
                selectedProfesseur.setNom(((JTextField) panel.getComponent(1)).getText());
                selectedProfesseur.setPrenom(((JTextField) panel.getComponent(3)).getText());
                selectedProfesseur.setDateNaissance(((JTextField) panel.getComponent(5)).getText());

                // Mettre à jour l'ID de la matière
                int selectedMatiereIndex = matiereComboBox.getSelectedIndex();
                if (selectedMatiereIndex != -1) {
                    selectedProfesseur.setMatiere(matieres.get(selectedMatiereIndex).getNumeroMatiere());
                }

                // Mettre à jour le login et le mot de passe
                selectedProfesseur.setLogin(((JTextField) panel.getComponent(9)).getText());
                selectedProfesseur.setPassword(new String(((JPasswordField) panel.getComponent(11)).getPassword()));

                // Appeler la méthode pour mettre à jour le professeur dans la base de données
                DatabaseManager.updateEnseignant(selectedProfesseur);

                // Afficher un message d'information pour indiquer que la modification a été effectuée
                JOptionPane.showMessageDialog(this,
                        "Professeur modifié avec succès.",
                        "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                List<Enseignant> upprofesseurs = DatabaseManager.getEnseignants();
                List<Matiere> upmatieres = DatabaseManager.getMatiere();
                SwingUtilities.invokeLater(() -> new ShowProfesseurPage(upprofesseurs, upmatieres).setVisible(true));

                // Mettre à jour les détails après la modification
                updateDetails(professeurs, matieres);
            }
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
