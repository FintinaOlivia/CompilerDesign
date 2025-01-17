package Components.FA;

import DataStructures.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    private String initialState = "";

    private List<String> states = new ArrayList<>();

    private List<String> alphabet = new ArrayList<>();

    private List<String> finalStates = new ArrayList<>();

    private Map<Pair<String, String>, String> transitions;

    public FiniteAutomaton(String filePath){
        this.transitions = new LinkedHashMap<>();
        this.readFromFile(filePath);
    }

    private void readFromFile(String filePath) {
        int lineNumber = 1;
        boolean isTransition = false;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (lineNumber == 1) {
                    for (String state : line.split("[Q=\\n]")) {
                        if (!state.isEmpty()) {
                            states.add(state);
                        }
                    }
                } else if (lineNumber == 2) {
                    for (String letter : line.split("[E=\\n]")) {
                        if (!letter.isEmpty()) {
                            alphabet.add(letter);
                        }
                    }
                } else if (line.equals("start")) {
                    isTransition = true;
                    continue;
                } else if (line.equals("end")) {
                    isTransition = false;
                    continue;
                }

                if (isTransition) {
                    boolean isLast = false;
                    boolean isFirst = true;
                    String first = "";
                    String last = "";
                    List<String> letters = new ArrayList<>();

                    for (String word : line.split("[, \\[\\]\\n]")) {
                        if (word.isEmpty()) continue;

                        if (isFirst) {
                            first = word;
                            isFirst = false;
                        }
                        if (isLast) {
                            last = word;
                        }

                        if (word.equals("->")) {
                            isLast = true;
                        }
                        if (!word.equals(first) && !word.equals(last) && !word.equals("->") && !word.isEmpty()) {
                            letters.add(word);
                        }
                    }

                    for (String letter : letters) {
                        transitions.put(new Pair<>(first, letter), last);
                    }
                    continue;
                }

                if (line.split("[= ]")[0].equals("q0")) {
                    this.initialState = line.split("[= ]")[3];
                    continue;
                }

                if (line.split("[= ,]")[0].equals("F")) {
                    for (String word : line.split("[= ,]")) {
                        if (!word.equals("F") && !word.isEmpty()) {
                            finalStates.add(word);
                        }
                    }
                    continue;
                }

                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean checkWordIfIntegerConstant(String word) {
        String state = this.initialState;
        List<String> desiredFinalStates = Arrays.asList("q3", "q4");

        return checkSequence(state, word, desiredFinalStates);
    }

    public boolean checkWordIfIdentifier(String word) {
        String state = this.initialState;
        List<String> desiredFinalStates = Arrays.asList("q1");

        return checkSequence(state, word, desiredFinalStates);
    }

    private boolean checkSequence(String initialState, String word,
                                 List<String> desiredFinalStates) {
        String state = initialState;

        for (int i = 0; i < word.length(); i++) {
            String currentSymbol = word.substring(i, i + 1);
            Pair<String, String> transition = new Pair<>(state, currentSymbol);

            if (!this.transitions.containsKey(transition)) {
                return false;
            } else {
                state = this.transitions.get(transition);
            }

        }
        return desiredFinalStates.contains(state);
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public Map<Pair<String, String>, String> getTransitions() {
        return transitions;
    }

}
