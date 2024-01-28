package interfaces;

import javax.swing.*;

import classes.Enseignant;
import classes.Etudiant;
import classes.Evaluation;
import classes.Matiere;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceAddNotes extends JFrame {
	
	private Enseignant enseignant;
	
    public void ajouterNote(Enseignant enseignant) {
    	this.enseignant = enseignant;
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter des notes");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
        setVisible(true); 
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nomEvaluationLabel = new JLabel("Nom de l'évaluation : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomEvaluationLabel, gbc);

        JTextField nomEvaluationText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomEvaluationText, gbc);
        
        JLabel etudiantsLabel = new JLabel("Etudiant :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(etudiantsLabel,gbc);

        JComboBox<String> etudiantComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        loadEtudiantIntoComboBox(etudiantComboBox);
        panel.add(etudiantComboBox,gbc);

        JLabel matiereLabel = new JLabel("Sélectionnez la matière:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(matiereLabel,gbc);

        JComboBox<String> matiereComboBox = new JComboBox<>();
        loadMatieresIntoComboBox(matiereComboBox);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(matiereComboBox,gbc);
        
        JLabel noteLabel = new JLabel("Note :");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(noteLabel,gbc);

        JTextField noteText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(noteText,gbc);

        JButton ajouterAvertissementButton = new JButton("Ajouter Note");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(ajouterAvertissementButton, gbc);
        ajouterAvertissementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomEvaluation = nomEvaluationText.getText().trim();
                int idEtudiant = getEtudiantIdFromComboBox(etudiantComboBox);
                int idEnseignant = enseignant.getId();
                int idMatiere = getMatiereIdFromComboBox(matiereComboBox);

                int note;
                try {
                    note = Integer.parseInt(noteText.getText().trim());
                    if (note < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InterfaceAddNotes.this,
                            "Veuillez entrer un entier positif pour la note.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nomEvaluation.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceAddNotes.this,
                            "Veuillez remplir le champ 'Nom de l'évaluation'.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    Evaluation evaluation = new Evaluation(nomEvaluation, idMatiere, idEnseignant, note, idEtudiant);
                    DatabaseManager.insertEvaluation(evaluation);

                    JOptionPane.showMessageDialog(InterfaceAddNotes.this,
                            "Note ajoutée avec succès!",
                            "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(30), gbc);
        JButton retourButton = new JButton("Retour");
        gbc.gridy = 4;
        panel.add(retourButton, gbc);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void loadEtudiantIntoComboBox(JComboBox<String> etudiantComboBox) {
        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        for (Etudiant etudiant : listEtudiant) {
        	etudiantComboBox.addItem(etudiant.getNom()+" "+etudiant.getPrenom());
        }
    }
    
    private void loadMatieresIntoComboBox(JComboBox<String> matiereComboBox) {
        List<Matiere> matieres = DatabaseManager.getMatiere();
        for (Matiere matiere : matieres) {
            matiereComboBox.addItem(matiere.getNomMatiere());
        }
    }
    
    private int getEtudiantIdFromComboBox(JComboBox<String> etudiantComboBox) {
        String selectedEtudiant = etudiantComboBox.getSelectedItem().toString();
        String[] etudiantNomPrenom = selectedEtudiant.split(" ");
        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        for (Etudiant etudiant : listEtudiant) {
            if (etudiant.getNom().equals(etudiantNomPrenom[0]) && etudiant.getPrenom().equals(etudiantNomPrenom[1]) ) {
                return etudiant.getId();
            }
        }
        return 0; 
    }
    
    private int getMatiereIdFromComboBox(JComboBox<String> matiereComboBox) {
        String selectedMatiere = matiereComboBox.getSelectedItem().toString();
        List<Matiere> matieres = DatabaseManager.getMatiere();
        for (Matiere matiere : matieres) {
            if (matiere.getNomMatiere().equals(selectedMatiere)) {
                return matiere.getNumeroMatiere();
            }
        }
        return 0; 
    }

}
