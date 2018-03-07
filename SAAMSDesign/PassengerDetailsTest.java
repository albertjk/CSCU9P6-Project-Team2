import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a class containing methods for unit testing the methods of the PassengerDetails class
 * @author Jamie Porter 2213330
 * Date: 07/03/2018
 */

public class PassengerDetailsTest {
	
	
	PassengerDetails p1;
	PassengerDetails p2;
	
	//set up two passenger objects, assign them names
	@Before
	public void setUp() 
	{
		p1 = new PassengerDetails("James");
		p2 = new PassengerDetails("Brown");
	}
	
	
	//should return names of the two passenger objects
	@Test
	public void testName() {
		p1.getName();
		p2.getName();
	}
	
	
}

