import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    public void testGet() {
        ArrayDeque<Integer> orgin1 = new ArrayDeque<>();
        orgin1.addLast(1);
        orgin1.addLast(2);
        int expected1 = 2;
        int actual = orgin1.get(1);
        assertEquals(expected1, actual);
    }

}
