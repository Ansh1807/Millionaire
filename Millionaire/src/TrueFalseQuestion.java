import java.util.HashMap;
import java.util.Map;

/**
 * Represents a True/False question type.
 * Extends Question with only two options: True and False.
 */
public class TrueFalseQuestion extends Question {
    
    /**
     * Creates a True/False question.
     * @param question The question text
     * @param correctAnswer "True" or "False"
     * @param difficulty Difficulty level (1-15)
     * @param prize Prize amount for this question
     */
    public TrueFalseQuestion(String question, String correctAnswer, int difficulty, int prize) {
        super(question, createTrueFalseOptions(), correctAnswer, difficulty, prize);
    }
    
    /**
     * Creates the standard True/False options map.
     */
    private static Map<String, String> createTrueFalseOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("T", "True");
        options.put("F", "False");
        return options;
    }
    
    /**
     * Display the question and options in a formatted way.
     */
    @Override
    public void displayOptions() {
        System.out.println();
        TUI.printColor("┌─────────────────────────────────────────┐\n", TUI.CYAN);
        TUI.printColor("│ ", TUI.CYAN);
        TUI.printColor("Option T: True", TUI.BOLD + TUI.GREEN);
        TUI.printlnColor("                            │", TUI.CYAN);
        TUI.printColor("│ ", TUI.CYAN);
        TUI.printColor("Option F: False", TUI.BOLD + TUI.RED);
        TUI.printlnColor("                           │", TUI.CYAN);
        TUI.printColor("└─────────────────────────────────────────┘\n", TUI.CYAN);
    }
    
    /**
     * Override getInput to provide True/False specific prompt.
     */
    @Override
    @SuppressWarnings("resource")
    public String getInput() {
        // Scanner for System.in should not be closed in console applications
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;
        while (true) {
            TUI.printColor("Enter your answer (T for True, F for False): ", TUI.YELLOW);
            input = scanner.nextLine().toUpperCase().trim();
            if (input.equals("T") || input.equals("F")) {
                return input;
            } else {
                TUI.printlnColor("Invalid input! Please enter T for True or F for False.", TUI.RED);
            }
        }
    }
}

