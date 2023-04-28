import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    private final Node trie;
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        trie = buildTrie(frequencyTable);
    }
    private static Node buildTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char ch : frequencyTable.keySet()) {
            int freq = frequencyTable.get(ch);
            if (freq == 0) {
                continue;
            }
            Node charNode = new Node(ch, freq, null, null);
            pq.insert(charNode);
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        BitSequence longestPrefix = new BitSequence();
        Node pointer = trie;
        char ch = '\0';
        for (int i = 0; i < querySequence.length(); i++) {
            int bit = querySequence.bitAt(i);
            if (bit == 0) {
                pointer = pointer.left;
            } else {
                pointer = pointer.right;
            }
            if (pointer == null) {
                break;
            }
            ch = pointer.ch;
            longestPrefix = longestPrefix.appended(bit);
        }
        return new Match(longestPrefix, ch);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        lookupTableHelper(trie, new BitSequence(), lookupTable);
        return lookupTable;
    }
    private void lookupTableHelper(Node node, BitSequence prefix,
                                   Map<Character, BitSequence> lookupTable) {
        if (node == null) {
            return;
        } else if (node.isLeaf()) {
            char ch = node.ch;
            lookupTable.put(ch, prefix);
            return;
        }
        lookupTableHelper(node.left, prefix.appended(0), lookupTable);
        lookupTableHelper(node.right, prefix.appended(1), lookupTable);
    }
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
        private boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
