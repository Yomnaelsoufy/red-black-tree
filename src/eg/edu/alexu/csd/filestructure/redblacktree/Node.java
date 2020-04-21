package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node implements INode {
    Comparable key;
    Object value;
    private boolean RED = false;
    private Node parent = null;
    private Node leftChild = null;
    private Node rightChild = null;

    Node(Comparable key, Object value){
        this.key = key;
        this.value = value;
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public void setParent(INode parent) {
        this.parent = (Node) parent;
    }

    @Override
    public INode getLeftChild() {
        return leftChild;
    }

    @Override
    public void setLeftChild(INode leftChild) {
        this.leftChild = (Node) leftChild;
    }

    @Override
    public INode getRightChild() {
        return rightChild;
    }

    @Override
    public void setRightChild(INode rightChild) {
        this.rightChild = (Node) rightChild;
    }

    @Override
    public Comparable getKey() {
        return key;
    }

    @Override
    public void setKey(Comparable key) {
        this.key = key;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean getColor() {
        if (isNull()) {
            return false;
        }
        return RED;
    }

    @Override
    public void setColor(boolean color) {
        this.RED = color;
    }

    @Override
    public boolean isNull() {
        return value == null;
    }
}
