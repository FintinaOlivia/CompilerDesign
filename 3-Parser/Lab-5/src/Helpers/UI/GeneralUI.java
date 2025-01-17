package Helpers.UI;

import Assets.Constants;
import Components.FA.FiniteAutomaton;
import Components.Grammar.Grammar;

import java.util.Scanner;

public class GeneralUI {
    private static void printMenu(){
        System.out.println("Welcome to Dissection Area");
        System.out.println("Press 1 for the final automaton menu");
        System.out.println("Press 2 for the grammar menu");
        System.out.println("Press 3 for the parser menu");
        System.out.println("Press 0 to exit :(\n");
    }

    public static void startUI() throws Exception {
        FiniteAutomaton fa = new FiniteAutomaton(Constants.automatonPath);
        Grammar grammar = new Grammar();

        AutomatonHelper finiteAutomaton = new AutomatonHelper(fa);
        GrammarHelper grammarHelper = new GrammarHelper(grammar);
        ParserHelper parserHelper = new ParserHelper(grammar);

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    finiteAutomaton.startMenu();
                    break;
                case 2:
                    grammarHelper.startMenu();
                    break;
                case 3:
                    parserHelper.startMenu();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
