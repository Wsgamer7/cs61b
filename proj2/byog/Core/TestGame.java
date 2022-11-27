package byog.Core;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
public class TestGame {
    @Test
    public void testSeed() {
        Game game = new Game();
        Menu menu = new Menu(30, 50);
        long seed = menu.getSeed();
        long expect = 11;
        assertEquals(expect, seed);
    }
    public static void main(String[] args) {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);
        StdDraw.clear(Color.BLACK);
//        StdDraw.enableDoubleBuffering();
    }
}
