package interfaces;
import javax.swing.*;

import classes.Formation;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceAddPromotion extends JFrame {

    private JComboBox<Formation> formationComboBox;

    public InterfaceAddPromotion() {
        initUI();
    }

    private void initUI() {
        setTitle("Ajouter une promotion");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel anneePromoLabel = new JLabel("Année de la promotion : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(anneePromoLabel, gbc);

        JTextField anneePromoText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(anneePromoText, gbc);

        JLabel idFormationLabel = new JLabel("Formation : ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idFormationLabel, gbc);

        List<Formation> formations = DatabaseManager.getFormations();
        formationComboBox = new JComboBox<>(formations.toArray(new Formation[0]));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(formationComboBox, gbc);

        JButton ajouterPromotionButton = new JButton("Ajouter Promotion");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(ajouterPromotionButton, gbc);

        ajouterPromotionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String anneePromoTextValue = anneePromoText.getText().trim();

                if (anneePromoTextValue.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceAddPromotion.this,
                            "Veuillez remplir tous les champs.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int anneePromo = Integer.parseInt(anneePromoTextValue);
                        Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();

                        DatabaseManager.insertPromotion(anneePromo, selectedFormation.getId_Formation());

                        JOptionPane.showMessageDialog(InterfaceAddPromotion.this,
                                "Promotion ajoutée avec succès!",
                                "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                        anneePromoText.setText("");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(InterfaceAddPromotion.this,
                                "Veuillez saisir une valeur numérique valide pour 'Année de la promotion'.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        gbc.gridy = 3;
        panel.add(Box.createVerticalStrut(30), gbc);
        JButton retourButton = new JButton("Retour");
        gbc.gridy = 4;
        panel.add(retourButton, gbc);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceAddPromotion addPromotionPage = new InterfaceAddPromotion();
            addPromotionPage.setVisible(true);
        });
    }
}
