package interfaces;
import javax.swing.*;

import classes.Etudiant;
import classes.Cours;
import classes.Salle;
import classes.Enseignant;
import classes.Matiere;
import classes.Personnel;
import database.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowPageCours extends JFrame {
    //private JList<String> coursList;
    
    private List<Cours> listCours;

    public ShowPageCours(List<Cours> listCours) {
        this.listCours = listCours;
        initUICours();
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
                        // Logique pour la modification
                        // ...
                    } else if (option == 1) {
                        // Logique pour la suppression
                        int confirmOption = JOptionPane.showConfirmDialog(ShowPageCours.this,
                                "Voulez-vous vraiment supprimer le cours :\n" + details,
                                "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                        if (confirmOption == JOptionPane.YES_OPTION) {
                            // Appeler la fonction de suppression ici
                            DatabaseManager.deleteCours(selectedCours.getId());

                            // Rafraîchir la liste des cours ou effectuer d'autres actions nécessaires
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
