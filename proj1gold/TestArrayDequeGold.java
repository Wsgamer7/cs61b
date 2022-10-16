import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void randomTest() {
        int timesOfTest = 100;
        for (int i = 0; i < timesOfTest; i += 1) {
            int maxCallTimes = 4;
            String log = "";
            StudentArrayDeque<Integer> stdAD = new StudentArrayDeque<Integer>();
            ArrayDequeSolution<Integer> solAD = new ArrayDequeSolution<Integer>();
            for (int j = 0; j < maxCallTimes; j += 1) {
                int indexCalled = StdRandom.uniform(4);
                if (solAD.isEmpty()) {
                    indexCalled = StdRandom.uniform(2);
                }
                int actual = 0;
                int expected = 0;
                switch (indexCalled) {
                    case 0 -> {
                        log = log + "addFirst(" + j + ")\n";
                        stdAD.addFirst(j);
                        solAD.addFirst(j);
                    }
                    case 1 -> {
                        log = log + "addLast(" + j + ")\n";
                        stdAD.addLast(j);
                        solAD.addLast(j);
                    }
                    case 2 -> {
                        log = log + "removeFirst()\n";
                        actual = stdAD.removeFirst();
                        expected = solAD.removeFirst();
                    }
                    case 3 -> {
                        log = log + "removeLast()\n";
                        actual = stdAD.removeLast();
                        expected = solAD.removeLast();
                    }
                }
                if (actual != expected) {
                    assertEquals(log, actual, expected);
                }
            }
        }
    }
}
