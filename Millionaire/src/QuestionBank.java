import java.util.*;

/**
 * Manages the question bank for the Millionaire game.
 * Organizes questions by difficulty level and type.
 */
public class QuestionBank {
    
    private Map<Integer, List<Question>> questionsByDifficulty;
    private Random random;
    
    public QuestionBank() {
        this.questionsByDifficulty = new HashMap<>();
        this.random = new Random();
        initializeQuestions();
    }
    
    /**
     * Initialize questions for all difficulty levels.
     * Questions are distributed across difficulty levels 1-15.
     */
    private void initializeQuestions() {
        // Level 1-5: Easy questions
        addEasyQuestions();
        // Level 6-10: Medium questions
        addMediumQuestions();
        // Level 11-15: Hard questions
        addHardQuestions();
    }
    
    /**
     * Add easy questions (levels 1-5).
     */
    private void addEasyQuestions() {
        // Level 1
        Map<String, String> q1Options = new HashMap<>();
        q1Options.put("A", "Paris");
        q1Options.put("B", "London");
        q1Options.put("C", "Berlin");
        q1Options.put("D", "Madrid");
        addQuestion(1, new MultipleChoiceQuestion(
            "What is the capital of France?",
            q1Options, "Paris", 1, PrizeLadder.getPrize(1)
        ));
        
        // Level 2
        Map<String, String> q2Options = new HashMap<>();
        q2Options.put("A", "Jupiter");
        q2Options.put("B", "Saturn");
        q2Options.put("C", "Mars");
        q2Options.put("D", "Earth");
        addQuestion(2, new MultipleChoiceQuestion(
            "Which planet is known as the Red Planet?",
            q2Options, "Mars", 2, PrizeLadder.getPrize(2)
        ));
        
        // Level 3 - True/False
        addQuestion(3, new TrueFalseQuestion(
            "The Great Wall of China is visible from space.",
            "False", 3, PrizeLadder.getPrize(3)
        ));
        
        // Level 4
        Map<String, String> q4Options = new HashMap<>();
        q4Options.put("A", "Winston Churchill");
        q4Options.put("B", "Franklin D. Roosevelt");
        q4Options.put("C", "Adolf Hitler");
        q4Options.put("D", "Joseph Stalin");
        addQuestion(4, new MultipleChoiceQuestion(
            "Who was the Prime Minister of the United Kingdom during most of World War II?",
            q4Options, "Winston Churchill", 4, PrizeLadder.getPrize(4)
        ));
        
        // Level 5 - ASCII Art
        String asciiCat = 
            "    /\\_/\\  \n" +
            "   ( o.o ) \n  " +
            "    > ^ <      ";
        Map<String, String> q5Options = new HashMap<>();
        q5Options.put("A", "Dog");
        q5Options.put("B", "Cat");
        q5Options.put("C", "Rabbit");
        q5Options.put("D", "Hamster");
        addQuestion(5, new ASCIIArtQuestion(
            asciiCat,
            "What animal is represented by this ASCII art?",
            q5Options, "Cat", 5, PrizeLadder.getPrize(5)
        ));
    }
    
    /**
     * Add medium questions (levels 6-10).
     */
    private void addMediumQuestions() {
        // Level 6
        Map<String, String> q6Options = new HashMap<>();
        q6Options.put("A", "William Shakespeare");
        q6Options.put("B", "Charles Dickens");
        q6Options.put("C", "Jane Austen");
        q6Options.put("D", "Mark Twain");
        addQuestion(6, new MultipleChoiceQuestion(
            "Who wrote the play 'Romeo and Juliet'?",
            q6Options, "William Shakespeare", 6, PrizeLadder.getPrize(6)
        ));
        
        // Level 7 - True/False
        addQuestion(7, new TrueFalseQuestion(
            "The human body has four lungs.",
            "False", 7, PrizeLadder.getPrize(7)
        ));
        
        // Level 8 - ASCII Art
        String asciiHeart = 
            "  ***   ***  \n" +
            " *   * *   * \n" +
            "*     *     *\n" +
            " *         * \n" +
            "  *       *  \n" +
            "   *     *   \n" +
            "    *   *    \n" +
            "     * *     \n" +
            "      *      ";
        Map<String, String> q8Options = new HashMap<>();
        q8Options.put("A", "Circle");
        q8Options.put("B", "Heart");
        q8Options.put("C", "Diamond");
        q8Options.put("D", "Star");
        addQuestion(8, new ASCIIArtQuestion(
            asciiHeart,
            "What shape is represented by this ASCII art?",
            q8Options, "Heart", 8, PrizeLadder.getPrize(8)
        ));
        
        // Level 9
        Map<String, String> q9Options = new HashMap<>();
        q9Options.put("A", "Mount Everest");
        q9Options.put("B", "K2");
        q9Options.put("C", "Kilimanjaro");
        q9Options.put("D", "Matterhorn");
        addQuestion(9, new MultipleChoiceQuestion(
            "What is the highest mountain in the world?",
            q9Options, "Mount Everest", 9, PrizeLadder.getPrize(9)
        ));
        
        // Level 10
        Map<String, String> q10Options = new HashMap<>();
        q10Options.put("A", "Vikings");
        q10Options.put("B", "Romans");
        q10Options.put("C", "Greeks");
        q10Options.put("D", "Egyptians");
        addQuestion(10, new MultipleChoiceQuestion(
            "Who built the Colosseum in Rome?",
            q10Options, "Romans", 10, PrizeLadder.getPrize(10)
        ));
    }
    
    /**
     * Add hard questions (levels 11-15).
     */
    private void addHardQuestions() {
        // Level 11
        Map<String, String> q11Options = new HashMap<>();
        q11Options.put("A", "Marie Curie");
        q11Options.put("B", "Rosalind Franklin");
        q11Options.put("C", "Ada Lovelace");
        q11Options.put("D", "Dorothy Hodgkin");
        addQuestion(11, new MultipleChoiceQuestion(
            "Who was the first woman to win a Nobel Prize?",
            q11Options, "Marie Curie", 11, PrizeLadder.getPrize(11)
        ));
        
        // Level 12 - True/False
        addQuestion(12, new TrueFalseQuestion(
            "The speed of light is approximately 299,792,458 meters per second in a vacuum.",
            "True", 12, PrizeLadder.getPrize(12)
        ));
        
        // Level 13 - ASCII Art
        String asciiTree = 
            "      /\\      \n" +
            "     /  \\     \n" +
            "    /    \\    \n" +
            "   /      \\   \n" +
            "  /________\\  \n" +
            "      ||      ";
        Map<String, String> q13Options = new HashMap<>();
        q13Options.put("A", "House");
        q13Options.put("B", "Tree");
        q13Options.put("C", "Tower");
        q13Options.put("D", "Pyramid");
        addQuestion(13, new ASCIIArtQuestion(
            asciiTree,
            "What object is represented by this ASCII art?",
            q13Options, "Tree", 13, PrizeLadder.getPrize(13)
        ));
        
        // Level 14
        Map<String, String> q14Options = new HashMap<>();
        q14Options.put("A", "Einstein");
        q14Options.put("B", "Newton");
        q14Options.put("C", "Galileo");
        q14Options.put("D", "Copernicus");
        addQuestion(14, new MultipleChoiceQuestion(
            "Who formulated the theory of general relativity?",
            q14Options, "Einstein", 14, PrizeLadder.getPrize(14)
        ));
        
        // Level 15 - Final question
        Map<String, String> q15Options = new HashMap<>();
        q15Options.put("A", "1972");
        q15Options.put("B", "1969");
        q15Options.put("C", "1971");
        q15Options.put("D", "1970");
        addQuestion(15, new MultipleChoiceQuestion(
            "In what year did humans first land on the Moon?",
            q15Options, "1969", 15, PrizeLadder.getPrize(15)
        ));
        
        // Add more questions per level for variety (random selection)
        addAdditionalQuestions();
    }
    
    /**
     * Add additional questions to each level for variety.
     */
    private void addAdditionalQuestions() {
        // Level 1 alternatives
        Map<String, String> alt1 = new HashMap<>();
        alt1.put("A", "Tokyo");
        alt1.put("B", "Seoul");
        alt1.put("C", "Beijing");
        alt1.put("D", "Bangkok");
        addQuestion(1, new MultipleChoiceQuestion(
            "What is the capital of Japan?",
            alt1, "Tokyo", 1, PrizeLadder.getPrize(1)
        ));
        
        // Level 5 alternatives
        String asciiStar = 
            "    /\\    \n" +
            "   /  \\   \n" +
            "  /    \\  \n" +
            " \\      / \n" +
            "  \\    /  \n" +
            "   \\  /   \n" +
            "    \\/    ";
        Map<String, String> alt5 = new HashMap<>();
        alt5.put("A", "Star");
        alt5.put("B", "Diamond");
        alt5.put("C", "Square");
        alt5.put("D", "Circle");
        addQuestion(5, new ASCIIArtQuestion(
            asciiStar,
            "What shape is represented by this ASCII art?",
            alt5, "Star", 5, PrizeLadder.getPrize(5)
        ));
    }
    
    /**
     * Add a question to the bank for a specific difficulty level.
     */
    private void addQuestion(int difficulty, Question question) {
        questionsByDifficulty.putIfAbsent(difficulty, new ArrayList<>());
        questionsByDifficulty.get(difficulty).add(question);
    }
    
    /**
     * Get a random question for a given difficulty level.
     */
    public Question getQuestion(int difficulty) {
        List<Question> questions = questionsByDifficulty.get(difficulty);
        if (questions == null || questions.isEmpty()) {
            throw new RuntimeException("No questions available for difficulty level " + difficulty);
        }
        int randomIndex = random.nextInt(questions.size());
        return questions.get(randomIndex);
    }
    
    /**
     * Check if questions are available for a difficulty level.
     */
    public boolean hasQuestions(int difficulty) {
        List<Question> questions = questionsByDifficulty.get(difficulty);
        return questions != null && !questions.isEmpty();
    }
}

