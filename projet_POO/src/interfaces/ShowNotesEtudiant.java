package interfaces;
import javax.swing.*;
import database.DatabaseManager;

import classes.Etudiant;
import classes.Evaluation;
import classes.Matiere;

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
        this.idEtudiant = idEtudiant; 
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
            if (evaluation.getEtudiant() == idEtudiant) { 
            	int idMatiere=evaluation.getMatiere();
            	Matiere matiere=DatabaseManager.getMatiereById(idMatiere);
            	String nomMatiere = matiere.getNomMatiere();
                String evaluationInfo = String.format("Évaluation: %s, Matière : %s, Note: %d", 
                    evaluation.getNom(), nomMatiere, evaluation.getNote());
                listModel.addElement(evaluationInfo);
            }
        }

        JList<String> notesList = new JList<>(listModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(notesList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());


        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        

        this.add(mainPanel);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
