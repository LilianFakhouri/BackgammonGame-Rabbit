package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import Views.QuestionManagementScreen;
import Views.VueMenu;

public class ManagerSignIn extends JPanel {

    private VueMenu vueMenu;

    public ManagerSignIn(VueMenu vueMenu) {
        this.vueMenu = vueMenu;
        setLayout(new GridBagLayout());
     // Set background color
        setBackground(Color.BLACK);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameLabel.setForeground(Color.WHITE); // Set text color to white

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordLabel.setForeground(Color.WHITE); // Set text color to white

        JButton signInButton = new JButton("Sign In");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(signInButton, gbc);

        signInButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("manager") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(null, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                JFrame questionFrame = new JFrame("Questions Management");
                questionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                questionFrame.setSize(800, 600);
                questionFrame.setLocationRelativeTo(null);
                questionFrame.add(new QuestionManagementScreen(vueMenu));
                questionFrame.setVisible(true);

                SwingUtilities.getWindowAncestor(ManagerSignIn.this).dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
