package interfaces;

import classes.Matiere;
import database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowMatierePage extends JFrame {

    private List<Matiere> matieres;
    private JList<String> matiereList;

    public ShowMatierePage(List<Matiere> matieres) {
        this.matieres = matieres;
        initUI();
    }

    private void initUI() {
        setTitle("Liste des matières");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Matiere matiere : matieres) {
            listModel.addElement(matiere.getNomMatiere());
        }

        matiereList = new JList<>(listModel);
        matiereList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(matiereList);
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
                modifierMatiere();
            }
        });

        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        matiereList.addListSelectionListener(e -> {
            int selectedIndex = matiereList.getSelectedIndex();
            if (selectedIndex != -1) {
                Matiere selectedMatiere = matieres.get(selectedIndex);
                String details = "Nom de la Matière : " + selectedMatiere.getNomMatiere() + "\n" +
                        "Coefficient : " + selectedMatiere.getCoefficient();

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMatiere();
            }
        });
        mainPanel.add(ajouterButton, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void modifierMatiere() {
        int selectedIndex = matiereList.getSelectedIndex();
        if (selectedIndex != -1) {
            Matiere selectedMatiere = matieres.get(selectedIndex);

            // Créer une boîte de dialogue pour la modification de la matière
            ModifierMatiereDialog modifierDialog = new ModifierMatiereDialog(this, selectedMatiere);
            modifierDialog.setVisible(true);

            // Rafraîchir la liste des matières après modification
            refreshMatiereList();
        } else {
            JOptionPane.showMessageDialog(ShowMatierePage.this,
                    "Veuillez sélectionner une matière avant de modifier.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ajouterMatiere() {
        // Créer une boîte de dialogue pour l'ajout d'une nouvelle matière
        AjouterMatiereDialog ajouterDialog = new AjouterMatiereDialog(this);
        ajouterDialog.setVisible(true);

        // Rafraîchir la liste des matières après ajout
        refreshMatiereList();
    }

    private void refreshMatiereList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Matiere matiere : matieres) {
            listModel.addElement(matiere.getNomMatiere());
        }
        matiereList.setModel(listModel);
    }

    // Classe interne pour la boîte de dialogue de modification
    private class ModifierMatiereDialog extends JDialog {

        private Matiere matiere;
        private JTextField nomMatiereField;
        private JTextField coefficientField;

        public ModifierMatiereDialog(JFrame parent, Matiere matiere) {
            super(parent, "Modifier la matière", true);
            this.matiere = matiere;
            initUI();
        }

        private void initUI() {
            setSize(400, 200);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

            JLabel nomMatiereLabel = new JLabel("Nom de la matière:");
            nomMatiereField = new JTextField(matiere.getNomMatiere());

            JLabel coefficientLabel = new JLabel("Coefficient:");
            coefficientField = new JTextField(String.valueOf(matiere.getCoefficient()));

            JButton modifierButton = new JButton("Modifier");
            modifierButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modifierMatiere();
                    // Fermer la boîte de dialogue après modification
                    ModifierMatiereDialog.this.dispose();
                }
            });

            mainPanel.add(nomMatiereLabel);
            mainPanel.add(nomMatiereField);
            mainPanel.add(coefficientLabel);
            mainPanel.add(coefficientField);
            mainPanel.add(new JLabel());
            mainPanel.add(modifierButton);

            add(mainPanel);
        }

        private void modifierMatiere() {
            // Mettre à jour les informations de la matière
            try {
                String nomMatiere = nomMatiereField.getText();
                int coefficient = Integer.parseInt(coefficientField.getText());

                matiere.setNomMatiere(nomMatiere);
                matiere.setCoefficient(coefficient);

                // Mettre à jour les informations de la matière dans la base de données
                DatabaseManager.updateMatiere(matiere);

                // Rafraîchir la liste des matières après modification
                refreshMatiereList();

                JOptionPane.showMessageDialog(ShowMatierePage.this,
                        "La matière a été modifiée avec succès.",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ModifierMatiereDialog.this,
                        "Veuillez entrer des valeurs valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Classe interne pour la boîte de dialogue d'ajout
    private class AjouterMatiereDialog extends JDialog {

        private JTextField nomMatiereField;
        private JTextField coefficientField;

        public AjouterMatiereDialog(JFrame parent) {
            super(parent, "Ajouter une matière", true);
            initUI();
        }

        private void initUI() {
            setSize(400, 200);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

            JLabel nomMatiereLabel = new JLabel("Nom de la matière:");
            nomMatiereField = new JTextField();

            JLabel coefficientLabel = new JLabel("Coefficient:");
            coefficientField = new JTextField();

            JButton ajouterButton = new JButton("Ajouter");
            ajouterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ajouterMatiere();
                    // Fermer la boîte de dialogue après ajout
                    AjouterMatiereDialog.this.dispose();
                }
            });

            mainPanel.add(nomMatiereLabel);
            mainPanel.add(nomMatiereField);
            mainPanel.add(coefficientLabel);
            mainPanel.add(coefficientField);
            mainPanel.add(new JLabel());
            mainPanel.add(ajouterButton);

            add(mainPanel);
        }

        private void ajouterMatiere() {
            // Ajouter une nouvelle matière
            try {
                String nomMatiere = nomMatiereField.getText();
                int coefficient = Integer.parseInt(coefficientField.getText());

                Matiere newMatiere = new Matiere();
                newMatiere.setNomMatiere(nomMatiere);
                newMatiere.setCoefficient(coefficient);

                // Insérer la nouvelle matière dans la base de données
                DatabaseManager.insertMatiere(newMatiere);

                // Rafraîchir la liste des matières après ajout
                refreshMatiereList();

                JOptionPane.showMessageDialog(ShowMatierePage.this,
                        "La matière a été ajoutée avec succès.",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AjouterMatiereDialog.this,
                        "Veuillez entrer des valeurs valides.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Vous pouvez placer ici le code pour tester votre page
        List<Matiere> matieres = DatabaseManager.getMatiere();

        SwingUtilities.invokeLater(() -> {
            ShowMatierePage showMatierePage = new ShowMatierePage(matieres);
            showMatierePage.setVisible(true);
        });
    }
}
