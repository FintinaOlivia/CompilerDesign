package Components.SymbolTable;

import DataStructures.Pair;

import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Pair<Integer, Integer>>> tokenPositions;
    private List<TokenTypes> tokenTypes;

    public ProgramInternalForm() {
        this.tokenPositions = new ArrayList<>();
        this.tokenTypes = new ArrayList<>();
    }

    public void addToken(Pair<String, Pair<Integer, Integer>> pair, TokenTypes type){
        this.tokenPositions.add(pair);
        this.tokenTypes.add(type);
    }

    @Override
    public String toString() {
        StringBuilder computedString = new StringBuilder();
        for (int i = 0; i < this.tokenPositions.size(); i++) {
            Pair<String, Pair<Integer, Integer>> tokenPosition = this.tokenPositions.get(i);
            TokenTypes type = this.tokenTypes.get(i);

            if (type == TokenTypes.IDENTIFIER || type == TokenTypes.CONSTANT) {
                computedString.append(type)
                        .append(" - ")
                        .append(tokenPosition.getFirstTerm())
                        .append(" } - Position in Symbol Table: ")
                        .append(tokenPosition.getSecondTerm())
                        .append(" -> ")
                        .append(type)
                        .append("\n");
            } else {
                computedString.append(tokenPosition.getFirstTerm())
                        .append(" } - Position in Symbol Table: ")
                        .append(tokenPosition.getSecondTerm())
                        .append(" -> ")
                        .append(type)
                        .append("\n");
            }
        }

        return computedString.toString();
    }

}
