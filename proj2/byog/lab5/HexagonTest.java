package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

public class HexagonTest {
    @Test
    public void testGetNumberOfIth() {
        int expected1 = 4;
        int actual1 = Hexagon.getNumbersOfIth(5, 3);
        assertEquals(expected1,actual1);
    }
}
