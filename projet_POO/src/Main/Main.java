package Main;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class Main {

    public static void main(String[] args) {
        // Création d'une fenêtre
        Frame fenetre = new Frame("Interface Graphique AWT");

        // Utilisation de Font pour spécifier la taille de la police
        Font police = new Font("Arial", Font.PLAIN, 25); 

        // Création d'un groupe de cases à cocher
        CheckboxGroup choix = new CheckboxGroup();

        // Création des cases à cocher et ajout au groupe
        Checkbox etudiantCheckbox = new Checkbox("Etudiant", choix, true);
        Checkbox enseignantCheckbox = new Checkbox("Enseignant", choix, false);
        Checkbox personnelCheckbox = new Checkbox("Personnel", choix, false);

        // Ajout des cases à cocher à la fenêtre
        fenetre.add(etudiantCheckbox);
        fenetre.add(enseignantCheckbox);
        fenetre.add(personnelCheckbox);

        //Placement des cb
        etudiantCheckbox.setBounds(700,50,200,40);
        enseignantCheckbox.setBounds(700,100,200,40);
        personnelCheckbox.setBounds(700,150,200,40);


        //Création d'entrée texte
        TextField login = new TextField();
        TextField password = new TextField();


        login.setFont(police);
        password.setFont(police);

        // Ajout des CheckBox à la fenêtre
        login.setBounds(600,200,400,40);
        password.setBounds(600,250,400,40);
        password.setEchoChar('•');

        fenetre.add(login);
        fenetre.add(password);

        // Création du bouton
        Button bouton = new Button("Se connecter");

        // Ajout d'un gestionnaire d'événements pour le bouton
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bouton cliqué !");
            }
        });

        // Ajout du bouton à la fenêtre et spécification de sa position et taille
        bouton.setBounds(700, 300, 200, 30); // x, y, largeur, hauteur
        fenetre.add(bouton);

        // Configuration de la fenêtre
        fenetre.setSize(1600, 900); // Taille de la fenêtre en pixels
        fenetre.setLayout(null); // Pas de gestionnaire de disposition (layout) pour cet exemple
        fenetre.setVisible(true); // Rendre la fenêtre visible

        // Gestionnaire d'événements pour fermer la fenêtre lorsque l'utilisateur clique sur la croix de fermeture
        fenetre.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
}
