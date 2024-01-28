package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Batiment;
import classes.Salle;
import database.DatabaseManager;

public class InterfaceAddSalle extends JFrame {

    private JTextField nbPlacesField;
    private JTextField nbEtudiantsField;
    private JCheckBox equipInfoCheckBox;
    private JComboBox<Batiment> batimentComboBox;

    public InterfaceAddSalle() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter une salle");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nbPlacesLabel = new JLabel("Nombre de places:");
        nbPlacesField = new JTextField();


        JLabel equipInfoLabel = new JLabel("Equipement informatique:");
        equipInfoCheckBox = new JCheckBox();

        JLabel batimentLabel = new JLabel("Sélectionnez le bâtiment:");
        batimentComboBox = new JComboBox<>();

        List<Batiment> batiments = DatabaseManager.getBatiments();
        DefaultComboBoxModel<Batiment> batimentComboBoxModel = new DefaultComboBoxModel<>();
        batiments.forEach(batimentComboBoxModel::addElement);
        batimentComboBox.setModel(batimentComboBoxModel);
        batimentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Batiment) {
                    Batiment batiment = (Batiment) value;
                    setText(batiment.getNomBatiment());
                }
                return this;
            }
        });

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterSalle();
            }
        });

        mainPanel.add(nbPlacesLabel);
        mainPanel.add(nbPlacesField);
        mainPanel.add(equipInfoLabel);
        mainPanel.add(equipInfoCheckBox);
        mainPanel.add(batimentLabel);
        mainPanel.add(batimentComboBox);
        mainPanel.add(new JLabel());
        mainPanel.add(ajouterButton);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void ajouterSalle() {
        String nbPlacesText = nbPlacesField.getText();

        if (nbPlacesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int nbPlaces = Integer.parseInt(nbPlacesText);
            if (nbPlaces <= 0) {
                throw new NumberFormatException();
            }

            boolean equipInfo = equipInfoCheckBox.isSelected();

            Batiment selectedBatiment = (Batiment) batimentComboBox.getSelectedItem();

            if (selectedBatiment == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bâtiment.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Salle salle = new Salle(0, nbPlaces, equipInfo, selectedBatiment.getNumeroBatiment());

            DatabaseManager.insertSalle(salle);

            JOptionPane.showMessageDialog(this, "La salle a été ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre entier positif pour le nombre de places.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAddSalle interfaceAddSalle = new InterfaceAddSalle();
            interfaceAddSalle.setVisible(true);
        });
    }
}
