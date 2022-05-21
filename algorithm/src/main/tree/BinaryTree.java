package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @author puppylpg on 2021/12/30
 */
public class BinaryTree {
    Node root;

    public BinaryTree() {
        demoTree();
        nicePrint(root);
    }

    public static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("pre order traverse has nothing to show");
            return;
        }

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        while (!stack.empty()) {
            Node node = stack.pop();
            print(node);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public void midOrder() {
        if (root == null) {
            System.out.println("mid order traverse has nothing to show");
            return;
        }

        Node cur = root;
        Stack<Node> stack = new Stack<>();
        while (!stack.empty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            Node node = stack.pop();
            print(node);
            if (node.right != null) {
                cur = node.right;
            }
        }
    }

    public void rightOrder() {
        if (root == null) {
            System.out.println("right order traverse has nothing to show");
            return;
        }

        Node cur = root;
        Stack<Node> stack = new Stack<>();
        while (!stack.empty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            Node node = stack.pop();

            if (node.right != null) {
                cur = node.right;
            }
        }
    }

    public void print(Node node) {
        System.out.println(node.value);
    }

    public void demoTree() {
        root = new Node(1);
        root.left = new Node(2);
        root.left.right = new Node(3);
        root.left.right.left = new Node(4);
        root.left.right.left.right = new Node(5);
        root.left.right.right = new Node(6);
        root.right = new Node(7);
        root.right.left = new Node(8);
        root.right.left.right = new Node(9);
        root.right.right = new Node(10);
    }

    public static void main(String... args) {
        BinaryTree root = new BinaryTree();
        root.preOrder();
        root.midOrder();
    }


    ///////////////////////////////////////////////////////
    // below code is to print tree
    public static void nicePrint(Node root) {
        List< StringPoint > result = getStrings((getWidth(root) + 1) / 2, 0, root);
        TreeMap< Integer, List< StringPoint > > lines = new TreeMap<  >();
        for (StringPoint s : result) {
            if (lines.get(s.y) != null) {
                lines.get(s.y).add(s);
            } else {
                List< StringPoint > l = new ArrayList<  >();
                l.add(s);
                lines.put(s.y, l);
            }
        }
        for (List< StringPoint > l : lines.values()) {
            System.out.println(flatten(l));
        }
    }

    private static String flatten(List< StringPoint > l) {
        int x = 0;
        StringBuilder sb = new StringBuilder();
        for (StringPoint s : l) {
            sb.append(new String(new char[s.x - x]).replace('\0', ' '));
            sb.append(s.value);
            x = sb.length();
        }
        return sb.toString();
    }

    private static int getWidth(Node root) {
        int width = 0;
        if (root.left != null) {
            width += getWidth(root.left);
        }
        if (root.right != null) {
            width += getWidth(root.right);
        }
        width += ("" + root.value).length();
        return width;
    }

    private static List< StringPoint > getStrings(int x, int y, Node root) {
        List< StringPoint > result = new ArrayList< StringPoint >();
        result.add(new StringPoint(x - ("" + root.value).length() / 2, y, ""
                + root.value));
        if (root.left != null) {
            int width = getWidth(root.left);
            int i = 0;
            for (; i <  (width + 1) / 2; ++i)
                result.add(new StringPoint(x - i - 1, y + i + 1, "/"));
            result.addAll(getStrings(x - i - 1, y + i + 1, root.left));
        }
        if (root.right != null) {
            int width = getWidth(root.right);
            int i = 0;
            for (; i <  (width + 1) / 2; ++i)
                result.add(new StringPoint(x + i + 1, y + i + 1, "\\"));
            result.addAll(getStrings(x + i + 1, y + i + 1, root.right));
        }
        return result;
    }

    static class StringPoint {
        Integer x;
        Integer y;
        String value;

        StringPoint(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + value + ")";
        }
    }
}
