package DataStructures.ParsingTable;

import Components.Parser.ActionTypes;
import DataStructures.Pair;

import java.util.ArrayList;
import java.util.List;

public class Row {
    public int stateIndex;

    public ActionTypes action;

    public String reducedNonTerminal;

    public List<String> reducedRHS = new ArrayList<>();

    public List<Pair<String, Integer>> shifts = new ArrayList<>();

    public String reduceProductionString(){
        return this.reducedNonTerminal + " -> " + this.reducedRHS;
    }

    public String concatenateElements(List<String> rhs) {
        if(!(rhs == null)){
            return String.join(" ", rhs);
        }
        return "null";
    }

    @Override
    public String toString(){
        return  "State " + stateIndex + ", " +
                "Action: '" + action + '\'' + ", " +
                "Reduce Nonterminal: [" + reducedNonTerminal + "], " +
                "Reduced productions: [" + concatenateElements(reducedRHS) + "], " +
                "Shifts: " + shifts;
    }
}
