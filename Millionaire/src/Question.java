import java.util.Map;
import java.util.Scanner;

public abstract class Question {

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    private String question;
    private Map <String , String>  options;
    private String correctAnswer;
    private int difficulty;
    private int prize;



    public Question(String question, Map<String, String> options, String correctAnswer, int difficulty, int prize) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.prize = prize;

    }

    public boolean isCorrect(String userAnswer) throws RuntimeException{
        if (this.options.get(userAnswer).equals(correctAnswer)){
            return true;
        }
        return false;
    }


    public  void displayQuestion() {
        System.out.println(question);
    }

    public abstract void displayOptions();

    public  String getInput(){
        Scanner newScanner = new Scanner(System.in);
        System.out.print("Enter an option from "+getOptions().keySet()+": ");
        return newScanner.nextLine().toUpperCase();
    }


}
