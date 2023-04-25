import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class TestBoard {
    @Test
    public void testRead() {
        Boggle boggle = new Boggle();
        boggle.readBoard("exampleBoard2.txt");
        char[][] actual = boggle.getBoard();
        char[][] except = new char[4][3];
        except[0] = new char[]{'b', 'a', 'a'};
        except[1] = new char[]{'a', 'b', 'a'};
        except[2] = new char[]{'a', 'a', 'b'};
        except[3] = new char[]{'b', 'a', 'a'};
        assertArrayEquals(except, actual);
    }
    @Test
    public void testSolve() {
        Boggle boggle = new Boggle();
        List<String> actual = boggle.solve(7, "board500.txt");
        System.out.println(actual);
    }
}
