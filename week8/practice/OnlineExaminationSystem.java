import java.util.*;

abstract class Question {
    String text;
    String correctAnswer;
    public Question(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }
    abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(String text, String correctAnswer) {
        super(text, correctAnswer);
    }
    @Override
    boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Attempt {
    Student student;
    Examination examination;
    Map<Integer, String> answers = new HashMap<>();
    boolean isSubmitted = false;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void recordAnswer(int questionId, String answer) {
        if (!isSubmitted) {
            answers.put(questionId, answer);
            System.out.println("Question " + questionId + " answered with '" + answer + "'.");
        }
    }

    public void submit() {
        isSubmitted = true;
        System.out.println("Examination '" + examination.name + "' submitted successfully.");
    }

    public void evaluate() {
        int score = 0;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            Question q = examination.questions.get(entry.getKey() - 1);
            if (q.evaluate(entry.getValue())) {
                score++;
            }
        }
        System.out.println("Result for '" + examination.name + "' attempt: " + score + "/" + examination.questions.size() + " correct");
    }
}

class Examination {
    String name;
    List<Question> questions = new ArrayList<>();

    public Examination(String name) {
        this.name = name;
    }
}

class Student {
    String name;
    public Student(String name) {
        this.name = name;
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.questions.add(new MultipleChoiceQuestion("1+1", "A"));
        exam.questions.add(new MultipleChoiceQuestion("2+2", "B"));

        Student student = new Student("Student");
        System.out.println("Examination 'Math Quiz' started by Student.");

        Attempt attempt = new Attempt(student, exam);
        attempt.recordAnswer(1, "A");
        attempt.recordAnswer(2, "C");
        attempt.submit();
        
        attempt.evaluate();
    }
}
