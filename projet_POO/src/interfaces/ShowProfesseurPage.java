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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        String[] professeurInfoArray = new String[professeurs.size()];
        for (int i = 0; i < professeurs.size(); i++) {
            Enseignant professeur = professeurs.get(i);
            String matiereNom = findMatiereName(matieres, professeur.getMatiere());
            professeurInfoArray[i] = professeur.getNom() + " " + professeur.getPrenom() + " - matière " + matiereNom;
        }

        professeurList = new JList<>(professeurInfoArray);
        professeurList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(professeurList);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(300, getHeight())); 

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        professeurList.addListSelectionListener(e -> updateDetails(professeurs, matieres));

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JButton retourButton = new JButton("Retour");
        JButton modifierButton = new JButton("Modifier");
        JButton supprimerButton = new JButton("Supprimer");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(retourButton);
        buttonsPanel.add(modifierButton);
        buttonsPanel.add(supprimerButton);

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
            detailsTextArea.setText(""); 
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
                updateProfesseurList(professeurs);
                detailsTextArea.setText(""); 
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

            String[] matiereNames = matieres.stream().map(Matiere::getNomMatiere).toArray(String[]::new);

            JComboBox<String> matiereComboBox = new JComboBox<>(matiereNames);

            matiereComboBox.setSelectedItem(findMatiereName(matieres, selectedProfesseur.getMatiere()));

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
                selectedProfesseur.setNom(((JTextField) panel.getComponent(1)).getText());
                selectedProfesseur.setPrenom(((JTextField) panel.getComponent(3)).getText());
                selectedProfesseur.setDateNaissance(((JTextField) panel.getComponent(5)).getText());

                int selectedMatiereIndex = matiereComboBox.getSelectedIndex();
                if (selectedMatiereIndex != -1) {
                    selectedProfesseur.setMatiere(matieres.get(selectedMatiereIndex).getNumeroMatiere());
                }

                selectedProfesseur.setLogin(((JTextField) panel.getComponent(9)).getText());
                selectedProfesseur.setPassword(new String(((JPasswordField) panel.getComponent(11)).getPassword()));

                DatabaseManager.updateEnseignant(selectedProfesseur);

                JOptionPane.showMessageDialog(this,
                        "Professeur modifié avec succès.",
                        "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                List<Enseignant> upprofesseurs = DatabaseManager.getEnseignants();
                List<Matiere> upmatieres = DatabaseManager.getMatiere();
                SwingUtilities.invokeLater(() -> new ShowProfesseurPage(upprofesseurs, upmatieres).setVisible(true));

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
        DatabaseManager.createDatabaseIfNotExists();

        List<Enseignant> professeurs = DatabaseManager.getEnseignants();
        List<Matiere> matieres = DatabaseManager.getMatiere();

        SwingUtilities.invokeLater(() -> new ShowProfesseurPage(professeurs, matieres).setVisible(true));
    }
}
