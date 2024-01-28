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

public class ShowAvertissementEtudiant extends JFrame {

    private Etudiant etudiant;
    private List<Avertissement> listAvertissements;

    public ShowAvertissementEtudiant(Etudiant etudiant, List<Avertissement> listAvertissements) {
        this.etudiant = etudiant;
        this.listAvertissements = listAvertissements;
        initUI();
    }
    
    public static class AppContext {
        private static Etudiant etudiant;

        public static Etudiant getEtudiant() {
            return etudiant;
        }

        public static void setEtudiant(Etudiant etudiant) {
            AppContext.etudiant = etudiant;
        }
    }

    private void initUI() {
        setTitle("Liste des avertissements");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<Enseignant> listEnseignant = DatabaseManager.getEnseignants();
        for (Avertissement avertissement : listAvertissements) {
        	if (avertissement.getEtudiant()== etudiant.getId()) {
        		for (Enseignant enseignant : listEnseignant) {
        			if (avertissement.getEnseignant() == enseignant.getId()){
        				listModel.addElement(avertissement.getNom() + " / " + enseignant.getNom() + " " + enseignant.getPrenom());
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

        
        detailsPanel.add(retourButton, BorderLayout.NORTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        jListAvertissements.addListSelectionListener(e -> {
            int selectedIndex = jListAvertissements.getSelectedIndex();
            if (selectedIndex != -1) {
            	Avertissement selectedAvertissement = listAvertissements.get(selectedIndex);
            	String enseignantNom =  null;
				String enseignantPrenom =null;
            	for (Enseignant enseignant : listEnseignant) {
        			if (selectedAvertissement.getEnseignant() == enseignant.getId()){
        				enseignantNom =  enseignant.getNom();
        				enseignantPrenom = enseignant.getPrenom();
        			}
        		}
                
                String details = "Raison de l'avertissement : " + selectedAvertissement.getNom() + "\n" +
                                 "Enseignant : " + enseignantNom + " " + enseignantPrenom;

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
    	List<Avertissement> listAvertissements = DatabaseManager.getAvertissement();

        Etudiant etudiant = AppContext.getEtudiant();

        if (etudiant == null) {
            System.out.println("Etudiant non trouvé. Veuillez vérifier la connexion.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            ShowAvertissementEtudiant showAvertissementPage = new ShowAvertissementEtudiant(etudiant, listAvertissements);
            showAvertissementPage.setVisible(true);
        });
    }
}

