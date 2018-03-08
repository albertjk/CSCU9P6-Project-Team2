import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PassengerListTest {

	PassengerList passList1;
	PassengerList passList2;
	

	@Before
	public void setUp() {
		
		passList1 = new PassengerList();
		passList2 = new PassengerList();
		
		
	}
	
	@Test
	public void testAdd() {
		ArrayList testList = new ArrayList(); //to store the return function
		
		for(int i = 0; i < 10; i++) { //adds 10 passengers called mark to the ArrayList		
			
			PassengerDetails test = new PassengerDetails("Mark"); //Creates a new test object called mark
			passList1.addPassenger(test); //adds and new passenger to the array
			
			testList = passList1.getPassengerList(); //add the contents of the passList1 to a testable array
		}
		int count = testList.size(); //stores the size of the test array in number of elements
		
		
		System.out.println(passList1.getPassengerList() + " " + count); //print list to console
		
		assertEquals(10, count); //checks that 10 items have been added to the arraylist if true test passes
		
	}
	
	@Test
	public void testExtreme() {
		
		ArrayList testList = new ArrayList(); //to store the return function
		
		for(int i = 0; i < 100; i++) { //adds 10 passengers called mark to the ArrayList		
			
			PassengerDetails test = new PassengerDetails("Mark"); //Creates a new test object called mark
			passList1.addPassenger(test); //adds and new passenger to the array
			
			System.out.println(passList1.getDetails(i));
			
			testList = passList1.getPassengerList(); //add the contents of the passList1 to a testable array
		}
		int count = testList.size(); //stores the size of the test array in number of elements
		
		System.out.println(count); //print list to console
		
		assertEquals(100, count); //checks that 100 items have been added to the arraylist if true test passes
		
	}
}
