package interfaces;
import javax.swing.*;

import classes.Etudiant;
import classes.Cours;
import classes.Salle;
import classes.Enseignant;
import classes.Matiere;
import classes.Personnel;
import database.DatabaseManager;
import java.time.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShowPageCours extends JFrame {
    //private JList<String> coursList;
    
    private List<Cours> listCours;

    public ShowPageCours(List<Cours> listCours) {
        this.listCours = listCours;
        initUICours();
    }
    
    private void editCours(Cours selectedCours, JList<String> jListCours) {
        // Create a JPanel to hold the input fields for editing
    	JPanel editPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components

        JTextField nbEtudiantField = new JTextField(String.valueOf(selectedCours.getNbEtudiant()));
        nbEtudiantField.setPreferredSize(new Dimension(150, 25)); // Adjust the preferred size
        editPanel.add(new JLabel("Nombre d'étudiants:"), gbc);
        gbc.gridx++;
        editPanel.add(nbEtudiantField, gbc);
        
        JTextField tabEtudiantsField = new JTextField(selectedCours.getTabEtudiants());
        JTextField dateField = new JTextField(selectedCours.getDate().toString());
        JTextField heureField = new JTextField(String.valueOf(selectedCours.getHeure()));
        
        // Add more fields as needed based on your Cours class
        
        DefaultListModel<String> etudiantsListModel = new DefaultListModel<>();
        List<Etudiant> listeEtudiants = DatabaseManager.getStudents();
        for (Etudiant etudiant : listeEtudiants) {
            etudiantsListModel.addElement(etudiant.getNom() + " " + etudiant.getPrenom());
        }
        
        JList<String> etudiantsSelectedList = new JList<>(etudiantsListModel);
        etudiantsSelectedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane etudiantsScrollPane = new JScrollPane(etudiantsSelectedList);
        
        

        editPanel.add(new JLabel("Étudiants:"));
        editPanel.add(etudiantsScrollPane);
        editPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        editPanel.add(dateField);
        editPanel.add(new JLabel("Heure:"));
        editPanel.add(heureField);

        // Add more labels and fields based on your Cours class

        JScrollPane editScrollPane = new JScrollPane(editPanel);
        
        int result = JOptionPane.showConfirmDialog(null, editScrollPane,
                "Modifier le cours", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int editedNbEtudiant = Integer.parseInt(nbEtudiantField.getText());
            LocalDate editedDate = LocalDate.parse(dateField.getText());

            List<String> selectedEtudiants = etudiantsSelectedList.getSelectedValuesList();
            List<String> selectedEtudiantsID = new ArrayList<>();;
            for (String selectedEtu : selectedEtudiants) {
            	String[] etudiantNomPrenom = selectedEtu.split(" ");
            	for (Etudiant etudiant : listeEtudiants) {
                    if ( etudiant.getNom().equals(etudiantNomPrenom[0]) && etudiant.getPrenom().equals(etudiantNomPrenom[1]) ) {
                    	selectedEtudiantsID.add(Integer.toString(etudiant.getId()));
                    }
                }
            }
            
            String editedtabEtudiants = String.join(",", selectedEtudiantsID);
            
            int editedHeure = Integer.parseInt(heureField.getText());

            // Update the selectedCours object with the edited values
            selectedCours.setNbEtudiant(editedNbEtudiant);
            selectedCours.setTabEtudiants(editedtabEtudiants);
            selectedCours.setDate(editedDate);
            selectedCours.setHeure(editedHeure);

            // Update the course in the database
            DatabaseManager.updateCours(selectedCours);

            // Refresh the course list
            refreshCoursList(jListCours);
        }
    }
    
    private void initUICours() {
        setTitle("Gestion des cours");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        String[] coursArray = new String[listCours.size()];
        
        for (int i = 0; i < listCours.size(); i++) {
        	Cours cours = listCours.get(i);
        	List<Enseignant> listeEnseignants = DatabaseManager.getEnseignants();
        	Enseignant enseignantCours = null;
        	int idEnseignant = cours.getEnseignant();
		    for (Enseignant enseignant : listeEnseignants) {
		        if (enseignant.getId() == idEnseignant) {
		        	enseignantCours = enseignant;
		        }
		    }
		    int idMatiere = cours.getMatiere();
        	Matiere matiereCours = null;
        	List<Matiere> listeMatiere = DatabaseManager.getMatiere();
		    for (Matiere matiere : listeMatiere) {
		        if (matiere.getNumeroMatiere() == idMatiere) {
		        	matiereCours = matiere;
		        }
		    }
		    
		    int idSalle = cours.getSalle();
        	Salle salleCours = null;
        	List<Salle> listeSalle = DatabaseManager.getSalle();
		    for (Salle salle : listeSalle) {
		        if (salle.getNumeroSalle() == idSalle) {
		        	salleCours = salle;
		        }
		    }
		    
		    if (enseignantCours != null) {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " avec " + enseignantCours.getNom() + " " + enseignantCours.getPrenom() +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            } else {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " (Enseignant non trouvé)" +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            }
		    listModel.addElement(coursArray[i]);
        }
        
        JList<String> jListCours = new JList<>(listModel);
        jListCours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jListCours);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        scrollPane.setPreferredSize(new Dimension(500, getHeight()));  // Ajustez la largeur ici

        // Panel pour afficher les détails des étudiants
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        
        JPanel ajouterCoursPanel = new JPanel();
        ajouterCoursPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre actuelle
            }
        });

        JButton ajouterCoursButton = new JButton("Ajouter un cours");
        ajouterCoursButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		InterfaceAddCours interfaceAddCours = new InterfaceAddCours();
        		interfaceAddCours.afficherInterfaceCours();
        	}
        });
        
        JButton modifierSupprimerButton = new JButton("Modifier/Supprimer");
        modifierSupprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jListCours.getSelectedIndex();
                if (selectedIndex != -1) {
                    Cours selectedCours = listCours.get(selectedIndex);
                    String details = "";

                    List<Enseignant> listeEnseignants = DatabaseManager.getEnseignants();
                    int idEnseignant = selectedCours.getEnseignant();
                    Enseignant enseignantCours = null;
                    for (Enseignant enseignant : listeEnseignants) {
                        if (enseignant.getId() == idEnseignant) {
                            enseignantCours = enseignant;
                        }
                    }
                    int idMatiere = selectedCours.getMatiere();
                    Matiere matiereCours = null;
                    List<Matiere> listeMatiere = DatabaseManager.getMatiere();
                    for (Matiere matiere : listeMatiere) {
                        if (matiere.getNumeroMatiere() == idMatiere) {
                            matiereCours = matiere;
                        }
                    }

                    int idSalle = selectedCours.getSalle();
                    Salle salleCours = null;
                    List<Salle> listeSalle = DatabaseManager.getSalle();
                    for (Salle salle : listeSalle) {
                        if (salle.getNumeroSalle() == idSalle) {
                            salleCours = salle;
                        }
                    }

                    if (enseignantCours != null) {
                        details = "Cours de " + matiereCours.getNomMatiere() +
                                " avec " + enseignantCours.getNom() + " " + enseignantCours.getPrenom() +
                                " le " + selectedCours.getDate() +
                                " à " + selectedCours.getHeure() +
                                " en salle " + salleCours.getNumeroSalle();
                    } else {
                        details = "Cours de " + matiereCours.getNomMatiere() +
                                " (Enseignant non trouvé)" +
                                " le " + selectedCours.getDate() +
                                " à " + selectedCours.getHeure() +
                                " en salle " + salleCours.getNumeroSalle();
                    }

                    // Affichez le message de modification ou suppression ici
                    int option = JOptionPane.showOptionDialog(ShowPageCours.this,
                            "Que voulez-vous faire pour le cours :\n" + details,
                            "Modifier/Supprimer le cours", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null,
                            new Object[]{"Modifier", "Supprimer"}, null);

                    if (option == 0) {
                    	editCours(selectedCours,jListCours);
                    } else if (option == 1) {
                        int confirmOption = JOptionPane.showConfirmDialog(ShowPageCours.this,
                                "Voulez-vous vraiment supprimer le cours :\n" + details,
                                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                        if (confirmOption == JOptionPane.YES_OPTION) {
                            DatabaseManager.deleteCours(selectedCours.getId());

                            refreshCoursList(jListCours);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowPageCours.this,
                            "Veuillez sélectionner un cours avant de modifier ou supprimer.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierSupprimerButton, BorderLayout.SOUTH);
 
        ajouterCoursPanel.add(ajouterCoursButton);
        

        // Zone de texte pour afficher les détails des étudiants
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Écouteur pour mettre à jour les détails lorsque la sélection change
        jListCours.addListSelectionListener(e -> {
            int selectedIndex = jListCours.getSelectedIndex();
            if (selectedIndex != -1) {
                Cours selectedCours = listCours.get(selectedIndex);
                String details = "";
                
                List<Enseignant> listeEnseignants = DatabaseManager.getEnseignants();
            	int idEnseignant = selectedCours.getEnseignant();
            	Enseignant enseignantCours = null;
    		    for (Enseignant enseignant : listeEnseignants) {
    		        if (enseignant.getId() == idEnseignant) {
    		        	enseignantCours = enseignant;
    		        }
    		    }
    		    int idMatiere = selectedCours.getMatiere();
            	Matiere matiereCours = null;
            	List<Matiere> listeMatiere = DatabaseManager.getMatiere();
    		    for (Matiere matiere : listeMatiere) {
    		        if (matiere.getNumeroMatiere() == idMatiere) {
    		        	matiereCours = matiere;
    		        }
    		    }
    		    
    		    int idSalle = selectedCours.getSalle();
            	Salle salleCours = null;
            	List<Salle> listeSalle = DatabaseManager.getSalle();
    		    for (Salle salle : listeSalle) {
    		        if (salle.getNumeroSalle() == idSalle) {
    		        	salleCours = salle;
    		        }
    		    }
    		    
    		    if (enseignantCours != null) {
    		    	details = "Cours de " + matiereCours.getNomMatiere() +
                            " avec " + enseignantCours.getNom() + " " + enseignantCours.getPrenom() +
                            " le " + selectedCours.getDate() +
                            " à " + selectedCours.getHeure() +
                            " en salle " + salleCours.getNumeroSalle();
                } else {
                	details = "Cours de " + matiereCours.getNomMatiere() +
                            " (Enseignant non trouvé)" +
                            " le " + selectedCours.getDate() +
                            " à " + selectedCours.getHeure() +
                            " en salle " + salleCours.getNumeroSalle();
                }
                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText(""); // Effacer les détails si rien n'est sélectionné
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(ajouterCoursPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }
    
 // Ajoutez cette méthode à votre classe ShowPageCours
    private void refreshCoursList(JList<String> jListCours) {
        listCours = DatabaseManager.getCours();
        
        DefaultListModel<String> listModel = (DefaultListModel<String>) jListCours.getModel();
        listModel.clear(); 

        String[] coursArray = new String[listCours.size()];

        for (int i = 0; i < listCours.size(); i++) {
            Cours cours = listCours.get(i);
            List<Enseignant> listeEnseignants = DatabaseManager.getEnseignants();
            Enseignant enseignantCours = null;
            int idEnseignant = cours.getEnseignant();
            for (Enseignant enseignant : listeEnseignants) {
                if (enseignant.getId() == idEnseignant) {
                    enseignantCours = enseignant;
                }
            }

            int idMatiere = cours.getMatiere();
            Matiere matiereCours = null;
            List<Matiere> listeMatiere = DatabaseManager.getMatiere();
            for (Matiere matiere : listeMatiere) {
                if (matiere.getNumeroMatiere() == idMatiere) {
                    matiereCours = matiere;
                }
            }

            int idSalle = cours.getSalle();
            Salle salleCours = null;
            List<Salle> listeSalle = DatabaseManager.getSalle();
            for (Salle salle : listeSalle) {
                if (salle.getNumeroSalle() == idSalle) {
                    salleCours = salle;
                }
            }

            if (enseignantCours != null) {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " avec " + enseignantCours.getNom() + " " + enseignantCours.getPrenom() +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            } else {
                coursArray[i] = "Cours de " + matiereCours.getNomMatiere() +
                        " (Enseignant non trouvé)" +
                        " le " + cours.getDate() +
                        " à " + cours.getHeure() +
                        " en salle " + salleCours.getNumeroSalle();
            }
            listModel.addElement(coursArray[i]);
        }
    }


    public static void main(String[] args) {
        // Vous pouvez placer ici le code pour tester votre page
    	List<Cours> listCours = DatabaseManager.getCours();
        
        SwingUtilities.invokeLater(() -> {
        	ShowPageCours showPageCours = new ShowPageCours(listCours);
        	showPageCours.setVisible(true);
        });
    }
}
