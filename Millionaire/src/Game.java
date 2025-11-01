import java.util.*;

/**
 * Main game controller for the Millionaire game.
 * Manages game state, flow, and player interactions.
 */
public class Game {
    
    private QuestionBank questionBank;
    private Lifelines lifelines;
    private int currentLevel;
    private int currentPrize;
    private boolean gameOver;
    private boolean gameWon;
    private Scanner scanner;
    
    public Game() {
        this.questionBank = new QuestionBank();
        this.lifelines = new Lifelines();
        this.currentLevel = 1;
        this.currentPrize = 0;
        this.gameOver = false;
        this.gameWon = false;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Start the game.
     */
    public void start() {
        displayWelcomeScreen();
        waitForEnter();
        
        // Main game loop
        while (!gameOver && currentLevel <= PrizeLadder.getTotalLevels()) {
            playQuestion();
            
            if (gameOver) {
                break;
            }
            
            currentLevel++;
        }
        
        if (gameWon) {
            displayWinScreen();
        } else {
            displayLoseScreen();
        }
    }
    
    /**
     * Display welcome screen with game rules.
     */
    private void displayWelcomeScreen() {
        TUI.clearScreen();
        TUI.displayTitle("WHO WANTS TO BE A MILLIONAIRE");
        
        System.out.println();
        TUI.printColor("Welcome to the Millionaire game!\n\n", TUI.BOLD + TUI.YELLOW);
        
        TUI.printColor("Game Rules:\n", TUI.BOLD + TUI.CYAN);
        System.out.println("  • Answer 15 questions correctly to win $1,000,000");
        System.out.println("  • Checkpoints at questions 5, 10, and 15 (safety nets)");
        System.out.println("  • Use lifelines: 50/50, Phone a Friend, Ask the Audience");
        System.out.println("  • You can walk away at any time by typing 'WALK'");
        System.out.println("  • Wrong answer before a checkpoint means you lose!");
        
        System.out.println();
        TUI.printColor("Lifelines:\n", TUI.BOLD + TUI.CYAN);
        System.out.println("  • 50/50: Eliminates two wrong answers");
        System.out.println("  • Phone a Friend: Get a friend's suggestion");
        System.out.println("  • Ask the Audience: See audience poll results");
        
        System.out.println();
        PrizeLadder.displayLadder(0);
        
        System.out.println();
        TUI.printColor("Press ENTER to start the game...", TUI.YELLOW);
    }
    
    /**
     * Wait for user to press Enter.
     */
    private void waitForEnter() {
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Continue
        }
    }
    
    /**
     * Play a single question.
     */
    private void playQuestion() {
        TUI.clearScreen();
        
        // Display prize ladder
        PrizeLadder.displayLadder(currentLevel);
        System.out.println();
        
        // Get question
        Question question = questionBank.getQuestion(currentLevel);
        int prize = PrizeLadder.getPrize(currentLevel);
        boolean isCheckpoint = PrizeLadder.isCheckpoint(currentLevel);
        
        // Display question header
        TUI.displayTitle("QUESTION " + currentLevel);
        TUI.printColor("Prize: ", TUI.BOLD);
        TUI.printlnColor(TUI.formatPrize(prize), TUI.BOLD + TUI.GREEN);
        if (isCheckpoint) {
            TUI.printlnColor("✓ CHECKPOINT - Safety net at this level!", TUI.BOLD + TUI.YELLOW);
        }
        System.out.println();
        
        // Display question
        question.displayQuestion();
        question.displayOptions();
        
        // Display lifelines
        displayLifelines();
        
        // Handle user input
        boolean answered = false;
        Map<String, String> currentOptions = question.getOptions();
        
        while (!answered && !gameOver) {
            System.out.println();
            TUI.printColor("Your answer (or 'LIFELINE' to use one, 'WALK' to walk away): ", TUI.YELLOW);
            String input = scanner.nextLine().toUpperCase().trim();
            
            if (input.equals("WALK")) {
                handleWalkAway();
                return;
            } else if (input.equals("LIFELINE") || input.startsWith("LIFELINE")) {
                currentOptions = handleLifeline(question, currentOptions);
            } else if (currentOptions.containsKey(input)) {
                // Valid answer option
                answered = true;
                handleAnswer(question, input, isCheckpoint);
            } else {
                TUI.printlnColor("Invalid input! Please enter a valid option or command.", TUI.RED);
            }
        }
    }
    
    /**
     * Display available lifelines.
     */
    private void displayLifelines() {
        if (!lifelines.hasAnyLifelines()) {
            return;
        }
        
        System.out.println();
        TUI.printColor("Available Lifelines: ", TUI.BOLD + TUI.PURPLE);
        List<String> available = lifelines.getAvailableLifelines();
        for (int i = 0; i < available.size(); i++) {
            TUI.printColor(available.get(i), TUI.PURPLE);
            if (i < available.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    /**
     * Handle lifeline usage.
     */
    private Map<String, String> handleLifeline(Question question, Map<String, String> currentOptions) {
        if (!lifelines.hasAnyLifelines()) {
            TUI.printlnColor("No lifelines available!", TUI.RED);
            return currentOptions;
        }
        
        System.out.println();
        TUI.printColor("Available lifelines:\n", TUI.BOLD + TUI.CYAN);
        List<String> available = lifelines.getAvailableLifelines();
        for (int i = 0; i < available.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + available.get(i));
        }
        
        TUI.printColor("\nEnter lifeline number or name: ", TUI.YELLOW);
        String choice = scanner.nextLine().trim();
        
        try {
            int lifelineNum = Integer.parseInt(choice);
            if (lifelineNum >= 1 && lifelineNum <= available.size()) {
                choice = available.get(lifelineNum - 1);
            }
        } catch (NumberFormatException e) {
            // Use choice as-is
        }
        
        try {
            Object result = lifelines.useLifeline(choice, question);
            
            if (result instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, ?> resultMap = (Map<String, ?>) result;
                
                // Check if it's Ask the Audience (values are integers) or 50/50 (values are strings)
                Object firstValue = resultMap.isEmpty() ? null : resultMap.values().iterator().next();
                if (firstValue instanceof Integer) {
                    // Ask the Audience - percentages already displayed in lifeline method, options remain the same
                    // No need to change options
                } else {
                    // 50/50 - return new options map and display reduced options
                    @SuppressWarnings("unchecked")
                    Map<String, String> reducedOptions = (Map<String, String>) resultMap;
                    
                    TUI.printColor("\nRemaining options:\n", TUI.BOLD + TUI.CYAN);
                    TUI.printColor("╔═══════════════════════════════════════════════════════════════════╗\n", TUI.CYAN);
                    TUI.printColor("║                           OPTIONS                                ║\n", TUI.CYAN);
                    TUI.printColor("╠═══════════════════════════════════════════════════════════════════╣\n", TUI.CYAN);
                    
                    String[] optionColors = {TUI.GREEN, TUI.BLUE};
                    int colorIndex = 0;
                    
                    for (String key : reducedOptions.keySet()) {
                        String color = optionColors[colorIndex % optionColors.length];
                        TUI.printColor("║ ", TUI.CYAN);
                        TUI.printColor(String.format("Option %s: ", key), TUI.BOLD + color);
                        TUI.printColor(String.format("%-60s", reducedOptions.get(key)), TUI.WHITE);
                        TUI.printlnColor(" ║", TUI.CYAN);
                        colorIndex++;
                    }
                    
                    TUI.printColor("╚═══════════════════════════════════════════════════════════════════╝\n", TUI.CYAN);
                    return reducedOptions;
                }
            } else if (result instanceof String) {
                // Phone a Friend - just show suggestion, don't change options
                // Options remain the same
            }
        } catch (Exception e) {
            TUI.printlnColor("Error using lifeline: " + e.getMessage(), TUI.RED);
        }
        
        return currentOptions;
    }
    
    /**
     * Handle player's answer.
     */
    private void handleAnswer(Question question, String answer, boolean isCheckpoint) {
        System.out.println();
        TUI.displaySeparator();
        
        if (question.isCorrect(answer)) {
            // Correct answer
            currentPrize = PrizeLadder.getPrize(currentLevel);
            TUI.printlnColor("\n✓ CORRECT ANSWER!", TUI.BOLD + TUI.GREEN);
            TUI.printColor("You've won: ", TUI.WHITE);
            TUI.printlnColor(TUI.formatPrize(currentPrize), TUI.BOLD + TUI.GREEN);
            
            if (currentLevel == PrizeLadder.getTotalLevels()) {
                gameWon = true;
                gameOver = true;
            }
        } else {
            // Wrong answer
            if (isCheckpoint) {
                // At checkpoint, player gets checkpoint prize
                currentPrize = PrizeLadder.getCheckpointPrize(currentLevel);
                TUI.printlnColor("\n✗ WRONG ANSWER!", TUI.BOLD + TUI.RED);
                TUI.printColor("But you're at a checkpoint! You walk away with: ", TUI.YELLOW);
                TUI.printlnColor(TUI.formatPrize(currentPrize), TUI.BOLD + TUI.YELLOW);
            } else {
                // Not at checkpoint, player loses
                currentPrize = PrizeLadder.getHighestCheckpointPrize(currentLevel);
                TUI.printlnColor("\n✗ WRONG ANSWER!", TUI.BOLD + TUI.RED);
                if (currentPrize > 0) {
                    TUI.printColor("You walk away with: ", TUI.YELLOW);
                    TUI.printlnColor(TUI.formatPrize(currentPrize), TUI.BOLD + TUI.YELLOW);
                } else {
                    TUI.printlnColor("You walk away with $0", TUI.RED);
                }
            }
            gameOver = true;
        }
        
        TUI.displaySeparator();
        waitForEnter();
    }
    
    /**
     * Handle walk away option.
     */
    private void handleWalkAway() {
        currentPrize = PrizeLadder.getHighestCheckpointPrize(currentLevel - 1);
        if (currentLevel > 1) {
            // Walk away with previous level's prize
            currentPrize = PrizeLadder.getPrize(currentLevel - 1);
        }
        
        System.out.println();
        TUI.printlnColor("You've decided to walk away!", TUI.BOLD + TUI.YELLOW);
        TUI.printColor("You take home: ", TUI.WHITE);
        TUI.printlnColor(TUI.formatPrize(currentPrize), TUI.BOLD + TUI.GREEN);
        
        gameOver = true;
        waitForEnter();
    }
    
    /**
     * Display win screen.
     */
    private void displayWinScreen() {
        TUI.clearScreen();
        TUI.displayTitle("CONGRATULATIONS!");
        
        System.out.println();
        TUI.printlnColor("🎉 YOU ARE A MILLIONAIRE! 🎉", TUI.BOLD + TUI.GREEN);
        System.out.println();
        TUI.printColor("You've successfully answered all 15 questions!", TUI.WHITE);
        System.out.println();
        TUI.printColor("Total Prize: ", TUI.BOLD);
        TUI.printlnColor(TUI.formatPrize(1_000_000), TUI.BOLD + TUI.GREEN);
        System.out.println();
        TUI.displaySeparator();
    }
    
    /**
     * Display lose screen.
     */
    private void displayLoseScreen() {
        TUI.clearScreen();
        TUI.displayTitle("GAME OVER");
        
        System.out.println();
        TUI.printColor("You made it to question " + (currentLevel - 1), TUI.WHITE);
        System.out.println();
        TUI.printColor("Total Prize: ", TUI.BOLD);
        TUI.printlnColor(TUI.formatPrize(currentPrize), TUI.BOLD + TUI.YELLOW);
        System.out.println();
        TUI.displaySeparator();
    }
    
    /**
     * Close resources.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}

