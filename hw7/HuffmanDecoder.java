public class HuffmanDecoder {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: HuffmanDecoder watermelonsugar.txt.huf originalwatermelon.txt");
            return;
        }
        String fileToDecode = args[0];
        String fileToSave = args[1];

        //read coding trie
        ObjectReader or = new ObjectReader(fileToDecode);
        BinaryTrie binaryTrie = (BinaryTrie) or.readObject();
        int symbolsNum = binaryTrie.numberOfSymbols();

        //read bit the massive sequence of original txt and decode it to a file
        BitSequence toDecode = (BitSequence) or.readObject();
        char[] symbols = new char[symbolsNum];
        for (int i = 0; i < symbolsNum; i++) {
            Match match = binaryTrie.longestPrefixMatch(toDecode);
            symbols[i] = match.getSymbol();
            int matchSeqLength = match.getSequence().length();
            toDecode.removeFirstN(matchSeqLength);
        }
        FileUtils.writeCharArray(fileToSave, symbols);
    }
}
