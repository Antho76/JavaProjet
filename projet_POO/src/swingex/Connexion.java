package swingex;
import javax.swing.*;
import java.awt.*;

class Connexion {
    public static void main(String args[]) {
        // Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.gridwidth = GridBagConstraints.REMAINDER;
        gbcLogin.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Connexion");

        loginPanel.add(userLabel, gbcLogin);
        loginPanel.add(userField, gbcLogin);
        loginPanel.add(passLabel, gbcLogin);
        loginPanel.add(passField, gbcLogin);
        loginPanel.add(loginButton, gbcLogin);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        // Center Panel for loginPanel and textArea
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.weightx = 1;
        gbcCenter.weighty = 1;
        gbcCenter.fill = GridBagConstraints.BOTH;

        centerPanel.add(loginPanel, gbcCenter);

        gbcCenter.gridy++;
        centerPanel.add(new JScrollPane(ta), gbcCenter);


            
        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.setVisible(true);
    }
}
