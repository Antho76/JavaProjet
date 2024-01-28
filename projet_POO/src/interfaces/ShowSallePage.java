package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Batiment;
import classes.Salle;
import database.DatabaseManager;

public class ShowSallePage extends JFrame {

    private List<Salle> salles;
    private JList<String> salleList;

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

        salleList = new JList<>(listModel);
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
                dispose();
            }
        });

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierSalle();
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
                        "Équipement Informatique : " + (selectedSalle.getEquipInfo() ? "Oui" : "Non");

                Batiment batiment = DatabaseManager.getBatimentById(selectedSalle.getIdBatiment());
                String nomBatiment = (batiment != null) ? batiment.getNomBatiment() : "N/A";

                details += "\nBâtiment : " + nomBatiment;

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void modifierSalle() {
        int selectedIndex = salleList.getSelectedIndex();
        if (selectedIndex != -1) {
            Salle selectedSalle = salles.get(selectedIndex);

            ModifierSalleDialog modifierDialog = new ModifierSalleDialog(this, selectedSalle);
            modifierDialog.setVisible(true);

            refreshSalleList();
        } else {
            JOptionPane.showMessageDialog(ShowSallePage.this,
                    "Veuillez sélectionner une salle avant de modifier.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshSalleList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Salle salle : salles) {
            listModel.addElement("Salle " + salle.getNumeroSalle());
        }
        salleList.setModel(listModel);
    }

    private class ModifierSalleDialog extends JDialog {

        private Salle salle;
        private JTextField nbPlacesField;
        private JCheckBox equipInfoCheckBox;
        private JComboBox<String> batimentComboBox;

        public ModifierSalleDialog(JFrame parent, Salle salle) {
            super(parent, "Modifier la salle " + salle.getNumeroSalle(), true);
            this.salle = salle;
            initUI();
        }

        private void initUI() {
            setSize(400, 200);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

            JLabel nbPlacesLabel = new JLabel("Nombre de places:");
            nbPlacesField = new JTextField(String.valueOf(salle.getNbPlaces()));

            JLabel equipInfoLabel = new JLabel("Equipée d'informations:");
            equipInfoCheckBox = new JCheckBox("", salle.getEquipInfo());

            JLabel batimentLabel = new JLabel("Bâtiment:");
            batimentComboBox = new JComboBox<>(getBatimentNames());
            Batiment currentBatiment = DatabaseManager.getBatimentById(salle.getIdBatiment());
            if (currentBatiment != null) {
                batimentComboBox.setSelectedItem(currentBatiment.getNomBatiment());
            }

            JButton modifierButton = new JButton("Modifier");
            modifierButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modifierSalle();
                    ModifierSalleDialog.this.dispose();
                }
            });

            mainPanel.add(nbPlacesLabel);
            mainPanel.add(nbPlacesField);
            mainPanel.add(equipInfoLabel);
            mainPanel.add(equipInfoCheckBox);
            mainPanel.add(batimentLabel);
            mainPanel.add(batimentComboBox);
            mainPanel.add(new JLabel());
            mainPanel.add(modifierButton);

            add(mainPanel);
        }

        private String[] getBatimentNames() {
            List<Batiment> batiments = DatabaseManager.getBatiments();
            String[] batimentNames = new String[batiments.size()];
            for (int i = 0; i < batiments.size(); i++) {
                batimentNames[i] = batiments.get(i).getNomBatiment();
            }
            return batimentNames;
        }

        private void modifierSalle() {
            try {
                int nbPlaces = Integer.parseInt(nbPlacesField.getText());
                boolean equipInfo = equipInfoCheckBox.isSelected();
                String selectedBatimentName = (String) batimentComboBox.getSelectedItem();

                int selectedBatimentId = getBatimentIdByName(selectedBatimentName);
                if (selectedBatimentId != -1) {
                    salle.setNbPlaces(nbPlaces);
                    salle.setEquipInfo(equipInfo);
                    salle.setIdBatiment(selectedBatimentId);

                    DatabaseManager.updateSalle(salle);

                    refreshSalleList();

                    JOptionPane.showMessageDialog(ShowSallePage.this,
                            "La salle a été modifiée avec succès.",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ModifierSalleDialog.this,
                            "Erreur lors de la récupération du bâtiment.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ModifierSalleDialog.this,
                        "Veuillez entrer des valeurs valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        private int getBatimentIdByName(String batimentName) {
            List<Batiment> batiments = DatabaseManager.getBatiments();
            for (Batiment batiment : batiments) {
                if (batiment.getNomBatiment().equals(batimentName)) {
                    return batiment.getNumeroBatiment();
                }
            }
            return -1; 
        }
    }

    public static void main(String[] args) {
        List<Salle> salles = DatabaseManager.getSalle();

        SwingUtilities.invokeLater(() -> {
            ShowSallePage showSallePage = new ShowSallePage(salles);
            showSallePage.setVisible(true);
        });
    }
}
