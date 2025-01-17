package Components.Parser;

import Assets.Constants;

import java.util.*;

public class ParsingState {
    private Set<Item> items;
    private ActionTypes stateActionType;

    public ParsingState(Set<Item> states){
        this.items = states;
        this.setActionForState();
    }

    public Set<Item> getItems(){
        return items;
    }

    public List<String> getSymbolsAfterTheDot(){
        Set<String> symbols = new LinkedHashSet<>();

        for (Item item : this.getItems()) {
            List<String> rhs = item.getRightHandSide();
            int dotPosition = item.getDotPosition();

            while (dotPosition < rhs.size()) {
                symbols.add(rhs.get(dotPosition));
                dotPosition++;
            }
        }

        return new ArrayList<>(symbols);
    }

    public ActionTypes getStateActionType(){
        return this.stateActionType;
    }

    public void setActionForState(){
        if (items.size() == 1) {
            Item firstItem = items.iterator().next();
            int rhsSize = firstItem.getRightHandSide().size();
            int dotPosition = firstItem.getDotPosition();

            if (rhsSize == dotPosition &&
                    firstItem.getLeftHandSide() == Constants.augmentedStartingSymbol) {
                this.stateActionType = ActionTypes.ACCEPT;
            } else if (rhsSize == dotPosition) {
                this.stateActionType = ActionTypes.REDUCE;
            } else if (rhsSize > dotPosition) {
                this.stateActionType = ActionTypes.SHIFT;
            }
        } else if (items.size() > 1) {
            boolean allReduce = items.stream().allMatch(item -> item.getRightHandSide().size() == item.getDotPosition());
            boolean allShift = items.stream().allMatch(item -> item.getRightHandSide().size() > item.getDotPosition());

            if (allReduce) {
                this.stateActionType = ActionTypes.REDUCE_REDUCE_CONFLICT;
            } else if (!allShift) {
                this.stateActionType = ActionTypes.SHIFT_REDUCE_CONFLICT;
            } else {
                this.stateActionType = ActionTypes.SHIFT;
            }
        } else {
            this.stateActionType = ActionTypes.SHIFT_REDUCE_CONFLICT;
        }
    }


    @Override
    public String toString(){
        return stateActionType + " : " + items;
    }

    @Override
    public int hashCode(){
        return Objects.hash(items);
    }

    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (!(object instanceof ParsingState)) {
            return false;
        }

        ParsingState other = (ParsingState) object;
            return other.getItems().equals(this.getItems());
    }
}
