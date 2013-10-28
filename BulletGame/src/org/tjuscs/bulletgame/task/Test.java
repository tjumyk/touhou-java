package org.tjuscs.bulletgame.task;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		TestData data = new TestData();

		Task t = Tasks
				.sequence(
						Tasks.loop("100", Tasks.ifelse("data.a<50", Tasks
								.sequence(Tasks.execute("data.a=data.a+1"),
										Tasks.execute("data.print()")), Tasks
								.sequence(Tasks.execute("data.a=999"),
										Tasks.execute("data.print()")))),
						Tasks.sequence(
								Tasks.wait(98),
								Tasks.execute("var random = Math.random(10,20); "
										+ "var newInt = new('java.lang.Integer',random);"
										+ "data.arr.add(newInt);"
										+ "data.printArr();")));
		t.setBinding("data", data);
		t.setBinding("Math", new MathUtil());
		while (true) {
			if (t.act())
				break;
		}
	}

	public static class TestData {
		public int a = 0;
		public List<Integer> arr = new ArrayList<Integer>();

		public void print() {
			System.out.println(a);
		}

		public void printArr() {
			System.out.println(arr);
		}
	}

	public static class MathUtil {
		public int random(int min, int max) {
			return (int) (Math.random() * (max - min) + min);
		}
	}
}
