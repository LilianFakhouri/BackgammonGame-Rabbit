package GUI;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import models.CouleurCase;
import models.DeSixFaces;

public class DeButton extends JButton {
    private static final long serialVersionUID = 2520612785614004478L;
    private static final String DE_BLANC_1 = "/images/des/de_blanc_1.png";
    private static final String DE_BLANC_2 = "/images/des/de_blanc_2.png";
    private static final String DE_BLANC_3 = "/images/des/de_blanc_3.png";
    private static final String DE_BLANC_4 = "/images/des/de_blanc_4.png";
    private static final String DE_BLANC_5 = "/images/des/de_blanc_5.png";
    private static final String DE_BLANC_6 = "/images/des/de_blanc_6.png";
    private static final String DE_NOIR_1 = "/images/des/de_noir_1.png";
    private static final String DE_NOIR_2 = "/images/des/de_noir_2.png";
    private static final String DE_NOIR_3 = "/images/des/de_noir_3.png";
    private static final String DE_NOIR_4 = "/images/des/de_noir_4.png";
    private static final String DE_NOIR_5 = "/images/des/de_noir_5.png";
    private static final String DE_NOIR_6 = "/images/des/de_noir_6.png";
    
    private static final String dice0 = "/images/des/zerodice.png"; // Special case when dice value is 0
    private static final String diceminus1 = "/images/des/minus1dice.png";
    private static final String diceminus2 = "/images/des/minus2dice.png";
    private static final String diceminus3 = "/images/des/minus3dice.png";

    
    

    private DeSixFaces de;
    private ImageIcon icon;

    public DeButton(DeSixFaces de) {
        this.de = de;
        setEnabled(false);
        setOpaque(false);
        setPreferredSize(new Dimension(32, 32));
        update();
    }

    public DeSixFaces getDe() {
        return de;
    }

    public void setDe(DeSixFaces de) {
        this.de = de;
        update();
    }

    private void update() {
        String iconRef = null;

        if (de.getCouleurDe() == CouleurCase.BLANC) {
            switch (de.getValeur()) {
                case -3: iconRef = diceminus3; break;
                case -2: iconRef = diceminus2; break;
                case -1: iconRef = diceminus1; break;
                case 0: iconRef = dice0; break;
                case 1: iconRef = DE_BLANC_1; break;
                case 2: iconRef = DE_BLANC_2; break;
                case 3: iconRef = DE_BLANC_3; break;
                case 4: iconRef = DE_BLANC_4; break;
                case 5: iconRef = DE_BLANC_5; break;
                case 6: iconRef = DE_BLANC_6; break;
                default: throw new IllegalArgumentException("Invalid dice value: " + de.getValeur());
            }
        } else {
            switch (de.getValeur()) {
            case -3: iconRef = diceminus3; break;
            case -2: iconRef = diceminus2; break;
            case -1: iconRef = diceminus1; break;
            case 0: iconRef = dice0; break;
                case 1: iconRef = DE_NOIR_1; break;
                case 2: iconRef = DE_NOIR_2; break;
                case 3: iconRef = DE_NOIR_3; break;
                case 4: iconRef = DE_NOIR_4; break;
                case 5: iconRef = DE_NOIR_5; break;
                case 6: iconRef = DE_NOIR_6; break;
                default: throw new IllegalArgumentException("Invalid dice value: " + de.getValeur());
            }
        }

        // Load the icon
        if (iconRef != null) {
            java.net.URL resource = getClass().getResource(iconRef);
            if (resource != null) {
                icon = new ImageIcon(resource);
            } else {
                System.err.println("Error: Icon not found for " + iconRef);
            }
        }
        updateUI();
    }




    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (de.isUtilise()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        }
        
        // Draw the icon with scaling
        if (icon != null) {
            int width = getWidth();   // Get the button's width
            int height = getHeight(); // Get the button's height
            g2.drawImage(icon.getImage(), 0, 0, width, height, this); // Scale image to fit the button
        }
        
        g2.dispose();
    }


    @Override
    protected void paintBorder(Graphics g) {
        // No border painting
    }
}
