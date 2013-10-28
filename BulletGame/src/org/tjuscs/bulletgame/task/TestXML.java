package org.tjuscs.bulletgame.task;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.tjuscs.bulletgame.task.Test.MathUtil;
import org.tjuscs.bulletgame.task.Test.TestData;

public class TestXML {

	public static void main(String[] args) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(TestXML.class
					.getResourceAsStream("test_script.xml"));
			Element root = doc.getRootElement();
			Task t = Tasks.parse(root);
			t.setBinding("data", new TestData());
			t.setBinding("Math", new MathUtil());
			while (true) {
				if (t.act())
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
