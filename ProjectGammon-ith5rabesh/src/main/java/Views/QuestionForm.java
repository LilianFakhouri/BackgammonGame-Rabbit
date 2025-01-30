package Views;

import javax.swing.*;
import java.awt.*;
import models.Question;
import models.SysData;
import Utils.Level;
import controller.EmailService;

public class QuestionForm extends JDialog {

    private JTextField questionField;
    private JTextField answer1Field;
    private JTextField answer2Field;
    private JTextField answer3Field;
    private JTextField answer4Field;
    private JComboBox<Level> difficultyBox;
    private JComboBox<Integer> correctAnswerBox;

    private Question question;

    public QuestionForm(JFrame parent, Question question) {
        super(parent, question == null ? "Add Question" : "Edit Question", true);
        this.question = question;
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel(question == null ? "Add Question" : "Edit Question", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xCCCCCC));
        add(titleLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(new Color(0x333333));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Question Field
        formPanel.add(createLabel("Question:"));
        questionField = createTextField(question == null ? "" : question.getQuestionContent());
        formPanel.add(questionField);

        // Answer Fields
        formPanel.add(createLabel("Answer 1:"));
        answer1Field = createTextField(question == null ? "" : question.getAnswer1());
        formPanel.add(answer1Field);

        formPanel.add(createLabel("Answer 2:"));
        answer2Field = createTextField(question == null ? "" : question.getAnswer2());
        formPanel.add(answer2Field);

        formPanel.add(createLabel("Answer 3:"));
        answer3Field = createTextField(question == null ? "" : question.getAnswer3());
        formPanel.add(answer3Field);

        formPanel.add(createLabel("Answer 4:"));
        answer4Field = createTextField(question == null ? "" : question.getAnswer4());
        formPanel.add(answer4Field);

        // Difficulty Dropdown
        formPanel.add(createLabel("Difficulty:"));
        difficultyBox = new JComboBox<>(Level.values());
        difficultyBox.setSelectedItem(question == null ? Level.Easy : question.getLevel());
        formPanel.add(difficultyBox);

        // Correct Answer Dropdown
        formPanel.add(createLabel("Correct Answer:"));
        correctAnswerBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        correctAnswerBox.setSelectedItem(question == null ? 1 : question.getCorrectAnswerNumber());
        formPanel.add(correctAnswerBox);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x333333));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        styleButton(saveButton);
        styleButton(cancelButton);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        saveButton.addActionListener(e -> saveQuestion());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Creates a styled JLabel for the form.
     *
     * @param text The label text.
     * @return The styled JLabel.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(0xCCCCCC));
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    /**
     * Creates a styled JTextField for the form.
     *
     * @param text The initial text.
     * @return The styled JTextField.
     */
    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(0x555555));
        textField.setForeground(new Color(0xFFFFFF));
        textField.setCaretColor(new Color(0xFFFFFF));
        return textField;
    }

    /**
     * Styles a button with consistent design.
     *
     * @param button The button to style.
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(0x555555));
        button.setForeground(new Color(0xFFFFFF));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x888888)));
    }

    private void saveQuestion() {
        String questionContent = questionField.getText().trim();
        String answer1 = answer1Field.getText().trim();
        String answer2 = answer2Field.getText().trim();
        String answer3 = answer3Field.getText().trim();
        String answer4 = answer4Field.getText().trim();
        Level difficulty = (Level) difficultyBox.getSelectedItem();
        int correctAnswer = (int) correctAnswerBox.getSelectedItem();

        if (questionContent.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isNewQuestion = (question == null); // Check if this is a new question

        if (isNewQuestion) {
            // Add new question
            question = new Question(questionContent, difficulty, answer1, answer2, answer3, answer4, correctAnswer);
            SysData.getInstance().addQuestion(question);
        } else {
            // Update existing question
            question.setQuestionContent(questionContent);
            question.setAnswer1(answer1);
            question.setAnswer2(answer2);
            question.setAnswer3(answer3);
            question.setAnswer4(answer4);
            question.setLevel(difficulty);
            question.setCorrectAnswerNumber(correctAnswer);
        }

        SysData.getInstance().writeQuestionsToFile("questions.json");
        
        // **Send email notification only if it's a new question**
        if (isNewQuestion) {
            sendQuestionAddedEmail(questionContent, difficulty);
        }

        dispose();
    }

    private void sendQuestionAddedEmail(String questionContent, Level difficulty) {
        String answer1 = answer1Field.getText().trim();
        String answer2 = answer2Field.getText().trim();
        String answer3 = answer3Field.getText().trim();
        String answer4 = answer4Field.getText().trim();
        int correctAnswer = (int) correctAnswerBox.getSelectedItem();

        System.out.println("📧 Preparing to send email...");
        
        new Thread(() -> {
            try {
                System.out.println("📤 Sending email in background thread...");
                EmailService emailService = new EmailService();
                String recipientEmail = "rabbitdmn@gmail.com"; // Replace with your email
                String subject = "New Question Added";
                String content = "A new question has been added to the system:\n\n"
                        + "Question: " + questionContent + "\n"
                        + "Difficulty: " + difficulty.name() + "\n\n"
                        + "Answers:\n"
                        + "1. " + answer1 + "\n"
                        + "2. " + answer2 + "\n"
                        + "3. " + answer3 + "\n"
                        + "4. " + answer4 + "\n\n"
                        + "Correct Answer: " + correctAnswer + " (" + getCorrectAnswerText(correctAnswer) + ")\n\n"
                        + "Check the system for more details.";

                emailService.sendEmail(recipientEmail, subject, content);
                System.out.println("✅ Email sent successfully!");
            } catch (Exception e) {
                System.err.println("❌ Failed to send email.");
                e.printStackTrace();
            }
        }).start(); // Start the thread
    }

    /**
     * Returns the text of the correct answer based on the selected number.
     *
     * @param correctAnswer The number of the correct answer (1-4).
     * @return The text of the correct answer.
     */
    private String getCorrectAnswerText(int correctAnswer) {
        switch (correctAnswer) {
            case 1:
                return answer1Field.getText().trim();
            case 2:
                return answer2Field.getText().trim();
            case 3:
                return answer3Field.getText().trim();
            case 4:
                return answer4Field.getText().trim();
            default:
                return "Unknown";
        }
    }



}