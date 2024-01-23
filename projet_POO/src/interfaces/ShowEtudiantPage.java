package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Etudiant;
import classes.Formation;
import classes.Promotion;
import database.DatabaseManager;

public class ShowEtudiantPage extends JFrame {

    private List<Etudiant> etudiants;
    private JTextArea detailsTextArea;
    private DefaultListModel<String> listModel;

    public ShowEtudiantPage(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des étudiants");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        for (Etudiant etudiant : etudiants) {
            listModel.addElement(etudiant.getNom() + " " + etudiant.getPrenom());
        }

        JList<String> etudiantList = new JList<>(listModel);
        etudiantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(etudiantList);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = etudiantList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Etudiant selectedEtudiant = etudiants.get(selectedIndex);

                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "Voulez-vous vraiment supprimer l'étudiant sélectionné ?", "Confirmation de suppression",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        DatabaseManager.deleteEtudiantById(selectedEtudiant.getId());
                        etudiants.remove(selectedIndex);
                        listModel.removeElementAt(selectedIndex);
                        detailsTextArea.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowEtudiantPage.this,
                            "Veuillez sélectionner un étudiant avant de supprimer.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(supprimerButton, BorderLayout.SOUTH);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        etudiantList.addListSelectionListener(e -> {
            int selectedIndex = etudiantList.getSelectedIndex();
            if (selectedIndex != -1) {
                Etudiant selectedEtudiant = etudiants.get(selectedIndex);

                List<Promotion> promotions = DatabaseManager.getPromotions();
                List<Formation> formations = DatabaseManager.getFormations();

                String promotionName = promotions.stream()
                        .filter(promotion -> promotion.getId() == selectedEtudiant.getPromotion())
                        .findFirst()
                        .map(Promotion::toString)
                        .orElse("");

                String formationName = formations.stream()
                        .filter(formation -> formation.getId_Formation() == selectedEtudiant.getFormation())
                        .findFirst()
                        .map(Formation::getNomFormation)
                        .orElse("");

                String details = "Nom : " + selectedEtudiant.getNom() + "\n" +
                        "Prénom : " + selectedEtudiant.getPrenom() + "\n" +
                        "Promotion : " + promotionName + "\n" +
                        "Formation : " + formationName;

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Bouton "Modifier" placé en bas de la colonne de gauche
        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = etudiantList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Etudiant selectedEtudiant = etudiants.get(selectedIndex);

                    // Récupérer les listes de promotions et de formations depuis la base de données
                    List<Promotion> promotions = DatabaseManager.getPromotions();
                    List<Formation> formations = DatabaseManager.getFormations();

                    // Créer des tableaux pour stocker les noms des promotions et des formations
                    String[] promotionNames = promotions.stream().map(Promotion::toString).toArray(String[]::new);
                    String[] formationNames = formations.stream().map(Formation::toString).toArray(String[]::new);

                    // Créer des JComboBox avec les noms des promotions et des formations
                    JComboBox<String> promotionComboBox = new JComboBox<>(promotionNames);
                    JComboBox<String> formationComboBox = new JComboBox<>(formationNames);

                    // Sélectionner les valeurs actuelles de l'étudiant dans les JComboBox
                    promotionComboBox.setSelectedItem(selectedEtudiant.getPromotion());
                    formationComboBox.setSelectedItem(selectedEtudiant.getFormation());

                 // Créer une boîte de dialogue avec les JComboBox et les nouveaux champs
                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    panel.add(new JLabel("Nom:"));
                    panel.add(new JTextField(selectedEtudiant.getNom()));
                    panel.add(new JLabel("Prénom:"));
                    panel.add(new JTextField(selectedEtudiant.getPrenom()));
                    panel.add(new JLabel("Date de naissance:"));
                    panel.add(new JTextField(selectedEtudiant.getDateNaissance()));
                    panel.add(new JLabel("Login:"));
                    panel.add(new JTextField(selectedEtudiant.getLogin()));
                    panel.add(new JLabel("Mot de passe:"));
                    panel.add(new JTextField(selectedEtudiant.getPassword()));
                    panel.add(new JLabel("Promotion:"));
                    panel.add(promotionComboBox);
                    panel.add(new JLabel("Formation:"));
                    panel.add(formationComboBox);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Modifier l'étudiant",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        // Mettre à jour les informations de l'étudiant avec les nouvelles valeurs
                        selectedEtudiant.setNom(((JTextField) panel.getComponent(1)).getText());
                        selectedEtudiant.setPrenom(((JTextField) panel.getComponent(3)).getText());
                        selectedEtudiant.setDateNaissance(((JTextField) panel.getComponent(5)).getText());
                        selectedEtudiant.setLogin(((JTextField) panel.getComponent(7)).getText());
                        selectedEtudiant.setPassword(((JTextField) panel.getComponent(9)).getText());

                        // Mettre à jour les ID de promotion et de formation
                        int selectedPromotionIndex = promotionComboBox.getSelectedIndex();
                        if (selectedPromotionIndex != -1) {
                            selectedEtudiant.setPromotion(promotions.get(selectedPromotionIndex).getId());
                        }

                        int selectedFormationIndex = formationComboBox.getSelectedIndex();
                        if (selectedFormationIndex != -1) {
                            selectedEtudiant.setFormation(formations.get(selectedFormationIndex).getId_Formation());
                        }

                        // Appeler la méthode pour mettre à jour l'étudiant dans la base de données
                        DatabaseManager.updateEtudiant(selectedEtudiant);

                        // Fermer la fenêtre actuelle
                        ((Window) SwingUtilities.getRoot(panel)).dispose();
                        dispose();
                        // Relancer la fenêtre avec les mises à jour
                        SwingUtilities.invokeLater(() -> {
                            ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
                            showEtudiantPage.setVisible(true);
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowEtudiantPage.this,
                            "Veuillez sélectionner un étudiant avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        mainPanel.add(modifierButton, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        List<Etudiant> etudiants = DatabaseManager.getStudents();

        SwingUtilities.invokeLater(() -> {
            ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
            showEtudiantPage.setVisible(true);
        });
    }
}
