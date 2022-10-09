import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars() {
        OffByOne obo = new OffByOne();
        boolean t1 = obo.equalChars('a', 'b');
        boolean t2 = obo.equalChars('r', 'q');
        boolean f1 = obo.equalChars('a', 'a');
        boolean f2 = obo.equalChars('a', 'B');
        boolean f3 = obo.equalChars('a', 'z');
        assertTrue(t1 && t2);
        assertFalse(f1 && f2 && f3);
    }
}
