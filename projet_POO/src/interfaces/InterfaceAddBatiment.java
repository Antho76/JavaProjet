package interfaces;

import javax.swing.*;

import classes.Batiment;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceAddBatiment extends JFrame {

    private JTextField villeField;
    private JTextField nomBatimentField;


    public InterfaceAddBatiment() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter un bâtiment");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel villeLabel = new JLabel("Ville:");
        villeField = new JTextField();

        JLabel nomBatimentLabel = new JLabel("Nom du bâtiment:");
        nomBatimentField = new JTextField();


        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterBatiment();
            }
        });

        mainPanel.add(villeLabel);
        mainPanel.add(villeField);
        mainPanel.add(nomBatimentLabel);
        mainPanel.add(nomBatimentField);
        mainPanel.add(new JLabel());
        mainPanel.add(ajouterButton);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void ajouterBatiment() {
        String ville = villeField.getText();
        String nomBatiment = nomBatimentField.getText();


        // Vérifier si tous les champs sont remplis
        if (ville.isEmpty() || nomBatiment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            // Créer un nouvel objet Batiment
            Batiment batiment = new Batiment(0, ville, nomBatiment );

            // Ajouter le nouveau bâtiment à la base de données
            DatabaseManager.insertBatiment(batiment);

            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, "Le bâtiment a été ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Fermer la fenêtre d'ajout de bâtiment
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le nombre de salles doit être un nombre entier valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAddBatiment interfaceAddBatiment = new InterfaceAddBatiment();
            interfaceAddBatiment.setVisible(true);
        });
    }
}