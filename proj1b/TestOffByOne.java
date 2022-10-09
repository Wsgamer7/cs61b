import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset. */
    static CharacterComparator offByOne = new OffByOne();

    /*Your tests go here.
    Uncomment this class once you've created your
    CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars() {
        boolean t1 = OffByOne.equalChars('a', 'b');
        boolean t2 = OffByOne.equalChars('r', 'q');
        boolean f1 = OffByOne.equalChars('a', 'a');
        boolean f2 = OffByOne.equalChars('a', 'B');
        boolean f3 = OffByOne.equalChars('a', 'z');
        assertTrue(t1 && t2);
        assertFalse(f1 && f2 && f3);
    }
}
