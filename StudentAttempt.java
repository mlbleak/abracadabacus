package abracadabacus;


public class StudentAttempt {
	
	private final String  abacusColumns;
	private final String  abacusSize;
	private final String  grade;
	private final String  lesson;
	private final String  name;
	private final String  result;
	
	StudentAttempt(String abacusColumns, String abacusSize, String grade, String lesson, String name, String result) {
		this.abacusColumns = abacusColumns;
		this.abacusSize = abacusSize;
		this.grade = grade;
		this.lesson = lesson;
		this.name = name;
		this.result = result;
	}

	public String getAbacusColumns() {
		return abacusColumns;
	}

	public String getAbacusSize() {
		return abacusSize;
	}

	public String getGrade() {
		return grade;
	}

	public String getLesson() {
		return lesson;
	}

	public String getName() {
		return name;
	}

	public String getResult() {
		return result;
	}

}
