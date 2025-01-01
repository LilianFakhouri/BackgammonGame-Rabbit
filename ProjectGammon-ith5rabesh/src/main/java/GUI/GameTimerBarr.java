package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameTimerBarr extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel timerLabel;
    private Timer timer;
    private int elapsedTime = 0; // Elapsed time in seconds

    /**
     * Constructor initializes the timer and UI components
     */
    public GameTimerBarr() {
        setLayout(null);
        setOpaque(false); // Ensure transparency

        // Timer label for elapsed time
        timerLabel = new JLabel("00:00:00");
        timerLabel.setForeground(new Color(0xCCCCCC));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size and style
        timerLabel.setBounds(0, 0, 100, 20); // Adjust label size
        add(timerLabel);

        startTimer();
    }

    /**
     * Starts the timer to update elapsed time
     */
    public void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            int hours = elapsedTime / 3600;
            int minutes = (elapsedTime % 3600) / 60;
            int seconds = elapsedTime % 60;

            // Format time as HH:MM:SS
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerLabel.setText(timeString);
        });
        timer.start();
    }

    /**
     * Stops the timer, can be called when the game ends
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    public void resetTimer() {
        stopTimer();
        elapsedTime = 0;
        updateLabel();
    }
    
    
    private void updateLabel() {
        int hours = elapsedTime / 3600;
        int minutes = (elapsedTime % 3600) / 60;
        int seconds = elapsedTime % 60;
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
