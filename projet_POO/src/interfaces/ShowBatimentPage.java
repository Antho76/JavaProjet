package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Batiment;
import database.DatabaseManager;

public class ShowBatimentPage extends JFrame {

    private List<Batiment> batiments;
    private DefaultListModel<String> listModel;
    private JList<String> batimentList;
    private JTextArea detailsTextArea;

    public ShowBatimentPage(List<Batiment> batiments) {
        this.batiments = batiments;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des bâtiments");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        for (Batiment batiment : batiments) {
            listModel.addElement(batiment.getNomBatiment());
        }

        batimentList = new JList<>(listModel);
        batimentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(batimentList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierBatiment();
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        batimentList.addListSelectionListener(e -> {
            int selectedIndex = batimentList.getSelectedIndex();
            if (selectedIndex != -1) {
                Batiment selectedBatiment = batiments.get(selectedIndex);
                String details = "ID du Bâtiment : " + selectedBatiment.getNumeroBatiment() + "\n" +
                        "Ville : " + selectedBatiment.getVille() + "\n" +
                        "Nom du Bâtiment : " + selectedBatiment.getNomBatiment();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText(""); 
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void modifierBatiment() {
        int selectedIndex = batimentList.getSelectedIndex();
        if (selectedIndex != -1) {
            Batiment selectedBatiment = batiments.get(selectedIndex);

            String nouvelleVille = JOptionPane.showInputDialog(
                    ShowBatimentPage.this,
                    "Veuillez entrer la nouvelle ville du bâtiment :",
                    "Modifier le bâtiment",
                    JOptionPane.PLAIN_MESSAGE);

            String nouveauNomBatiment = JOptionPane.showInputDialog(
                    ShowBatimentPage.this,
                    "Veuillez entrer le nouveau nom du bâtiment (laissez vide pour conserver l'ancien) :",
                    "Modifier le bâtiment",
                    JOptionPane.PLAIN_MESSAGE);

            if (nouvelleVille != null && !nouvelleVille.isEmpty()) {
                selectedBatiment.setVille(nouvelleVille);
            }

            if (nouveauNomBatiment != null && !nouveauNomBatiment.isEmpty()) {
                selectedBatiment.setNomBatiment(nouveauNomBatiment);
                DatabaseManager.updateBatiment(selectedBatiment);
            }

            listModel.setElementAt(selectedBatiment.getNomBatiment(), selectedIndex);
        } else {
            JOptionPane.showMessageDialog(ShowBatimentPage.this,
                    "Veuillez sélectionner un bâtiment avant de modifier.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
        dispose();
        List<Batiment> batiments = DatabaseManager.getBatiments();

        SwingUtilities.invokeLater(() -> {
            ShowBatimentPage showBatimentPage = new ShowBatimentPage(batiments);
            showBatimentPage.setVisible(true);
        });
    }

    public static void main(String[] args) {
        List<Batiment> batiments = DatabaseManager.getBatiments();

        SwingUtilities.invokeLater(() -> {
            ShowBatimentPage showBatimentPage = new ShowBatimentPage(batiments);
            showBatimentPage.setVisible(true);
        });
    }
}
