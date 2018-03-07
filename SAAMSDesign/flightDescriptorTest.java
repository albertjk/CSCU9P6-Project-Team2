import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class flightDescriptorTest {
@Before
	public static void setUp() {
		PassengerList pl1 = new PassengerList();
		
		Itinerary it1 = new Itinerary("Glasgow", "Stirling", "London");

	
		for(int i = 0; i < 10; i++) { //adds 10 passengers called mark to the ArrayList		
			
			PassengerDetails test = new PassengerDetails("Mark"); //Creates a new test object called mark
				pl1.addPassenger(test); //adds and new passenger to the array
		}
		FlightDescriptor fd1 = new FlightDescriptor("BE111", it1, pl1 );
		
	}

@Test
public void testItinerary()
{
	
}
@Test
public void testFlightNumber()
{
	
}

@Test
public void testPassengerList()
{
	
}
}
