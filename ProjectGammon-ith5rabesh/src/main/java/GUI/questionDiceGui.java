package GUI;

import javax.swing.JPanel;
import models.QuestionDice;

public class questionDiceGui extends JPanel {
    private QuestionDice questionDice;

    public questionDiceGui() {
        // יצירת QuestionDice
        questionDice = new QuestionDice();
     
        // הוספת ה-QuestionDice ל-PANEL
        setLayout(null); // לayout חופשי
        add(questionDice);
    }
 // Method to set the position of the dice dynamically
    public void setDiceBounds(int x, int y, int width, int height) {
        questionDice.setBounds(x, y, width, height);
        revalidate();
        repaint();
    }
    // אם נדרש להפעיל את הקובייה
    public void rollDice() {
        
    	questionDice.roll();   
    }
}
