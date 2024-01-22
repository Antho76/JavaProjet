package interfaces;
import javax.swing.*;

import classes.Formation;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowFormationPage extends JFrame {

    private List<Formation> formations;

    public ShowFormationPage(List<Formation> formations) {
        this.formations = formations;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des formations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Formation formation : formations) {
            listModel.addElement(formation.toString());
        }

        JList<String> formationList = new JList<>(listModel);
        formationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(formationList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));  // Ajustez la largeur ici

        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));  // Ajustez la largeur ici

        // Panel pour afficher les détails des formations
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = formationList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Formation selectedFormation = formations.get(selectedIndex);

                    // Demander le nouveau nom de la formation via une boîte de dialogue
                    String nouveauNomFormation = JOptionPane.showInputDialog(
                            ShowFormationPage.this,
                            "Veuillez entrer le nouveau nom de la formation :",
                            "Modifier la formation",
                            JOptionPane.PLAIN_MESSAGE);

                    // Vérifier si l'utilisateur a cliqué sur OK et si un nouveau nom a été saisi
                    if (nouveauNomFormation != null && !nouveauNomFormation.isEmpty()) {
                        // Mettre à jour le nom de la formation dans la liste des formations
                        selectedFormation.setNomFormation(nouveauNomFormation);
                        // Mettre à jour le nom de la formation dans la base de données
                        DatabaseManager.updateFormation(selectedFormation);

                        // Mettre à jour le modèle de liste pour refléter le nouveau nom
                        listModel.setElementAt(selectedFormation.toString(), selectedIndex);
                        // Mettre à jour les détails
                        dispose();
                        List<Formation> formations = DatabaseManager.getFormations();
                        SwingUtilities.invokeLater(() -> {
                            ShowFormationPage showFormationPage = new ShowFormationPage(formations);
                            showFormationPage.setVisible(true);
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowFormationPage.this,
                            "Veuillez sélectionner une formation avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        // Zone de texte pour afficher les détails des formations
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        formationList.addListSelectionListener(e -> {
            int selectedIndex = formationList.getSelectedIndex();
            if (selectedIndex != -1) {
                Formation selectedFormation = formations.get(selectedIndex);
                String details = "ID de la Formation : " + selectedFormation.getId_Formation() + "\n" +
                                 "Nom de la Formation : " + selectedFormation.getNomFormation();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText(""); // Effacer les détails si rien n'est sélectionné
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Vous pouvez placer ici le code pour tester votre page
        List<Formation> formations = DatabaseManager.getFormations();

        SwingUtilities.invokeLater(() -> {
            ShowFormationPage showFormationPage = new ShowFormationPage(formations);
            showFormationPage.setVisible(true);
        });
    }
}
