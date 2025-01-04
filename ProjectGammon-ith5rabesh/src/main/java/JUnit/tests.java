package JUnit;

import static org.junit.Assert.assertEquals;
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
	    @Test
	    public void testAddingNewQuestion() throws IOException, ParseException {
	        Level qLevel = Level.Medium;
	        Question question = new Question(
	            "A software component is an architectural entity that.",
	            qLevel,
	            "encapsulates a subset of the system's functionality and/or data",
	            "restricts access to that subset via an explicitly defined interface",
	            "has explicitly defined dependencies on its required execution context",
	            "all the answers are correct",
	            4
	        );

	        SysData.getInstance().addQuestion(question);

	        assertTrue("New question should appear in the list", SysData.getInstance().getAllQuestions().contains(question));
	    }

}