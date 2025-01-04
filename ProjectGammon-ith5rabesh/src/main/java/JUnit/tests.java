package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import models.Question;
import models.SysData;
import Utils.Level;
public class tests {
	

	//This test checks if the load is correct.
	 @Test
	    public void testload() throws IOException, ParseException {
		 
	        SysData.getInstance().loadQuestions();
	        System.out.print(SysData.getInstance().getAllQuestions());

	        assertTrue(SysData.getInstance().getAllQuestions() != null);
	    }
	 
	 // Test for adding a new question to the JSON file
	   // @Test
//	    public void testAddingNewQuestion() throws IOException, ParseException {
//	        Level qLevel = Level.Medium;
//	        Question question = new Question(
//	            "A software component is an architectural entity that.",
//	            qLevel,
//	            "encapsulates a subset of the system's functionality and/or data",
//	            "restricts access to that subset via an explicitly defined interface",
//	            "has explicitly defined dependencies on its required execution context",
//	            "all the answers are correct",
//	            4
//	        );
//
//	        SysData.getInstance().addQuestion(question);
//
//	        assertTrue("New question should appear in the list", SysData.getInstance().getAllQuestions().contains(question));
//	    }
	    // Test for adding a new question to the JSON file
	    @Test
	    public void testAddingNewQuestion() throws IOException, ParseException {
	        Level qLevel = Level.Medium;
	        Question question = new Question(
	            "What is the purpose of design patterns in software engineering?",
	            qLevel,
	            "To provide detailed, ready-to-use code for specific problems.",
	            "To offer a reusable solution to a common design problem.",
	            "To describe the user experience of a software product.",
	            "To define the steps for implementing software testing strategies.",
	            2
	        );

	        SysData.getInstance().addQuestion(question);

	        assertTrue("New question should appear in the list", SysData.getInstance().getAllQuestions().contains(question));
	    }

	    // Test for removing a question from the JSON file
//	 @Test
	    public void testRemovingQuestion() throws IOException, ParseException {
	        // Create a question identical to one in the JSON file
	        String content = "What is the purpose of design patterns in software engineering?";
	        String answer1 = "To provide detailed, ready-to-use code for specific problems.";
	        String answer2 = "To offer a reusable solution to a common design problem.";
	        String answer3 = "To describe the user experience of a software product.";
	        String answer4 = "To define the steps for implementing software testing strategies.";
	        int correctAnswer = 2;
	        Level level = Level.Medium;

	        Question tryQuestion = new Question(content, level, answer1, answer2, answer3, answer4, correctAnswer);

	        // Remove the question
	        SysData.getInstance().removeQuestion(tryQuestion);

	        // Assert that the question was removed
	        assertFalse("Removed question should not appear in the list", 
	                    SysData.getInstance().getAllQuestions().contains(tryQuestion));
	    }

	    @Test
	    public void answersNumOfQuestionsTest() {
	        for (Question q : SysData.getInstance().getAllQuestions()) {
	            ArrayList<String> actualAnswers = new ArrayList<>();
	            actualAnswers.add(q.getAnswer1());
	            actualAnswers.add(q.getAnswer2());
	            actualAnswers.add(q.getAnswer3());
	            actualAnswers.add(q.getAnswer4());

	            assertEquals("Each question should have exactly 4 answers", 4, actualAnswers.size());
	        }
	    }



	    // Test for specific question content validation
	    @Test
	    public void testSpecificQuestionContent() {
	        String content = "Which of the following is NOT an attribute of good software?";
	        boolean found = false;
	        for (Question q : SysData.getInstance().getAllQuestions()) {
	            if (q.getQuestionContent().equals(content)) {
	                found = true;
	                break;
	            }
	        }
	        assertTrue("Specific question should exist in the JSON", found);
	    }

}