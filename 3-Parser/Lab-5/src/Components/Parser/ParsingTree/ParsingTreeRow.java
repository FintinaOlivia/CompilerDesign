package Components.Parser.ParsingTree;

public class ParsingTreeRow {
    private Integer index;
    private String info;
    private ParsingTreeRow parent;
    private ParsingTreeRow rightSibling;

    private ParsingTreeRow leftChild;

    private Integer level;

    public ParsingTreeRow(String info) {
        this.info = info;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index){
        this.index = index;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public ParsingTreeRow getParent() {
        return parent;
    }

    public void setParent(ParsingTreeRow parent){
        this.parent = parent;
    }

    public ParsingTreeRow getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(ParsingTreeRow rightSibling){
        this.rightSibling = rightSibling;
    }

    public ParsingTreeRow getLeftChild(){
        return this.leftChild;
    }

    public void setLeftChild(ParsingTreeRow leftChild){
        this.leftChild = leftChild;
    }

    public Integer getLevel(){
        return this.level;
    }

    public void setLevel(Integer level){
        this.level = level;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        result.append("Index ").append(index).append(", ")
            .append("Info: ").append(info).append(", ")
            .append("Left Child: ").append(leftChild != null ? leftChild.getIndex() : -1).append(", ")
            .append("Right Child: ").append(rightSibling != null ? rightSibling.getIndex() : -1).append(", ")
            .append("Parent: ").append(parent != null ? parent.getIndex() : -1).append(", ")
            .append("Level: ").append(level);

        return result.toString();
    }
}
