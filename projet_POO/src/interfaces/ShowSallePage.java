package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Salle;
import database.DatabaseManager;

public class ShowSallePage extends JFrame {

    private List<Salle> salles;

    public ShowSallePage(List<Salle> salles) {
        this.salles = salles;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des salles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Salle salle : salles) {
            listModel.addElement("Salle " + salle.getNumeroSalle());
        }

        JList<String> salleList = new JList<>(listModel);
        salleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(salleList);
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
                int selectedIndex = salleList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Salle selectedSalle = salles.get(selectedIndex);
                    JOptionPane.showMessageDialog(ShowSallePage.this,
                            "Modifier la salle : " + selectedSalle.getNumeroSalle(),
                            "Modifier la salle", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ShowSallePage.this,
                            "Veuillez sélectionner une salle avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        salleList.addListSelectionListener(e -> {
            int selectedIndex = salleList.getSelectedIndex();
            if (selectedIndex != -1) {
                Salle selectedSalle = salles.get(selectedIndex);
                String details = "Numéro de la Salle : " + selectedSalle.getNumeroSalle() + "\n" +
                        "Nombre de Places : " + selectedSalle.getNbPlaces() + "\n" +
                        "Nombre d'Étudiants : " + selectedSalle.getNbEtudiants() + "\n" +
                        "Équipement Informatique : " + (selectedSalle.getEquipInfo() ? "Oui" : "Non");

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
        List<Salle> salles = DatabaseManager.getSalle();

        SwingUtilities.invokeLater(() -> {
            ShowSallePage showSallePage = new ShowSallePage(salles);
            showSallePage.setVisible(true);
        });
    }
}
