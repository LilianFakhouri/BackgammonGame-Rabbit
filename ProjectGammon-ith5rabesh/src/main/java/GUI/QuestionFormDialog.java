package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Utils.Level;
import models.Question;

public class QuestionFormDialog extends JDialog {
    private JTextField questionField;
    private JComboBox<Level> levelComboBox;
    private JTextField answer1Field;
    private JTextField answer2Field;
    private JTextField answer3Field;
    private JTextField answer4Field;
    private JComboBox<Integer> correctAnswerComboBox;
    private Question question;
    private boolean isConfirmed;

    public QuestionFormDialog(JFrame parent, String title) {
        this(parent, title, null);
    }

    public QuestionFormDialog(JFrame parent, String title, Question existingQuestion) {
        super(parent, title, true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Question:"));
        questionField = new JTextField();
        formPanel.add(questionField);

        formPanel.add(new JLabel("Level:"));
        levelComboBox = new JComboBox<>(Level.values());
        formPanel.add(levelComboBox);

        formPanel.add(new JLabel("Answer 1:"));
        answer1Field = new JTextField();
        formPanel.add(answer1Field);

        formPanel.add(new JLabel("Answer 2:"));
        answer2Field = new JTextField();
        formPanel.add(answer2Field);

        formPanel.add(new JLabel("Answer 3:"));
        answer3Field = new JTextField();
        formPanel.add(answer3Field);

        formPanel.add(new JLabel("Answer 4:"));
        answer4Field = new JTextField();
        formPanel.add(answer4Field);

        formPanel.add(new JLabel("Correct Answer (1-4):"));
        correctAnswerComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        formPanel.add(correctAnswerComboBox);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate Fields if Editing
        if (existingQuestion != null) {
            populateFields(existingQuestion);
        }

        // Button Actions
        confirmButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> onCancel());
    }

    private void populateFields(Question existingQuestion) {
        questionField.setText(existingQuestion.getQuestionContent());
        levelComboBox.setSelectedItem(existingQuestion.getLevel());
        answer1Field.setText(existingQuestion.getAnswer1());
        answer2Field.setText(existingQuestion.getAnswer2());
        answer3Field.setText(existingQuestion.getAnswer3());
        answer4Field.setText(existingQuestion.getAnswer4());
        correctAnswerComboBox.setSelectedItem(existingQuestion.getCorrectAnswerNumber());
    }

    private void onConfirm() {
        String questionContent = questionField.getText().trim();
        Level level = (Level) levelComboBox.getSelectedItem();
        String answer1 = answer1Field.getText().trim();
        String answer2 = answer2Field.getText().trim();
        String answer3 = answer3Field.getText().trim();
        String answer4 = answer4Field.getText().trim();
        int correctAnswer = (int) correctAnswerComboBox.getSelectedItem();

        if (questionContent.isEmpty() || answer1.isEmpty() || answer2.isEmpty() ||
            answer3.isEmpty() || answer4.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        question = new Question(questionContent, level, answer1, answer2, answer3, answer4, correctAnswer);
        isConfirmed = true;
        dispose();
    }

    private void onCancel() {
        question = null;
        isConfirmed = false;
        dispose();
    }

    public Question showDialog() {
        setVisible(true);
        return isConfirmed ? question : null;
    }
}
