package cs608;

import static org.junit.Assert.*;

import org.junit.Test;

import cs608.Lights.Color;

public class LightsOOTest {

	@Test
	public void testLights() {
		Lights l = new Lights("test_1", Color.RED);
		assertEquals("test_1",l.getId());
		assertEquals(Color.RED,l.get());
	}

	@Test
	public void testSet() {
		Lights l = new Lights("test_2", Color.RED);
		l.set(Color.GREEN);
		assertEquals(Color.GREEN,l.get());
	}

	@Test
	public void testGet() {
		Lights l = new Lights("test_3", Color.RED);
		l.set(Color.AMBER);
		assertEquals(Color.AMBER,l.get());
	}

}
