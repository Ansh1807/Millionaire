import java.util.Map;

public class MultipleChoiceQuestion extends Question{
    public MultipleChoiceQuestion(String question, Map<String, String> options, String correctAnswer, int difficulty, int prize) {
        super(question, options, correctAnswer, difficulty, prize);
    }

    @Override
    public void displayOptions() {
        getOptions().keySet().forEach(key -> System.out.println("Option "+key+" : "+getOptions().get(key)));
    }


}

