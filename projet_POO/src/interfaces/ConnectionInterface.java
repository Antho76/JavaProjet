package interfaces;
import javax.swing.*;

import classes.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.ConnexionController;

public class ConnectionInterface {
    private JFrame frame;
    private JTextField userField;
    private JPasswordField passField;
    private JRadioButton radioButtonEtudiant;
    private JRadioButton radioButtonEnseignant;
    private JRadioButton radioButtonPersonnel;

    public void afficherInterface() {
    	
        frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.gridwidth = GridBagConstraints.REMAINDER;
        gbcLogin.fill = GridBagConstraints.HORIZONTAL;

        ButtonGroup group = new ButtonGroup();
        radioButtonEtudiant = new JRadioButton("Etudiant");
        radioButtonEnseignant = new JRadioButton("Enseignant");
        radioButtonPersonnel = new JRadioButton("Personnel");
        group.add(radioButtonEtudiant);
        group.add(radioButtonEnseignant);
        group.add(radioButtonPersonnel);

        JLabel welcomeLabel = new JLabel("Bienvenue sur l'environnement numérique de travail");
        loginPanel.add(welcomeLabel, gbcLogin);

        loginPanel.add(radioButtonEtudiant, gbcLogin);
        loginPanel.add(radioButtonEnseignant, gbcLogin);
        loginPanel.add(radioButtonPersonnel, gbcLogin);

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField(20);
        JButton loginButton = new JButton("Connexion");



        loginPanel.add(userLabel, gbcLogin);
        loginPanel.add(userField, gbcLogin);
        loginPanel.add(passLabel, gbcLogin);
        loginPanel.add(passField, gbcLogin);
        loginPanel.add(loginButton, gbcLogin);

        JTextArea ta = new JTextArea();

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.weightx = 1;
        gbcCenter.weighty = 1;
        gbcCenter.fill = GridBagConstraints.BOTH;

        centerPanel.add(loginPanel, gbcCenter);

        gbcCenter.gridy++;
        centerPanel.add(new JScrollPane(ta), gbcCenter);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoginButtonClick();
            }
        });


        

        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void onLoginButtonClick() {
        String username = userField.getText();
        char[] passwordChars = passField.getPassword();
        String password = new String(passwordChars);

        ConnexionController connexionController = new ConnexionController();

        if (radioButtonEtudiant.isSelected()) {
            	Object[] result = connexionController.checkConnexionEtudiant(username, password);
            	boolean exist = (boolean) result[0];
            	if (exist) {
            		Etudiant etu = (Etudiant) result[1];
	                JOptionPane.showMessageDialog(frame, "Connexion réussie");
	                frame.dispose();
	                AccueilEtudiant accueiletu = new AccueilEtudiant();
	                accueiletu.afficherInterface(etu); // Ajouter plus tard le parametre de l'étudiant
	                // Ajoutez ici le code pour l'interface de l'étudiant si nécessaire
	                
            	}else {
                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        } else if (radioButtonEnseignant.isSelected()) {
        	Object[] result = connexionController.checkConnexionEnseignant(username, password);
        	boolean exist = (boolean) result[0];
            if (exist) {
            	Enseignant enseignant = (Enseignant) result[1];
                JOptionPane.showMessageDialog(frame, "Connexion réussie");
                frame.dispose();
                EnseignantPage enseignantPage = new EnseignantPage();
                enseignantPage.afficherInterface(enseignant);
            } else {
                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        } else if (radioButtonPersonnel.isSelected()) {
        	Object[] result = connexionController.checkConnexionPersonnel(username, password);
        	boolean exist = (boolean) result[0];
            if (exist) {
            	Personnel person = (Personnel) result[1];
                JOptionPane.showMessageDialog(frame, "Connexion réussie");
                frame.dispose(); // Fermer la fenêtre de connexion
                AdminPage adminpage = new AdminPage();
                adminpage.afficherInterface(person); // Passe la référence de la fenêtre de connexion                frame.dispose();
            } else {

                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectionInterface().afficherInterface();
            }
        });
    }

}
    

