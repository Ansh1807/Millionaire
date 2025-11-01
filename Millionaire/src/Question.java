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

    /**
     * Check if the user's answer is correct.
     * @param userAnswer The option key (e.g., "A", "B", "T", "F")
     * @return true if correct, false otherwise
     */
    public boolean isCorrect(String userAnswer) {
        if (userAnswer == null) {
            return false;
        }
        userAnswer = userAnswer.toUpperCase().trim();
        
        // Check if the option key exists
        if (!options.containsKey(userAnswer)) {
            return false;
        }
        
        // Compare the option value with the correct answer
        String selectedAnswer = options.get(userAnswer);
        return selectedAnswer.equals(correctAnswer);
    }



    public void displayQuestion() {
        TUI.printColor("\nQuestion: ", TUI.BOLD + TUI.CYAN);
        TUI.printlnColor(question, TUI.WHITE);
        System.out.println();
    }

    public abstract void displayOptions();

    /**
     * Get input from the user.
     * @return The user's selected option key
     */
    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            TUI.printColor("Enter an option from " + getOptions().keySet() + ": ", TUI.YELLOW);
            return scanner.nextLine().toUpperCase().trim();
        } finally {
            // System.in typically doesn't need closing, but this satisfies linter
        }
    }


}
