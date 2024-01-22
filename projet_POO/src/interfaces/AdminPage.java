package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.*;

import database.DatabaseManager;

public class AdminPage {
    public void afficherInterface(Personnel person) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Page d'Administration");
        frame.setSize(700, 600); // Ajustez la taille selon vos besoins
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
        JButton ajouterProfesseurButton = new JButton("Ajouter un Enseignant");
        ajouterProfesseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceAddProfesseurs interfaceAddProfesseurs = new interfaceAddProfesseurs();
                interfaceAddProfesseurs.afficherInterface(person);
                frame.dispose();
            }
        });
        panel.add(ajouterProfesseurButton, gbc);

        JButton afficherProfesseurButton = new JButton("Afficher les Enseignants");
        afficherProfesseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page d'affichage des enseignants
                List<Enseignant> enseignants = DatabaseManager.getEnseignants();
                List<Matiere> matieres = DatabaseManager.getMatiere();


                // Créer une instance de la page d'affichage des enseignants
                SwingUtilities.invokeLater(() -> {
                    ShowProfesseurPage showProfesseurPage = new ShowProfesseurPage(enseignants,matieres);
                    showProfesseurPage.setVisible(true);
                });
            }
        });
        panel.add(afficherProfesseurButton, gbc);

        JButton ajouterElevesButton = new JButton("Ajouter des Élèves");
        ajouterElevesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceAddEtudiant interfaceAddEtudiant = new interfaceAddEtudiant();
                interfaceAddEtudiant.afficherInterface(person);
                frame.dispose();
            }
        });
        panel.add(ajouterElevesButton, gbc);

        JButton afficherEleves = new JButton("Afficher les Élèves");
        afficherEleves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page d'affichage des étudiants
                List<Etudiant> etudiants = DatabaseManager.getStudents();

                // Créer une instance de la page d'affichage des étudiants
                SwingUtilities.invokeLater(() -> {
                    ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
                    showEtudiantPage.setVisible(true);
                });
            }
        });
        panel.add(afficherEleves, gbc);


        JButton creerFormationButton = new JButton("ajouter une formation");
        creerFormationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de formation
                InterfaceAddFormation addFormationPage = new InterfaceAddFormation();
                addFormationPage.setVisible(true);            }
        });

        panel.add(creerFormationButton, gbc);

			JButton creerPromotionButton = new JButton("Créer une Promotion");
			creerPromotionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de promotion
                JOptionPane.showMessageDialog(frame, "Ouvrir la page d'ajout de promotion");
            }
        });
        panel.add(creerPromotionButton, gbc);

        JButton creerBatimentsButton = new JButton("Créer un bâtiment");
        creerBatimentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de bâtiment
                JOptionPane.showMessageDialog(frame, "Ouvrir la page de création de bâtiment");
            }
        });
        panel.add(creerBatimentsButton, gbc);

        JButton creerSalleeButton = new JButton("Créer une Salle");
        creerSalleeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de salle
                JOptionPane.showMessageDialog(frame, "Ouvrir la page de création de salle");
            }
        });
        panel.add(creerSalleeButton, gbc);

        panel.add(creerPromotionButton, gbc);
        
        JButton gererCoursButton = new JButton("Gérer les cours");
        gererCoursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici la logique pour ouvrir la page de création de classe
                JOptionPane.showMessageDialog(frame, "Ouvrir la page de gestion de cours");
                
                List<Cours> cours = DatabaseManager.getCours();
                
                SwingUtilities.invokeLater(() -> {
                    ShowPageCours showPageCours = new ShowPageCours(cours);
                    showPageCours.setVisible(true);
                });
            }
           });
        panel.add(gererCoursButton, gbc);
        
        JButton retourButton = new JButton("Deconnection");
        retourButton.setBounds(10, 340, 120, 25);

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre d'administration et réafficher la fenêtre de connexion
                frame.dispose();
                new ConnectionInterface().afficherInterface();
            }
        });
        panel.add(retourButton, gbc);

        // Affichage de la fenêtre principale
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
