package interfaces;
import javax.swing.*;

import classes.Promotion;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowPromotionPage extends JFrame {

    private List<Promotion> promotions;

    public ShowPromotionPage(List<Promotion> promotions) {
        this.promotions = promotions;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des promotions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Promotion promotion : promotions) {
            listModel.addElement(promotion.toString());
        }

        JList<String> promotionList = new JList<>(listModel);
        promotionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(promotionList);
        mainPanel.add(scrollPane, BorderLayout.WEST);

        // Panel pour afficher les détails des promotions
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
                int selectedIndex = promotionList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Promotion selectedPromotion = promotions.get(selectedIndex);
                    JOptionPane.showMessageDialog(ShowPromotionPage.this,
                            "Modifier la promotion : " + selectedPromotion.toString(),
                            "Modifier la promotion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ShowPromotionPage.this,
                            "Veuillez sélectionner une promotion avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        // Zone de texte pour afficher les détails des promotions
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        promotionList.addListSelectionListener(e -> {
            int selectedIndex = promotionList.getSelectedIndex();
            if (selectedIndex != -1) {
                Promotion selectedPromotion = promotions.get(selectedIndex);
                String details = "Numéro de Promotion : " + selectedPromotion.getId() + "\n" +
                                 "Année : " + selectedPromotion.getAnnee() + "\n" +
                                 "ID Formation : " + selectedPromotion.getidFormation();

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
        List<Promotion> promotions = DatabaseManager.getPromotions();

        SwingUtilities.invokeLater(() -> {
            ShowPromotionPage showPromotionPage = new ShowPromotionPage(promotions);
            showPromotionPage.setVisible(true);
        });
    }
}
