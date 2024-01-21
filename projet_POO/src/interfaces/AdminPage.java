package interfaces;
import classes.*;
import interfaces.ShowEtudiantPage;
import javax.swing.*;
import classes.Personnel;
import database.DatabaseManager;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage {
    public void afficherInterface(Personnel person) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Page d'Administration");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panel pour ajouter les composants
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajout du message d'accueil au milieu de la fenêtre
        JLabel welcomeLabel = new JLabel("Bonjour MR. " + person.getNom());
        panel.add(welcomeLabel, gbc);

        frame.add(panel);

        // Ajout des boutons
        JButton ajouterProfesseurButton = new JButton("Ajouter un Professeur");
        ajouterProfesseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page d'ajout de professeur
                JOptionPane.showMessageDialog(frame, "Ouvrir la page d'ajout de professeur");
            }
        });
        panel.add(ajouterProfesseurButton, gbc);

        JButton ajouterElevesButton = new JButton("Ajouter des Élèves");
        ajouterElevesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page d'ajout d'élèves
                JOptionPane.showMessageDialog(frame, "Ouvrir la page d'ajout d'élèves");
            }
        });
        panel.add(ajouterElevesButton, gbc);
        
        JButton afficherEleves = new JButton("Afficher les élèves");
        afficherEleves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page d'affichage des élèves
                List<Etudiant> etudiants = DatabaseManager.getStudents();

                // Créer une instance de la page d'affichage des étudiants
                SwingUtilities.invokeLater(() -> {
                    ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
                    showEtudiantPage.setVisible(true);
                });
            }
        });
        panel.add(afficherEleves, gbc);

        JButton creerClasseButton = new JButton("Créer une Classe");
        creerClasseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de classe
                JOptionPane.showMessageDialog(frame, "Ouvrir la page de création de classe");
            }
        });
        panel.add(creerClasseButton, gbc);
        
        JButton retourButton = new JButton("Deconnection");
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
        
        // Affichage de la fenêtre principale
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
