package cs608;
import static org.junit.Assert.*;

import org.junit.Test;

import cs608.Lights.Color;


public class TrafficLightOOTest {
	
	@Test
	public void testTrafficLight() {
		TrafficLight tl = new TrafficLight("test_1");
        assertEquals(2, tl.getLights().length);
        assertEquals(0, tl.getCycles());
        assertEquals(0, tl.getCycles());
        assertEquals(false, tl.maintenanceNeeded());
        assertEquals("test_1", tl.getId());       
	}

	@Test
	public void testGetLights() {
		TrafficLight tl = new TrafficLight("test_2");
        assertEquals(2, tl.getLights().length);
	}

	@Test
	public void testGetCycles() {
		TrafficLight tl = new TrafficLight("test_3");
        assertEquals(0, tl.getCycles());
	}

	@Test
	public void testMaintenanceNeeded() {
		TrafficLight tl = new TrafficLight("test_4");
        assertEquals(false, tl.maintenanceNeeded());
	}

	@Test
	public void testGetId() {
		TrafficLight tl = new TrafficLight("test_5");
        assertEquals("test_5", tl.getId());
	}

}
