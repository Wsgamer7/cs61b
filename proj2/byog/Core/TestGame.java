package byog.Core;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestGame {
    @Test
    public void testSeed() {
        Game newGame = new Game();
        long expected = 12345;
        newGame.getSeedAndActionFrom("N123ni45swda");
        long actual = newGame.seed;
        assertEquals(expected, actual);
    }
}
