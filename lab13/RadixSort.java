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
        // TODO: Implement LSD Sort
        //get the max length of Strings in asciis
        int maxLength = 0;
        for (String str : asciis) {
            int strLength = str.length();
            maxLength = strLength > maxLength ? strLength : maxLength;
        }
        // sort
        String[] sorted = asciis;
        for (int index = 0; index < maxLength; index++) {
            sorted = sortHelperLSD(sorted, index);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        //get the min and max in index
        char max = Character.MIN_VALUE;
        char min = Character.MAX_VALUE;
        char[] charsAtIndex = new char[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String str = asciis[i];
            char c = fetchChar(str, index);
            charsAtIndex[i] = c;
            max = max > c ? max : c;
            min = min < c ? min : c;
        }

        //get count
        int[] count = new int[transToId(max, min) + 1];
        for (char c : charsAtIndex) {
            int idC = transToId(c, min);
            count[idC] += 1;
        }

        // get start
        int[] start = new int[transToId(max, min) + 1];
        int pos = 0;
        for (int i = 0; i < start.length; i++) {
            start[i] = pos;
            pos += count[i];
        }

        //sort
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            int idInStart = transToId(charsAtIndex[i], min);
            int posInSorted = start[idInStart];
            sorted[posInSorted] = asciis[i];
            start[idInStart] += 1;
        }

        return sorted;
    }
    private static int transToId(char c, char min) {
        return c - min;
    }
    /* get the char at index */
    private static char fetchChar(String str, int id) {
        int lenStr = str.length();
        if (id < lenStr){
            return str.charAt(lenStr - 1 - id);
        } else {
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
        return;
    }
}
