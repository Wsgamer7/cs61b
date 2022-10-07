import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {
    @Test
    public void testGet() {
        ArrayDeque<Integer> orgin1 = new ArrayDeque<>();
        orgin1.addFirst(0);
        orgin1.addFirst(1);
        orgin1.addLast(2);
        orgin1.addFirst(3);
        orgin1.removeFirst();
        orgin1.removeFirst();
        orgin1.addFirst(6);
        orgin1.get(0);
        orgin1.removeFirst();
        orgin1.removeLast();
        orgin1.removeLast();
        orgin1.addFirst(11);
    }

}
