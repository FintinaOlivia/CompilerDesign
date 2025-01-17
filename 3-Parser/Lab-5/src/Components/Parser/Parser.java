package Components.Parser;

import Components.Grammar.Grammar;
import Components.Parser.ParsingTree.OutputTree;
import DataStructures.CanonicalCollection;
import DataStructures.Pair;
import DataStructures.ParsingTable.ParsingTable;
import DataStructures.ParsingTable.Row;
import java.io.IOException;
import java.util.*;

import static Helpers.FileHandler.writeLineToFile;

public class Parser {
    private final Grammar grammar;
    private List<Pair<String, List<String>>> productions;

    public Parser(Grammar grammar) {
        if (!grammar.checkIfAugmented()) {
            grammar.augmentGrammar();
        }
        this.grammar = grammar;
        this.productions = grammar.getProductionsForEachNT();
    }

    private String nonterminalAfterDot(Item item){
        var rhs = item.getRightHandSide();
        var dotPosition = item.getDotPosition();
        String nonterminal = null;

        if(dotPosition == rhs.size()){
            return nonterminal;
        }

        if(grammar.getNonterminals().contains(rhs.get(dotPosition))){
            nonterminal = rhs.get(dotPosition);
        }
        return nonterminal;
    }

    public ParsingState closure(Item item){
        Set<Item> currentClosure = new LinkedHashSet<>();
        currentClosure.add(item); // Add the initial item

        boolean changed;
        do {
            changed = false;
            Set<Item> newItems = new LinkedHashSet<>();

            for (Item i : currentClosure) {
                String nonTerminal = nonterminalAfterDot(i);
                if (nonTerminal != null) {
                    for (List<String> production : grammar.getProductionsFor(nonTerminal)) {
                        Item newItem = new Item(nonTerminal, production);
                        if (!currentClosure.contains(newItem)) {
                            newItems.add(newItem);
                            changed = true;
                        }
                    }
                }
            }
            currentClosure.addAll(newItems);
        } while (changed);

        return new ParsingState(currentClosure);
    }

    public ParsingState goTo(ParsingState state, String element) {
        Set<Item> result = new LinkedHashSet<>();

        for (Item item : state.getItems()) {
            var rhs = item.getRightHandSide();
            var lhs = item.getLeftHandSide();
            var dotPosition = item.getDotPosition();
            try {
                if (Objects.equals(rhs.get(dotPosition), element)) {
                    Item nextItem = new Item(lhs, rhs, dotPosition + 1);
                    ParsingState newState = closure(nextItem);
                    result.addAll(newState.getItems());
                }
            } catch (Exception ignored) {
            }
        }
        return new ParsingState(result);
    }

    public CanonicalCollection canonicalCollection() {
        var canonicalCollection = new CanonicalCollection();

        // first iteration: add S0 -> S, where S0 is the augmented starting symbol
        Item startingItem = new Item(grammar.getStartingSymbol(),
                grammar.getProductionsFor(grammar.getStartingSymbol()).get(0));
        canonicalCollection.add(closure(startingItem));

        for (int index = 0; index < canonicalCollection.getStates().size(); index++) {
            ParsingState currentState = canonicalCollection.getStates().get(index);
            for (String symbol : currentState.getSymbolsAfterTheDot()) {
                ParsingState newState = goTo(currentState, symbol);

                if (!newState.getItems().isEmpty()) {
                    int indexState = canonicalCollection.getStates().indexOf(newState);

                    if (indexState == -1) {
                        canonicalCollection.getStates().add(newState);
                        indexState = canonicalCollection.getStates().size() - 1;
                    }

                    canonicalCollection.connectStates(index, symbol, indexState);
                }
            }
        }
        return canonicalCollection;
    }

    public CanonicalCollection getCanonicalCollection() {
        return canonicalCollection();
    }

//    TODO: Find a better way to do this
    public ParsingTable getParsingTable(CanonicalCollection canonicalCollection) throws Exception {
        ParsingTable parsingTable = new ParsingTable();

        for (int i = 0; i < this.canonicalCollection().getStates().size(); i++) {

            ParsingState state = this.canonicalCollection().getStates().get(i);

            Row row = new Row();

            row.stateIndex = i;
            row.action = state.getStateActionType();
            row.shifts = new ArrayList<>();

            switch (state.getStateActionType()) {
                case SHIFT_REDUCE_CONFLICT:
                case REDUCE_REDUCE_CONFLICT:
                case REDUCE_SHIFT_CONFLICT:
                    handleConflict(canonicalCollection, row, state, parsingTable);
                    return parsingTable;

                case REDUCE:
                    Item item = state.getItems().stream().filter(Item::checkDotIsLast).findAny().orElse(null);
                    if (item != null) {
                        row.shifts = null;
                        row.reducedNonTerminal = item.getLeftHandSide();
                        row.reducedRHS = item.getRightHandSide();
                    } else {
                        throw new Exception("??");
                    }
                    break;

                case ACCEPT:
                    row.reducedRHS = null;
                    row.reducedNonTerminal = null;
                    row.shifts = null;
                    break;

                case SHIFT:
                    List<Pair<String, Integer>> goTos = computeShifts(canonicalCollection, row.stateIndex);
                    row.shifts = goTos;
                    row.reducedRHS = null;
                    row.reducedNonTerminal = null;
                    break;

                default:
                    throw new IllegalStateException("Unexpected state action type: " + state.getStateActionType());
            }

            parsingTable.entries.add(row);

        }

        return parsingTable;
    }

    private void handleConflict(CanonicalCollection canonicalCollection, Row row, ParsingState state, ParsingTable parsingTable) throws IOException {
        for (Map.Entry<Pair<Integer, String>, Integer> entry : canonicalCollection.getAdjacencyList().entrySet()) {
            Pair<Integer, String> key = entry.getKey();
            Integer value = entry.getValue();

            if (value.equals(row.stateIndex)) {
                logConflict(row, key, state);
                break;
            }
        }
        parsingTable.entries = new ArrayList<>();
    }

    private void logConflict(Row row, Pair<Integer, String> key, ParsingState state) throws IOException {
        String filePath = "src/Assets/ParsingResults/Out.txt";
        String logMessage = String.format("STATE INDEX -> %d\nSYMBOL -> %s\nINITIAL STATE -> %d\n( %d, %s ) -> %d\nSTATE -> %s",
                row.stateIndex, key.getSecondTerm(), key.getFirstTerm(), key.getFirstTerm(), key.getSecondTerm(), row.stateIndex, state);
        System.out.println(logMessage);
        writeLineToFile(filePath, logMessage);
    }


    private List<Pair<String, Integer>> computeShifts(CanonicalCollection canonicalCollection, int stateIndex) {
        List<Pair<String, Integer>> goTos = new ArrayList<>();
        for (Map.Entry<Pair<Integer, String>, Integer> entry : canonicalCollection.getAdjacencyList().entrySet()) {
            Pair<Integer, String> key = entry.getKey();
            if (key.getFirstTerm() == stateIndex) {
                goTos.add(new Pair<>(key.getSecondTerm(), entry.getValue()));
            }
        }
        return goTos;
    }


    public void parse(Stack<String> inputStack, ParsingTable parsingTable, String filePath) throws IOException {
        Stack<Pair<String, Integer>> workingStack = new Stack<>();
        Stack<String> outputStack = new Stack<>();
        Stack<Integer> outputNumberStack = new Stack<>();

        String lastSymbol = "";
        int stateIndex = 0;

        boolean sem = true;

        workingStack.push(new Pair<>(lastSymbol, stateIndex));
        Row lastRow = null;
        String onErrorSymbol = null;

        try {
            do{
                if(inputStack.size() != 0){
                    onErrorSymbol = inputStack.peek();
                }
                lastRow = parsingTable.entries.get(stateIndex);

                Row entry = parsingTable.entries.get(stateIndex);

                switch (entry.action) {
                    case SHIFT: {
                        String symbol = inputStack.pop();
                        Pair<String, Integer> state = entry.shifts.stream()
                                .filter(it -> it.getFirstTerm().equals(symbol))
                                .findAny()
                                .orElse(null);

                        if (state != null) {
                            stateIndex = state.getSecondTerm();
                            lastSymbol = symbol;
                            workingStack.push(new Pair<>(lastSymbol, stateIndex));
                        } else {
                            throw new NullPointerException("Action is SHIFT but there are no matching states");
                        }
                        break;
                    }
                    case REDUCE: {
                        List<String> reduceContent = new ArrayList<>(entry.reducedRHS);

                        while (reduceContent.contains(workingStack.peek().getFirstTerm()) && !workingStack.isEmpty()) {
                            reduceContent.remove(workingStack.peek().getFirstTerm());
                            workingStack.pop();
                        }

                        Pair<String, Integer> state = parsingTable.entries
                                .get(workingStack.peek().getSecondTerm())
                                .shifts.stream()
                                .filter(it -> it.getFirstTerm().equals(entry.reducedNonTerminal))
                                .findAny()
                                .orElse(null);

                        stateIndex = state.getSecondTerm();
                        lastSymbol = entry.reducedNonTerminal;
                        workingStack.push(new Pair<>(lastSymbol, stateIndex));

                        outputStack.push(entry.reduceProductionString());

                        var index = new Pair<>(entry.reducedNonTerminal, entry.reducedRHS);
                        int productionNumber = this.productions.indexOf(index);

                        outputNumberStack.push(productionNumber);
                        break;
                    }
                    case ACCEPT: {
                        handleAcceptAction(outputStack, outputNumberStack, filePath, grammar);
                        sem = false;
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected action type: " + entry.action);
                    }
                }
            } while(sem);
        } catch (NullPointerException ex){
            System.out.println("ERROR at state " + stateIndex + " - before symbol " + onErrorSymbol);
            System.out.println(lastRow);

            System.out.println("Current working stack: " + workingStack);
            System.out.println("Current input stack: " + inputStack);
            System.out.println("State index: " + stateIndex);
            System.out.println("Current parsing table entry: " + lastRow);

            writeLineToFile(filePath, "ERROR at state " + stateIndex + " - before symbol " + onErrorSymbol);
            writeLineToFile(filePath, lastRow.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAcceptAction(Stack<String> outputStack, Stack<Integer> outputNumberStack,
                                    String filePath, Grammar grammar) throws IOException {
        List<String> output = new ArrayList<>(outputStack);
        Collections.reverse(output);
        List<Integer> numberOutput = new ArrayList<>(outputNumberStack);
        Collections.reverse(numberOutput);

        System.out.println("ACCEPTED");
        writeLineToFile(filePath, "ACCEPTED");
        System.out.println("Production strings: " + output);
        writeLineToFile(filePath, "Production strings: " + output);
        System.out.println("Production number: " + numberOutput);
        writeLineToFile(filePath, "Production number: " + numberOutput);

        OutputTree outputTree = new OutputTree(grammar);
        outputTree.generateTreeFromSequence(numberOutput);

        System.out.println("The output tree: ");
        writeLineToFile(filePath, "The output tree: ");
        outputTree.printTree(outputTree.getRoot(), filePath);
    }

}
