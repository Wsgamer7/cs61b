package lab14;
import edu.princeton.cs.algs4.In;
import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
		Generator generator = new StrangeBitwiseGenerator(800, 0, 0);
		Generator generator2 = new StrangeBitwiseGenerator(400, 5, 3);
		Generator generator3 = new StrangeBitwiseGenerator(60, 9, 3);
		ArrayList<Generator> generators = new ArrayList<>();
//		generators.add(generator3);
//		generators.add(generator2);
		generators.add(generator);

		MultiGenerator multiGenerator = new MultiGenerator(generators);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(multiGenerator);
		gav.drawAndPlay(2000, 1000000);
	}
} 