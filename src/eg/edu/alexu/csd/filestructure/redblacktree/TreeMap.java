package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.*;

public class TreeMap implements ITreeMap {
    private final IRedBlackTree iRedBlackTree;
    private int size = 0;
    private final ArrayList<Comparable> keys = new ArrayList<>();
    private final ArrayList<Object> values = new ArrayList<>();

    TreeMap() {
        iRedBlackTree = new RedBlackTree();
    }

    @Override
    public Map.Entry ceilingEntry(Comparable key) {
        Comparable ResultKey = ceilingKey(key);
        if (ResultKey == null)
            return null;
        return Map.entry(ResultKey, get(ResultKey));
    }

    @Override
    public Comparable ceilingKey(Comparable key) {
        if (key == null) return null;
        if (containsKey(key)) return key;
        else {
            iRedBlackTree.insert(key, 100);
            Comparable Result = getSuccessor(searchNode(key)).getKey();
            iRedBlackTree.delete(key);
            return Result;
        }
    }

    private INode searchNode(Comparable key) {
        if (key == null) return null;
        INode temp = iRedBlackTree.getRoot();
        while (temp != null) {
            if (temp.getKey().compareTo(key) > 0)
                temp = temp.getLeftChild();
            else if (temp.getKey().compareTo(key) == 0)
                return temp;
            else temp = temp.getRightChild();
        }
        return null;
    }

    private INode getSuccessor(INode node) {
        INode temp = node;
        if (temp.getRightChild() != null) {
            temp = temp.getRightChild();
            while (temp.getLeftChild() != null) {
                temp = temp.getLeftChild();
            }
            return temp;
        }
        while (temp != iRedBlackTree.getRoot() && temp.getParent().getRightChild() != null && temp.getParent().getRightChild().equals(temp)) {
            temp = temp.getParent();
        }
        return temp.getParent();
    }

    private INode getPredecessor(INode node) {
        INode temp = node;
        if (temp.getLeftChild() != null) {
            temp = temp.getLeftChild();
            while (temp.getRightChild() != null) {
                temp = temp.getRightChild();
            }
            return temp;
        }
        while (temp != iRedBlackTree.getRoot() && temp.getParent().getLeftChild() != null && temp.getParent().getLeftChild().equals(temp)) {
            temp = temp.getParent();
        }
        return temp.getParent();
    }

    @Override
    public void clear() {
        iRedBlackTree.clear();
        keys.clear();
        values.clear();
        size = 0;
        return;
    }

    @Override
    public boolean containsKey(Comparable key) {
        if (key == null)
            return false;
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public Set<Map.Entry> entrySet() {
        if (iRedBlackTree.isEmpty()) return null;
        LinkedHashSet<Map.Entry> KeyValue = new LinkedHashSet<>();
        ArrayList<Comparable> temp = (ArrayList<Comparable>) keys.clone();
        for (int i = 0; i < keys.size(); i++) {
            int min = findMin(temp);
            KeyValue.add(Map.entry(keys.get(min), values.get(min)));
            temp.set(min, null);
        }
        return KeyValue;
    }

    private int findMin(ArrayList<Comparable> Keys) {
        int min = 0;
        while (Keys.get(min) == null && min < Keys.size())
            min++;
        for (int i = 0; i < Keys.size(); i++) {
            if (Keys.get(i) != null && Keys.get(i).compareTo(Keys.get(min)) < 0) {
                min = i;
            }
        }
        return min;
    }

    private int findMax(ArrayList<Comparable> Keys) {
        int max = 0;
        while (Keys.get(max) == null && max < Keys.size())
            max++;
        for (int i = 0; i < Keys.size(); i++) {
            if (Keys.get(i) != null && Keys.get(i).compareTo(Keys.get(max)) > 0) {
                max = i;
            }
        }
        return max;
    }


    @Override
    public Map.Entry firstEntry() {
        if (iRedBlackTree.isEmpty()) return null;
        Comparable ResultKey = firstKey();
        return Map.entry(ResultKey, values.get(keys.indexOf(ResultKey)));
    }

    @Override
    public Comparable firstKey() {
        if (iRedBlackTree.isEmpty()) return null;
        return keys.get(findMin(keys));
    }

    @Override
    public Map.Entry floorEntry(Comparable key) {
        if (!containsKey(key)) return null;
        Comparable ResultKey = floorKey(key);
        return Map.entry(ResultKey, values.get(keys.indexOf(ResultKey)));
    }

    @Override
    public Comparable floorKey(Comparable key) {
        if (key == null) return null;
        if (containsKey(key)) return key;
        else {
            iRedBlackTree.insert(key, 100);
            Comparable Result = getPredecessor(searchNode(key)).getKey();
            iRedBlackTree.delete(key);
            return Result;
        }
    }

    private INode NODE(Comparable key) {
        INode temp = iRedBlackTree.getRoot();
        INode n = null;
        INode temp1;
        while (temp != null) {
            n = temp;
            if (temp.getKey().compareTo(key) == 0) {
                break;
            } else if (temp.getKey().compareTo(key) > 0) {
                temp = temp.getLeftChild();
            } else {
                temp = temp.getRightChild();
            }
        }
        return n;
    }

    private INode maxValue(INode node) {
        INode n = node;
        while (n.getRightChild() != null)
            n = n.getRightChild();
        return n;
    }

    private INode inOrderPredecessor(INode root, INode x) {
        if (x.getLeftChild() != null)
            return maxValue(x.getLeftChild());
        else {
            INode y = x.getParent();
            if (y == null)
                return x;
            while (y != null && x == y.getLeftChild()) {
                x = y;
                y = y.getParent();
            }
            return y;
        }
    }

    @Override
    public Object get(Comparable key) {
        if (!containsKey(key))
            return null;
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey) {
        if (!containsKey(toKey))
            return null;
        Set<Map.Entry> KeyValue = entrySet();
        ArrayList<Map.Entry> Result = new ArrayList<>();
        for (Map.Entry element : KeyValue) {
            if (((Comparable) element.getKey()).compareTo(toKey) < 0) {
                Result.add(element);
            } else
                break;
        }
        return Result;
    }

    @Override
    public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
        if (!containsKey(toKey))
            return null;
        if (!inclusive)
            return headMap(toKey);
        LinkedHashSet<Map.Entry> KeyValue = (LinkedHashSet<Map.Entry>) entrySet();
        ArrayList<Map.Entry> Result = new ArrayList<>();
        for (Map.Entry element : KeyValue) {
            if (((Comparable) element.getKey()).compareTo(toKey) <= 0) {
                Result.add(element);
            } else
                break;
        }
        return Result;

    }

    @Override
    public Set keySet() {
        if (iRedBlackTree.isEmpty()) return null;
        Set<Comparable> SetKeys = new LinkedHashSet<>();
        ArrayList<Comparable> temp = (ArrayList<Comparable>) keys.clone();
        for (int i = 0; i < keys.size(); i++) {
            int min = findMin(temp);
            SetKeys.add(keys.get(min));
            temp.set(min, null);
        }
        return SetKeys;
    }

    @Override
    public Map.Entry lastEntry() {
        if (iRedBlackTree.isEmpty()) return null;
        Comparable ResultKey = lastKey();
        return Map.entry(ResultKey, values.get(keys.indexOf(ResultKey)));
    }

    @Override
    public Comparable lastKey() {
        if (iRedBlackTree.isEmpty()) return null;
        return keys.get(findMax(keys));
    }

    @Override
    public Map.Entry pollFirstEntry() {
        if (iRedBlackTree.isEmpty()) return null;
        Map.Entry First = firstEntry();
        iRedBlackTree.delete((Comparable) First.getKey());
        int index = keys.indexOf(First.getKey());
        keys.remove(index);
        values.remove(index);
        size--;
        return First;
    }

    @Override
    public Map.Entry pollLastEntry() {
        if (iRedBlackTree.isEmpty()) return null;
        Map.Entry Last = lastEntry();
        iRedBlackTree.delete((Comparable) Last.getKey());
        int index = keys.indexOf(Last.getKey());
        keys.remove(index);
        values.remove(index);
        size--;
        return Last;
    }

    @Override
    public void put(Comparable key, Object value) {
        if (!containsKey(key)) {
            values.add(value);
            keys.add(key);
            size++;
        }
        values.set(keys.indexOf(key), value);
        iRedBlackTree.insert(key, value);

    }

    @Override
    public void putAll(Map map) {
        if (map == null) return;
        Object[] Key = map.keySet().toArray();
        Object[] Val = map.values().toArray();
        for (int i = 0; i < Key.length; i++) {
            if (!containsKey((Comparable) Key[i])) {
                keys.add((Comparable) Key[i]);
                values.add(Val[i]);
                size++;
            } else
                values.set(keys.indexOf(Key[i]), Val[i]);
            iRedBlackTree.insert((Comparable) Key[i], Val[i]);
        }
    }

    @Override
    public boolean remove(Comparable key) {
        if (!containsKey(key)) return false;
        Boolean check = iRedBlackTree.delete(key);
        if (!check) return false;
        int index = keys.indexOf(key);
        keys.remove(index);
        values.remove(index);
        size--;
        return check;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection values() {
        return values;
    }
}
