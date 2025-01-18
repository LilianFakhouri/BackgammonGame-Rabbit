package GUI;

import javax.swing.JPanel;

import models.EnhancedDice;
import models.QuestionDice;

public class EnhancedDiceGUI extends JPanel {
    private EnhancedDice enhancedDice;

    public EnhancedDiceGUI() {
        // יצירת QuestionDice
        enhancedDice = new EnhancedDice();
     
        // הוספת ה-QuestionDice ל-PANEL
        setLayout(null); // לayout חופשי
        add(enhancedDice);
    }
 // Method to set the position of the dice dynamically
    public void setDiceBounds(int x, int y, int width, int height) {
    	enhancedDice.setBounds(x, y, width, height);
        revalidate();
        repaint();
    }
    // אם נדרש להפעיל את הקובייה
    public void rollDice() {
        
    	enhancedDice.roll();   
    }
}
