package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import controller.InscriptionController;

public class InscriptionInterface {
    static InscriptionController inscriptionController = new InscriptionController();

    public void afficherInterface() {
        JFrame frame = new JFrame("Page d'Inscription");
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        frame.add(panel);
        placeComponents(panel, frame);

        // Ajout d'un bouton "Retour"
        JButton retourButton = new JButton("Retour");
        retourButton.setBounds(10, 340, 120, 25);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre d'inscription et réafficher la fenêtre de connexion
                frame.dispose();
                new ConnectionInterface().afficherInterface();
            }
        });
        panel.add(retourButton);


        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame mainFrame) { // Ajouter le paramètre JFrame
        panel.setLayout(null);

        JLabel nomLabel = new JLabel("Nom : ");
        nomLabel.setBounds(10, 20, 120, 25);
        panel.add(nomLabel);

        JTextField nomText = new JTextField(20);
        nomText.setBounds(150, 20, 165, 25);
        panel.add(nomText);

        JLabel prenomLabel = new JLabel("Prénom : ");
        prenomLabel.setBounds(10, 60, 120, 25);
        panel.add(prenomLabel);

        JTextField prenomText = new JTextField(20);
        prenomText.setBounds(150, 60, 165, 25);
        panel.add(prenomText);

        JLabel birthLabel = new JLabel("Date de naissance:");
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFormattedTextField birthText = new JFormattedTextField(dateFormatter);
        birthText.setBounds(150, 100, 165, 25);
        panel.add(birthLabel);
        birthLabel.setBounds(10, 100, 140, 25);

        panel.add(birthText);

        JLabel metierLabel = new JLabel("Métier:");
        metierLabel.setBounds(10, 140, 120, 25);
        panel.add(metierLabel);

        JTextField metierText = new JTextField(20);
        metierText.setBounds(150, 140, 165, 25);
        panel.add(metierText);

        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        userLabel.setBounds(10, 180, 120, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 180, 165, 25);
        panel.add(userText);

        // Création du label et du champ pour l'e-mail
        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setBounds(10, 220, 120, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(150, 220, 165, 25);
        panel.add(emailText);

        // Création du label et du champ pour le mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(10, 260, 120, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 260, 165, 25);
        panel.add(passwordText);

        JButton registerButton = new JButton("S'inscrire");
        registerButton.setBounds(10, 300, 120, 25);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomText.getText();
                String prenom = prenomText.getText();
                String dateNaissance = birthText.getText();
                String metier = metierText.getText();
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // Vérifier si tous les champs sont remplis
                if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || metier.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        inscriptionController.inscriptionPersonnel(nom, prenom, dateNaissance, metier, username, password);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(registerButton);
    }


}
