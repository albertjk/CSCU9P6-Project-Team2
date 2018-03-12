import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a class containing methods for unit testing the methods of the GateInfoDatabase class.
 * Each method from the GateInfoDatabase class has a tester method in this class. 
 * 
 * @author Albert Jozsa-Kiraly
 * Date: 12/03/2018
 */
public class GateInfoDatabaseTest {
	
	// A constant: the number of aircraft gates at the airport.
	public int maxGateNumber = 2;
	
	// The two gates of the airport
	Gate gate1;
	Gate gate2;
	
	// Holds the Gate object of all gate present at the airport (i.e. 2 objects)
	Gate[] gates;

	@Before
	public void setUp() {
		
		// Initialise the Gate objects and add them to the gates array
		gate1 = new Gate();
		gate2 = new Gate();
		
		gates[0] = gate1;
		gates[1] = gate2;
	}
	
	@Test
	public void testGetStatus() {
		
	}
	
	@Test
	public void testGetStatuses() {
		
	}
	
	@Test
	public void testAllocate() {
		
	}
	
	@Test
	public void testDocked() {
		
	}
	
	@Test
	public void testDeparted() {
		
	}
}
