import Components.LexicalAnalyzer;
import Helpers.UI.GeneralUI;

public class Main {
    public static void main(String[] args) throws Exception {
        LexicalAnalyzer scanner = new LexicalAnalyzer("src/Assets/Files/p1.ubb");
        scanner.run();
        GeneralUI.startUI();
    }
}