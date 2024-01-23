package interfaces;
import javax.swing.*;

import classes.Etudiant;
import classes.Evaluation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowNotesEtudiant extends JFrame {

    private List<Evaluation> evaluations;
    private Etudiant connectedEtudiant;
    private int idEtudiant; 

    
    public ShowNotesEtudiant(List<Evaluation> evaluations, int idEtudiant) {
        this.evaluations = evaluations;
        this.idEtudiant = idEtudiant; // Assigner l'ID de l'étudiant
        initUI(idEtudiant);
    }

    private void initUI(int idEtudiant) {
        setTitle("Notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getEtudiant() == idEtudiant) { // Filtrer selon l'ID de l'étudiant
                String evaluationInfo = String.format("Évaluation: %s, Matière ID: %d, Note: %d", 
                    evaluation.getNom(), evaluation.getMatiere(), evaluation.getNote());
                listModel.addElement(evaluationInfo);
            }
        }

        JList<String> notesList = new JList<>(listModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(notesList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        // Panel pour afficher les détails des évaluations
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        // Ici vous pouvez ajouter des composants pour afficher les détails des évaluations
        // Par exemple, un JLabel pour afficher les informations d'une évaluation sélectionnée
        // JLabel detailsLabel = new JLabel();
        // detailsPanel.add(detailsLabel, BorderLayout.CENTER);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Ajout du bouton "Retour"
        JPanel bottomPanel = new JPanel();
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre actuelle
            }
        });
        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        

        // Ajouter le mainPanel à la JFrame
        this.add(mainPanel);
        setLocationRelativeTo(null);

        // Rendre la fenêtre visible
        setVisible(true);
    }
}
