package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Enseignant;
import classes.Avertissement;
import classes.Cours;
import classes.Etudiant;
import classes.Matiere;
import classes.Salle;
import database.DatabaseManager;

public class ShowAvertissementPage extends JFrame {

    private Enseignant enseignant;
    private List<Avertissement> listAvertissements;

    public ShowAvertissementPage(Enseignant enseignant, List<Avertissement> listAvertissements) {
        this.enseignant = enseignant;
        this.listAvertissements = listAvertissements;
        initUI();
    }
    
    public static class AppContext {
        private static Enseignant enseignant;

        public static Enseignant getEnseignant() {
            return enseignant;
        }

        public static void setEnseignant(Enseignant enseignant) {
            AppContext.enseignant = enseignant;
        }
    }

    private void initUI() {
        setTitle("Liste des avertissements");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        for (Avertissement avertissement : listAvertissements) {
        	if (avertissement.getEnseignant()== enseignant.getId()) {
        		for (Etudiant etudiant : listEtudiant) {
        			if (avertissement.getEtudiant() == etudiant.getId()){
        				listModel.addElement(avertissement.getNom() + " / " + etudiant.getNom() + " " + etudiant.getPrenom()  );
        			}
        		}

        	}
        }

        JList<String> jListAvertissements = new JList<>(listModel);
        jListAvertissements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jListAvertissements);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> dispose());

        JButton supprimerButton = new JButton("Supprimer l'avertissement");
        supprimerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                int selectedIndex = jListAvertissements.getSelectedIndex();
                if (selectedIndex != -1) {
                	Avertissement selectedAvertissement = listAvertissements.get(selectedIndex);
                	String etudiantNom =  null;
    				String etudiantPrenom =null;
                	for (Etudiant etudiant : listEtudiant) {
            			if (selectedAvertissement.getEtudiant() == etudiant.getId()){
            				etudiantNom =  etudiant.getNom();
            				etudiantPrenom =etudiant.getPrenom();
            			}
            		}
                    
                    String details = "Raison de l'avertissement : " + selectedAvertissement.getNom() + "\n" +
                                     "Etudiant : " + etudiantNom + " " + etudiantPrenom;
                    
                	int confirmOption = JOptionPane.showConfirmDialog(ShowAvertissementPage.this,
                            "Voulez-vous vraiment supprimer l'avertissment :\n" + details,
                            "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                    if (confirmOption == JOptionPane.YES_OPTION) {
                        DatabaseManager.deleteAvertissement(selectedAvertissement);

                        refreshAvertissementList(jListAvertissements);
                    }
                }
        	}
        });
        
        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(supprimerButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        jListAvertissements.addListSelectionListener(e -> {
            int selectedIndex = jListAvertissements.getSelectedIndex();
            if (selectedIndex != -1) {
            	Avertissement selectedAvertissement = listAvertissements.get(selectedIndex);
            	String etudiantNom =  null;
				String etudiantPrenom =null;
            	for (Etudiant etudiant : listEtudiant) {
        			if (selectedAvertissement.getEtudiant() == etudiant.getId()){
        				etudiantNom =  etudiant.getNom();
        				etudiantPrenom =etudiant.getPrenom();
        			}
        		}
                
                String details = "Raison de l'avertissement : " + selectedAvertissement.getNom() + "\n" +
                                 "Etudiant : " + etudiantNom + " " + etudiantPrenom;

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
        
    }

    private void refreshAvertissementList(JList<String> jListAvertissements) {
        List<Avertissement> listAvertissements = DatabaseManager.getAvertissement();
        
        DefaultListModel<String> listModel = (DefaultListModel<String>) jListAvertissements.getModel();
        listModel.clear(); 

        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        for (Avertissement avertissement : listAvertissements) {
        	if (avertissement.getEnseignant()== enseignant.getId()) {
        		for (Etudiant etudiant : listEtudiant) {
        			if (avertissement.getEtudiant() == etudiant.getId()){
        				listModel.addElement(avertissement.getNom() + " " + etudiant.getNom() + " " + etudiant.getPrenom()  );
        			}
        		}

        	}
        }
    }

    public static void main(String[] args) {
    	List<Avertissement> listAvertissements = DatabaseManager.getAvertissement();

        Enseignant enseignant = AppContext.getEnseignant();

        if (enseignant == null) {
            System.out.println("Enseignant non trouvé. Veuillez vérifier la connexion.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            ShowAvertissementPage showAvertissementPage = new ShowAvertissementPage(enseignant, listAvertissements);
            showAvertissementPage.setVisible(true);
        });
    }
}

