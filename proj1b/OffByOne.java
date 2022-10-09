public class OffByOne implements CharacterComparator {
    @Override
    public static boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
