import java.util.Map;

/**
 * Represents a multiple choice question with 4 options (A, B, C, D).
 * Displays options in a formatted box using TUI utilities.
 */
public class MultipleChoiceQuestion extends Question {
    
    public MultipleChoiceQuestion(String question, Map<String, String> options, String correctAnswer, int difficulty, int prize) {
        super(question, options, correctAnswer, difficulty, prize);
    }

    /**
     * Display options in a formatted box.
     */
    @Override
    public void displayOptions() {
        System.out.println();
        TUI.printColor("╔═══════════════════════════════════════════════════════════════════╗\n", TUI.CYAN);
        TUI.printColor("║                           OPTIONS                                 ║\n", TUI.CYAN);
        TUI.printColor("╠═══════════════════════════════════════════════════════════════════╣\n", TUI.CYAN);
        
        String[] optionColors = {TUI.GREEN, TUI.BLUE, TUI.YELLOW, TUI.PURPLE};
        int colorIndex = 0;
        
        for (String key : getOptions().keySet()) {
            String color = optionColors[colorIndex % optionColors.length];
            TUI.printColor("║ ", TUI.CYAN);
            TUI.printColor(String.format("Option %s: ", key), TUI.BOLD + color);
            TUI.printColor(String.format("%-60s", getOptions().get(key)), TUI.WHITE);
            TUI.printlnColor(" ║", TUI.CYAN);
            colorIndex++;
        }
        
        TUI.printColor("╚═══════════════════════════════════════════════════════════════════╝\n", TUI.CYAN);
    }
}

