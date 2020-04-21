package eg.edu.alexu.csd.filestructure.redblacktree;

public class RedBlackTree implements IRedBlackTree {
    private INode Root = null;
    private INode DoubleBlack = null;

    private void RotateRight(INode n) {
        INode parentOfN = n.getParent();
        if (parentOfN == null) return;
        if (n.getRightChild() != null) {
            parentOfN.setLeftChild(n.getRightChild());
            n.getRightChild().setParent(parentOfN);
        } else
            parentOfN.setLeftChild(null);
        if (parentOfN.getParent() == null) {
            Root = n;
            n.setParent(null);
        } else {
            if (parentOfN.getParent().getRightChild() != null && parentOfN.getParent().getRightChild().equals(parentOfN)) {
                parentOfN.getParent().setRightChild(n);

            } else {
                parentOfN.getParent().setLeftChild(n);
            }
            n.setParent(parentOfN.getParent());
        }
        parentOfN.setParent(n);
        n.setRightChild(parentOfN);
    }

    private void RotateLeft(INode n) {
        INode parentOfN = n.getParent();
        if (parentOfN == null) return;
        if (n.getLeftChild() != null) {
            parentOfN.setRightChild(n.getLeftChild());
            n.getLeftChild().setParent(parentOfN);
        } else
            parentOfN.setRightChild(null);
        if (parentOfN.getParent() == null) {
            Root = n;
            n.setParent(null);
        } else {
            if (parentOfN.getParent().getLeftChild() != null && parentOfN.getParent().getLeftChild().equals(parentOfN)) {
                parentOfN.getParent().setLeftChild(n);

            } else {
                parentOfN.getParent().setRightChild(n);
            }
            n.setParent(parentOfN.getParent());
        }
        parentOfN.setParent(n);
        n.setLeftChild(parentOfN);
    }


    @Override
    public INode getRoot() {
        return this.Root;
    }

    @Override
    public boolean isEmpty() {
        return Root == null;
    }

    @Override
    public void clear() {
        if (Root == null)
            return;
        this.Root.setLeftChild(null);
        this.Root.setRightChild(null);
        this.Root.setValue(null);
        this.Root.setKey(null);
        Root = null;
    }

    @Override
    public Object search(Comparable key) {
        INode temp = searchNode(key);
        if (temp == null)
            return null;
        return searchNode(key).getValue();
    }

    private INode searchNode(Comparable key) {
        if (key == null) return null;
        INode temp = Root;
        while (temp != null) {
            if (temp.getKey().compareTo(key) > 0)
                temp = temp.getLeftChild();
            else if (temp.getKey().compareTo(key) == 0)
                return temp;
            else temp = temp.getRightChild();
        }
        return null;
    }


    @Override
    public boolean contains(Comparable key) {
        return search(key) != null;
    }

    private void updateValue(Comparable key, Object value) {
        INode temp = Root;
        while (temp != null) {
            if (temp.getKey().compareTo(key) > 0)
                temp = temp.getLeftChild();
            else if (temp.getKey().compareTo(key) == 0) {
                temp.setValue(value);
                return;
            } else temp = temp.getRightChild();
        }
    }

    @Override
    public void insert(Comparable key, Object value) {
        if (key == null || value == null) {
            return;
        }
        if (contains(key)) {
            updateValue(key, value);
            return;
        }
        INode temp = new Node(key, value);
        if (Root == null) {
            Root = temp;
            this.Root.setColor(false);
        } else {
            temp = add(key, value);
            if (!temp.getParent().getColor()) {
                return;
            }
            InsertCases(temp);
            Root.setColor(false);
        }

    }

    private void InsertCases(INode newNode) {
        boolean left = true;
        if (newNode == null)
            return;
        if (newNode.equals(Root)) {
            newNode.setColor(false);
            return;
        }
        if (!newNode.getParent().getColor() || !newNode.getColor() || newNode.getParent().equals(Root)) {
            return;
        }
        //check whether the parent is the right or left child
        if (newNode.getParent().getParent().getRightChild() != null && newNode.getParent().getParent().getRightChild().equals(newNode.getParent())) {
            left = false;
        }
        //case1
        if (left) {
            if (newNode.getParent().getParent().getRightChild() != null && newNode.getParent().getParent().getRightChild().getColor()) {
                newNode.getParent().setColor(false);
                newNode.getParent().getParent().getRightChild().setColor(false);
                newNode.getParent().getParent().setColor(true);
                InsertCases(newNode.getParent().getParent());
                return;
            }
        } else {
            if (newNode.getParent().getParent().getLeftChild() != null && newNode.getParent().getParent().getLeftChild().getColor()) {
                newNode.getParent().setColor(false);
                newNode.getParent().getParent().getLeftChild().setColor(false);
                newNode.getParent().getParent().setColor(true);
                InsertCases(newNode.getParent().getParent());
                return;
            }
        } //EndCase1
        //Case2 & 3
        if (left) {
            if (newNode.getParent().getParent().getRightChild() == null || !newNode.getParent().getParent().getRightChild().getColor()) {
                if (newNode.getParent().getLeftChild() != null && newNode.getParent().getLeftChild().equals(newNode)) {
                    newNode.getParent().setColor(!newNode.getParent().getColor());
                    newNode.getParent().getParent().setColor(!newNode.getParent().getParent().getColor());
                    RotateRight(newNode.getParent());
                    InsertCases(newNode.getParent());
                } else {
                    RotateLeft(newNode);
                    newNode.setColor(!newNode.getColor());
                    newNode.getParent().setColor(!newNode.getParent().getColor());
                    RotateRight(newNode);
                    InsertCases(newNode.getParent());
                }
            }
            return;
        } else {
            if (newNode.getParent().getParent().getLeftChild() == null || !newNode.getParent().getParent().getLeftChild().getColor()) {
                if (newNode.getParent().getRightChild() != null && newNode.getParent().getRightChild().equals(newNode)) {
                    newNode.getParent().setColor(!newNode.getParent().getColor());
                    newNode.getParent().getParent().setColor(!newNode.getParent().getParent().getColor());
                    RotateLeft(newNode.getParent());
                    InsertCases(newNode.getParent());
                } else {
                    RotateRight(newNode);
                    newNode.setColor(!newNode.getColor());
                    newNode.getParent().setColor(!newNode.getParent().getColor());
                    RotateLeft(newNode);
                    InsertCases(newNode.getParent());
                }
            }
            return;
        }
    }

    private boolean isLeaf(INode node) {
        return (node.getLeftChild() == null && node.getRightChild() == null);
    }

    private boolean isLeft(INode node) {
        if (node == null || node.getParent() == null) return false;
        return node.getParent().getLeftChild() != null && node.getParent().getLeftChild().equals(node);
    }

    @Override
    public boolean delete(Comparable key) {
        if (!contains(key) || Root == null)
            return true;
        if (key == null)
            return false;
        //First Apply BSTDeletion
        INode node = searchNode(key);
        if (node.getParent() == null && node.getRightChild() == null && node.getLeftChild() == null) {
            return actualDelete(Root);
        }
        if (!isLeaf(node)) {
            INode suc = successor(node);
            if (!suc.equals(node)) {
                swapKeyVal(node, suc);
                node = suc;
            }
        }
        if (!isLeaf(node)) {
            if (node.getRightChild() != null) {
                if (node.getColor() || node.getRightChild().getColor()) {
                    node.setColor(false);
                    swapKeyVal(node, node.getRightChild());
                    Root.setColor(false);
                    return actualDelete(node.getRightChild());
                } else {
                    swapKeyVal(node, node.getRightChild());
                    node = node.getRightChild();
                }
            } else {
                if (node.getColor() || node.getLeftChild().getColor()) {
                    node.setColor(false);
                    swapKeyVal(node, node.getLeftChild());
                    Root.setColor(false);
                    return actualDelete(node.getLeftChild());
                } else {
                    swapKeyVal(node, node.getLeftChild());
                    node = node.getLeftChild();
                }
            }
        }
        DoubleBlack = node;
        while (DoubleBlack != null && DoubleBlack != Root) {
            if (isLeaf(node)) {
                if (node.getColor())
                    return actualDelete(node);
                else {
                    INode sibling = getSibling(DoubleBlack);
                    if (sibling == null || !sibling.getColor()) {
                        if (sibling != null) {
                            if ((sibling.getLeftChild() != null && sibling.getLeftChild().getColor())
                                    || (sibling.getRightChild() != null && sibling.getRightChild().getColor())) {
                                INode redChild;
                                if ((sibling.getRightChild() != null && sibling.getRightChild().getColor())) {
                                    redChild = sibling.getRightChild();
                                } else
                                    redChild = sibling.getLeftChild();
                                if (isLeft(sibling) && isLeft(redChild)) {
                                    sibling.getParent().setColor(false);
                                    RotateRight(sibling);
                                    sibling.setColor(true);
                                    redChild.setColor(false);
                                    DoubleBlack = null;
                                } else if (!isLeft(sibling) && !isLeft(redChild)) {
                                    sibling.getParent().setColor(false);
                                    RotateLeft(sibling);
                                    sibling.setColor(true);
                                    redChild.setColor(false);
                                    DoubleBlack = null;
                                } else if (!isLeft(sibling) && isLeft(redChild)) {
                                    RotateRight(redChild);
                                    sibling.setColor(true);
                                    redChild.setColor(false);
                                } else if (isLeft(sibling) && !isLeft(redChild)) {
                                    RotateLeft(redChild);
                                    sibling.setColor(true);
                                    redChild.setColor(false);
                                }

                            } else {
                                sibling.setColor(true);
                                if (DoubleBlack.getParent().getColor()) {
                                    DoubleBlack.getParent().setColor(false);
                                    DoubleBlack = null;
                                } else
                                    DoubleBlack = DoubleBlack.getParent();
                            }
                        } else {
                            if (DoubleBlack.getParent().getColor()) {
                                DoubleBlack.getParent().setColor(false);
                                DoubleBlack = null;
                            } else
                                DoubleBlack = DoubleBlack.getParent();
                        }
                    } else {
                        sibling.setColor(false);
                        sibling.getParent().setColor(true);
                        if (isLeft(sibling)) {
                            RotateRight(sibling);
                        } else {
                            RotateLeft(sibling);
                        }
                    }
                }
            } else {
                if (node.getRightChild() != null) {
                    if (node.getColor() || node.getRightChild().getColor()) {
                        node.setColor(false);
                        swapKeyVal(node, node.getRightChild());
                        Root.setColor(false);
                        return actualDelete(node.getRightChild());
                    } else {
                        swapKeyVal(node, node.getRightChild());
                        node = node.getRightChild();
                    }
                } else {
                    if (node.getColor() || node.getLeftChild().getColor()) {
                        node.setColor(false);
                        swapKeyVal(node, node.getLeftChild());
                        Root.setColor(false);
                        return actualDelete(node.getLeftChild());
                    } else {
                        swapKeyVal(node, node.getLeftChild());
                        node = node.getLeftChild();
                    }
                }
            }
        }
        Root.setColor(false);
        return actualDelete(node);
    }

    private INode getSibling(INode node) {
        if (node == null || node.getParent() == null) return null;
        if (isLeft(node)) {
            return node.getParent().getRightChild();
        } else
            return node.getParent().getLeftChild();
    }

    private void swapKeyVal(INode u, INode v) {
        if (u == null || v == null || u.isNull()) return;
        Node temp = new Node(u.getKey(), u.getValue());
        u.setValue(v.getValue());
        u.setKey(v.getKey());
        v.setValue(temp.getValue());
        v.setKey(temp.getKey());
    }

    private boolean actualDelete(INode node) {
        if (Root == node) {
            Root = null;
            return true;
        }
        if (node == null) return false;
        if (isLeft(node)) {
            node.getParent().setLeftChild(null);
        } else
            node.getParent().setRightChild(null);
        return true;
    }

    private INode add(Comparable key, Object value) {
        INode temp = Root;
        INode node = null;
        boolean left = false;
        while (temp != null) {
            node = temp;
            if (temp.getKey().compareTo(key) <= 0) {
                temp = temp.getRightChild();
                left = false;
            } else {
                temp = temp.getLeftChild();
                left = true;
            }
        }
        temp = new Node(key, value);
        temp.setColor(true);
        if (left) {
            node.setLeftChild(temp);
            temp.setParent(node);
            return node.getLeftChild();
        } else {
            node.setRightChild(temp);
            temp.setParent(node);
            return node.getRightChild();
        }
    }


    private INode successor(INode x) {
        INode temp = x;
        if (temp.getRightChild() != null) {
            temp = temp.getRightChild();
            while (temp.getLeftChild() != null) {
                temp = temp.getLeftChild();
            }
        }
        return temp;
    }

}