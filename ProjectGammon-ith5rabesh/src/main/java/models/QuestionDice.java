package models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Utils.Level;

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
        setPreferredSize(new Dimension(10, 10)); // Set the size of the dice
      //  roll(); // Initialize the dice with a random value (but don't trigger a question)
    }

    public Utils.Level getLevelByCurrentValue(int currentValue) {
        switch (currentValue) {
            case 1:
                return Level.Easy;
            case 2:
                return Level.Medium;
            case 3:
                return Level.Hard;
            default:
                throw new IllegalStateException("Invalid dice value: " + currentValue);
        }
    }

 

    public void handleQuestionByLevel(int level) {
    	   Utils.Level levels = getLevelByCurrentValue(level); // Get difficulty level based on roll value
        System.out.println("we are in " + level);

        SysData sysData = SysData.getInstance();
        Question question = sysData.getRandomQuestionByLevel(levels); // Retrieve a random question for the level

        if (question != null) {
            JOptionPane.showMessageDialog(this,
                    "Question: " + question.getQuestionContent(),
                    "Level: " + level,
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No question available for level: " + level,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rolls the dice and updates the face to a random value between 1 and 3.
     * @return The new value of the dice.
     */
    public int roll() {
        currentValue = random.nextInt(3) + 1; // Generate a value between 1 and 3
        updateFace(); // Update the dice face based on the new value 
       System.out.println(currentValue);
      handleQuestionByLevel(currentValue);
        return currentValue;
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

    public void setValue(int value) {
        if (value >= 1 && value <= 3) { // Ensure the value is within the valid range
            currentValue = value; // Set the current value to the passed value
            updateFace(); // Update the face of the dice
        }
    }

    /**
     * Indicates that this dice always triggers a question.
     * @return true, as this is a dedicated Question Dice.
     */
    public boolean triggersQuestion() {
        return true;
    }
}
