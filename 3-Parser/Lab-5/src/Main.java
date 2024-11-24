import Components.LexicalAnalyzer;
import Helpers.UI.GeneralUI;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer scanner1 = new LexicalAnalyzer("src/Assets/Files/p3.ubb");
        LexicalAnalyzer scanner2 = new LexicalAnalyzer("src/Assets/Files/p1error.ubb");

        System.out.println("Should be lexically correct:");
        scanner1.run();
        System.out.println("\n");

        System.out.println("Should be lexically incorrect:");
        scanner2.run();
        System.out.println("\n");

        GeneralUI ui = new GeneralUI();
        ui.startUI();
    }
}