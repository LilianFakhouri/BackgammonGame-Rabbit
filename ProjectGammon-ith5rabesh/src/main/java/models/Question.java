package models;

import Utils.Level;

public class Question implements  java.io.Serializable,Comparable<Question>{
	private static int questionNumber=1;
	private int questionId;
	private String questionContent;
	private Level level;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
	private int correctAnswerNumber;
	
	

	public Question(String theQuestion, Level theLevel, String answer1, String answer2, String answer3, String answer4,
			int correctAnswerNumber) {
		this.questionId=questionNumber++;
		this.questionContent = theQuestion;
		this.level = theLevel;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.correctAnswerNumber = correctAnswerNumber;
	}

	

	public Question(String questionContent) {
		super();
		this.questionContent = questionContent;
	}



	public static int getQuestionNumber() {
		return questionNumber;
	}



	public static void setQuestionNumber(int questionNumber) {
		Question.questionNumber = questionNumber;
	}



	public int getQuestionId() {
		return questionId;
	}



	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}



	public String getQuestionContent() {
		return questionContent;
	}



	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}



	public Level getLevel() {
		return level;
	}



	public void setLevel(Level level) {
		this.level = level;
	}



	public String getAnswer1() {
		return answer1;
	}



	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}



	public String getAnswer2() {
		return answer2;
	}



	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}



	public String getAnswer3() {
		return answer3;
	}



	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}



	public String getAnswer4() {
		return answer4;
	}


	public void setCorrectAnswerNumber(int correctAnswerNumber) {
		   if (correctAnswerNumber < 0 || correctAnswerNumber >4) {
	        	
	            throw new IllegalArgumentException("Invalid Index number like this");
	        } else {
	            this.correctAnswerNumber = correctAnswerNumber;
	        }
	}


	public int getCorrectAnswerNumber() {
		return correctAnswerNumber;
	}



	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}



	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionContent=" + questionContent + ", level=" + level
				+ ", answer1=" + answer1 + ", answer2=" + answer2 + ", answer3=" + answer3 + ", answer4=" + answer4
				+ ", correctAnswerNumber=" + correctAnswerNumber + "]";
	}



	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionContent == null) ? 0 : questionContent.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionContent == null) {
			if (other.questionContent != null)
				return false;
		} else if (!questionContent.equals(other.questionContent))
			return false;
		return true;
	}



	@Override
	public int compareTo(Question o) {
		// TODO Auto-generated method stub
		return this.questionContent.compareToIgnoreCase(o.getQuestionContent());

	}
	
	public String[] getAnswers() {
	    return new String[]{answer1, answer2, answer3, answer4};
	}

	
	

	

}



