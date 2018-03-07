import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class ItineraryTest {
	/**
	 * This class contains the methods for unit testing the itinary method
	 */
	Itinerary it1;
	Itinerary it2;
	
	@Before
	public void setUp()
	{
		it1 = new Itinerary("Glasgow", "Stirling", "London");
		it2 = new Itinerary("Stirling", "Callais", "Glasgow");
	}
	@Test
	public void testFrom()
	{
		it1.getFrom();
		it2.getFrom();
	}
	@Test
	public void testTo()
	{
		it1.getTo();
		it2.getTo();
	}
	@Test
	public void testNext()
	{
		it1.getNext();
		it2.getNext();
	}
}
