import java.util.*;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    private DictTrie dictTrie;
    private char[][] board;
    private PriorityQueue<String> pQ;
    public Boggle() {
        readDict();
    }
    private void readDict() {
        In wordsStream = new In(dictPath);
        if (!wordsStream.exists()) {
            throw new IllegalArgumentException();
        }
        dictTrie = new DictTrie();
        while (true) {
            String word = wordsStream.readLine();
            if (word == null) {
                continue;
            }
            dictTrie.put(word);
            if (!wordsStream.hasNextLine()) {
                break;
            }
        }
    }
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        //read board
        readBoard(boardFilePath);
        pQ = new PriorityQueue<>(new WordComparator()); //TODO
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                solveStartIn(x, y);
            }
        }
        List<String> aimList = new ArrayList<>();
        while (aimList.size() < k && !pQ.isEmpty()) {
            String toAdd = pQ.remove();
            if (aimList.size() > 0 && aimList.get(aimList.size() - 1).equals(toAdd)) {
                continue;
            }
            aimList.add(toAdd);
        }
        return aimList;
    }
    private void solveStartIn(int x, int y) {
        Cube startCube = new Cube(x, y);
        addCubeInTrie(dictTrie.root, startCube);
    }
    private void addCubeInTrie(Node preNod, Cube cub) {
        char c = cub.letter;
        if (!preNod.haveChild(c)) {
            return;
        }
        Node nowNode = preNod.child(c);
        nowNode.haveCube = true;
        nowNode.cube = cub;
        if (nowNode.exist) {
            String word = nowNode.wordUntil();
            pQ.add(word);
        }

        //recursive
        for (Cube adj : cub.adj(nowNode)) {
            addCubeInTrie(nowNode, adj);
        }
    }
    void readBoard(String boardFilePath) {
        List<char[]> boardList = new ArrayList<>();
        In in = new In(boardFilePath);
        int width = - 1;
        while(!in.isEmpty()) {
            String line = in.readLine();
            int lineLength = line.length();
            if (width == - 1) {
                width = lineLength;
            }

            //checkout rectangular
            if ((width > 0) && (lineLength != width)) {
                throw new IllegalArgumentException();
            }

            char[] lineArray = new char[lineLength];
            for (int i = 0; i < lineLength; i++) {
                lineArray[i] = line.charAt(i);
            }
            boardList.add(lineArray);
        }
        board = boardList.toArray(new char[0][]);
    }
    public char[][] getBoard() {
        return board;
    }
    private int width() {
        return board[0].length;
    }
    private int height() {
        return board.length;
    }
    private class Cube {
        int x;
        int y;
        char letter;
        public Cube(int x, int y) {
            if(!validate(x, y)) {
                throw new IllegalArgumentException("cube position is out of range");
            }
            this.x = x;
            this.y = y;
            letter = board[y][x];
        }
//        @Override
//        public int hashCode() {
//            String strX = Integer.toString(x);
//            String strY = Integer.toString(y);
//            return Integer.parseInt(strX + strY);
//        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            } else if (!(obj instanceof Cube)) {
                return false;
            }
            Cube other = (Cube) obj;
            return (x == other.x) && (y == other.y);
        }
        public List<Cube> adj(Node nowNode) {
            //TODO
            List<Cube> adjReturn = new ArrayList<>();
            for (int xDelta = - 1; xDelta <= 1; xDelta++) {
                for (int yDelta = -1; yDelta <= 1; yDelta++) {
                    int adjX = x + xDelta;
                    int adjY = y + yDelta;
                    if (!validate(adjX, adjY)) {
                        continue;
                    }
                    Cube toAdd = new Cube(adjX, adjY);
                    if (nowNode.isUsed(toAdd)) {
                        continue;
                    }
                    adjReturn.add(toAdd);
                }
            }
            return adjReturn;
        }
        private boolean validate(int x, int y) {
            if (x < 0 || x >= width()) {
                return false;
            }
            if (y < 0 || y >= height()) {
                return false;
            }
            return true;
        }
    }
    private class DictTrie {
        Node root = new Node();
        public void put(String word) {
            put(root, word, 0);
        }
        private Node put(Node node, String word, int d) {
            if (node == null) {
                node = new Node();
            }
            if (d == word.length()) {
                node.exist = true;
                return node;
            }
            char c = word.charAt(d);
            node.add(c, put(node.child(c), word, d + 1));
            return node;
        }
    }
    private class Node {
        boolean exist;
        Map<Character, Node> map;
        boolean haveCube;
        Node preNode;
        Cube cube;
        public Node () {
            exist = false;
            map = new HashMap<>();
            haveCube = false;
        }
        public void add(char c, Node child) {
            child.preNode = this;
            map.put(c, child);
        }
        public Node child(char c) {
            return map.get(c);
        }
        public boolean haveChild(char c) {
            return map.containsKey(c);
        }
        public String wordUntil() {
            if (preNode == null) {
                return "";
            }
            if (!haveCube) {
                throw new RuntimeException("output words only haveCube");
            }
            return preNode.wordUntil() + cube.letter;
        }
        public boolean isUsed(Cube otherCube) {
            if (!haveCube) {
                return false;
            }
            return otherCube.equals(cube) || preNode.isUsed(otherCube);
        }
    }
}
