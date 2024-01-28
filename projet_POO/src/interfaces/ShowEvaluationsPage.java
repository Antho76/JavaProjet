package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import classes.Enseignant;
import classes.Evaluation;
import classes.Cours;
import classes.Etudiant;
import classes.Matiere;
import classes.Salle;
import database.DatabaseManager;

public class ShowEvaluationsPage extends JFrame {

    private Enseignant enseignant;
    private List<Evaluation> listEvaluations;

    public ShowEvaluationsPage(Enseignant enseignant, List<Evaluation> listEvaluations) {
        this.enseignant = enseignant;
        this.listEvaluations = listEvaluations;
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
        setTitle("Liste des evaluations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        List<Matiere> listeMatiere = DatabaseManager.getMatiere();
        for (Evaluation evaluation : listEvaluations) {
        	if (evaluation.getEnseignant()== enseignant.getId()) {
        		for (Etudiant etudiant : listEtudiant) {
        			if (evaluation.getEtudiant() == etudiant.getId()){
        	            Matiere matiereEvaluation = null;
        	            
        	            for (Matiere matiere : listeMatiere) {
        	                if (matiere.getNumeroMatiere() == evaluation.getMatiere()) {
        	                	matiereEvaluation = matiere;
        	                }
        	            }
        				listModel.addElement(
        						evaluation.getNom() + " " + matiereEvaluation.getNomMatiere() + 
        						" / " + etudiant.getNom() + " " + etudiant.getPrenom() + 
        						" / Note : " + evaluation.getNote() 
        				);
        			}
        		}

        	}
        }

        JList<String> jListEvaluation = new JList<>(listModel);
        jListEvaluation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jListEvaluation);
        scrollPane.setPreferredSize(new Dimension(300, getHeight()));

        mainPanel.add(scrollPane, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> dispose());

        JButton supprimerButton = new JButton("Supprimer l'évaluation");
        supprimerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                int selectedIndex = jListEvaluation.getSelectedIndex();
                if (selectedIndex != -1) {
                	Evaluation selectedEvaluation = listEvaluations.get(selectedIndex);
                	String etudiantNom =  null;
    				String etudiantPrenom =null;
                	for (Etudiant etudiant : listEtudiant) {
            			if (selectedEvaluation.getEtudiant() == etudiant.getId()){
            				etudiantNom = etudiant.getNom();
            				etudiantPrenom = etudiant.getPrenom();
            			}
            		}
                	String matiereNom = null;
                	for (Matiere matiere : listeMatiere) {
    	                if (matiere.getNumeroMatiere() == selectedEvaluation.getMatiere()) {
    	                	matiereNom = matiere.getNomMatiere();
    	                }
    	            }
                    
                    String details = "Nom de l'évaluation : " + selectedEvaluation.getNom() + "\n" +
                                     "Etudiant : " + etudiantNom + " " + etudiantPrenom + "\n" +
                                     "Matiere : " + matiereNom;
                                     ;
                    
                	int confirmOption = JOptionPane.showConfirmDialog(ShowEvaluationsPage.this,
                            "Voulez-vous vraiment supprimer l'évaluation :\n" + details,
                            "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

                    if (confirmOption == JOptionPane.YES_OPTION) {
                        DatabaseManager.deleteEvaluation(selectedEvaluation);

                        refreshEvaluationList(jListEvaluation);
                    }
                }
        	}
        });
        
        detailsPanel.add(retourButton, BorderLayout.NORTH);
        detailsPanel.add(supprimerButton, BorderLayout.SOUTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        jListEvaluation.addListSelectionListener(e -> {
            int selectedIndex = jListEvaluation.getSelectedIndex();
            if (selectedIndex != -1) {
            	Evaluation selectedEvaluation = listEvaluations.get(selectedIndex);
            	String etudiantNom =  null;
				String etudiantPrenom =null;
            	for (Etudiant etudiant : listEtudiant) {
        			if (selectedEvaluation.getEtudiant() == etudiant.getId()){
        				etudiantNom = etudiant.getNom();
        				etudiantPrenom = etudiant.getPrenom();
        			}
        		}
            	String matiereNom = null;
            	for (Matiere matiere : listeMatiere) {
	                if (matiere.getNumeroMatiere() == selectedEvaluation.getMatiere()) {
	                	matiereNom = matiere.getNomMatiere();
	                }
	            }
                
                String details = "Nom de l'évaluation : " + selectedEvaluation.getNom() + "\n" +
                                 "Etudiant : " + etudiantNom + " " + etudiantPrenom + "\n" +
                                 "Matiere : " + matiereNom + "\n" +
                                 "Note : " + selectedEvaluation.getNote()
                                 ;

                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("");
            }
        });

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
        
    }

    private void refreshEvaluationList(JList<String> jListEvaluation) {
        List<Evaluation> listEvaluations = DatabaseManager.getEvaluations();
        
        DefaultListModel<String> listModel = (DefaultListModel<String>) jListEvaluation.getModel();
        listModel.clear(); 

        List<Etudiant> listEtudiant = DatabaseManager.getStudents();
        List<Matiere> listeMatiere = DatabaseManager.getMatiere();
        for (Evaluation evaluation : listEvaluations) {
        	if (evaluation.getEnseignant()== enseignant.getId()) {
        		for (Etudiant etudiant : listEtudiant) {
        			if (evaluation.getEtudiant() == etudiant.getId()){
        	            Matiere matiereEvaluation = null;
        	            
        	            for (Matiere matiere : listeMatiere) {
        	                if (matiere.getNumeroMatiere() == evaluation.getMatiere()) {
        	                	matiereEvaluation = matiere;
        	                }
        	            }
        				listModel.addElement(
        						evaluation.getNom() + " " + matiereEvaluation.getNomMatiere() + 
        						" / " + etudiant.getNom() + " " + etudiant.getPrenom() + 
        						" / Note : " + evaluation.getNote() 
        				);
        			}
        		}

        	}
        }
    }

    public static void main(String[] args) {
    	List<Evaluation> listEvaluations = DatabaseManager.getEvaluations();

        Enseignant enseignant = AppContext.getEnseignant();

        if (enseignant == null) {
            System.out.println("Enseignant non trouvé. Veuillez vérifier la connexion.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            ShowEvaluationsPage showEvaluationsPage = new ShowEvaluationsPage(enseignant, listEvaluations);
            showEvaluationsPage.setVisible(true);
        });
    }
}

