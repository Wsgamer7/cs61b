public class Palindrome {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> newDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            newDeque.addLast(word.charAt(i));
        }
        return newDeque;
    }
    /*return ture if a word is palindrome */
    public static boolean isPalindrome(String word) {
        Deque<Character> dq = wordToDeque(word);
        String reservedWord = "";
        for (int i = 0; i < word.length(); i++) {
            reservedWord += dq.removeLast();
        }
        return word.equals(reservedWord);
    }
    /* return true if a word is generalized palindrome on CharacterComparator cc */
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = wordToDeque(word);
        if (word.length() > 1) {
            while (dq.size() > 1) {
                boolean charsPassComparator = cc.equalChars(dq.removeFirst(), dq.removeLast());
                if (!charsPassComparator) {
                    return false;
                }
            }
        }
        return true;
    }

}
