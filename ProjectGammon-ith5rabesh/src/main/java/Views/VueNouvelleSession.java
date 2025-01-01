package Views;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.IconMonochromeType;
import GUI.MonochromeButton;
import GUI.MonochromeIconButton;
import GUI.PanelJoueur;
import GUI.PanelParametre;
import models.CouleurCase;
import models.Player;

public class VueNouvelleSession extends JPanel {
    
    private static final long serialVersionUID = -5590865478480555417L;
    
    private MonochromeButton boutonCommencer;
    private MonochromeIconButton boutonChangerCouleur;
    private PanelJoueur panelJoueur1;
    private PanelJoueur panelJoueur2;
    private PanelParametre panelParamètre;
    
    private MonochromeButton boutonChangerJoueurBlanc;
    private MonochromeButton boutonChangerJoueurNoir;
    
    private Player j1;
    private Player j2;
    
    private JCheckBox easyCheckBox;
    private JCheckBox mediumCheckBox;
    private JCheckBox hardCheckBox;

    /**
     * Constructor for VueNouvelleSession
     */
    public VueNouvelleSession() {
        build();
    }
    
    /**
     * Builds the UI and initializes components.
     */
    private void build() {        
        setLayout(null);

        // Initialize players as null
        j1 = null;
        j2 = null;

        // Create panels
        panelJoueur1 = new PanelJoueur(j1, CouleurCase.BLANC);
        panelJoueur2 = new PanelJoueur(j2, CouleurCase.NOIR);
        panelParamètre = new PanelParametre();

        // Start button
        boutonCommencer = new MonochromeButton("Start");

        // Level selection with checkboxes
        JLabel textLevel = new JLabel("Select Level:");
        textLevel.setForeground(new Color(0xCCCCCC));
        textLevel.setBounds(20, 10, 100, 30);
        add(textLevel);

        easyCheckBox = new JCheckBox("Easy");
        easyCheckBox.setBounds(130, 10, 70, 30);
        easyCheckBox.setBackground(new Color(0x333333));
        easyCheckBox.setForeground(new Color(0xCCCCCC));
        add(easyCheckBox);

        mediumCheckBox = new JCheckBox("Medium");
        mediumCheckBox.setBounds(200, 10, 90, 30);
        mediumCheckBox.setBackground(new Color(0x333333));
        mediumCheckBox.setForeground(new Color(0xCCCCCC));
        add(mediumCheckBox);

        hardCheckBox = new JCheckBox("Hard");
        hardCheckBox.setBounds(290, 10, 70, 30);
        hardCheckBox.setBackground(new Color(0x333333));
        hardCheckBox.setForeground(new Color(0xCCCCCC));
        add(hardCheckBox);

        // Ensure only one checkbox can be selected at a time
        easyCheckBox.addActionListener(e -> {
            if (easyCheckBox.isSelected()) {
                mediumCheckBox.setSelected(false);
                hardCheckBox.setSelected(false);
            }
        });

        mediumCheckBox.addActionListener(e -> {
            if (mediumCheckBox.isSelected()) {
                easyCheckBox.setSelected(false);
                hardCheckBox.setSelected(false);
            }
        });

        hardCheckBox.addActionListener(e -> {
            if (hardCheckBox.isSelected()) {
                easyCheckBox.setSelected(false);
                mediumCheckBox.setSelected(false);
            }
        });

        // Button to switch player colors
        boutonChangerCouleur = new MonochromeIconButton(IconMonochromeType.SWITCH, "MonochromeIconButton", "NOIR");
        boutonChangerCouleur.setSizeBig();
        boutonChangerCouleur.setToolTipText("Switch the colors of the players.");
        add(boutonChangerCouleur);

        // Buttons to change individual players
        boutonChangerJoueurBlanc = new MonochromeButton("Change White");
        boutonChangerJoueurBlanc.setBounds(250, 75, 105, 50);
        add(boutonChangerJoueurBlanc);

        boutonChangerJoueurNoir = new MonochromeButton("Change Black");
        boutonChangerJoueurNoir.setBounds(250, 285, 105, 50);
        add(boutonChangerJoueurNoir);

        // Set panel bounds
        panelJoueur1.setBounds(37, 35, 332, 141);
        panelJoueur2.setBounds(37, 245, 332, 141);
        panelParamètre.setBounds(420, 35, 344, 352);

        boutonCommencer.setBounds(200, 420, 380, 58);
        add(panelJoueur1);
        add(panelJoueur2);
        add(panelParamètre);
        add(boutonCommencer);

        // Dynamically position the "Switch Player Colors" button
        positionBoutonChangerCouleur();

        // Add listener for switching player colors
        listenerBoutonChangerCouleur();
    }

    /**
     * Dynamically positions the button to switch player colors.
     */
    private void positionBoutonChangerCouleur() {
        int panel1Y = panelJoueur1.getBounds().y + panelJoueur1.getBounds().height;
        int panel2Y = panelJoueur2.getBounds().y;
        int centerY = (panel1Y + panel2Y) / 2 - (boutonChangerCouleur.getPreferredSize().height / 2);

        boutonChangerCouleur.setBounds(
            panelJoueur1.getBounds().x + (panelJoueur1.getBounds().width / 2) - (boutonChangerCouleur.getPreferredSize().width / 2),
            centerY,
            boutonChangerCouleur.getPreferredSize().width,
            boutonChangerCouleur.getPreferredSize().height
        );
    }

    /**
     * Adds functionality to switch player colors when the button is clicked.
     */
    private void listenerBoutonChangerCouleur() {
        boutonChangerCouleur.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}        
            @Override
            public void mouseExited(MouseEvent e) {}            
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                Player temp = j1;
                setJoueur1(j2);
                setJoueur2(temp);
            }
        });
    }

    public void setJoueur1(Player jBlanc) {
        panelJoueur1.setJoueur(jBlanc);
        j1 = jBlanc;
    }

    public void setJoueur2(Player jNoir) {
        panelJoueur2.setJoueur(jNoir);
        j2 = jNoir;
    }

    public MonochromeButton getBoutonChangerJoueurBlanc() {
        return boutonChangerJoueurBlanc;
    }

    public MonochromeButton getBoutonChangerJoueurNoir() {
        return boutonChangerJoueurNoir;
    }

    public MonochromeButton getBoutonCommencer() {
        return boutonCommencer;
    }

    public MonochromeIconButton getBoutonChangerCouleur() {
        return boutonChangerCouleur;
    }

    public PanelJoueur getPanelJoueur1() {
        return panelJoueur1;
    }

    public PanelJoueur getPanelJoueur2() {
        return panelJoueur2;
    }

    public PanelParametre getPanelParamètre() {
        return panelParamètre;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        
        Paint p;
        int h = getHeight(); 
        int w = getWidth(); 
        
        // Background
        p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
                getHeight() / 2.0), 
                getHeight(),
                new float[] { 0.0f, 0.8f },
                new Color[] { new Color(0x333333), new Color(0x000000) },
                RadialGradientPaint.CycleMethod.NO_CYCLE);
        
        g2.setPaint(p); 
        g2.fillRect(0, 0, w, h); 
        
        // Border
        p = new Color(0x808080);
        g2.setStroke(new BasicStroke(5.0f));
        g2.setPaint(p); 
        g2.drawRect(2, 0, w - 5 , h - 5);
        
        g2.dispose(); 
    }
}
