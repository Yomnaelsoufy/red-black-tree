package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TreeMap t = new TreeMap();
        t.put(10, "A");
        t.put(20, "B");
        t.put(30, "C");
        t.put(40, "D");
        t.put(60, "E");
        t.put(45, "F");
        t.put(42, "G");
        System.out.println("Ceiling key of 20 = " + t.ceilingKey(20));
        System.out.println("Floor key of 100 = " + t.floorKey(100));
        System.out.println("The first key = " + t.firstEntry().getKey() + " value = " + t.firstEntry().getValue());
        System.out.println("The last key = " + t.lastEntry().getKey() + " value = " + t.lastEntry().getValue());
        System.out.println("The values in the tree map :");
        Collection tree = t.entrySet();
        for (Object elem : tree) {
            System.out.println(elem);
        }
        t.pollFirstEntry();
        System.out.println("After deleting the first entry (key = 10):");
        Collection tree1 = t.entrySet();
        for (Object elem : tree1) {
            System.out.println(elem);
        }
        System.out.println("Head Map of 40 :");
        Collection Head = t.headMap(40,true);
        for (Object elem : Head) {
            System.out.println(elem);
        }
        System.out.println("value of key 60 : " + t.get(60));
    }


    public static void print(INode root) {
        List<List<String>> lines = new ArrayList<List<String>>();
        List<INode> level = new ArrayList<INode>();
        List<INode> next = new ArrayList<INode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (INode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = (n.getKey().toString()) + (n.getColor() ? "(R)" : "(B)");
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeftChild());
                    next.add(n.getRightChild());

                    if (n.getLeftChild() != null) nn++;
                    if (n.getRightChild() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<INode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}



