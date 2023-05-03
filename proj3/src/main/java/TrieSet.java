import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieSet {
    private Node root = new Node();
    public void add(String key) {
        add(key, root, 0);
    }
    private Node add(String key, Node node, int d) {
        if (node == null) {
            node = new Node();
        }
        if (d == key.length()) {
            node.exist = true;
            return node;
        }
        char ch = key.charAt(d);
        char chLow = Character.toLowerCase(ch);
        Node nodeOfCh = add(key, node.links.get(chLow), d + 1);
        nodeOfCh.preChar = ch;

        node.links.put(chLow, nodeOfCh);
        nodeOfCh.preNode = node;
        return node;
    }
    public List<String> getStringsByPrefix(String prefix) {
        // prefix letters are all lowercase
        Node pointer = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            pointer = pointer.links.get(ch);
            if (pointer == null) {
                return new ArrayList<>();
            }
        }
        List<String> stringList = new ArrayList<>();
        gSPHelper(pointer, stringList);
        return stringList;
    }
    /* travel node and its child node, if a node exist, add its string to stringList*/
    private void gSPHelper(Node node, List<String> stringList) {
        if (node == null) {
            return;
        }
        if (node.exist) {
            stringList.add(node.getString());
        }
        for (Node child : node.children()) {
            gSPHelper(child, stringList);
        }
    }
    private class Node {
        boolean exist;
        Node preNode;
        char preChar;
        Map<Character, Node> links;
        public Node() {
            exist = false;
            links = new HashMap<>();
            preNode = null;
        }
        public Iterable<Node> children() {
            return links.values();
        }
        public String getString() {
            if (preNode == null) {
                return "";
            }
            return preNode.getString() + preChar;
        }
    }
}
