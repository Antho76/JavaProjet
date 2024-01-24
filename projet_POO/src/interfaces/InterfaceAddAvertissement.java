package interfaces;

import javax.swing.*;

import classes.Enseignant;
import classes.Etudiant;
import classes.Avertissement;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceAddAvertissement extends JFrame {
	
	private Enseignant enseignant;
	
    public void ajouterAvertissement(Enseignant enseignant) {
    	this.enseignant = enseignant;
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter un avertissement");
        setSize(600, 400); // Ajustez les dimensions selon vos besoins
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15); // Ajustez ces marges selon vos besoins

        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
        setVisible(true); // Ne pas rendre visible ici, cela est fait à la fin pour éviter le problème de dimension
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nomAvertissementLabel = new JLabel("Raison de l'avertissement : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomAvertissementLabel, gbc);

        JTextField nomAvertissementText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomAvertissementText, gbc);
        
        JLabel etudiantsLabel = new JLabel("Etudiant qui prend l'avertissement:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(etudiantsLabel,gbc);

        JComboBox<String> etudiantComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        loadEtudiantIntoComboBox(etudiantComboBox);
        panel.add(etudiantComboBox,gbc);


        // Bouton Ajouter Formation
        JButton ajouterAvertissementButton = new JButton("Ajouter Avertissement");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(ajouterAvertissementButton, gbc);
        ajouterAvertissementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomAvertissement = nomAvertissementText.getText().trim();
                int idEtudiant = getEtudiantIdFromComboBox(etudiantComboBox);
                int idEnseignant = enseignant.getId();
                if (nomAvertissement.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceAddAvertissement.this,
                            "Veuillez remplir le champ 'Raison de l'avertissement'.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                	Avertissement avertissement = new Avertissement(nomAvertissement,idEtudiant,idEnseignant);
                    DatabaseManager.insertAvertissement(avertissement);

                    JOptionPane.showMessageDialog(InterfaceAddAvertissement.this,
                            "Avertissement ajouté avec succès!",
                            "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Bouton Retour en bas
        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(30), gbc); // Espace vertical
        JButton retourButton = new JButton("Retour");
        gbc.gridy = 4;
        panel.add(retourButton, gbc);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Ajoutez ici la logique pour retourner à la page précédente
            }
        });
    }
    
    private void loadEtudiantIntoComboBox(JComboBox<String> etudiantComboBox) {
        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        for (Etudiant etudiant : listEtudiant) {
        	etudiantComboBox.addItem(etudiant.getNom()+" "+etudiant.getPrenom());
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
        return 0; // Ou une valeur par défaut, selon votre logique
    }

}
