package models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EnhancedDice extends JButton {

    private static final long serialVersionUID = 1L;
    //test

    // Paths to dice face images
    private static final String dice1 = "/images/des/dice1.png";
    private static final String dice2 = "/images/des/dice2.png";
    private static final String dice3 = "/images/des/dice3.png";
    private static final String dice4 = "/images/des/dice4.png";
    private static final String dice5 = "/images/des/dice5.png";
    private static final String dice6 = "/images/des/dice6.png";
    
    private static final String dice0 = "/images/des/zerodice.png"; // Special case when dice value is 0
    private static final String diceminus1 = "/images/des/minus1dice.png";
    private static final String diceminus2 = "/images/des/minus2dice.png";
    private static final String diceminus3 = "/images/des/minus3dice.png";
    
    private int currentValue; // The current face value of the dice
    private Random random; // Random number generator
    private ImageIcon icon; // Current icon for the dice face
    
    private static final int MIN_VALUE = -3; // Minimum dice value
    private static final int MAX_VALUE = 6; // Maximum dice value
    
    public EnhancedDice() {

        random = new Random();
        setOpaque(false); // Ensures transparency
        setPreferredSize(new Dimension(50, 50)); // Set the size of the dice button
        roll(); // Initialize the dice with a random value
    }

    // Rolls the dice and updates the face to a random value between MIN_VALUE and MAX_VALUE
    public int roll() {
        currentValue = random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE; // Random value between MIN_VALUE and MAX_VALUE
        updateFace(); // Update the dice face based on the new value
        return currentValue;
    }

    // Updates the dice face image based on the current value
    private void updateFace() {
        String iconPath = dice1; // Default to the first image
        switch (currentValue) {
            case 1: iconPath = dice1; break;
            case 2: iconPath = dice2; break;
            case 3: iconPath = dice3; break;
            case 4: iconPath = dice4; break;
            case 5: iconPath = dice5; break;
            case 6: iconPath = dice6; break;
            case 0: iconPath = dice0; break; // Special case for 0 value
            case -1: iconPath = diceminus1; break;
            case -2: iconPath = diceminus2; break;
            case -3: iconPath = diceminus3; break;
            default: throw new IllegalStateException("Unexpected value: " + currentValue);
        }
        
        // Load the image resource
        java.net.URL resource = getClass().getResource(iconPath);
        if (resource != null) {
            icon = new ImageIcon(resource); // Load the image if found
        } else {
            System.err.println("Error: Resource not found - " + iconPath);
            icon = null; // Fallback if resource is missing
        }
        
        repaint(); // Repaint the component to reflect changes
        updateUI(); // Update the button UI
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the dice image if available
        if (icon != null) {
            g2.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        g2.dispose();
    }

    // Returns the current value of the dice
    public int getCurrentValue() {
        return currentValue;
    }

    // Indicates whether this dice is a special dice (for instance, a question dice)
    public boolean isSpecialDice() {
        return currentValue == 0 || currentValue < 0;
    }

    // Sets the value of the dice directly (if needed for some specific logic)
    public void setValue(int value) {
        if (value >= MIN_VALUE && value <= MAX_VALUE) { // Ensure the value is within the valid range
            currentValue = value; // Set the current value to the passed value
            updateFace(); // Update the face of the dice
        }
    }
}
