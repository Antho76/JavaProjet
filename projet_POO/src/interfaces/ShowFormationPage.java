package interfaces;
import javax.swing.*;

import classes.Formation;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowFormationPage extends JFrame {

    private List<Formation> formations;

    public ShowFormationPage(List<Formation> formations) {
        this.formations = formations;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des formations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Formation formation : formations) {
            listModel.addElement(formation.toString());
        }

        JList<String> formationList = new JList<>(listModel);
        formationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(formationList);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));  

        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(300, getHeight())); 

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
                int selectedIndex = formationList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Formation selectedFormation = formations.get(selectedIndex);

                    String nouveauNomFormation = JOptionPane.showInputDialog(
                            ShowFormationPage.this,
                            "Veuillez entrer le nouveau nom de la formation :",
                            "Modifier la formation",
                            JOptionPane.PLAIN_MESSAGE);

                    if (nouveauNomFormation != null && !nouveauNomFormation.isEmpty()) {
                        selectedFormation.setNomFormation(nouveauNomFormation);
                        DatabaseManager.updateFormation(selectedFormation);

                        listModel.setElementAt(selectedFormation.toString(), selectedIndex);
                        dispose();
                        List<Formation> formations = DatabaseManager.getFormations();
                        SwingUtilities.invokeLater(() -> {
                            ShowFormationPage showFormationPage = new ShowFormationPage(formations);
                            showFormationPage.setVisible(true);
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowFormationPage.this,
                            "Veuillez sélectionner une formation avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        formationList.addListSelectionListener(e -> {
            int selectedIndex = formationList.getSelectedIndex();
            if (selectedIndex != -1) {
                Formation selectedFormation = formations.get(selectedIndex);
                String details = "ID de la Formation : " + selectedFormation.getId_Formation() + "\n" +
                                 "Nom de la Formation : " + selectedFormation.getNomFormation();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        List<Formation> formations = DatabaseManager.getFormations();

        SwingUtilities.invokeLater(() -> {
            ShowFormationPage showFormationPage = new ShowFormationPage(formations);
            showFormationPage.setVisible(true);
        });
    }
}
