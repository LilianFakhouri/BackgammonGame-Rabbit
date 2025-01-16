package models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class QuestionDice extends JButton {

    private static final long serialVersionUID = 2520612785614004478L;

    // Paths to dice face images
    private static final String DE_1 = "/images/des/q1.png";
    private static final String DE_2 = "/images/des/q2.png";
    private static final String DE_3 = "/images/des/q3.png";

    private int currentValue; // The current face value of the dice
    private Random random; // Random number generator
    private ImageIcon icon; // Current icon for the dice face

    public QuestionDice() {
        random = new Random();
        setOpaque(false); // Ensures transparency
        setPreferredSize(new Dimension(64, 64)); // Set the size of the dice
        roll(); // Initialize the dice with a random value
    }

    /**
     * Rolls the dice and updates the face to a random value between 1 and 3.
     * @return The new value of the dice.
     */
    public int roll() {
        currentValue = random.nextInt(3) + 1; // Generate a value between 1 and 3
        updateFace(); // Update the dice face based on the new value
        return currentValue;
    }

    /**
     * Returns the current value of the dice.
     * @return The current face value of the dice.
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Indicates that this dice always triggers a question.
     * @return true, as this is a dedicated Question Dice.
     */
    public boolean triggersQuestion() {
        return true;
    }

    /**
     * Updates the dice face image based on the current value.
     */
    private void updateFace() {
        String iconPath = DE_1; // Default to the first image
        switch (currentValue) {
            case 1: iconPath = DE_1; break;
            case 2: iconPath = DE_2; break;
            case 3: iconPath = DE_3; break;
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
}
