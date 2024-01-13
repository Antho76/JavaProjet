package interfaces;
import javax.swing.*;
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

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

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
        JButton inscriptionButton = new JButton("Inscription");
        loginPanel.add(userLabel, gbcLogin);
        loginPanel.add(userField, gbcLogin);
        loginPanel.add(passLabel, gbcLogin);
        loginPanel.add(passField, gbcLogin);
        loginPanel.add(loginButton, gbcLogin);
        loginPanel.add(inscriptionButton, gbcLogin);

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

        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onInscriptionButtonClick();
            }
        });
        

        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.setVisible(true);
    }

    private void onLoginButtonClick() {
        String username = userField.getText();
        char[] passwordChars = passField.getPassword();
        String password = new String(passwordChars);

        ConnexionController connexionController = new ConnexionController();

        if (radioButtonEtudiant.isSelected()) {
            if (connexionController.checkConnexionEtudiant(username, password)) {
                JOptionPane.showMessageDialog(frame, "Connexion réussie");
                // Ajoutez ici le code pour l'interface de l'étudiant si nécessaire
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        } else if (radioButtonEnseignant.isSelected()) {
            if (connexionController.checkConnexionEnseignant(username, password)) {
                JOptionPane.showMessageDialog(frame, "Connexion réussie");
                // Ajoutez ici le code pour l'interface de l'enseignant si nécessaire
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        } else if (radioButtonPersonnel.isSelected()) {

            if (connexionController.checkConnexionPersonnel(username, password)) {
                JOptionPane.showMessageDialog(frame, "Connexion réussie");
                // Ajoutez ici le code pour l'interface du personnel si nécessaire
                frame.dispose();
            } else {

                JOptionPane.showMessageDialog(frame, "Login ou mot de passe incorrect");
            }
        }
    }

    private void onInscriptionButtonClick() {
        frame.dispose(); // Ferme la fenêtre de connexion
        InscriptionInterface inscriptionInterface = new InscriptionInterface();
        inscriptionInterface.afficherInterface(); // Passe la référence de la fenêtre de connexion
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
    

