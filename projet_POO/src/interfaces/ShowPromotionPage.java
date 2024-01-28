package interfaces;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import classes.Promotion;
import classes.Formation;
import database.DatabaseManager;

public class ShowPromotionPage extends JFrame {

    private List<Promotion> promotions;
    private List<Formation> formations;

    public ShowPromotionPage(List<Promotion> promotions, List<Formation> formations) {
        this.promotions = promotions;
        this.formations = formations;
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
            listModel.addElement(DatabaseManager.getNomFormationById(promotion.getidFormation()) + " - Promotion " + promotion.getAnnee());
        }

        JList<String> promotionList = new JList<>(listModel);
        promotionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(promotionList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> dispose());

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> modifierPromotion(promotions, promotionList, listModel));

        
        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        promotionList.addListSelectionListener(e -> {
            int selectedIndex = promotionList.getSelectedIndex();
            if (selectedIndex != -1) {
                Promotion selectedPromotion = promotions.get(selectedIndex);
                String details = "ID de la Promotion : " + selectedPromotion.getId() + "\n" +
                                 "Nom de la Formation : " + DatabaseManager.getNomFormationById(selectedPromotion.getidFormation()) + "\n" +
                                 "Année : " + selectedPromotion.getAnnee();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
        
    }

    private void modifierPromotion(List<Promotion> promotions, JList<String> promotionList, DefaultListModel<String> listModel) {
        int selectedIndex = promotionList.getSelectedIndex();
        if (selectedIndex != -1) {
            Promotion selectedPromotion = promotions.get(selectedIndex);

            JTextField anneeField = new JTextField();
            JComboBox<Formation> formationComboBox = new JComboBox<>(formations.toArray(new Formation[0]));

            Object[] message = {
                    "Nouvelle Année:", anneeField,
                    "Nouvelle Formation:", formationComboBox
            };

            int option = JOptionPane.showConfirmDialog(
                    ShowPromotionPage.this,
                    message,
                    "Modifier la promotion",
                    JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String nouvelleAnnee = anneeField.getText();
                Formation nouvelleFormation = (Formation) formationComboBox.getSelectedItem();

                if (!nouvelleAnnee.isEmpty() && nouvelleFormation != null) {
                    selectedPromotion.setAnnee(Integer.parseInt(nouvelleAnnee));
                    selectedPromotion.setidFormation(nouvelleFormation.getId_Formation());
                    DatabaseManager.updatePromotion(selectedPromotion.getId(), selectedPromotion.getAnnee(), selectedPromotion.getidFormation());

                    listModel.setElementAt(DatabaseManager.getNomFormationById(selectedPromotion.getidFormation()) + " - Promotion " + selectedPromotion.getAnnee(), selectedIndex);
                }
                dispose();
                List<Formation> formations = DatabaseManager.getFormations();

                SwingUtilities.invokeLater(() -> {
                    ShowPromotionPage showPromotionPage = new ShowPromotionPage(promotions, formations);
                    showPromotionPage.setVisible(true);
                });
            }
        } else {
            JOptionPane.showMessageDialog(ShowPromotionPage.this,
                    "Veuillez sélectionner une promotion avant de modifier.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        List<Promotion> promotions = DatabaseManager.getPromotions();
        List<Formation> formations = DatabaseManager.getFormations();

        SwingUtilities.invokeLater(() -> {
            ShowPromotionPage showPromotionPage = new ShowPromotionPage(promotions, formations);
            showPromotionPage.setVisible(true);
        });
    }
}
