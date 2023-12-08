package Main;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
 
        Frame fenetre = new Frame("Interface Graphique AWT");


        Button bouton = new Button("Cliquez-moi");

        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bouton");
            }
        });

        bouton.setBounds(50, 50, 200, 30); 
        fenetre.add(bouton);

        fenetre.setSize(300, 200); 
        fenetre.setLayout(null); 
        fenetre.setVisible(true); 
        fenetre.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
}
