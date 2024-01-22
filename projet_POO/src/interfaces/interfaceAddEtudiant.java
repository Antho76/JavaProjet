package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import java.util.ArrayList;
import java.util.List;
import classes.*;
import controller.InscriptionController;
import database.DatabaseManager;

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

        // Ajout de la liste des formations
        JLabel formationLabel = new JLabel("Formation :");
        formationLabel.setBounds(10, 180, 120, 25);
        panel.add(formationLabel);

        JComboBox<Formation> formationComboBox = new JComboBox<>();
        List<Formation> formations = DatabaseManager.getFormations();
        for (Formation formation : formations) {
            formationComboBox.addItem(formation);
        }
        formationComboBox.setBounds(150, 180, 165, 25);
        panel.add(formationComboBox);

        // Ajout de la liste des promotions
        JLabel promotionLabel = new JLabel("Promotion :");
        promotionLabel.setBounds(10, 220, 120, 25);
        panel.add(promotionLabel);

        JComboBox<Promotion> promotionComboBox = new JComboBox<>();
        List<Promotion> promotions = DatabaseManager.getPromotions();
        for (Promotion promotion : promotions) {
            promotionComboBox.addItem(promotion);
        }
        promotionComboBox.setBounds(150, 220, 165, 25);
        panel.add(promotionComboBox);

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
                String login = userText.getText();
                String password = new String(passwordText.getPassword());

                if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || login.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();
                        Promotion selectedPromotion = (Promotion) promotionComboBox.getSelectedItem();

                        int promotionInt = selectedPromotion.getId();
                        int formationInt = selectedFormation.getId_Formation();

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