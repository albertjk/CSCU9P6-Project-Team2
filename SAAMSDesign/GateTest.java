import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a class containing methods for unit testing the methods of the Gate class.
 * Each method from the Gate class has a tester method in this class. 
 * The getStatus and getmCode methods are tested as part of the other test methods 
 * as we repeatedly check the statuses of the gates and the mCode of the aircraft allocated to the gate.
 * CSCU9P6 Project Group 2
 * Student ID: 2421468
 * Date: 05/03/2018
 */
public class GateTest {
	
	// These gate objects will be used in the tests
	Gate gate1;
	Gate gate2;
	
	@Before
	public void setUp() {
	
		// Set up the two Gate objects that are used in the tests.
		gate1 = new Gate(0);
		gate2 = new Gate(1);		
	}
	
	@Test
	public void testAllocate() {
		
		// Test the gate allocation to a plane:
		
		// Without allocation, the returned status code should be 0 (FREE)
		assertEquals(0, gate1.getStatus());
		
		// Allocate a gate to the plane. This mCode is a dummy value.
		gate1.allocate(5);
		
		// Test if the allocation was successful. The returned status code should be 1 (RESERVED).
		assertEquals(1, gate1.getStatus());
		assertEquals(gate1.getmCode(), 5); // Test that the correct mCode is returned.
	}
	
	@Test
	public void testDocked() {
		
		// Test what happens if the docked method is called without calling the allocate method first (bad scenario):
		
		/* Without allocation, the docked method should not change anything.
		The returned status code should be 0 (FREE) */
		gate1.docked();
		assertEquals(0, gate1.getStatus());
		
		// Test what happens if the methods are executed in the correct order:
		
		// First, allocate the gate to an aircraft. The mCode is a dummy value here.
		gate1.allocate(5);
		
		// Then, the plane can dock.
		gate1.docked();
		
		// Test if the aircraft has successfully docked. The returned status code should be 2 (OCCUPIED).
		assertEquals(2, gate1.getStatus());
		assertEquals(gate1.getmCode(), 5); // Test that the correct mCode is returned.
	}
	
	@Test
	public void testDeparted() {
		
		// Test what happens in three different bad scenarios:
		
		/* Without allocation and docking, the departed method should not change anything.
		The returned status code should be 0 (FREE) */ 
		gate1.departed();
		assertEquals(0, gate1.getStatus());
		
		/* Without allocation first, the docked and departed methods should not change anything.
		The returned status code should be 0 (FREE) */ 
		gate1.docked();
		gate1.departed();
		assertEquals(0, gate1.getStatus());		
		
		/* Without docking after allocation, the departed method should not change anything.
		The mCode is a dummy value here.
		The returned status code should be 1 (RESERVED) after the allocation is done. */
		gate1.allocate(5);
		gate1.departed();
		assertEquals(1, gate1.getStatus());
		assertEquals(gate1.getmCode(), 5); // Test that the correct mCode is returned.
		
		// Test what happens if the methods of GateTest are executed in the correct order:
		
		/* The gate is first allocated to the plane, then the plane is docked, 
		and, finally, it is departed. We repeatedly check the status after each step.
		After all these steps, the returned status code should be 0 (FREE),
		since the plane has already departed. Use gate2 to test this case because gate1 was already
		allocated in the previous case.	The mCode is a dummy value here. */
		gate2.allocate(3);
		assertEquals(1, gate2.getStatus());	// After allocation, the status code should be 1 (RESERVED)
		gate2.docked();
		assertEquals(2, gate2.getStatus()); // After docking, the status code should be 2 (OCCUPIED)
		gate2.departed();
		assertEquals(0, gate2.getStatus());	 // After departure, the status code should be 0 (FREE)
		assertEquals(gate2.getmCode(), 3); // Test that the correct mCode is returned
	}	
	
	@Test
	public void testToString() {
		
		// Test if the toString method returns the correct strings with the gate number for each gate.
		String gate1String = "Gate 0";
		String gate2String = "Gate 1";
		
		assertEquals(gate1.toString(), gate1String);
		assertEquals(gate2.toString(), gate2String);
	}
}