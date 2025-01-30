package Views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GUI.ManagerSignIn;
import GUI.MonochromeButton;
import GUI.MonochromeVue;

public class VueMenu extends MonochromeVue {

    private static final long serialVersionUID = 3060121008717453091L;
    public static final String img_menu = "images/menu_bg.png";

    // Buttons
    private MonochromeButton boutonNouvellePartie;
    private MonochromeButton boutonReprendrePartie;
    private MonochromeButton boutonAjouter;
    private MonochromeButton boutonAide;
    private MonochromeButton boutonQuitter;
    private MonochromeButton HistoryOfGames;
    private MonochromeButton boutonQuestionsManagement;
    private MonochromeButton Manager;

    private ImageIcon icon;

    public VueMenu() {
        build();
    }

    private void build() {
        // Load background image
        try {
            icon = new ImageIcon(img_menu);
        } catch (Exception err) {
            System.err.println("Error loading background image: " + err.getMessage());
        }

        // Enable free placement of panels
        setLayout(null);

        // Create panels for buttons
        JPanel conteneurgrid = new JPanel();
        JPanel conteneurbouton = new JPanel();

        GridLayout gl = new GridLayout(7, 1); // 7 rows for the existing buttons

        // Configure the grid container
        conteneurgrid.setBounds(200, 150, 400, 450); // Adjust size for the central buttons
        conteneurgrid.setOpaque(false);

        // Configure the button container
        gl.setVgap(10); // Vertical gap between buttons
        conteneurbouton.setLayout(gl);
        conteneurbouton.setPreferredSize(new Dimension(400, 400));
        conteneurbouton.setOpaque(false);

        // Initialize central buttons
        boutonNouvellePartie = new MonochromeButton("New Session");
        conteneurbouton.add(boutonNouvellePartie);

        boutonReprendrePartie = new MonochromeButton("Load Session");
        conteneurbouton.add(boutonReprendrePartie);

        boutonAjouter = new MonochromeButton("Player List");
        conteneurbouton.add(boutonAjouter);

        boutonAide = new MonochromeButton("Rules");
        conteneurbouton.add(boutonAide);

        boutonQuestionsManagement = new MonochromeButton("Questions Management");
        conteneurbouton.add(boutonQuestionsManagement);

        HistoryOfGames = new MonochromeButton("HistoryOfGames");
        conteneurbouton.add(HistoryOfGames);

        boutonQuitter = new MonochromeButton("Quit");
        conteneurbouton.add(boutonQuitter);

        // Add central button container
        add(conteneurgrid);
        conteneurgrid.add(conteneurbouton);

        // Add ActionListeners for existing buttons
        boutonQuestionsManagement.addActionListener(e -> openQuestionManagementScreen());
        HistoryOfGames.addActionListener(e -> openGameHistoryScreen());

        // Create a top-right panel for the Manager button
        JPanel managerPanel = new JPanel();
        managerPanel.setBounds(550, 20, 200, 60); // Position: Top-right corner
        managerPanel.setOpaque(false); // Transparent background
        managerPanel.setLayout((LayoutManager) new FlowLayout(FlowLayout.RIGHT)); // Align content to the right

        // Create and add the Manager button
        Manager = new MonochromeButton("Sign In");
        Manager.setPreferredSize(new Dimension(120, 40)); // Adjust button size
        managerPanel.add(Manager);

        // Add ActionListener for the Manager button
        Manager.addActionListener(e -> openSignInScreen());

        // Add the Manager button panel to the screen
        add(managerPanel);
    }

    private void openSignInScreen() {
        JFrame signInFrame = new JFrame("Manager Sign In");
        signInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signInFrame.setSize(400, 300);
        signInFrame.setLocationRelativeTo(null);
        signInFrame.add(new ManagerSignIn(this));
        signInFrame.setVisible(true);
    }
    //test

    private void openQuestionManagementScreen() {
        System.out.println("Switching to Question Management Screen...");

        // Get the parent JFrame
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            // Clear the current content
            parentFrame.getContentPane().removeAll();

            // Add QuestionManagementScreen as the new content
            parentFrame.setContentPane(new QuestionManagementScreen(this));

            // Revalidate and repaint to refresh the frame
            parentFrame.revalidate();
            parentFrame.repaint();

            System.out.println("Successfully switched to Question Management Screen.");
        } else {
            System.err.println("Parent frame not found!");
        }
    }

    private void openGameHistoryScreen() {
        System.out.println("Switching to Game History Screen...");

        // Get the parent JFrame
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            // Clear the current content
            parentFrame.getContentPane().removeAll();

            // Add GameHistoryScreen as the new content
            parentFrame.setContentPane(new GameHistoryScreen());

            // Revalidate and repaint to refresh the frame
            parentFrame.revalidate();
            parentFrame.repaint();

            System.out.println("Successfully switched to Game History Screen.");
        } else {
            System.err.println("Parent frame not found!");
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(icon.getImage(), 0, 0, this);
    }

    // Getters for other buttons
    public MonochromeButton getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public MonochromeButton getBoutonReprendrePartie() {
        return boutonReprendrePartie;
    }
 // In VueIntermediairePartie
    public MonochromeButton getHistoryOfGamesButton() {
        return HistoryOfGames;  // Make sure the button is correctly initialized in the GUI
    }


    public MonochromeButton getBoutonAjouter() {
        return boutonAjouter;
    }

    public MonochromeButton getBoutonAide() {
        return boutonAide;
    }

    public MonochromeButton getBoutonQuitter() {
        return boutonQuitter;
    }
    
    public MonochromeButton getBoutonQuestionsManagement(){
    	return boutonQuestionsManagement;
    }
    
    
}