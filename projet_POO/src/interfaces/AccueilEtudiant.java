package interfaces;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccueilEtudiant {

    public void afficherInterface() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Emploi du Temps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Colonnes (jours de la semaine)
        String[] columns = new String[] {"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

        // Données (plages horaires)
        String[][] data = new String[][] {
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
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);

        // Ajout de la JTable dans un JScrollPane
        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Afficher la fenêtre
        frame.setVisible(true);
    }
}