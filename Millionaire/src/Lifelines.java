import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Manages lifelines for the Millionaire game.
 * Implements: 50/50, Phone a Friend, and Ask the Audience.
 */
public class Lifelines {
    
    private static final String FIFTY_FIFTY = "50/50";
    private static final String PHONE_FRIEND = "Phone a Friend";
    private static final String ASK_AUDIENCE = "Ask the Audience";
    
    private ArrayList<String> availableLifelines;
    private Random random;
    
    public Lifelines() {
        this.availableLifelines = new ArrayList<>();
        this.availableLifelines.add(FIFTY_FIFTY);
        this.availableLifelines.add(PHONE_FRIEND);
        this.availableLifelines.add(ASK_AUDIENCE);
        this.random = new Random();
    }
    
    /**
     * Get list of available lifelines.
     */
    public ArrayList<String> getAvailableLifelines() {
        return new ArrayList<>(availableLifelines);
    }
    
    /**
     * Check if a lifeline is available.
     */
    public boolean isAvailable(String lifeline) {
        return availableLifelines.contains(lifeline);
    }
    
    /**
     * Check if any lifelines are available.
     */
    public boolean hasAnyLifelines() {
        return !availableLifelines.isEmpty();
    }
    
    /**
     * Use 50/50 lifeline - removes 2 incorrect answers.
     * @param question The current question
     * @return Modified options map with only 2 options remaining
     */
    public Map<String, String> useFiftyFifty(Question question) {
        if (!isAvailable(FIFTY_FIFTY)) {
            throw new IllegalStateException("50/50 lifeline is not available!");
        }
        
        Map<String, String> options = new HashMap<>(question.getOptions());
        String correctKey = null;
        
        // Find the key for the correct answer
        for (Map.Entry<String, String> entry : options.entrySet()) {
            if (entry.getValue().equals(question.getCorrectAnswer())) {
                correctKey = entry.getKey();
                break;
            }
        }
        
        if (correctKey == null) {
            throw new RuntimeException("Could not find correct answer in options!");
        }
        
        // Get all incorrect keys
        List<String> incorrectKeys = new ArrayList<>();
        for (String key : options.keySet()) {
            if (!key.equals(correctKey)) {
                incorrectKeys.add(key);
            }
        }
        
        // Remove 2 random incorrect answers
        while (incorrectKeys.size() > 1) {
            int randomIndex = random.nextInt(incorrectKeys.size());
            incorrectKeys.remove(randomIndex);
        }
        
        // Create new map with only correct answer and one wrong answer
        Map<String, String> remainingOptions = new HashMap<>();
        remainingOptions.put(correctKey, options.get(correctKey));
        remainingOptions.put(incorrectKeys.get(0), options.get(incorrectKeys.get(0)));
        
        availableLifelines.remove(FIFTY_FIFTY);
        
        TUI.printlnColor("\n" + "=".repeat(60), TUI.YELLOW);
        TUI.printlnColor("50/50 LIFELINE USED!", TUI.BOLD + TUI.YELLOW);
        TUI.printlnColor("Two incorrect answers have been removed.", TUI.WHITE);
        TUI.printlnColor("=".repeat(60) + "\n", TUI.YELLOW);
        
        return remainingOptions;
    }
    
    /**
     * Use Phone a Friend lifeline.
     * 70% chance of correct answer, 30% chance of wrong answer.
     * @param question The current question
     * @return The friend's suggested answer (key)
     */
    public String usePhoneAFriend(Question question) {
        if (!isAvailable(PHONE_FRIEND)) {
            throw new IllegalStateException("Phone a Friend lifeline is not available!");
        }
        
        String friendAnswer;
        boolean isCorrect = random.nextDouble() < 0.70; // 70% chance
        
        if (isCorrect) {
            // Find correct answer key
            for (Map.Entry<String, String> entry : question.getOptions().entrySet()) {
                if (entry.getValue().equals(question.getCorrectAnswer())) {
                    friendAnswer = entry.getKey();
                    availableLifelines.remove(PHONE_FRIEND);
                    
                    TUI.printlnColor("\n" + "=".repeat(60), TUI.CYAN);
                    TUI.printlnColor("PHONE A FRIEND LIFELINE USED!", TUI.BOLD + TUI.CYAN);
                    TUI.printColor("Your friend says: \"I'm ", TUI.WHITE);
                    TUI.printColor(String.valueOf((random.nextInt(40) + 60)) + "%", TUI.BOLD + TUI.GREEN);
                    TUI.printlnColor(" sure the answer is " + friendAnswer + "!\"", TUI.WHITE);
                    TUI.printlnColor("=".repeat(60) + "\n", TUI.CYAN);
                    
                    return friendAnswer;
                }
            }
        }
        
        // 30% chance - give a random wrong answer
        List<String> keys = new ArrayList<>(question.getOptions().keySet());
        String correctKey = null;
        for (Map.Entry<String, String> entry : question.getOptions().entrySet()) {
            if (entry.getValue().equals(question.getCorrectAnswer())) {
                correctKey = entry.getKey();
                break;
            }
        }
        if (correctKey != null) {
            keys.remove(correctKey);
        }
        friendAnswer = keys.get(random.nextInt(keys.size()));
        
        availableLifelines.remove(PHONE_FRIEND);
        
        TUI.printlnColor("\n" + "=".repeat(60), TUI.CYAN);
        TUI.printlnColor("PHONE A FRIEND LIFELINE USED!", TUI.BOLD + TUI.CYAN);
        TUI.printColor("Your friend says: \"Hmm, I think it might be ", TUI.WHITE);
        TUI.printColor(friendAnswer, TUI.YELLOW);
        TUI.printlnColor(", but I'm not 100% sure...\"", TUI.WHITE);
        TUI.printlnColor("=".repeat(60) + "\n", TUI.CYAN);
        
        return friendAnswer;
    }
    
    /**
     * Use Ask the Audience lifeline.
     * Shows percentage distribution biased toward correct answer.
     * @param question The current question
     * @return Map of option keys to percentage votes
     */
    public Map<String, Integer> useAskTheAudience(Question question) {
        if (!isAvailable(ASK_AUDIENCE)) {
            throw new IllegalStateException("Ask the Audience lifeline is not available!");
        }
        
        Map<String, Integer> percentages = new HashMap<>();
        List<String> keys = new ArrayList<>(question.getOptions().keySet());
        String correctKey = null;
        
        // Find correct answer key
        for (Map.Entry<String, String> entry : question.getOptions().entrySet()) {
            if (entry.getValue().equals(question.getCorrectAnswer())) {
                correctKey = entry.getKey();
                break;
            }
        }
        
        // Allocate percentages with bias toward correct answer
        if (correctKey != null) {
            // Correct answer gets 45-65% of votes
            int correctPercent = 45 + random.nextInt(21);
            percentages.put(correctKey, correctPercent);
            
            int remaining = 100 - correctPercent;
            // Distribute remaining among wrong answers
            for (String key : keys) {
                if (!key.equals(correctKey)) {
                    int percent = remaining / (keys.size() - 1);
                    if (random.nextBoolean() && percent > 5) {
                        percent += random.nextInt(10) - 5; // Add some variation
                    }
                    percentages.put(key, Math.max(5, Math.min(percent, remaining)));
                    remaining -= Math.max(5, Math.min(percent, remaining));
                }
            }
            
            // Adjust for rounding errors
            if (remaining != 0) {
                String lastKey = keys.get(keys.size() - 1);
                percentages.put(lastKey, percentages.get(lastKey) + remaining);
            }
        }
        
        availableLifelines.remove(ASK_AUDIENCE);
        
        // Display audience results
        TUI.printlnColor("\n" + "=".repeat(60), TUI.PURPLE);
        TUI.printlnColor("ASK THE AUDIENCE LIFELINE USED!", TUI.BOLD + TUI.PURPLE);
        TUI.printlnColor("The audience votes:", TUI.WHITE);
        System.out.println();
        
        for (String key : keys) {
            int percent = percentages.get(key);
            TUI.printColor("Option " + key + ": ", TUI.WHITE);
            // Create a visual bar
            int barLength = (percent / 2);
            StringBuilder bar = new StringBuilder();
            for (int i = 0; i < barLength; i++) {
                bar.append("█");
            }
            TUI.printColor(bar.toString(), TUI.GREEN);
            TUI.printlnColor(" " + percent + "%", TUI.BOLD + TUI.YELLOW);
        }
        
        TUI.printlnColor("=".repeat(60) + "\n", TUI.PURPLE);
        
        return percentages;
    }
    
    /**
     * Use a lifeline by name.
     * @param lifelineName Name of the lifeline
     * @param question Current question
     * @return Result depends on lifeline type (String for Phone a Friend, Map for others)
     */
    public Object useLifeline(String lifelineName, Question question) {
        if (!isAvailable(lifelineName)) {
            throw new IllegalStateException("Lifeline '" + lifelineName + "' is not available!");
        }
        
        switch (lifelineName) {
            case FIFTY_FIFTY:
                return useFiftyFifty(question);
            case PHONE_FRIEND:
                return usePhoneAFriend(question);
            case ASK_AUDIENCE:
                return useAskTheAudience(question);
            default:
                throw new IllegalArgumentException("Unknown lifeline: " + lifelineName);
        }
    }
}
