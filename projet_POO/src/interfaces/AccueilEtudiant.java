package interfaces;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccueilEtudiant {

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private JLabel labelSemaine;
    private DefaultTableModel model;
    private String[][] semaine;
    private String dateDebutSemaine;
    private String dateFinSemaine;

    public void afficherInterface() {
        JFrame frame = new JFrame("Emploi du Temps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);

        // Initialisation de la matrice semaine
        initSemaine();

        // Panneau pour les boutons et le label de la semaine
        JPanel panelSemaine = new JPanel();
        JButton btnSemainePrecedente = new JButton("<");
        JButton btnSemaineSuivante = new JButton(">");
        labelSemaine = new JLabel();
        updateLabelSemaine();

        panelSemaine.add(btnSemainePrecedente);
        panelSemaine.add(labelSemaine);
        panelSemaine.add(btnSemaineSuivante);
        frame.add(panelSemaine, BorderLayout.NORTH);

        // Configuration des boutons
        btnSemaineSuivante.addActionListener(e -> changeSemaine(1));
        btnSemainePrecedente.addActionListener(e -> changeSemaine(-1));

        // Colonnes (jours de la semaine)
        String[] columns = new String[]{"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        model = new DefaultTableModel(generateData(), columns);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private void initSemaine() {
        semaine = new String[][]{
                {"Mathématiques", "", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "Pedestre", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", "", ""},
        };
        // Mettre à jour en fonction des données réelles
    }

    private void changeSemaine(int amount) {
        calendar.add(Calendar.WEEK_OF_YEAR, amount);
        updateLabelSemaine();
        /*updateTable();*/
    }

    private void updateLabelSemaine() {
        Calendar startOfWeek = (Calendar) calendar.clone();
        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Calendar endOfWeek = (Calendar) calendar.clone();
        endOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        
        dateDebutSemaine = dateFormat.format(startOfWeek.getTime());
        dateFinSemaine = dateFormat.format(endOfWeek.getTime());
        
        labelSemaine.setText("Semaine du " + dateFormat.format(startOfWeek.getTime()) + " au " + dateFormat.format(endOfWeek.getTime()));
    }

    /*private void updateTable() {
        // Mettre à jour la matrice semaine en fonction de la semaine sélectionnée
        // Exemple : initSemaine(); // Réinitialiser ou charger les données pour la semaine
        model.setDataVector(generateData(), model.getColumnIdentifiers());
    }*/

    private String[][] generateData() {
        String[][] data = new String[10][6];
        String[] heures = new String[]{"08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00"};
        for (int i = 0; i < heures.length; i++) {
            data[i][0] = heures[i];
            for (int j = 1; j <= 5; j++) {
                data[i][j] = semaine[j - 1][i];
            }
        }
        return data;
    }
}
