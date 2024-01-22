package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.text.MaskFormatter;
import classes.Cours;
import classes.Formation;
import classes.Promotion;
import controller.CoursController;
import database.DatabaseManager;

public class InterfaceAddCours {
    private JFrame mainFrame;
    private CoursController coursController = new CoursController();
    private Personnel person; // Assuming you have a Personnel class

    public void afficherInterface(Personnel person) {
        this.person = person;
        mainFrame = new JFrame("Page d'Ajout de Cours");
        mainFrame.setSize(400, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        mainFrame.add(panel);
        placeComponents(panel);

        JButton retourButton = new JButton("Retour");
        retourButton.setBounds(10, 340, 120, 25);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                // Replace 'AdminPage' with the appropriate class for navigating back
                new AdminPage().afficherInterface(person);
            }
        });
        panel.add(retourButton);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Similar to your existing placeComponents method, add components for adding courses.
        // You can reuse the structure and modify it accordingly.

        // Example components:
        JLabel coursLabel = new JLabel("Nom du cours:");
        coursLabel.setBounds(10, 20, 120, 25);
        panel.add(coursLabel);

        JTextField coursText = new JTextField(20);
        coursText.setBounds(150, 20, 165, 25);
        panel.add(coursText);

        // Add more components as needed for other course-related information.

        JButton addCoursButton = new JButton("Ajouter le cours");
        addCoursButton.setBounds(10, 300, 150, 25);

        addCoursButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve course information from the input fields
                String coursName = coursText.getText();
                // Retrieve other course-related information similarly

                if (coursName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        // Create a new Cours object with the retrieved information
                        Cours newCours = new Cours();
                        newCours.setNom(coursName);
                        // Set other course-related information similarly

                        // Call the method to insert the new course into the database
                        coursController.insertCours(newCours);

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
                new AdminPage().afficherInterface(person);
            }
        });

        messageFrame.setVisible(true);
    }
}
