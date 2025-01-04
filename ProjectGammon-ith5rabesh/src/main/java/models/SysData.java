package models;

import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Utils.Level;

public class SysData implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String QUESTIONS_FILE = "questions.json";
    private HashSet<Question> allQuestions = new HashSet<>();
    private static SysData sys = null;

    private SysData() {
        loadQuestions();
    }

    public static SysData getInstance() {
        if (sys == null) {
            sys = new SysData();
        }
        return sys;
    }

    public void loadQuestions() {
        allQuestions.clear();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(QUESTIONS_FILE)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + QUESTIONS_FILE);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray questionsArray = (JSONArray) jsonObject.get("questions");

            for (Object obj : questionsArray) {
                JSONObject questionObj = (JSONObject) obj;
                String content = (String) questionObj.get("question");
                JSONArray answers = (JSONArray) questionObj.get("answers");
                String answer1 = (String) answers.get(0);
                String answer2 = (String) answers.get(1);
                String answer3 = (String) answers.get(2);
                String answer4 = (String) answers.get(3);
                int correctAnswer = Integer.parseInt((String) questionObj.get("correct_ans"));

                String difficulty = (String) questionObj.get("difficulty");
                Level level;
                switch (difficulty) {
                    case "1":
                        level = Level.Easy;
                        break;
                    case "2":
                        level = Level.Medium;
                        break;
                    case "3":
                        level = Level.Hard;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown difficulty level: " + difficulty);
                }

                Question question = new Question(content, level, answer1, answer2, answer3, answer4, correctAnswer);
                allQuestions.add(question);
            }
            System.out.println("Questions loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeQuestionsToFile(String outputPath) {
        JSONArray questionsArray = new JSONArray();

        for (Question question : allQuestions) {
            JSONObject questionObj = new JSONObject();
            questionObj.put("question", question.getQuestionContent());

            String difficultyValue;
            switch (question.getLevel()) {
                case Easy:
                    difficultyValue = "1";
                    break;
                case Medium:
                    difficultyValue = "2";
                    break;
                case Hard:
                    difficultyValue = "3";
                    break;
                default:
                    throw new IllegalArgumentException("Unknown difficulty level: " + question.getLevel());
            }
            questionObj.put("difficulty", difficultyValue);

            JSONArray answers = new JSONArray();
            answers.add(question.getAnswer1());
            answers.add(question.getAnswer2());
            answers.add(question.getAnswer3());
            answers.add(question.getAnswer4());
            questionObj.put("answers", answers);

            questionObj.put("correct_ans", String.valueOf(question.getCorrectAnswerNumber()));
            questionsArray.add(questionObj);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("questions", questionsArray);

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(jsonObject.toJSONString());
            System.out.println("Questions saved successfully to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    public void addQuestion(Question question) {
        if (question.getQuestionContent() == null || question.getQuestionContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Question content cannot be empty.");
        }

        String[] answers = question.getAnswers();
        for (String answer : answers) {
            if (answer == null || answer.trim().isEmpty()) {
                throw new IllegalArgumentException("All answer options must be provided.");
            }
        }

       

        // If all validations pass, add the question
        allQuestions.add(question);
        writeQuestionsToFile("questions.json"); // Save to JSON
    }

    public void removeQuestion(Question question) {
        allQuestions.remove(question);
        writeQuestionsToFile("questions.json");
    }

    public void updateQuestion(Question oldQuestion, Question updatedQuestion) {
        allQuestions.remove(oldQuestion);
        allQuestions.add(updatedQuestion);
        writeQuestionsToFile("questions.json");
    }

    public HashSet<Question> getAllQuestions() {
        return allQuestions;
    }
    public void loadQuestionsFromInputStream(InputStream inputStream) {
        allQuestions.clear();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray questionsArray = (JSONArray) jsonObject.get("questions");

            for (Object obj : questionsArray) {
                JSONObject questionObj = (JSONObject) obj;
                String content = (String) questionObj.get("question");
                JSONArray answers = (JSONArray) questionObj.get("answers");
                String answer1 = (String) answers.get(0);
                String answer2 = (String) answers.get(1);
                String answer3 = (String) answers.get(2);
                String answer4 = (String) answers.get(3);
                int correctAnswer = Integer.parseInt((String) questionObj.get("correct_ans"));

                String difficulty = (String) questionObj.get("difficulty");
                Level level;
                switch (difficulty) {
                    case "1":
                        level = Level.Easy;
                        break;
                    case "2":
                        level = Level.Medium;
                        break;
                    case "3":
                        level = Level.Hard;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown difficulty level: " + difficulty);
                }

                Question question = new Question(content, level, answer1, answer2, answer3, answer4, correctAnswer);
                allQuestions.add(question);
            }
            System.out.println("Questions loaded successfully from resource stream.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    //Players
    
    private List<Player> allPlayers = new ArrayList<>();

 // Getter for the list of all players
 public List<Player> getAllPlayers() {
     return allPlayers;
 }
 
    public void addPlayer(Player player) {
        if (player.getPseudo() == null || player.getPseudo().trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }
        if (player.getImageSource() == null || player.getImageSource().trim().isEmpty()) {
            throw new IllegalArgumentException("Player avatar cannot be empty.");
        }
        allPlayers.add(player);
    }

}
