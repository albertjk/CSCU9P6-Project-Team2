import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a class containing methods for unit testing the methods of the GateInfoDatabase class.
 * Each method from the GateInfoDatabase class has a tester method in this class. The getStatus 
 * method is tested as part of the other test methods as we repeatedly check the status of the gates.
 * The getStatuses method is tested in the testDepartedGoodScenario and testDepartedBadScenario test 
 * methods as we repeatedly check the array elements for the status codes.
 * @author Albert Jozsa-Kiraly
 * Date: 12/03/2018
 */
public class GateInfoDatabaseTest {
		
	// The GateInfoDatabase object used in the tests
	GateInfoDatabase gateInfoDatabase;

	@Before
	public void setUp() {		
		
		/* Initialise the GateInfoDatabase object. 
		The object in its constructor initialises a new gates array
		and two Gate objects added to the array. */
		gateInfoDatabase = new GateInfoDatabase();		
	}	
	
	@Test
	public void testAllocate() {		
		
		/* Without allocation, the status is FREE for both gates (no aircraft has landed yet).
		The returned status code should be 0 (FREE) for both gates . */
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));

		// Allocate the gates to the planes. The mCodes are dummy codes.
		gateInfoDatabase.allocate(0, 112);
		gateInfoDatabase.allocate(1, 332);
		
		/* Test if the allocations were successful. The returned status codes should be 1 (RESERVED)
		for both gates. */
		assertEquals(1, gateInfoDatabase.getStatus(0));
		assertEquals(1, gateInfoDatabase.getStatus(1));
	}
	
	@Test
	public void testDocked() {			
		
		/* Test what happens if the docked method is called without calling the allocate method first
		(bad scenario): */
		
		/* Without allocation, the docked method should not change anything.
		The returned status code should be 0 (FREE) for both gates (no aircraft has landed yet). */
		gateInfoDatabase.docked(0);
		gateInfoDatabase.docked(1);
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test what happens if the methods are executed in the correct order:
		
		// Allocate the gates to the planes. The mCodes are dummy codes.
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
		
		// Test what happens in three different bad scenarios:
		
		/* Without the allocation and docking of the planes to the gates, the departed method should 
		not change anything. The returned status code should be 0 (FREE) for both gates. */ 
		gateInfoDatabase.departed(0);
		gateInfoDatabase.departed(1);
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (0 - FREE).		
		assertEquals(0, gateInfoDatabase.getStatuses()[0]);
		assertEquals(0, gateInfoDatabase.getStatuses()[1]);
		
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
		
		/* Without docking after allocation, the departed method should not change anything for either gates.
		The mCodes are dummy codes here. The returned status code should be 1 (RESERVED) 
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
		
		// Test what happens if the methods of GateInfoDatabase are executed in the correct order:		
		
		// Allocate the gates to the planes. The mCodes are dummy codes.
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
		already departed. */
		assertEquals(0, gateInfoDatabase.getStatus(0));
		assertEquals(0, gateInfoDatabase.getStatus(1));
		
		// Test if the getStatuses method returns an array storing the correct status codes (0 - FREE).		
		assertEquals(0, gateInfoDatabase.getStatuses()[0]);
		assertEquals(0, gateInfoDatabase.getStatuses()[1]);
	}
}