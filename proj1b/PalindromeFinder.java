/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        int[] maxNPalindrome = new int[] {0, 0};
        for (int n = 0; n < 26; n++) {
            int countNPalindrome = 0;
            CharacterComparator cc = new OffByN(n);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                    countNPalindrome += 1;
                }
            }
            if (countNPalindrome > maxNPalindrome[1]) {
                maxNPalindrome[0] = n;
                maxNPalindrome[1] = countNPalindrome;
            }
        }
        System.out.printf("max N is %d : numbers of NPalindrome is %d", maxNPalindrome[0], maxNPalindrome[1]);
    }
}
