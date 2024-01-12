package interfaces;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilEtudiantInterface {

    public void afficherInterface() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Emploi du Temps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout()); 

        // Colonnes (jours de la semaine)
        String[] columns = new String[] {"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

        // Données (plages horaires)
        String[][] dataSemaine1 = new String[][] {
            {"08:00-09:00", "Mathématiques", "Legifrance", "", "", ""},
            {"09:00-10:00", "", "", "", "", ""},
            {"10:00-11:00", "", "", "", "", ""},
            {"11:00-12:00", "", "", "", "", ""},
            {"12:00-13:00", "", "", "", "", ""},
            {"13:00-14:00", "", "", "", "", ""},
            {"14:00-15:00", "", "", "", "", ""},
            {"15:00-16:00", "", "", "", "", ""},
            {"16:00-17:00", "", "", "", "", ""},
            {"17:00-18:00", "", "", "", "", ""},
            // Ajouter d'autres plages horaires ici
        };
        
        String[][] dataSemaine2 = new String[][] {
            {"08:00-09:00", "Mathématiques", "Legifrance", "", "", ""},
            {"09:00-10:00", "", "", "", "", ""},
            {"10:00-11:00", "", "", "", "", ""},
            {"11:00-12:00", "", "", "", "", ""},
            {"12:00-13:00", "", "", "", "", ""},
            {"13:00-14:00", "", "", "", "", ""},
            {"14:00-15:00", "", "", "", "", ""},
            {"15:00-16:00", "", "", "", "", ""},
            {"16:00-17:00", "", "", "", "", ""},
            {"17:00-18:00", "", "", "", "", ""},
            // Ajouter d'autres plages horaires ici
        };

        // Création du modèle de tableau et de la JTable
        DefaultTableModel model = new DefaultTableModel(dataSemaine1, columns);
        JTable table = new JTable(model);
        
        JButton btnSemainePrecedente = new JButton("Semaine précédente");
        JButton btnSemaineSuivante = new JButton("Semaine suivante");
        
        btnSemainePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code pour afficher la semaine précédente
                model.setDataVector(dataSemaine1, columns); // Exemple pour revenir à la semaine 1
            }
        });

        btnSemaineSuivante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code pour afficher la semaine suivante
                model.setDataVector(dataSemaine2, columns); // Exemple pour aller à la semaine 2
            }
        });

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnSemainePrecedente);
        panelBoutons.add(btnSemaineSuivante);
        frame.add(panelBoutons, BorderLayout.NORTH);
        // Ajout de la JTable dans un JScrollPane
        JLabel welcomeLabel = new JLabel("Emploi du temps");
        frame.add(welcomeLabel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER); 

        // Afficher la fenêtre
        frame.setVisible(true);
    }
}