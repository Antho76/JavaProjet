package interfaces;
import javax.swing.*;

import classes.Etudiant;
import classes.Cours;
import classes.Salle;
import classes.Enseignant;
import classes.Matiere;
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
                    
                    JOptionPane.showMessageDialog(ShowPageCours.this,
                            "Modifier un évènement pour le cours : " + details,
                            "Modifier un évènement", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ShowPageCours.this,
                            "Veuillez sélectionner un étudiant avant de modifier.",
                            "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton ajouterCoursButton = new JButton("Modifier");
        ajouterCoursButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(modifierButton, BorderLayout.SOUTH);

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

        add(mainPanel);
        setLocationRelativeTo(null);
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
