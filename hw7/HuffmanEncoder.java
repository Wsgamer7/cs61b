import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char ch : inputSymbols) {
            if (!frequencyTable.containsKey(ch)) {
                frequencyTable.put(ch, 1);
            } else {
                int preFreqOfCh = frequencyTable.get(ch);
                frequencyTable.put(ch, preFreqOfCh + 1);
            }
        }
        return frequencyTable;
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: HuffmanEncoder.java <fileName>");
            return;
        }
        String fileName = args[0];
        ObjectWriter ow = new ObjectWriter(fileName + ".huf");

        //build decoding trie
        char[] allSymbols = FileUtils.readFile(fileName);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(allSymbols);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);
        ow.writeObject(binaryTrie);

        //decoding
        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> bitSeqList = new ArrayList<>();
        for (char ch : allSymbols) {
            BitSequence bitSeqOfCh = lookupTable.get(ch);
            bitSeqList.add(bitSeqOfCh);
        }
        BitSequence assembledBitSeq = BitSequence.assemble(bitSeqList);
        ow.writeObject(assembledBitSeq);
    }
}
