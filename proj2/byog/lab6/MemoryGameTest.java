package byog.lab6;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryGameTest {
    public static void main(String[] args){
        MemoryGame mG = new MemoryGame(20, 20, 88);
        mG.drawFrame("ws");
    }
}
