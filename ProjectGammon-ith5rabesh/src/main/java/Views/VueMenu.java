package Views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    private MonochromeButton boutonQuestionsManagement;

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

        GridLayout gl = new GridLayout(7, 1); // 7 rows for the new button

        // Configure the grid container
        conteneurgrid.setBounds(200, 150, 400, 450); // Adjust size for the extra button
        conteneurgrid.setOpaque(false);

        // Configure the button container
        gl.setVgap(10); // Vertical gap between buttons
        conteneurbouton.setLayout(gl);
        conteneurbouton.setPreferredSize(new Dimension(400, 400));
        conteneurbouton.setOpaque(false);

        // Initialize buttons
        boutonNouvellePartie = new MonochromeButton("New Session");
        conteneurbouton.add(boutonNouvellePartie);

        boutonReprendrePartie = new MonochromeButton("Load Session");
        conteneurbouton.add(boutonReprendrePartie);

        boutonAjouter = new MonochromeButton("Player List");
        conteneurbouton.add(boutonAjouter);

        boutonAide = new MonochromeButton("Rules");
        conteneurbouton.add(boutonAide);

        boutonQuestionsManagement = new MonochromeButton("Questions Management");
        conteneurbouton.add(boutonQuestionsManagement); // Add the new button
        

        boutonQuitter = new MonochromeButton("Quit");
        conteneurbouton.add(boutonQuitter);

        add(conteneurgrid);
        conteneurgrid.add(conteneurbouton);
        
        

        // Add ActionListener for Questions Management
        
        boutonQuestionsManagement.addActionListener(e -> openQuestionManagementScreen());

    }
    private void openQuestionManagementScreen() {
        System.out.println("Switching to Question Management Screen...");

        // Get the parent JFrame
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            // Clear the current content
            parentFrame.getContentPane().removeAll();

            // Add QuestionManagementScreen as the new content
            parentFrame.setContentPane(new QuestionManagementScreen());

            // Revalidate and repaint to refresh the frame
            parentFrame.revalidate();
            parentFrame.repaint();

            System.out.println("Successfully switched to Question Management Screen.");
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

    public MonochromeButton getBoutonAjouter() {
        return boutonAjouter;
    }

    public MonochromeButton getBoutonAide() {
        return boutonAide;
    }

    public MonochromeButton getBoutonQuitter() {
        return boutonQuitter;
    }
}
