package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SurpriseStationBarr extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel surpriseLabel;
    private String gift;
    
    private boolean isUsed = false; // Tracks whether the station is already used

    /**
     * Constructor initializes the surprise station GUI
     * @param gift The gift associated with the station
     */
    public SurpriseStationBarr(String gift) {
        this.gift = gift;

        setLayout(null);
        setOpaque(false); // Ensure transparency for the panel

        // Label for the surprise mark
        surpriseLabel = new JLabel("$");
        surpriseLabel.setForeground(Color.RED); // Red color for the mark
        surpriseLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Adjust font size for visibility
        surpriseLabel.setBounds(10, 10, 50, 50); // Adjust label size and position
        add(surpriseLabel);
    }

    /**
     * Updates the gift message displayed on the surprise station.
     * Keeps the $ as the mark while updating internal state.
     * @param newGift New gift message
     */
    public void updateGift(String newGift) {
        this.gift = newGift;
        surpriseLabel.setText("$"); // The mark remains as $
        repaint();
    }

    /**
     * Resets the surprise station to a default state.
     * Clears the gift message but retains the $ mark.
     */
    public void resetStation() {
        this.gift = null; // Clear the gift message
        surpriseLabel.setText("$"); // Reset to just the mark
        repaint();
    }

    /**
     * Retrieves the current gift message associated with the station.
     * @return The gift message
     */
    public String getGift() {
        return gift;
    }

    /**
     * Sets a new gift message and updates the label display.
     * @param gift The new gift message to associate with the station
     */
    public void setGift(String gift) {
        this.gift = gift;
        surpriseLabel.setText("$"); // The mark remains $ but updates internally
        repaint();
    }

    /**
     * Customizes the appearance of the $ mark, such as font size or color.
     * Can be used dynamically to emphasize a surprise.
     * @param color The color of the $ mark
     * @param fontSize The size of the $ mark
     */
    public void customizeAppearance(Color color, int fontSize) {
        surpriseLabel.setForeground(color);
        surpriseLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        repaint();
    }
    


    public boolean isUsed() {
        return isUsed;
    }

    public void markAsUsed() {
        isUsed = true;
        surpriseLabel.setText(""); // Clear the `$` mark
        repaint();
    }

    
}
