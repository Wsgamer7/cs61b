package byog.Core;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
public class TestRoom {
    @Test
    public void testInRoom() {
        Random RANDOM = new Random(200);
        for (int i = 0; i < 7; i ++) {
            System.out.print(RANDOM.nextInt(0, 3));
        }
    }
}
