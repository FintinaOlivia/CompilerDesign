package Helpers.UI;

import Components.Grammar.Grammar;
import Components.Parser.Parser;
import DataStructures.CanonicalCollection;
import DataStructures.Pair;
import DataStructures.ParsingTable.ParsingTable;
import Components.LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import static Helpers.FileHandler.printCanonicalCollection;
import static Helpers.FileHandler.writeLineToFile;

public class ParserHelper {
        private Grammar grammar;

        public ParserHelper(Grammar grammar) {
            this.grammar = grammar;
        }

        private void printMenu() {
            System.out.println("\nWelcome to the LR(0) Dissection Area");
            System.out.println("Press 1 to run the parser for G1.txt and parse Sequence.txt");
            System.out.println("Unavailable at the moment :)");
            System.out.println("Press 3 to run the parser for G3.txt");
            System.out.println("Press 4 to run the parser for G4.txt");
            System.out.println("Press 5 to parse p1.ubb");
            System.out.println("Press 6 to parse p2.ubb");
            System.out.println("Press 7 to parse p3.ubb");
            System.out.println("Press 0 for a brief departure\n");
        }

        public void startMenu() throws Exception {
            Scanner scanner = new Scanner(System.in);
            int option = -1;

            while (option != 0) {
                printMenu();
                System.out.print("Choose an option: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        runParser("src/Assets/Grammars/G1.txt", "src/Assets/ParsingResults/ParsingMockInput/Sequence1.txt", "src/Assets/ParsingResults/Out1.txt");
                        break;
                    case 2:
//                        runParser("src/Assets/Grammars/G2.txt", "src/Assets/ParsingResults/ParsingMockInput/Sequence2.txt", "src/Assets/ParsingResults/Out2.txt");
                        break;
                    case 3:
                        runParser("src/Assets/Grammars/G3.txt", "src/Assets/ParsingResults/ParsingMockInput/Sequence3.txt", "src/Assets/ParsingResults/Out3.txt");
                        break;
                    case 4:
                        runParser("src/Assets/Grammars/G4.txt", "src/Assets/ParsingResults/ParsingMockInput/Sequence4.txt", "src/Assets/ParsingResults/Out4.txt");
                        break;
                    case 5:
                        parseFile();
                        break;
                    case 6: {
                        break;
                    }
                    case 7: {
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

    private void runParser(String grammarFile, String sequenceFile, String outputFile) throws Exception {
        Grammar grammar = new Grammar(grammarFile);
        Parser parser = new Parser(grammar);

        CanonicalCollection canonicalCollection = parser.canonicalCollection();
        printCanonicalCollection(canonicalCollection);

        ParsingTable parsingTable = parser.getParsingTable(canonicalCollection);
        if (parsingTable.entries.size() == 0) {
            System.out.println("We have conflicts in the parsing table so we can't go further with the algorithm");
            writeLineToFile(outputFile, "We have conflicts in the parsing table so we can't go further with the algorithm");
        } else {
            System.out.println(parsingTable);
        }

        Stack<String> word = readSequence(sequenceFile);
        parser.parse(word, parsingTable, outputFile);
    }

    private Stack<String> readSequence(String filename) {
        BufferedReader reader;
        Stack<String> wordStack = new Stack<>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            if (line != null) {
                Arrays.stream(new StringBuilder(line).reverse().toString().split("")).forEach(wordStack::push);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordStack;
    }

    private void parseFile() throws Exception {
        LexicalAnalyzer scanner = new LexicalAnalyzer("src/Assets/Files/p1.ubb");
        scanner.scan();

        scanner.run();

        Stack<String> content = readTokensFromPif("src/Assets/Files/p1PIF.out");

        Grammar grammar = new Grammar("src/Assets/Grammars/g2.txt");
        Parser parser = new Parser(grammar);
        CanonicalCollection canonicalCollection2 = parser.canonicalCollection();

        System.out.println("States");
        for (int i = 0; i < canonicalCollection2.getStates().size(); i++) {
            System.out.println(i + " " + canonicalCollection2.getStates().get(i));
        }

        System.out.println("\nState.State transitions");
        for (Map.Entry<Pair<Integer, String>, Integer> entry : canonicalCollection2.getAdjacencyList().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println();

        ParsingTable parsingTable = parser.getParsingTable(canonicalCollection2);
        if (parsingTable.entries.size() == 0) {
            System.out.println("Conflicts in paradise.");
            writeLineToFile("src/Assets/ParsingResults/Out2.txt", "Conflicts in paradise, the algorithm cannot continue like this.");
        } else {
            System.out.println(parsingTable);
        }

        parser.parse(content, parsingTable, "src/Assets/ParsingResults/Out2.txt");
    }

    public static Stack<String> readTokensFromPif(String filename) {
        Stack<String> wordStack = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                if (split.length > 0) {
                    wordStack.push(split[0]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file: " + filename, e);
        }

        Stack<String> reversedStack = new Stack<>();
        while (!wordStack.isEmpty()) {
            reversedStack.push(wordStack.pop());
        }

        return reversedStack;
    }


}
