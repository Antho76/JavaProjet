package interfaces;

import javax.swing.*;

import classes.Formation;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceAddFormation extends JFrame {

    public InterfaceAddFormation() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter une formation");
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
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nomFormationLabel = new JLabel("Nom de la formation : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomFormationLabel, gbc);

        JTextField nomFormationText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomFormationText, gbc);


        JButton ajouterFormationButton = new JButton("Ajouter Formation");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(ajouterFormationButton, gbc);
        ajouterFormationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomFormation = nomFormationText.getText().trim();

                if (nomFormation.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceAddFormation.this,
                            "Veuillez remplir le champ 'Nom de la formation'.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean ajoutReussi = DatabaseManager.insertFormation(nomFormation);
                	if (ajoutReussi) {
                        JOptionPane.showMessageDialog(InterfaceAddFormation.this,
                                "Formation ajoutée avec succès!",
                                "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                        nomFormationText.setText("");
                    } else {
                        JOptionPane.showMessageDialog(InterfaceAddFormation.this,
                                "La formation existe déjà. L'ajout n'a pas été effectué.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAddFormation addFormationPage = new InterfaceAddFormation();
            addFormationPage.setVisible(true);
        });
    }
}
