package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.*;
import database.DatabaseManager;

public class EnseignantPage {
    public void afficherInterface(Enseignant enseignant) {

        JFrame frame = new JFrame("Page d'Enseignant");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panel pour ajouter les composants
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajout du message d'accueil au milieu de la fenêtre
        JLabel welcomeLabel = new JLabel("Bonjour MR. " + enseignant.getNom());
        panel.add(welcomeLabel, gbc);

        frame.add(panel);


     addButton("Ajouter un avertissement", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     InterfaceAddAvertissement interfaceAddAvertissement = new InterfaceAddAvertissement();
                     interfaceAddAvertissement.ajouterAvertissement(enseignant);
                 }
             });
     
     addButton("Afficher les avertissements", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                	 List<Avertissement> listAvertissement = DatabaseManager.getAvertissement();
                	 ShowAvertissementPage showAvertissementPage = new ShowAvertissementPage(enseignant,listAvertissement);
                	 showAvertissementPage.setVisible(true);
                 }
             });
     
     addButton("Ajouter des notes", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                	 InterfaceAddNotes interfaceAddNotes = new InterfaceAddNotes();
                	 interfaceAddNotes.ajouterNote(enseignant);
                 }
             });
     

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


    private void addButton(String label, JPanel panel, GridBagConstraints gbc, JFrame frame,
                           ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        panel.add(button, gbc);
    }
}
