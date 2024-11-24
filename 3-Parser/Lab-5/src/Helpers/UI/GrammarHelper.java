package Helpers.UI;

import Components.Grammar;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GrammarHelper {
    private Grammar grammar;

    public GrammarHelper(Grammar grammar) {
        this.grammar = grammar;
    }

    private void printMenu() {
        System.out.println("Welcome to Grammar Dissection Area");
        System.out.println("Press 1 for changing the default grammar file");
        System.out.println("Press 2 for the set of non-terminals");
        System.out.println("Press 3 for the set of terminals");
        System.out.println("Press 4 for the set of productions");
        System.out.println("Press 5 to get productions for a given non-terminal");
        System.out.println("Press 6 to check if the grammar is context-free");
        System.out.println("Press 0 for a brief departure\n");
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter the path: ");
                    String newFilepath = scanner.nextLine();
                    try {
                        this.grammar = new Grammar(newFilepath);
                    }
                    catch (RuntimeException fileNotFoundException) {
                        System.out.println("File not found");
                    }
                    finally {
                        break;
                    }
                case 2:
                    System.out.println("N = " + grammar.getNonterminals()
                            .stream()
                            .collect(Collectors.joining(", ")) + "\n");
                    break;
                case 3:
                    System.out.println("T = " + grammar.getTerminals()
                            .stream()
                            .collect(Collectors.joining(", ")) + "\n");
                    break;
                case 4:
                    System.out.println("P = " + grammar.getProductions().toString() + "\n");
                    break;
                case 5:
                    System.out.print("Enter the non-terminal: ");
                    String nonterminal = scanner.nextLine();
                    if (grammar.getNonterminals().contains(nonterminal)) {
                        System.out.println("P0 = " + grammar.getProductionsFor(nonterminal)
                                .stream()
                                .collect(Collectors.joining(" | ")) + "\n");
                    }
                    else {
                        System.out.println(nonterminal+" is not an existing non-terminal");
                    }
                    break;
                case 6: {
                    System.out.println(grammar.isCFG());
                    break;
                }
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
