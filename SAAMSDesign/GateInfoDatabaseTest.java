import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the class containing methods for unit testing the methods of the GateInfoDatabase class.
 * Each method from the GateInfoDatabase class has a tester method in this class. The getStatus 
 * method is tested as part of the other test methods as we repeatedly check the statuses of gates.
 * The getStatuses method is tested in the testDepartedGoodScenario and testDepartedBadScenario test 
 * methods as we repeatedly check the elements of the array for the status codes (the array is returned by getStatuses).
 * CSCU9P6 Project Group 2
 * Student ID: 2421468
 * Date: 22/03/2018
 */
public class GateInfoDatabaseTest {
		
	// The GateInfoDatabase object used in the tests
	GateInfoDatabase gateInfoDatabase;

	@Before
	public void setUp() {		
		
		/* Initialise the GateInfoDatabase object. 
		The object in its constructor initialises a new gates array
		with two Gate objects added to the array (corresponding to the two gates at the airport). */
		gateInfoDatabase = new GateInfoDatabase();		
	}	
	
	@Test
	public void testAllocate() {	
		
		// This test method tests the allocate method of GateInfoDatabase.
		
		/* Without allocation, the status is FREE for both gates (no aircraft has landed yet).
		The returned status code should be 0 (FREE) for both gates . */
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));

		// Now, allocate the gates to the planes. The mCodes are dummy values.
		gateInfoDatabase.allocate(0, 112);
		gateInfoDatabase.allocate(1, 332);
		
		/* Test if the allocations were successful. The returned status codes should be 1 (RESERVED)
		for both gates. */
		assertEquals(1, gateInfoDatabase.getStatus(0));
		assertEquals(1, gateInfoDatabase.getStatus(1));
	}
	
	@Test
	public void testDocked() {	
		
		// This test method tests the docked method of GateInfoDatabase.
		
		/* Test what happens if the docked method is called without calling the allocate method first
		(bad scenario): */
		
		/* Without allocation, the docked method should not change anything.
		The returned status code should be 0 (FREE) for both gates (no aircraft has landed yet). */
		gateInfoDatabase.docked(0);
		gateInfoDatabase.docked(1);
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test what happens if the methods are executed in the correct order:
		
		// Allocate the gates to the planes. The mCodes are dummy values.
		gateInfoDatabase.allocate(0, 112);
		gateInfoDatabase.allocate(1, 332);
				
		/* Test if the allocations were successful. The returned status codes should be 1 (RESERVED)
		for both gates. */
		assertEquals(1, gateInfoDatabase.getStatus(0));
		assertEquals(1, gateInfoDatabase.getStatus(1));
		
		// Then, the planes are docked.
		gateInfoDatabase.docked(0);
		gateInfoDatabase.docked(1);
		
		/* Test if the aircrafts have successfully docked.
		The returned status codes should be 2 (OCCUPIED) for both gates. */
		assertEquals(2, gateInfoDatabase.getStatus(0));
		assertEquals(2, gateInfoDatabase.getStatus(1));		
	}
	
	@Test
	public void testDepartedBadScenario() {
		
		// This test method tests the departed method of GateInfoDatabase.
		
		// Test what happens in three different bad scenarios (the allocate, docked, and departed methods are not executed in the correct order):
		
		// Test first bad scenario:
		
		/* Without the allocation and docking of the planes to the gates, the departed method should 
		not change anything. The returned status code should be 0 (FREE) for both gates. */ 
		gateInfoDatabase.departed(0);
		gateInfoDatabase.departed(1);
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (0 - FREE).		
		assertEquals(0, gateInfoDatabase.getStatuses()[0]);
		assertEquals(0, gateInfoDatabase.getStatuses()[1]);
		
		// Test second bad scenario:
		
		/* Without the allocation of gates first, the docked and departed methods should not change 
		anything. The returned status code should be 0 (FREE) for both gates. */ 
		gateInfoDatabase.docked(0);
		gateInfoDatabase.docked(1);		
		gateInfoDatabase.departed(0);
		gateInfoDatabase.departed(1);		
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (0 - FREE).			
		assertEquals(0, gateInfoDatabase.getStatuses()[0]);
		assertEquals(0, gateInfoDatabase.getStatuses()[1]);
		
		// Test third bad scenario:
		
		/* Without docking after allocation, the departed method should not change anything for either gates.
		The mCodes are dummy values here. The returned status code should be 1 (RESERVED) 
		for both gates after the allocation is done for them. */
		gateInfoDatabase.allocate(0, 123);
		gateInfoDatabase.allocate(1, 345);
		gateInfoDatabase.departed(0);
		gateInfoDatabase.departed(1);	
		assertEquals(1, gateInfoDatabase.getStatus(0));
		assertEquals(1, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (1 - RESERVED).			
		assertEquals(1, gateInfoDatabase.getStatuses()[0]);
		assertEquals(1, gateInfoDatabase.getStatuses()[1]);
	}
	
	@Test
	public void testDepartedGoodScenario() {	
		
		// This test method also tests the departed method of GateInfoDatabase.
		
		// Test what happens if the allocate, docked, and departed methods are executed in the correct order:
	
		// Allocate the gates to the planes. The mCodes are dummy values.
		gateInfoDatabase.allocate(0, 112);
		gateInfoDatabase.allocate(1, 332);
				
		/* Test if the allocations were successful. The returned status code should be 1 (RESERVED)
		for both gates. */
		assertEquals(1, gateInfoDatabase.getStatus(0));
		assertEquals(1, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (1 - RESERVED).				
		assertEquals(1, gateInfoDatabase.getStatuses()[0]);
		assertEquals(1, gateInfoDatabase.getStatuses()[1]);
		
		// Then, the planes are docked.
		gateInfoDatabase.docked(0);
		gateInfoDatabase.docked(1);
		
		/* Test if the aircrafts have successfully docked. 
		The returned status codes should be 2 (OCCUPIED) for both gates. */
		assertEquals(2, gateInfoDatabase.getStatus(0));
		assertEquals(2, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (2 - OCCUPIED).		
		assertEquals(2, gateInfoDatabase.getStatuses()[0]);
		assertEquals(2, gateInfoDatabase.getStatuses()[1]);
		
		// Finally, the planes depart. 		
		gateInfoDatabase.departed(0);
		gateInfoDatabase.departed(1);
		
		/* Test that the aircrafts have successfully departed.
		The returned status codes should be 0 (FREE) for both gates, since the planes have 
		already departed and the gates have been freed. */
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (0 - FREE).		
		assertEquals(0, gateInfoDatabase.getStatuses()[0]);
		assertEquals(0, gateInfoDatabase.getStatuses()[1]);
	}
}