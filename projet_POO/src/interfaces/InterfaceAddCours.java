package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.MaskFormatter;
import classes.Etudiant;
import classes.Matiere;
import classes.Salle;
import classes.Enseignant;
import controller.CoursController;
import database.DatabaseManager;

public class InterfaceAddCours {
    private JFrame mainFrame;
    private CoursController coursController = new CoursController();

    public void afficherInterfaceCours() {
        mainFrame = new JFrame("Page d'Ajout de Cours");
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        mainFrame.add(panel);
        placeComponents(panel);

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                // Replace 'AdminPage' with the appropriate class for navigating back
            }
        });
        panel.add(retourButton);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        JLabel nbEtudiantLabel = new JLabel("Nombre d'étudiants:");
        panel.add(nbEtudiantLabel);

        JTextField nbEtudiantText = new JTextField(20);
        panel.add(nbEtudiantText);

        JLabel etudiantsLabel = new JLabel("Étudiants:");
        panel.add(etudiantsLabel);

        DefaultListModel<String> etudiantsListModel = new DefaultListModel<>();
        List<Etudiant> listeEtudiants = DatabaseManager.getStudents();
        for (Etudiant etudiant : listeEtudiants) {
            etudiantsListModel.addElement(etudiant.getNom()+" " + etudiant.getPrenom());
        }

        JList<String> etudiantsSelectedList = new JList<>(etudiantsListModel);
        etudiantsSelectedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane etudiantsScrollPane = new JScrollPane(etudiantsSelectedList);
        panel.add(etudiantsScrollPane);

        JLabel enseignantLabel = new JLabel("Sélectionnez l'enseignant:");
        panel.add(enseignantLabel);

        JComboBox<String> enseignantComboBox = new JComboBox<>();
        loadEnseignantIntoComboBox(enseignantComboBox);
        panel.add(enseignantComboBox);

        JLabel dateCoursLabel = new JLabel("Date du cours:");
        panel.add(dateCoursLabel);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("####-##-##");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFormattedTextField dateCoursText = new JFormattedTextField(dateFormatter);
        panel.add(dateCoursText);

        JLabel heureLabel = new JLabel("Heure du cours:");
        panel.add(heureLabel);

        JTextField heureText = new JTextField(20);
        panel.add(heureText);

        JLabel matiereLabel = new JLabel("Sélectionnez la matière:");
        panel.add(matiereLabel);

        JComboBox<String> matiereComboBox = new JComboBox<>();
        loadMatieresIntoComboBox(matiereComboBox);
        panel.add(matiereComboBox);

        JLabel salleLabel = new JLabel("Sélectionnez la salle:");
        panel.add(salleLabel);

        JComboBox<String> salleComboBox = new JComboBox<>();
        loadSalleIntoComboBox(salleComboBox);
        panel.add(salleComboBox);

        JButton addCoursButton = new JButton("Ajouter le cours");
        addCoursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve course information from the input fields
                String dateString = dateCoursText.getText();
                LocalDate date = parseDate(dateString);
                
                int heure = Integer.parseInt(heureText.getText());
                
                int nbEtudiant = Integer.parseInt(nbEtudiantText.getText());
                
                List<Etudiant> listEtudiant = DatabaseManager.getStudents();
                List<String> selectedEtudiants = etudiantsSelectedList.getSelectedValuesList();
                List<String> selectedEtudiantsID = new ArrayList<>();;
                for (String selectedEtu : selectedEtudiants) {
                	String[] etudiantNomPrenom = selectedEtu.split(" ");
                	for (Etudiant etudiant : listEtudiant) {
                        if ( etudiant.getNom().equals(etudiantNomPrenom[0]) && etudiant.getPrenom().equals(etudiantNomPrenom[1]) ) {
                        	selectedEtudiantsID.add(Integer.toString(etudiant.getId()));
                        }
                    }
                }
                
                String tabEtudiants = String.join(",", selectedEtudiantsID);

                if (tabEtudiants.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int maxCoursId = DatabaseManager.getMaxCoursId();

                        int idMatiere = getMatiereIdFromComboBox(matiereComboBox);
                        int idSalle = getSalleIdFromComboBox(salleComboBox);
                        int idEnseignant = getEnseignantIdFromComboBox(enseignantComboBox);

                        coursController.ajoutCours(maxCoursId+1, nbEtudiant, tabEtudiants, idEnseignant, date, heure, idMatiere, idSalle);

                        // Show a success message or navigate to another page if needed
                        afficherMessageCoursAjoute();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(addCoursButton);
    }

    private void afficherMessageCoursAjoute() {
        mainFrame.setVisible(false);
        JFrame messageFrame = new JFrame("Cours ajouté");
        messageFrame.setSize(200, 100);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel messagePanel = new JPanel();
        JLabel messageLabel = new JLabel("Cours ajouté");
        messagePanel.add(messageLabel);
        messageFrame.add(messagePanel);
        messageFrame.setLocationRelativeTo(null);

        messageFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Replace 'AdminPage' with the appropriate class for navigating back
                //new AdminPage().afficherInterface();
            }
        });

        messageFrame.setVisible(true);
    }

    private void loadMatieresIntoComboBox(JComboBox<String> matiereComboBox) {
        List<Matiere> matieres = DatabaseManager.getMatiere();
        for (Matiere matiere : matieres) {
            matiereComboBox.addItem(matiere.getNomMatiere());
        }
    }

    private void loadSalleIntoComboBox(JComboBox<String> salleComboBox) {
        List<Salle> listSalle = DatabaseManager.getSalle();
        for (Salle salle : listSalle) {
            salleComboBox.addItem(Integer.toString(salle.getNumeroSalle()));
        }
    }

    private void loadEnseignantIntoComboBox(JComboBox<String> enseignantComboBox) {
        List<Enseignant> listEnseignant = DatabaseManager.getEnseignants();
        for (Enseignant enseignant : listEnseignant) {
            enseignantComboBox.addItem(enseignant.getNom()+" "+enseignant.getPrenom());
        }
    }

    private int getMatiereIdFromComboBox(JComboBox<String> matiereComboBox) {
        String selectedMatiere = matiereComboBox.getSelectedItem().toString();
        List<Matiere> matieres = DatabaseManager.getMatiere();
        for (Matiere matiere : matieres) {
            if (matiere.getNomMatiere().equals(selectedMatiere)) {
                return matiere.getNumeroMatiere();
            }
        }
        return 0; // Ou une valeur par défaut, selon votre logique
    }

    private int getSalleIdFromComboBox(JComboBox<String> salleComboBox) {
        String selectedSalle = salleComboBox.getSelectedItem().toString();
        List<Salle> listSalle = DatabaseManager.getSalle();
        for (Salle salle : listSalle) {
            if (Integer.toString(salle.getNumeroSalle()).equals(selectedSalle)) {
                return salle.getNumeroSalle();
            }
        }
        return 0; // Ou une valeur par défaut, selon votre logique
    }

    private int getEnseignantIdFromComboBox(JComboBox<String> enseignantComboBox) {
        String selectedEnseignant = enseignantComboBox.getSelectedItem().toString();
        String[] enseignantNomPrenom = selectedEnseignant.split(" ");
        List<Enseignant> listEnseignant = DatabaseManager.getEnseignants();
        for (Enseignant enseignant : listEnseignant) {
            if (enseignant.getNom().equals(enseignantNomPrenom[0]) && enseignant.getPrenom().equals(enseignantNomPrenom[1]) ) {
                return enseignant.getId();
            }
        }
        return 0; // Ou une valeur par défaut, selon votre logique
    }

    private LocalDate parseDate(String dateString) {

            LocalDate localDate = LocalDate.parse(dateString);
            System.out.println(localDate);
            return localDate;

    }
}
