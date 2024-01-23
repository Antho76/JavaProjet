package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import classes.Matiere;
import database.DatabaseManager;

public class InterfaceAddMatiere extends JFrame {

    private JTextField nomMatiereField;
    private JTextField coefficientField;

    public InterfaceAddMatiere() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter une Matière");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));


        JLabel nomMatiereLabel = new JLabel("Nom de Matière:");
        nomMatiereField = new JTextField();

        JLabel coefficientLabel = new JLabel("Coefficient:");
        coefficientField = new JTextField();

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMatiere();
            }
        });


        mainPanel.add(nomMatiereLabel);
        mainPanel.add(nomMatiereField);

        mainPanel.add(coefficientLabel);
        mainPanel.add(coefficientField);

        mainPanel.add(new JLabel());
        mainPanel.add(ajouterButton);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void ajouterMatiere() {
        try {
            String nomMatiere = nomMatiereField.getText();
            int coefficient = Integer.parseInt(coefficientField.getText());

            Matiere newMatiere = new Matiere(0, nomMatiere, coefficient);

            // Appel à la méthode DatabaseManager pour insérer la matière
            DatabaseManager.insertMatiere(newMatiere);

            // Affichage d'un message de succès
            JOptionPane.showMessageDialog(InterfaceAddMatiere.this,
                    "La matière a été ajoutée avec succès.",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Fermeture de la fenêtre après ajout
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(InterfaceAddMatiere.this,
                    "Veuillez entrer des valeurs numériques valides.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAddMatiere interfaceAddMatiere = new InterfaceAddMatiere();
            interfaceAddMatiere.setVisible(true);
        });
    }
}
