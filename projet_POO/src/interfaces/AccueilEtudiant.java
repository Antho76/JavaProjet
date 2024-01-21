package interfaces;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class AccueilEtudiant {

    public void afficherInterface() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Emploi du Temps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        
        JComboBox<String> comboBoxSemaines = new JComboBox<>(new String[]{"Semaine 1", "Semaine 2", "Semaine 3", "Semaine 4", "Semaine 5"});
        frame.add(comboBoxSemaines, BorderLayout.NORTH);
        
        String [][] semaine = new String[][] { 
        	{"Mathématiques","","","","","","","","",""},
        	{"","","","","","","","","",""},
        	{"","","","","","","","","",""},
        	{"","","","","","Pedestre","","","",""},
        	{"","","","","","","","","",""},
        };
        // Colonnes (jours de la semaine)
        String[] columns = new String[] {"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

        // Données (plages horaires)
        String[][] data = new String[][] {
            {"08:00-09:00", semaine[0][0],semaine[1][0], semaine[2][0], semaine[3][0], semaine[4][0]},
            {"09:00-10:00", semaine[0][1],semaine[1][1], semaine[2][1], semaine[3][1], semaine[4][1]},
            {"10:00-11:00", semaine[0][2], semaine[1][2], semaine[2][2], semaine[3][2],semaine[4][2]},
            {"11:00-12:00", semaine[0][3], semaine[1][3], semaine[2][3], semaine[3][3],semaine[4][3]},
            {"12:00-13:00", semaine[0][4], semaine[1][4], semaine[2][4], semaine[3][4],semaine[4][4]},
            {"13:00-14:00", semaine[0][5], semaine[1][5], semaine[2][5], semaine[3][5],semaine[4][5]},
            {"14:00-15:00", semaine[0][6], semaine[1][6], semaine[2][6], semaine[3][6],semaine[4][6]},
            {"15:00-16:00", semaine[0][7], semaine[1][7], semaine[2][7], semaine[3][7],semaine[4][7]},
            {"16:00-17:00", semaine[0][8], semaine[1][8], semaine[2][8], semaine[3][8],semaine[4][8]},
            {"17:00-18:00", semaine[0][9], semaine[1][9], semaine[2][9], semaine[3][9],semaine[4][9]},
            // Ajouter d'autres plages horaires ici
        };

        // Création du modèle de tableau et de la JTable
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        

        // Ajout de la JTable dans un JScrollPane
        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane,BorderLayout.CENTER);

        // Afficher la fenêtre
        frame.setVisible(true);
    }
}