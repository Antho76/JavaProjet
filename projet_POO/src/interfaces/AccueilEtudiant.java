package interfaces;
import javax.swing.*;
import controller.EmploiDuTempsController;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AccueilEtudiant {

	private EmploiDuTempsController emploiDuTempsController = new EmploiDuTempsController();
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private JLabel labelSemaine;
    private DefaultTableModel model;
    private String[][] semaine;
    private LocalDate dateDebutSemaine;
    private LocalDate dateFinSemaine;

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
        // Convertir Calendar en LocalDate
        LocalDate current = LocalDate.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());

        // Définir le début et la fin de la semaine basés sur la date actuelle du calendrier
        LocalDate startOfWeek = current.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        dateDebutSemaine = startOfWeek;
        dateFinSemaine = endOfWeek;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        labelSemaine.setText("Semaine du " + startOfWeek.format(formatter) + " au " + endOfWeek.format(formatter));
        updateTable();
    }


    
    public LocalDate  getStartDate() {
    	return dateDebutSemaine;
    }
    
    public LocalDate getEndDate() {
    	return dateFinSemaine;
    }

    private void updateTable() {
    	if (model == null) {
            return;  // Ou initialiser le model ici si nécessaire
        }
    	String[][] dataFromController = emploiDuTempsController.getCoursPourSemaineInterface(dateDebutSemaine, dateFinSemaine);
        String[][] updatedData = mergeDataWithHours(dataFromController);

    	String[] columns = new String[]{"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

        // Mettez à jour le modèle de votre tableau avec les nouvelles données
        model.setDataVector(updatedData, columns);
    }
    
    private String[][] mergeDataWithHours(String[][] data) {
        String[][] mergedData = new String[10][6];
        String[] heures = new String[]{"08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00"};
        
        for (int i = 0; i < heures.length; i++) {
            mergedData[i][0] = heures[i];
            for (int j = 1; j <= 5; j++) {
                mergedData[i][j] = data[i][j - 1];
            }
        }
        return mergedData;
    }

    private String[][] generateData() {
        String[][] newData = emploiDuTempsController.getCoursPourSemaineInterface(dateDebutSemaine, dateFinSemaine);
        String[] heures = new String[]{"08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00"};
        
        String[][] data = new String[10][6];
        for (int i = 0; i < heures.length; i++) {
            data[i][0] = heures[i];
            for (int j = 1; j <= 5; j++) {
                data[i][j] = newData[i][j - 1];
            }
        }
        return data;
    }

}
