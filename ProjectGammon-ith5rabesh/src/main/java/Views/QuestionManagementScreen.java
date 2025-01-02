package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import models.Question;
import models.SysData;

public class QuestionManagementScreen extends JPanel {

    private JLabel questionLabel;
    private JLabel answerLabel;
    private JLabel correctAnswerLabel;
    private int currentIndex = 0; // Current question index
    private List<Question> questions;

    public QuestionManagementScreen() {
        build();
    }

    /**
     * Builds the UI and initializes components.
     */
    private void build() {
        setLayout(null);
        setPreferredSize(new Dimension(800, 600)); // Set screen size

        // Load questions
        questions = new ArrayList<>(SysData.getInstance().getAllQuestions());
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available!");
            return;
        }

        // Title
        JLabel titleLabel = new JLabel("Manage Questions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xCCCCCC));
        titleLabel.setBounds(200, 20, 400, 40);
        add(titleLabel);

        // Navigation Buttons
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        prevButton.setBounds(50, 250, 100, 40);
        nextButton.setBounds(650, 250, 100, 40);
        add(prevButton);
        add(nextButton);

        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());

        // Question Display
        questionLabel = new JLabel("", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(new Color(0xCCCCCC));
        questionLabel.setBounds(50, 100, 700, 100); // Adjusted bounds for better visibility
        add(questionLabel);

        answerLabel = new JLabel("", JLabel.CENTER);
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        answerLabel.setForeground(new Color(0xCCCCCC));
        answerLabel.setBounds(50, 200, 700, 100); // Adjusted bounds for better spacing
        add(answerLabel);

        correctAnswerLabel = new JLabel("", JLabel.CENTER);
        correctAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        correctAnswerLabel.setForeground(new Color(0xCCCCCC));
        correctAnswerLabel.setBounds(50, 320, 700, 30); // Adjusted bounds for better spacing
        add(correctAnswerLabel);

        // Action Buttons
        JButton addButton = new JButton("Add Question");
        JButton editButton = new JButton("Edit Question");
        JButton deleteButton = new JButton("Delete Question");

        addButton.setBounds(150, 400, 150, 40);
        editButton.setBounds(325, 400, 150, 40);
        deleteButton.setBounds(500, 400, 150, 40);

        add(addButton);
        add(editButton);
        add(deleteButton);

        addButton.addActionListener(e -> addQuestion());
        editButton.addActionListener(e -> editQuestion());
        deleteButton.addActionListener(e -> deleteQuestion());

        // Initialize the first question display
        updateQuestionDisplay();
    }

    /**
     * Updates the displayed question.
     */
    private void updateQuestionDisplay() {
        if (questions.isEmpty()) {
            questionLabel.setText("No questions available.");
            answerLabel.setText("");
            correctAnswerLabel.setText("");
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        questionLabel.setText("<html><div style='text-align: center;'>Question:<br>" + currentQuestion.getQuestionContent() + "</div></html>");
        answerLabel.setText(
                "<html><div style='text-align: center;'>"
                        + "1. " + currentQuestion.getAnswer1() + "<br>"
                        + "2. " + currentQuestion.getAnswer2() + "<br>"
                        + "3. " + currentQuestion.getAnswer3() + "<br>"
                        + "4. " + currentQuestion.getAnswer4()
                        + "</div></html>"
        );
        correctAnswerLabel.setText("Correct Answer: " + currentQuestion.getCorrectAnswerNumber());
    }

    private void showNextQuestion() {
        if (!questions.isEmpty()) {
            currentIndex = (currentIndex + 1) % questions.size(); // Loop back to the start
            updateQuestionDisplay();
        }
    }

    private void showPreviousQuestion() {
        if (!questions.isEmpty()) {
            currentIndex = (currentIndex - 1 + questions.size()) % questions.size(); // Loop back to the end
            updateQuestionDisplay();
        }
    }

    private void addQuestion() {
        QuestionForm dialog = new QuestionForm(null, null);
        dialog.setVisible(true);
        JOptionPane.showMessageDialog(this, "Question Added Successfully!");
        refreshQuestions();
    }

    private void editQuestion() {
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions to edit!");
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        QuestionForm dialog = new QuestionForm(null, currentQuestion);
        dialog.setVisible(true);
        JOptionPane.showMessageDialog(this, "Question Updated Successfully!");
        refreshQuestions();
    }

    private void deleteQuestion() {
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions to delete!");
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this question?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            SysData.getInstance().removeQuestion(currentQuestion);
            SysData.getInstance().writeQuestionsToFile("questions.json");
            refreshQuestions();
        }
    }

    private void refreshQuestions() {
        questions = new ArrayList<>(SysData.getInstance().getAllQuestions());
        if (questions.isEmpty()) {
            currentIndex = 0;
        } else {
            currentIndex = Math.min(currentIndex, questions.size() - 1);
        }
        updateQuestionDisplay();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint p;
        int h = getHeight();
        int w = getWidth();

        // Background
        p = new RadialGradientPaint(
                new Point2D.Double(getWidth() / 2.0, getHeight() / 2.0),
                getHeight(),
                new float[]{0.0f, 0.8f},
                new Color[]{new Color(0x333333), new Color(0x000000)},
                RadialGradientPaint.CycleMethod.NO_CYCLE
        );

        g2.setPaint(p);
        g2.fillRect(0, 0, w, h);

        // Border
        p = new Color(0x808080);
        g2.setStroke(new BasicStroke(5.0f));
        g2.setPaint(p);
        g2.drawRect(2, 0, w - 5, h - 5);

        g2.dispose();
    }
    



    public static void main(String[] args) {
        JFrame frame = new JFrame("Questions Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new QuestionManagementScreen());
        frame.setVisible(true);
    }
}
