import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class flightDescriptorTest {
	
	static PassengerList pl1 = new PassengerList();
	static Itinerary it1 = new Itinerary("Glasgow", "Stirling", "London");

	
@Before
	public  void setUp() {

		for(int i = 0; i < 10; i++) { //adds 10 passengers called mark to the ArrayList			
			PassengerDetails test = new PassengerDetails("Mark"); //Creates a new test object called mark
				pl1.addPassenger(test); //adds and new passenger to the array
		}
	}

	FlightDescriptor fd1 = new FlightDescriptor("BE111", it1, pl1 );

@Test
public void testItinerary()
{
	fd1.getIT();	
}
@Test
public void testFlightNumber()
{
	fd1.getfCode();
}

@Test
public void testPassengerList()
{
	fd1.getPassList();
}
}