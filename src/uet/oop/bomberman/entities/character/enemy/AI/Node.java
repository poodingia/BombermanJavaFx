package uet.oop.bomberman.entities.character.enemy.AI;

public class Node {

    private int h;
    private int g;
    private int f;
    private int row;
    private int col;
    private boolean is_block;   // This variable to check if the object is blocks.
    private Node parent;

    // Constructor to create object Node.
    public Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    // Method Getter h
    private int getH() {
        return h;
    }

    // Method Getter g
    private int getG() {
        return g;
    }

    // Method Setter g
    private void setG(int g) {
        this.g = g;
    }

    // Method Getter f
    public int getF() {
        return f;
    }

    // Method Setter f
    private void setF(int f) {
        this.f = f;
    }

    // Method Getter row
    public int getRow() {
        return row;
    }

    // Method Getter column
    public int getCol() {
        return col;
    }

    // Method Getter return boolean check if "is block?"
    public boolean isIs_block() {
        return is_block;
    }

    // Method Setter set boolean variable to "is block?"
    public void setIs_block(boolean is_block) {
        this.is_block = is_block;
    }

    // Method Getter to return parent object in Node class
    public Node getParent() {
        return parent;
    }

    // Method Setter to set parent object
    private void setParent(Node parent) {
        this.parent = parent;
    }

    // Method calculateHeuristic() with parameter final_node in Node class to apply AI in Doll enemy.
    public void calculateHeuristic(Node final_node) {
        this.h =
            Math.abs(final_node.getRow() - getRow()) + Math.abs(final_node.getCol() - getCol());
    }

    public void setNodeData(Node current_node) {
        setParent(current_node);
        setG(current_node.getG() + 1);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node current_node) {
        int f_cost = current_node.getF();
        if (f_cost < getF()) {
            setNodeData(current_node);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int final_cost = getG() + getH();
        setF(final_cost);
    }

    // Override "equals" method with obj parameter in Object class to determine the feature in game.
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node other = (Node) obj;
            return this.getRow() == other.getRow() && this.getCol() == other.getCol();
        }
        return false;
    }

    // Override output string statement
    @Override
    public String toString() {
        return "Node[row=" + row + ",col=" + col + "]";
    }
}