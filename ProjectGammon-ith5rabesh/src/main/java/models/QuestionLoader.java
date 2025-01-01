/*
 * package models;
 * 
 * import java.io.FileReader; import java.util.ArrayList; import java.util.List;
 * 
 * public class QuestionLoader {
 * 
 * public static List<Question> loadQuestions(String filePath) { List<Question>
 * questions = new ArrayList<>();
 * 
 * try { JSONParser parser = new JSONParser(); JSONObject rootObject =
 * (JSONObject) parser.parse(new FileReader(filePath)); JSONArray questionArray
 * = (JSONArray) rootObject.get("questions");
 * 
 * for (Object obj : questionArray) { JSONObject questionObject = (JSONObject)
 * obj; String questionContent = (String) questionObject.get("question");
 * JSONArray answersArray = (JSONArray) questionObject.get("answers");
 * 
 * String[] answers = new String[answersArray.size()]; for (int i = 0; i <
 * answersArray.size(); i++) { answers[i] = (String) answersArray.get(i); }
 * 
 * int correctAnswerNumber = Integer.parseInt((String)
 * questionObject.get("correct_ans")); Level level = Level.valueOf((String)
 * questionObject.get("difficulty"));
 * 
 * Question question = new Question(questionContent, level, answers,
 * correctAnswerNumber); questions.add(question); } } catch (Exception e) {
 * e.printStackTrace(); }
 * 
 * return questions; } }
 */