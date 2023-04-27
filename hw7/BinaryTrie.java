import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
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

    }
    public Map<Character, BitSequence> buildLookupTable() {

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
    }
}
