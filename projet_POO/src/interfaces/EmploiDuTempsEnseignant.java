package interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import controller.EmploiDuTempsController;
import database.DatabaseManager;

import classes.Enseignant;
import classes.Evaluation;
import classes.Avertissement;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EmploiDuTempsEnseignant {

    private EmploiDuTempsController emploiDuTempsController = new EmploiDuTempsController();
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private JLabel labelSemaine;
    private DefaultTableModel model;
    private String[][] semaine;
    private LocalDate dateDebutSemaine;
    private LocalDate dateFinSemaine;
    private Enseignant connectedEnseignant;

    public void afficherInterface(Enseignant connectedEnseignant) {
        JFrame frame = new JFrame("Emploi du Temps Enseignant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1400, 1000);

        initSemaine();
        JPanel panelSemaine = new JPanel();
        JButton btnSemainePrecedente = new JButton("<");
        JButton btnSemaineSuivante = new JButton(">");
        labelSemaine = new JLabel();
        updateLabelSemaine(connectedEnseignant);

        panelSemaine.add(btnSemainePrecedente);
        panelSemaine.add(labelSemaine);
        panelSemaine.add(btnSemaineSuivante);
        frame.add(panelSemaine, BorderLayout.NORTH);

        btnSemaineSuivante.addActionListener(e -> changeSemaine(1,connectedEnseignant));
        btnSemainePrecedente.addActionListener(e -> changeSemaine(-1,connectedEnseignant));

        String[] columns = new String[]{"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        model = new DefaultTableModel(generateData(connectedEnseignant.getId()), columns);
        JTable table = new JTable(model);
        table.setRowHeight(70);
        
        JPanel panelBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton retourButton = new JButton("Retour");
        
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // Ajoutez ici la logique pour retourner à la page précédente
            }
        });
        
        panelBouton.add(retourButton);
        frame.add(panelBouton, BorderLayout.SOUTH);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.TOP);
                    if (value != null) {
                        label.setText("<html><div style='text-align:center;'>" + value.toString().replaceAll("\n", "<br>") + "</div></html>");
                    }
                }
                return c;
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Ajouter ici d'autres boutons si nécessaire
        frame.setLocationRelativeTo(null);

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
    }

    private void changeSemaine(int amount,Enseignant connectedEnseignant) {
        calendar.add(Calendar.WEEK_OF_YEAR, amount);
        updateLabelSemaine(connectedEnseignant);
    }

    private void updateLabelSemaine(Enseignant connectedEnseignant) {
        LocalDate current = LocalDate.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        LocalDate startOfWeek = current.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        dateDebutSemaine = startOfWeek;
        dateFinSemaine = endOfWeek;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        labelSemaine.setText("Semaine du " + startOfWeek.format(formatter) + " au " + endOfWeek.format(formatter));
        updateTable(connectedEnseignant);
    }

    public LocalDate getStartDate() {
        return dateDebutSemaine;
    }

    public LocalDate getEndDate() {
        return dateFinSemaine;
    }

    private void updateTable(Enseignant connectedEnseignant) {
    	int idEnseignant=connectedEnseignant.getId();
        if (model == null) {
            return;
        }
        String[][] dataFromController = emploiDuTempsController.getCoursPourSemaineInterfaceEnseignant(dateDebutSemaine, dateFinSemaine, idEnseignant);
        String[][] updatedData = mergeDataWithHours(dataFromController);
        String[] columns = new String[]{"Heure", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
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

    private String[][] generateData(int idEnseignant) {
        String[][] newData = emploiDuTempsController.getCoursPourSemaineInterface(dateDebutSemaine, dateFinSemaine, idEnseignant);
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
