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
        JFrame frame = new JFrame("Page d'Administration");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Bonjour MR. " + person.getNom());
        panel.add(welcomeLabel, gbc);

        frame.add(panel);


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
     addButtonPair("Ajouter des Etudiant", "Afficher les Etudiants", panel, gbc, frame,
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

     // Créer un bâtiment
     addButtonPair("Créer un Bâtiment", "afficher les Batiments", panel, gbc, frame, 
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                	 SwingUtilities.invokeLater(() -> {
                         InterfaceAddBatiment interfaceAddBatiment = new InterfaceAddBatiment();
                         interfaceAddBatiment.setVisible(true);
                     });                 }
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
        	 SwingUtilities.invokeLater(() -> {
                 InterfaceAddSalle interfaceAddSalle = new InterfaceAddSalle();
                 interfaceAddSalle.setVisible(true);
             });
         }
     },
         new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 List<Salle> salles = DatabaseManager.getSalle();

        	 SwingUtilities.invokeLater(() -> {
             	ShowSallePage showAndModifySallePage = new ShowSallePage(salles);
                 showAndModifySallePage.setVisible(true);
             });
         }
     });
     
     addButtonPair("Créer une matiére", "Afficher les matières", panel, gbc, frame, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        	 SwingUtilities.invokeLater(() -> {
                 InterfaceAddMatiere interfaceAddMatiere = new InterfaceAddMatiere();
                 interfaceAddMatiere.setVisible(true);
             });
         }
     },
         new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 List<Matiere> matieres = DatabaseManager.getMatiere();

             SwingUtilities.invokeLater(() -> {
             	ShowMatierePage showMatierePage = new ShowMatierePage(matieres);
                 showMatierePage.setVisible(true);
             });
         }
     });

     gbc.gridy++;

     panel.add(Box.createHorizontalStrut(20), gbc);

     gbc.gridy++;

     addButton("Gérer les cours", panel, gbc, frame, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             List<Cours> cours = DatabaseManager.getCours();
             SwingUtilities.invokeLater(() -> {
                 ShowPageCours showPageCours = new ShowPageCours(cours);
                 showPageCours.setVisible(true);
             });
         }
     });
     
     panel.add(Box.createHorizontalStrut(20), gbc);

     gbc.gridy++;
     
     addButton("Ajouter un Admin", panel, gbc, frame, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             InscriptionInterface inscriptionInterface = new InscriptionInterface();
             inscriptionInterface.afficherInterface(); 
             
         }
     });
    

     gbc.gridy++;

     panel.add(Box.createHorizontalStrut(20), gbc);

     gbc.gridy++;

     JButton retourButton = new JButton("Déconnexion");
     retourButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             frame.dispose();
             new ConnectionInterface().afficherInterface();
         }
     });
     gbc.anchor = GridBagConstraints.CENTER;
     panel.add(retourButton, gbc);

     gbc.anchor = GridBagConstraints.WEST;

     gbc.gridy++;

     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
    }

    private void addButtonPair(String createLabel, String showLabel, JPanel panel, GridBagConstraints gbc,
            JFrame frame, ActionListener createAction, ActionListener showAction) {
    	gbc.gridy++;
	gbc.gridy++;
	
	JButton createButton = new JButton(createLabel);
	createButton.addActionListener(createAction);
	
	JButton showButton = new JButton(showLabel);
	showButton.addActionListener(showAction);
	
	panel.add(createButton, gbc);
	panel.add(showButton, gbc);
	
	gbc.gridy++;
	}

    private void addButton(String label, JPanel panel, GridBagConstraints gbc, JFrame frame,
                           ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        panel.add(button, gbc);
    }
}
