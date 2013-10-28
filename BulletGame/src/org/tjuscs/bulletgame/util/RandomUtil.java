package org.tjuscs.bulletgame.util;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();

	public static void Seed(int seed) {
		random.setSeed(seed);
	}
	
	public static double rand(){
		return random.nextDouble();
	}

	public static int Int(int a, int b) {
		return random.nextInt(b - a + 1) + a;
	}

	public static double Float(double a, double b) {
		return random.nextDouble() * (b - a) + a;
	}

	public static int Sign() {
		return random.nextBoolean() ? -1 : 1;
	}

}
