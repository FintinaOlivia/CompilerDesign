package Components.Parser;

import java.util.List;
import java.util.Objects;

public class Item {
    private String leftHandSide;
    private List<String> rightHandSide;
    private Integer dotPosition;

    public Item(String leftHandSide, List<String> rightHandSide, Integer positionForDot){
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.dotPosition = positionForDot;
    }

    public Item(String leftHandSide, List<String> rightHandSide){
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
        this.dotPosition = 0;
    }

    public String getLeftHandSide(){
        return this.leftHandSide;
    }

    public List<String> getRightHandSide(){
        return this.rightHandSide;
    }

    public Integer getDotPosition(){
        return this.dotPosition;
    }
    public boolean checkDotIsLast(){
        return this.dotPosition == this.rightHandSide.size();
    }

    @Override
    public String toString() {
        String stringRightHandSideBeforeDot = String.join("", rightHandSide.subList(0, dotPosition));
        String stringRightHandSideAfterDot = String.join("", rightHandSide.subList(dotPosition, rightHandSide.size()));

        return leftHandSide + " -> " + stringRightHandSideBeforeDot + "." + stringRightHandSideAfterDot;
    }

    @Override
    public int hashCode(){
        return Objects.hash(leftHandSide, rightHandSide, dotPosition);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Item)) {
            return false;
        }

        Item other = (Item) object;
        return Objects.equals(leftHandSide, other.leftHandSide) &&
               Objects.equals(rightHandSide, other.rightHandSide) &&
               Objects.equals(dotPosition,other.dotPosition);
    }
}
