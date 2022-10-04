import org.junit.Test;
import static org.junit.Assert.*;
public class TestFlik {
    @Test
    public void testFlik(){
        int a = 128;
        int b = 128;
        int c = 2;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));
    }
}
