public class OffByN implements CharacterComparator {
    private final int N;
    public OffByN(int n) {
        N = n;
    }
    /* return true if two chars are OffByN */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
