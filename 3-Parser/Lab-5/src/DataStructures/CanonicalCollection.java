package DataStructures;

import Components.Parser.ParsingState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanonicalCollection {
    private List<ParsingState> states   ;
    private Map<Pair<Integer, String>, Integer> adjacencyList;

    public CanonicalCollection() {
        this.states = new ArrayList();
        this.adjacencyList = new HashMap();
    }

    public void add(ParsingState entity) {
        this.states.add(entity);
    }

    public List<ParsingState> getStates() {
        return this.states;
    }

    public void connectStates(Integer indexFirstState, String symbol, Integer indexSecondState){
        this.adjacencyList.put(new Pair<>(indexFirstState, symbol), indexSecondState);
    }

    public Map<Pair<Integer, String>, Integer> getAdjacencyList(){
        return this.adjacencyList;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Canonical Collection:\n");

        for (ParsingState element : states) {
            builder.append("  ").append(element.toString()).append("\n");
        }

        return builder.toString();
    }
}
