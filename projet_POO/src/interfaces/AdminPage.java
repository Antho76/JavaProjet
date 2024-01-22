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
        frame.setSize(700, 600);
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

     // Ajout des boutons "Créer" et "Afficher"
     // Enseignants
     addButtonPair("Ajouter un Enseignant", "Afficher les Enseignants", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     interfaceAddProfesseurs interfaceAddProfesseurs = new interfaceAddProfesseurs();
                     interfaceAddProfesseurs.afficherInterface(person);
                     frame.dispose();
                 }
             },
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     List<Enseignant> enseignants = DatabaseManager.getEnseignants();
                     List<Matiere> matieres = DatabaseManager.getMatiere();
                     SwingUtilities.invokeLater(() -> {
                         ShowProfesseurPage showProfesseurPage = new ShowProfesseurPage(enseignants, matieres);
                         showProfesseurPage.setVisible(true);
                     });
                 }
             });

     // Élèves
     addButtonPair("Ajouter des Élèves", "Afficher les Élèves", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     interfaceAddEtudiant interfaceAddEtudiant = new interfaceAddEtudiant();
                     interfaceAddEtudiant.afficherInterface(person);
                     frame.dispose();
                 }
             },
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     List<Etudiant> etudiants = DatabaseManager.getStudents();
                     SwingUtilities.invokeLater(() -> {
                         ShowEtudiantPage showEtudiantPage = new ShowEtudiantPage(etudiants);
                         showEtudiantPage.setVisible(true);
                     });
                 }
             });

     // Formations
     addButtonPair("Ajouter une Formation", "Afficher les Formations", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     InterfaceAddFormation addFormationPage = new InterfaceAddFormation();
                     addFormationPage.setVisible(true);
                 }
             },
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     List<Formation> formations = DatabaseManager.getFormations();
                     SwingUtilities.invokeLater(() -> {
                         ShowFormationPage showFormationPage = new ShowFormationPage(formations);
                         showFormationPage.setVisible(true);
                     });
                 }
             });

     // Promotions
     addButtonPair("Créer une Promotion", "Afficher les Promotions", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     InterfaceAddPromotion addPromotionPage = new InterfaceAddPromotion();
                     addPromotionPage.setVisible(true);
                 }
             },
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     List<Promotion> promotions = DatabaseManager.getPromotions();
                     List<Formation> formations = DatabaseManager.getFormations();

                     SwingUtilities.invokeLater(() -> {
                         ShowPromotionPage showPromotionPage = new ShowPromotionPage(promotions, formations);
                         showPromotionPage.setVisible(true);
                     });
                 }
             });

     // Autres boutons
     // Créer un bâtiment
     addButtonPair("Créer un Bâtiment", "afficher les Batiments", panel, gbc, frame, 
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     JOptionPane.showMessageDialog(frame, "Ouvrir la page de création de bâtiment");
                 }
             },
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                 List<Batiment> batiments = DatabaseManager.getBatiments();
                 SwingUtilities.invokeLater(() -> {
                     ShowBatimentPage showBatimentPage = new ShowBatimentPage(batiments);
                     showBatimentPage.setVisible(true);
                         });
                     }
             });


     // Créer une Salle
     addButtonPair("Créer une Salle", "Afficher les salles", panel, gbc, frame, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             JOptionPane.showMessageDialog(frame, "Ouvrir la page de création de salle");
         }
     },
         new ActionListener() {
         List<Salle> salles = DatabaseManager.getSalle();
         public void actionPerformed(ActionEvent e) {
             SwingUtilities.invokeLater(() -> {
                 ShowSallePage showSallePage = new ShowSallePage(salles);
                 showSallePage.setVisible(true);
             });
         }
     });

     // Saut de ligne
     gbc.gridy++;

     // Ajout de deux boutons vides pour séparer les deux colonnes
     panel.add(Box.createHorizontalStrut(20), gbc);

     // Saut de ligne
     gbc.gridy++;

     // Bouton "Gérer les cours"
     addButton("Gérer les cours", panel, gbc, frame, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             List<Cours> cours = DatabaseManager.getCours();
             SwingUtilities.invokeLater(() -> {
                 ShowPageCours showPageCours = new ShowPageCours(cours);
                 showPageCours.setVisible(true);
             });
         }
     });

     // Saut de ligne
     gbc.gridy++;

     // Ajout de deux boutons vides pour séparer les deux colonnes
     panel.add(Box.createHorizontalStrut(20), gbc);

     // Saut de ligne
     gbc.gridy++;

     // Déconnexion
     JButton retourButton = new JButton("Déconnexion");
     retourButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             frame.dispose();
             new ConnectionInterface().afficherInterface();
         }
     });
     // Centrer le bouton "Déconnexion" en changeant l'ancrage uniquement pour ce composant
     gbc.anchor = GridBagConstraints.CENTER;
     panel.add(retourButton, gbc);

     // Réinitialiser l'ancrage pour les futurs composants
     gbc.anchor = GridBagConstraints.WEST;

     // Saut de ligne
     gbc.gridy++;

     // Affichage de la fenêtre principale
     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
    }

    private void addButtonPair(String createLabel, String showLabel, JPanel panel, GridBagConstraints gbc,
            JFrame frame, ActionListener createAction, ActionListener showAction) {
	// Ajout du message d'accueil au milieu de la fenêtre
    	gbc.gridy++;
	// Ajouter une ligne vide
	gbc.gridy++;
	
	// Bouton "Créer"
	JButton createButton = new JButton(createLabel);
	createButton.addActionListener(createAction);
	
	// Bouton "Afficher"
	JButton showButton = new JButton(showLabel);
	showButton.addActionListener(showAction);
	
	// Ajouter les boutons dans la même colonne
	panel.add(createButton, gbc);
	panel.add(showButton, gbc);
	
	// Passer à une nouvelle ligne
	gbc.gridy++;
	}

    private void addButton(String label, JPanel panel, GridBagConstraints gbc, JFrame frame,
                           ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        panel.add(button, gbc);
    }
}
