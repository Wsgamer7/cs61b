import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {
    @Test
    public void testEqualChars() {
        OffByN offBy6 = new OffByN(6);
        boolean t1 = offBy6.equalChars('a', 'g');
        boolean t2 = offBy6.equalChars('g', 'a');
        boolean f1 = offBy6.equalChars('a', 'b');
        assertTrue(t1 && t2);
        assertFalse(f1);
    }
}
