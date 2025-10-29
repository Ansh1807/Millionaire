import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Map<String,String> options = new HashMap<>();
        options.put("A","his train was late");
        options.put("B","his bus was late");
        options.put("C","he fell on his face");
        options.put("D","he did not fill in his iComply");
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(
                "why was ansh late", options,"A",1,9595195

        );
        q1.displayQuestion();
        q1.displayOptions();
        System.out.println(q1.isCorrect(q1.getInput()));

    }

}