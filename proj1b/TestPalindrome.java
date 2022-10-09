import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        String wordA1 = "nooon";
        String wordA2 = "a";
        assertTrue(Palindrome.isPalindrome(wordA1) && Palindrome.isPalindrome(wordA2));

        String wordB1 = "wang";
        String wordB2 = "Aa";
        assertFalse(Palindrome.isPalindrome(wordB1) && Palindrome.isPalindrome(wordB2));
    }
    @Test
    public void testIsPalindromeGeneralized() {
        String wordT1 = "flake";
        String wordF1 = "wang";
        CharacterComparator cc = new OffByOne();
        assertTrue(Palindrome.isPalindrome(wordT1, cc));
        assertFalse(Palindrome.isPalindrome(wordF1, cc));
    }
    /**Uncomment this class once you've created your Palindrome class. */
}
