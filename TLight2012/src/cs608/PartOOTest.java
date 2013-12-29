package cs608;

import static org.junit.Assert.*;

import org.junit.Test;

public class PartOOTest {

	@Test
	public void testPart_1() {
		Part part = new Part("test_1");
		assertEquals("test_1", part.getId());
	}

	@Test
	public void testPart_2() {
		Part part = new Part("");
         assertNotNull(part.getId());

	}

	@Test
	public void testGetId() {
		Part part = new Part("test_3");
		assertEquals("test_3", part.getId());
	}

}
