package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Question;
import models.SysData;

public class QuestionAnswerScreen extends JFrame {

    private Question currentQuestion;

    public QuestionAnswerScreen() {
        setTitle("Answer the Question");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(null);

        // Create the background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0x1A1A1D), // Top (Dark Gray)
                        0, getHeight(), new Color(0x4E4E50) // Bottom (Medium Gray)
                );

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };

        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Load a random question from SysData
        SysData sysData = SysData.getInstance();
        if (sysData == null || sysData.getAllQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Question> questions = new ArrayList<>(sysData.getAllQuestions());
        if (!questions.isEmpty()) {
            Random random = new Random();
            currentQuestion = questions.get(random.nextInt(questions.size()));
        }

        // Title Label
        JLabel titleLabel = new JLabel("Answer the Question", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(150, 20, 500, 50);
        backgroundPanel.add(titleLabel);

        // Question content
        JLabel questionLabel = new JLabel("<html>" + currentQuestion.getQuestionContent() + "</html>", JLabel.CENTER);
        questionLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
        questionLabel.setForeground(new Color(0xEAEAEA));
        questionLabel.setBounds(50, 100, 700, 100);
        backgroundPanel.add(questionLabel);

        // Answers panel
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new GridLayout(4, 1, 10, 10));
        answersPanel.setBounds(150, 220, 500, 200);
        answersPanel.setOpaque(false); // Transparent panel
        ButtonGroup answerGroup = new ButtonGroup();
        JRadioButton[] answerButtons = new JRadioButton[4];
        String[] answers = {
                currentQuestion.getAnswer1(),
                currentQuestion.getAnswer2(),
                currentQuestion.getAnswer3(),
                currentQuestion.getAnswer4()
        };

        for (int i = 0; i < answers.length; i++) {
            answerButtons[i] = new JRadioButton(answers[i]);
            answerButtons[i].setFont(new Font("Roboto", Font.PLAIN, 18));
            answerButtons[i].setForeground(Color.WHITE);
            answerButtons[i].setBackground(new Color(0x333333));
            answerButtons[i].setFocusPainted(false);
            answerButtons[i].setOpaque(true);
            answerGroup.add(answerButtons[i]);
            answersPanel.add(answerButtons[i]);
        }
        backgroundPanel.add(answersPanel);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0xA9A9A9)); // Light Gray
        submitButton.setForeground(Color.BLACK); // Black text for better contrast
        submitButton.setFocusPainted(false);
        submitButton.setBounds(325, 440, 150, 50);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedAnswer = -1;
                for (int i = 0; i < answerButtons.length; i++) {
                    if (answerButtons[i].isSelected()) {
                        selectedAnswer = i + 1;
                        break;
                    }
                }

                // Feedback area
                JLabel feedbackLabel = new JLabel("", JLabel.CENTER);
                feedbackLabel.setFont(new Font("Roboto", Font.BOLD, 18));
                feedbackLabel.setBounds(50, 510, 700, 40);
                feedbackLabel.setForeground(Color.WHITE);
                backgroundPanel.add(feedbackLabel);

                if (selectedAnswer == -1) {
                    JOptionPane.showMessageDialog(QuestionAnswerScreen.this, "Please select an answer!", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (selectedAnswer == currentQuestion.getCorrectAnswerNumber()) {
                        feedbackLabel.setText("Correct! Well done.");
                        feedbackLabel.setForeground(new Color(0x4CAF50)); // Green
                    } else {
                        feedbackLabel.setText("Wrong! The correct answer is: " + answers[currentQuestion.getCorrectAnswerNumber() - 1]);
                        feedbackLabel.setForeground(new Color(0xFF6347)); // Red
                    }
                }
                feedbackLabel.setVisible(true);
                revalidate();
                repaint();
            }
        });
        backgroundPanel.add(submitButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionAnswerScreen().setVisible(true));
    }
}
