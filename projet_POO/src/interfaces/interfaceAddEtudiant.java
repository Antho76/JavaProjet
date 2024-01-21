package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

import classes.Personnel;
import controller.InscriptionController;

public class interfaceAddEtudiant {
    private JFrame mainFrame;
    private InscriptionController inscriptionController = new InscriptionController();
    private Personnel person; // Ajout de la variable pour stocker la référence à la personne connectée

    public void afficherInterface(Personnel person) {
        this.person = person; // Stocker la référence à la personne connectée
        mainFrame = new JFrame("Page d'Inscription");
        mainFrame.setSize(400, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        mainFrame.add(panel);
        placeComponents(panel);

        JButton retourButton = new JButton("Retour");
        retourButton.setBounds(10, 340, 120, 25);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new AdminPage().afficherInterface(person);
            }
        });
        panel.add(retourButton);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
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

        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        userLabel.setBounds(10, 140, 120, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 140, 165, 25);
        panel.add(userText);

        JLabel formationLabel = new JLabel("id Formation:");
        formationLabel.setBounds(10, 180, 120, 25);
        panel.add(formationLabel);

        JTextField formationText = new JTextField(20);
        formationText.setBounds(150, 180, 165, 25);
        panel.add(formationText);

        JLabel promotionLabel = new JLabel("id Promotion:");
        promotionLabel.setBounds(10, 220, 120, 25);
        panel.add(promotionLabel);

        JTextField promotionText = new JTextField(20);
        promotionText.setBounds(150, 220, 165, 25);
        panel.add(promotionText);

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
                String formation = formationText.getText();
                String promotion = promotionText.getText();
                String login = userText.getText();
                String password = new String(passwordText.getPassword());

                if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || login.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int promotionInt = 0;
                        int formationInt = 0;
                        try {
                            promotionInt = Integer.parseInt(promotionText.getText());
                            formationInt = Integer.parseInt(formationText.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre entier pour l'id de promotion.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        inscriptionController.inscriptionEtudiant(nom, prenom, promotionInt, dateNaissance, formationInt, login, password);
                        afficherMessageUtilisateurInscrit();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(registerButton);
    }

    private void afficherMessageUtilisateurInscrit() {
        mainFrame.setVisible(false);
        JFrame messageFrame = new JFrame("Utilisateur inscrit");
        messageFrame.setSize(200, 100);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("Utilisateur inscrit");
        messagePanel.add(messageLabel);
        messageFrame.add(messagePanel);
        messageFrame.setLocationRelativeTo(null);

        messageFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                new AdminPage().afficherInterface(person);
            }
        });

        messageFrame.setVisible(true);
    }
}