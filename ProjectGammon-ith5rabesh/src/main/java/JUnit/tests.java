package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import models.Question;
import models.NiveauAssistant;
import models.Player;
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

	 @Test
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
	    
	    @Test
	    public void updateQuestionsTest() throws IOException, ParseException {
	        Level qLevel = Level.Easy;

	        // Updated question with modified details
	        Question questionUpdated = new Question(
	            "What is the primary focus of Software Engineering?",
	            qLevel,
	            "Designing hardware components.",
	            "Developing theories and fundamentals of computing.",
	            "Delivering reliable and useful software products.", // Same as old question
	            "Creating comprehensive software testing processes.", // Updated option
	            4 // Updated correct answer
	        );

	        // Old question before the update
	        Question oldQuestion = new Question(
	            "What is the primary focus of Software Engineering?",
	            qLevel,
	            "Designing hardware components.",
	            "Developing theories and fundamentals of computing.",
	            "Delivering reliable and useful software products.",
	            "Creating user manuals for software systems.",
	            3 // Original correct answer
	        );

	        // Update the question in the system
	        SysData.getInstance().updateQuestion(oldQuestion, questionUpdated);

	        // Verify the updated question exists in the system with the correct changes
	        boolean foundUpdatedQuestion = false;

	        for (Question q : SysData.getInstance().getAllQuestions()) {
	            if (q.getQuestionContent().equals(questionUpdated.getQuestionContent())) {
	                foundUpdatedQuestion = true;

	                // Validate that the question options and answer are updated
	                assertEquals("Correct answer number should be updated", 4, q.getCorrectAnswerNumber());
	                assertTrue("Updated option should be reflected",
	                    q.getAnswers()[3].equals("Creating comprehensive software testing processes."));
	                assertFalse("Old option should no longer exist",
	                    q.getAnswers()[3].equals("Creating user manuals for software systems."));
	            }
	        }

	        // Ensure the updated question was actually found in the list
	        assertTrue("Updated question should be present in the system", foundUpdatedQuestion);
	    }
	    


	    // Test for mandatory fields when adding a new question
	    @Test
	    public void testMandatoryFieldsForNewQuestion() {
	        Level qLevel = Level.Easy;

	        try {
	            // Attempt to create an invalid question with empty fields
	            Question invalidQuestion = new Question("", qLevel, "", "", "", "", 1);

	            // Add the invalid question to the system
	            SysData.getInstance().addQuestion(invalidQuestion);

	            // Assert that the invalid question was not added to the system
	            assertTrue("Invalid question should not be added", 
	                       !SysData.getInstance().getAllQuestions().contains(invalidQuestion));
	        } catch (IllegalArgumentException e) {
	            // Expected behavior: the constructor or addQuestion should throw an exception for invalid input
	            System.out.println("Caught expected exception: " + e.getMessage());
	        }
	    }
	 
	     @Test
	    public void testAddPlayerValid() {
	        // Create a valid player using the no-argument constructor
	        Player validPlayer = new Player();
	        validPlayer.setPseudo("John");
	        validPlayer.setImageSource("avatar1.png");
	        validPlayer.setNiveauAssistant(NiveauAssistant.SIMPLE);

	        // Add the player to the system
	        SysData.getInstance().addPlayer(validPlayer);

	        // Assert the player is added successfully
	        assertTrue("Valid player should be added successfully", 
	                   SysData.getInstance().getAllPlayers().contains(validPlayer));
	    }
	     //check if the addPlayer is correct
	   @Test
	    public void testAddPlayerInvalid() {
	        try {
	            // Attempt to add a player with missing name
	            Player invalidPlayer = new Player();
	            invalidPlayer.setPseudo(""); // Missing name
	            invalidPlayer.setImageSource("avatar1.png");
	            SysData.getInstance().addPlayer(invalidPlayer);
	            fail("Adding a player with missing name should throw an exception.");
	        } catch (IllegalArgumentException e) {
	            assertEquals("Player name cannot be empty.", e.getMessage());
	        }

	        try {
	            // Attempt to add a player with missing avatar
	            Player invalidPlayer = new Player();
	            invalidPlayer.setPseudo("John");
	            invalidPlayer.setImageSource(""); // Missing avatar
	            SysData.getInstance().addPlayer(invalidPlayer);
	            fail("Adding a player with missing avatar should throw an exception.");
	        } catch (IllegalArgumentException e) {
	            assertEquals("Player avatar cannot be empty.", e.getMessage());
	        }
	    }

}