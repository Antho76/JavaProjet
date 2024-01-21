package interfaces;
import javax.swing.*;

import classes.Etudiant;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowEtudiantPage extends JFrame {

    private List<Etudiant> etudiants;

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

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Etudiant etudiant : etudiants) {
            listModel.addElement(etudiant.getNom() + " " + etudiant.getPrenom());
        }

        JList<String> etudiantList = new JList<>(listModel);
        etudiantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(etudiantList);
        mainPanel.add(scrollPane, BorderLayout.WEST);

        // Panel pour afficher les détails des étudiants
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
                int selectedIndex = etudiantList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Etudiant selectedEtudiant = etudiants.get(selectedIndex);
                    JOptionPane.showMessageDialog(ShowEtudiantPage.this,
                            "Modifier un évènement pour l'étudiant : " + selectedEtudiant.getNom() + " " + selectedEtudiant.getPrenom(),
                            "Modifier un évènement", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ShowEtudiantPage.this,
                            "Veuillez sélectionner un étudiant avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        // Zone de texte pour afficher les détails des étudiants
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        etudiantList.addListSelectionListener(e -> {
            int selectedIndex = etudiantList.getSelectedIndex();
            if (selectedIndex != -1) {
                Etudiant selectedEtudiant = etudiants.get(selectedIndex);
                String details = "Nom : " + selectedEtudiant.getNom() + "\n" +
                                 "Prénom : " + selectedEtudiant.getPrenom() + "\n" +
                                 "Promotion : " + selectedEtudiant.getPromotion() + "\n" +
                                 "Formation : " + selectedEtudiant.getFormation();

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
        List<Etudiant> etudiants = DatabaseManager.getStudents();
        
        SwingUtilities.invokeLater(() -> {
            ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
            showEtudiantPage.setVisible(true);
        });
    }
}

