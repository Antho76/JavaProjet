package interfaces;
import javax.swing.*;
import java.awt.*;
import controller.ConnexionController;
import interfaces.InscriptionInterface;

public class ConnectionInterface {
	InscriptionInterface inscriptionInterface = new InscriptionInterface();
    public void afficherInterface() {

        // Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.gridwidth = GridBagConstraints.REMAINDER;
        gbcLogin.fill = GridBagConstraints.HORIZONTAL;

        ButtonGroup group = new ButtonGroup();
        JRadioButton radioButtonEtudiant = new JRadioButton("Etudiant");
        JRadioButton radioButtonEnseignant = new JRadioButton("Enseignant");
        JRadioButton radioButtonPersonnel = new JRadioButton("Personnel");
        group.add(radioButtonEtudiant);
        group.add(radioButtonEnseignant);
        group.add(radioButtonPersonnel);
        
        JLabel welcomeLabel = new JLabel("Bienvenue sur l'environnement numérique de travail");
        loginPanel.add(welcomeLabel, gbcLogin);

        loginPanel.add(radioButtonEtudiant, gbcLogin);
        loginPanel.add(radioButtonEnseignant, gbcLogin);
        loginPanel.add(radioButtonPersonnel, gbcLogin);
        
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Connexion");
        JButton inscriptionButton = new JButton("Inscription");
        loginPanel.add(userLabel, gbcLogin);
        loginPanel.add(userField, gbcLogin);
        loginPanel.add(passLabel, gbcLogin);
        loginPanel.add(passField, gbcLogin);
        loginPanel.add(loginButton, gbcLogin);
        loginPanel.add(inscriptionButton,gbcLogin);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        // Center Panel for loginPanel and textArea
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

        // Creating the panel at bottom and adding components
        /*JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);*/

        /*ImagePanel imagePanel = new ImagePanel();

        // Panel pour les champs de login et l'image
        centerPanel.add(loginPanel, BorderLayout.NORTH);
        centerPanel.add(imagePanel, BorderLayout.CENTER);
         */
        
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            char[] passwordChars = passField.getPassword();

            // Convertir le tableau de caractères en une chaîne de caractères
            String password = new String(passwordChars);

            // Ajoutez ici la logique pour déterminer quel type d'utilisateur est sélectionné (étudiant, enseignant, personnel)
            // Utilisez les méthodes de ConnexionController en conséquence

            ConnexionController connexionController = new ConnexionController();

            if (radioButtonEtudiant.isSelected()) {
            	if (connexionController.checkConnexionEtudiant(username, password)) {
            		System.out.println("Etudiant connecté");
            	}
            	else {
            		System.out.println("Identifiants Etudiant incorrects");
            	}
            } else if (radioButtonEnseignant.isSelected()) {
                if (connexionController.checkConnexionEnseignant(username, password)) {
                	System.out.println("Enseignant connecté");
                }
                else {
            		System.out.println("Identifiants Enseignant incorrects");
            	}
            } else if (radioButtonPersonnel.isSelected()) {
                if (connexionController.checkConnexionPersonnel(username, password)) {
                	System.out.println("Personnel connecté");
                }
                else {
            		System.out.println("Identifiants Personnel incorrects");
            	}
            }
        });
        
        inscriptionButton.addActionListener(e -> {
        	inscriptionInterface.afficherInterface();
        });
        
        
        // Adding Components to the frame.
        //frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.setVisible(true);
    }
}