import Assets.Constants;
import Components.FiniteAutomaton;
import Components.LexicalAnalyzer;
import Helpers.AutomatonHelper;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer scanner1 = new LexicalAnalyzer("src/Assets/Files/p3.ubb");
        LexicalAnalyzer scanner2 = new LexicalAnalyzer("src/Assets/Files/p1error.ubb");

        FiniteAutomaton fa = new FiniteAutomaton(Constants.FApath);
        AutomatonHelper finiteAutomaton = new AutomatonHelper(fa);

        System.out.println("Should be lexically correct:");
        scanner1.run();
        System.out.println("\n");

        System.out.println("Should be lexically incorrect:");
        scanner2.run();
        System.out.println("\n");

        finiteAutomaton.startMenu();
    }
}