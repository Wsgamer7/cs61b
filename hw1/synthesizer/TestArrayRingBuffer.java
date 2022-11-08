package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        BoundedQueue<Integer> queue1 = new ArrayRingBuffer<>(8);
        for(int i = 0; i < 8; i++) {
            queue1.enqueue(i);
        }
        queue1.dequeue();
        int actual = queue1.dequeue();
        int expect = 1;
        assertEquals(expect, actual);
    }
    @Test
    public void iteratorTest() {
        BoundedQueue<Integer> bq = new ArrayRingBuffer<>(8);
        for (int i = 0; i < 4; i++) {
            bq.enqueue(i);
        }
        Iterator<Integer> thisIterator = bq.iterator();
        int expect = 1;
        thisIterator.next();
        int actual = thisIterator.next();
        assertEquals(expect, actual);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
