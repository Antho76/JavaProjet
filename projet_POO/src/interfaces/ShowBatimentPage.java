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

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Batiment batiment : batiments) {
            listModel.addElement(batiment.getNomBatiment());
        }

        JList<String> batimentList = new JList<>(listModel);
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
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = batimentList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Batiment selectedBatiment = batiments.get(selectedIndex);
                    JOptionPane.showMessageDialog(ShowBatimentPage.this,
                            "Modifier le bâtiment : " + selectedBatiment.getNomBatiment(),
                            "Modifier le bâtiment", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ShowBatimentPage.this,
                            "Veuillez sélectionner un bâtiment avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        batimentList.addListSelectionListener(e -> {
            int selectedIndex = batimentList.getSelectedIndex();
            if (selectedIndex != -1) {
                Batiment selectedBatiment = batiments.get(selectedIndex);
                String details = "ID du Bâtiment : " + selectedBatiment.getNumeroBatiment() + "\n" +
                        "Ville : " + selectedBatiment.getVille() + "\n" +
                        "Nom du Bâtiment : " + selectedBatiment.getNomBatiment() + "\n" +
                        "Nombre de Salles : " + selectedBatiment.getNbSalles() + "\n" +
                        "Liste des Salles : " + selectedBatiment.getTabSalles();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText(""); // Effacer les détails si rien n'est sélectionné
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Vous pouvez placer ici le code pour tester votre page
        List<Batiment> batiments = DatabaseManager.getBatiments();

        SwingUtilities.invokeLater(() -> {
            ShowBatimentPage showBatimentPage = new ShowBatimentPage(batiments);
            showBatimentPage.setVisible(true);
        });
    }
}
