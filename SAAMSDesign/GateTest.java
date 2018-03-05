import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a class containing methods for unit testing the methods of the Gate class.
 * Each method from the Gate class has a tester method in this class. 
 * The getStatus method is being tested as part of the other test methods 
 * as we repeatedly check the status.
 * @author Albert Jozsa-Kiraly
 * Date: 05/03/2018
 */
public class GateTest {
	
	Gate gate1;
	Gate gate2;
	
	@Before
	public void setUp() {
	
		// Set up the two Gate objects that are used in the tests.
		gate1 = new Gate();
		gate2 = new Gate();		
	}
	
	@Test
	public void testAllocate() {
		
		// Test the gate allocation to a plane:
		
		// Allocate a gate to the plane. This mCode is a dummy code.
		gate1.allocate(5);
		
		// Test if the allocation was successful. The returned status code should be 1 (RESERVED).
		assertEquals(1, gate1.getStatus());
	}
	
	@Test
	public void testDocked() {
		
		// Test what happens if the docked method is called without calling the allocate method first (bad scenario):
		
		/* Without allocation, the docked method should not change anything.
		The status code should remain 0 (FREE). */
		gate1.docked();
		assertEquals(0, gate1.getStatus());
		
		// Test what happens if the methods are executed in the correct order:
		
		// First, allocate the gate. The mCode is a dummy code here.
		gate1.allocate(5);
		
		// Then, the plane can dock.
		gate1.docked();
		
		// Test if the aircraft has successfully docked. The returned status code should be 2 (OCCUPIED).
		assertEquals(2, gate1.getStatus());
	}
	
	@Test
	public void testDeparted() {
		
		// Test what happens in three different bad scenarios:
		
		/* Without allocation and docking, the departed method should not change anything.
		The status code should remain 0 (FREE). */ 
		gate1.departed();
		assertEquals(0, gate1.getStatus());
		
		/* Without allocation first, the departed method should not change anything.
		The status code should remain 0 (FREE). */ 
		gate1.docked();
		gate1.departed();
		assertEquals(0, gate1.getStatus());		
		
		/* Without docking after allocation, the departed method should not change anything.
		The mCode is a dummy code here.
		The status code should remain 1 (RESERVED) after the allocation is done. */
		gate1.allocate(5);
		gate1.departed();
		assertEquals(1, gate1.getStatus());
		
		// Test what happens if the methods are executed in the correct order:
		
		/* The gate is first allocated to the plane, then the plane is docked, 
		and, finally, it is departed.
		The returned status code should be 0 (FREE), since the plane has already departed.
		Use gate2 to test this case because gate1 was already allocated in the previous case.
		The mCode is a dummy code here. */
		gate2.allocate(3);
		gate2.docked();
		gate2.departed();
		assertEquals(0, gate2.getStatus());		
	}
}