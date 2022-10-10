import org.junit.Test;
import static org.junit.Assert.*;
public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
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
        assertTrue(palindrome.isPalindrome(wordA1) && palindrome.isPalindrome(wordA2));

        String wordB1 = "wang";
        String wordB2 = "Aa";
        assertFalse(palindrome.isPalindrome(wordB1) && palindrome.isPalindrome(wordB2));
    }
    @Test
    public void testIsPalindromeGeneralized() {
        String wordT1 = "flake";
        String wordF1 = "wang";
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(wordT1, cc));
        assertFalse(palindrome.isPalindrome(wordF1, cc));
    }
}
