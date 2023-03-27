import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // find max length of string in  asciis
        int lengthMax = 0;
        for (String str : asciis) {
            lengthMax = Math.max(lengthMax, str.length());
        }

        // sort
        String[] sorted = asciis.clone();
        for (int index = 0; index < lengthMax; index++) {
            sortHelperLSD(sorted, index, lengthMax);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index, int lengthMax) {
        // Optional LSD helper method for required LSD radix sort
        // get int of max char
        int maxCountId = 0;
        for (String str : asciis) {
            maxCountId = Math.max(maxCountId, intOfCharAt(str, index, lengthMax));
        }
        // count
        int[] count = new int[maxCountId + 1];
        for (String str : asciis) {
            int countId = intOfCharAt(str, index, lengthMax);
            count[countId] += 1;
        }
        // start
        int[] start = new int[count.length];
        int pos = 0;
        for (int i = 0; i < count.length; i++) {
            start[i] = pos;
            pos += count[i];
        }
        //sort
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String str = asciis[i];
            int idInStart = intOfCharAt(str, index, lengthMax);
            int idInSorted = start[idInStart];
            sorted[idInSorted] = str;
            start[idInStart] +=1;
        }
        // sort asciis
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
    }
    private static int intOfCharAt(String str, int index, int lengthMax) {
        try {
            int idInStr = lengthMax - index - 1;
            return (int) str.charAt(idInStr);
        } catch (Exception e) {
            return Character.MIN_VALUE;
        }
    }
    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        String[] subStrings = Arrays.copyOfRange(asciis, start, end);
        // find max length of string in substring
        int lengthMax = 0;
        for (String str : subStrings) {
            lengthMax = Math.max(lengthMax, str.length());
        }
        if (index >= lengthMax) {
            return;
        }
        //sort substring by indexLSD
        int indexLSD = lengthMax - index - 1;
        sortHelperLSD(subStrings, indexLSD, lengthMax);
        System.arraycopy(subStrings,0, asciis, start, subStrings.length);
        //get start pos of subSubString
        ArrayList<Integer> startPos = new ArrayList<>();
        int intOfPreChar = intOfCharAt(asciis[start], indexLSD, lengthMax);
        startPos.add(start);
        for (int i = start + 1; i < end; i++) {
            String str = asciis[i];
            int intOfCharAtIndex = intOfCharAt(str, lengthMax -index - 1, lengthMax);
            if (intOfCharAtIndex != intOfPreChar) {
                startPos.add(i);
                intOfPreChar = intOfCharAtIndex;
            }
        }
        // recursive
        for(int i = 0; i < startPos.size(); i++) {
            int startNewSubStrings = startPos.get(i);
            int endNewSubStrings = end;
            if (i < startPos.size() - 1) {
                endNewSubStrings = startPos.get(i + 1);
            }
            sortHelperMSD(asciis,startNewSubStrings, endNewSubStrings, index + 1);
        }

    }
}
