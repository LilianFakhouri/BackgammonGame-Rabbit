package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionStationBarr extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel questionLabel;
    private String question;

    /**
     * Constructor initializes the question station GUI
     * @param question The question or identifier for the station
     */
    public QuestionStationBarr(String question) {
        this.question = question;

        setLayout(null);
        setOpaque(false); // Ensure transparency for the panel

        // Label for the question mark
        questionLabel = new JLabel("?");
        questionLabel.setForeground(Color.BLUE); // Blue color for the question mark
        questionLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Adjust font size for visibility
        questionLabel.setBounds(10, 10, 50, 50); // Adjust label size and position
        add(questionLabel);
    }

    /**
     * Updates the question displayed on the station.
     * Keeps the ? as the mark while updating internal state.
     * @param newQuestion New question or identifier
     */
    public void updateQuestion(String newQuestion) {
        this.question = newQuestion;
        questionLabel.setText("?"); // The mark remains as ?
        repaint();
    }

    /**
     * Resets the question station to a default state.
     * Clears the question but retains the ? mark.
     */
    public void resetStation() {
        this.question = null; // Clear the question
        questionLabel.setText("?"); // Reset to just the mark
        repaint();
    }

    /**
     * Retrieves the current question associated with the station.
     * @return The question or identifier
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets a new question and updates the label display.
     * @param question The new question to associate with the station
     */
    public void setQuestion(String question) {
        this.question = question;
        questionLabel.setText("?"); // The mark remains ? but updates internally
        repaint();
    }

    /**
     * Customizes the appearance of the ? mark, such as font size or color.
     * Can be used dynamically to emphasize the station.
     * @param color The color of the ? mark
     * @param fontSize The size of the ? mark
     */
    public void customizeAppearance(Color color, int fontSize) {
        questionLabel.setForeground(color);
        questionLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        repaint();
    }
}
