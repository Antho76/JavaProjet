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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

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
     
     addButton("Afficher les évaluations", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                	 List<Evaluation> listEvaluations = DatabaseManager.getEvaluations();
                	 ShowEvaluationsPage showEvaluationsPage = new ShowEvaluationsPage(enseignant,listEvaluations);
                	 showEvaluationsPage.setVisible(true);
                 }
             });

     addButton("Voir l'emploi du temps", panel, gbc, frame,
             new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                	 EmploiDuTempsEnseignant edtenseignant = new EmploiDuTempsEnseignant();
 	                 edtenseignant.afficherInterface(enseignant);

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
     gbc.anchor = GridBagConstraints.CENTER;
     panel.add(retourButton, gbc);

     gbc.anchor = GridBagConstraints.WEST;

     gbc.gridy++;

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
