package Helpers.UI;

import Components.FiniteAutomaton;
import DataStructures.Pair;

import java.util.Map;
import java.util.Scanner;

public class AutomatonHelper {
    private final FiniteAutomaton automaton;

    public AutomatonHelper(FiniteAutomaton automaton) {
        this.automaton = automaton;
    }

    private void printMenu() {
        System.out.println("Welcome to the FiniteAutomaton Dissection Area");
        System.out.println("Press 1 for set of states");
        System.out.println("Press 2 for alphabet");
        System.out.println("Press 3 for set of transitions");
        System.out.println("Press 4 for initial state");
        System.out.println("Press 5 for final states");
        System.out.println("Press 6 for checking a numeric sequence");
        System.out.println("Press 7 for checking an identifier");
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
                    System.out.println("Q = " + automaton.getStates().stream().reduce("", (a,b)->(a + " " + b)) + "\n");
                    break;
                case 2:
                    System.out.println("E = " + automaton.getAlphabet().stream().reduce("", (a,b)->(a + " " + b)) + "\n");
                    break;
                case 3:
                    System.out.println("RO = " + printTransitions(automaton.getTransitions()) + "\n");
                    break;
                case 4:
                    System.out.println("q0 = " + automaton.getInitialState() + "\n");
                    break;
                case 5:
                    System.out.println("F = " + automaton.getFinalStates().stream().reduce("", (a,b)->(a + " " + b)) + "\n");
                    break;
                case 6: {
                    System.out.print("Enter the numeric sequence: ");
                    String sequence = scanner.nextLine();
                    System.out.println(automaton.checkWordIfIntegerConstant(sequence));
                    break;
                }
                case 7: {
                    System.out.print("Enter the identifier: ");
                    String identifier = scanner.nextLine();
                    System.out.println(automaton.checkWordIfIdentifier(identifier));
                    break;
                }

                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    public String printTransitions(Map<Pair<String, String>, String> transitions){
        StringBuilder builder = new StringBuilder();
        builder.append("Transitions: \n");
        transitions.forEach((key, value) -> {
            builder.append("<").append(key.getFirstTerm()).append(",").append(key.getSecondTerm()).append("> -> ").append(value).append("\n");
        });

        return builder.toString();
    }

}
