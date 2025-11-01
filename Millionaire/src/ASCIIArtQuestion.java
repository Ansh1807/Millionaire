import java.util.Map;

/**
 * Represents a question with ASCII art as the question display.
 * The ASCII art is stored in the question text field and displayed
 * in a formatted multi-line box.
 */
public class ASCIIArtQuestion extends Question {
    
    /**
     * Creates an ASCII art question.
     * @param asciiArt The ASCII art text (can be multi-line, use \n for line breaks)
     * @param questionText The actual question text about the ASCII art
     * @param options The answer options
     * @param correctAnswer The key of the correct answer
     * @param difficulty Difficulty level (1-15)
     * @param prize Prize amount for this question
     */
    public ASCIIArtQuestion(String asciiArt, String questionText, Map<String, String> options, 
                           String correctAnswer, int difficulty, int prize) {
        // Store both ASCII art and question text, separated by a special marker
        super(asciiArt + "\n---QUESTION---\n" + questionText, options, correctAnswer, difficulty, prize);
    }
    
    /**
     * Display the ASCII art in a formatted box, then the question.
     */
    @Override
    public void displayQuestion() {
        String fullText = getQuestion();
        String[] parts = fullText.split("\n---QUESTION---\n");
        
        if (parts.length == 2) {
            String asciiArt = parts[0];
            String questionText = parts[1];
            
            // Display ASCII art in a box
            String[] asciiLines = asciiArt.split("\n");
            System.out.println();
            TUI.printColor("╔═══════════════════════════════════════════════════════════════════╗\n", TUI.CYAN);
            for (String line : asciiLines) {
                TUI.printColor("║ ", TUI.CYAN);
                TUI.printColor(TUI.centerText(line.trim(), 65), TUI.BOLD + TUI.YELLOW);
                TUI.printlnColor(" ║", TUI.CYAN);
            }
            TUI.printColor("╚═══════════════════════════════════════════════════════════════════╝\n", TUI.CYAN);
            System.out.println();
            
            // Display the question text
            TUI.printColor("Question: ", TUI.BOLD + TUI.CYAN);
            TUI.printlnColor(questionText, TUI.WHITE);
            System.out.println();
        } else {
            // Fallback if format is wrong
            System.out.println(getQuestion());
        }
    }
    
    /**
     * Display options in a formatted way for ASCII art questions.
     */
    @Override
    public void displayOptions() {
        System.out.println();
        TUI.printColor("╔═══════════════════════════════════════════════════════════════════╗\n", TUI.CYAN);
        TUI.printColor("║                           OPTIONS                                 ║\n", TUI.CYAN);
        TUI.printColor("╠═══════════════════════════════════════════════════════════════════╣\n", TUI.CYAN);
        
        for (String key : getOptions().keySet()) {
            TUI.printColor("║ ", TUI.CYAN);
            TUI.printColor(String.format("Option %s: %-60s", key, getOptions().get(key)), TUI.WHITE);
            TUI.printlnColor(" ║", TUI.CYAN);
        }
        
        TUI.printColor("╚═══════════════════════════════════════════════════════════════════╝\n", TUI.CYAN);
    }
}

