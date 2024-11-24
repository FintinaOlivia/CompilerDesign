package Components;

import Assets.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.PatternSyntaxException;

public class Grammar {
    private List<String> terminals = new ArrayList<>();
    private List<String> nonterminals = new ArrayList<>();
    private Map<List<String>, List<String>> productions = new HashMap<>();
    private String startingSymbol;

    public Grammar(String filepath) {
        try{
            this.readFromFile(filepath);
        } catch (ParseException e) {
            System.out.println("Error in parsing file!");
            System.out.println(e.getMessage());
        }

    }

    public Grammar(){
        try{
            this.readFromFile(Constants.defaultGrammarPath);
        } catch (ParseException e) {
            System.out.println("Error in parsing file!");
            System.out.println(e.getMessage());
        }

    }

    private void readFromFile(String filePath) throws ParseException {
        int lineNumber = 1;
        boolean isProduction = false;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (lineNumber == 1) {
                    String nonterminalPart = line.replaceFirst("^N\\s*=\\s*", "");
                    for (String nonterminal : nonterminalPart.split("[= ,\\n]")) {
                        if (!nonterminal.isEmpty()) {
                            this.nonterminals.add(nonterminal);
                        }
                    }
                } else if (lineNumber == 2) {
                    String nonterminalPart = line.replaceFirst("^T\\s*=\\s*", "");
                    for (String terminal : nonterminalPart.split("[= ,\\n]")) {
                        if (!terminal.isEmpty()) {
                            this.terminals.add(terminal);
                        }
                    }
                } else if (line.equals("start")) {
                    isProduction = true;
                    continue;
                } else if (line.equals("end")) {
                    isProduction = false;
                    continue;
                }

                if (isProduction) {
                    String[] productionSplitByRightLeftHS = line.split("->");
                    List<String> lhs = List.of(productionSplitByRightLeftHS[0].split(", "));
                    List<String> rhs = List.of(productionSplitByRightLeftHS[1].split("[|,]"));
                    List<String> productions = new ArrayList<>();

                    for (String lh : lhs) {
                        if (lh.isEmpty() ||
                                !this.nonterminals.contains(lh) && !this.terminals.contains(lh)) {
                            throw new ParseException("Invalid production symbol: " + lh +"\n\n", 0);
                        }
                    }

                    for (String production : rhs) {
                        if (!production.isEmpty()) {
                            productions.add(production);
                        }
                    }
                    this.productions.put(lhs, productions);
                }

                if (line.split("[= ]")[0].equals("S0")) {
                    this.startingSymbol = line.split("[= ]")[3];
                    continue;
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw e;
        }
    }

    public boolean isCFG(){
        for(var production : productions.keySet()){
            if(production.stream().count() > 1 ||
                    this.terminals.contains(production.get(0))){
                return false;
            }
        }
        return true;
    }

    public List<String> getTerminals() {
        return this.terminals;
    }

    public List<String> getNonterminals() {
        return this.nonterminals;
    }

    public Map<List<String>, List<String>> getProductions() {
        return this.productions;
    }

    public List<String> getProductionsFor(String nonterminal) {
        if(!this.nonterminals.contains(nonterminal)){
            return null;
        }
        for(var production : this.productions.keySet()){
            if(production.contains(nonterminal)){
                return this.productions.get(production);
            }
        }
        return null;
    }
}
