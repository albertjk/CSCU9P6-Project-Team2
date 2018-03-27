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
		assertEquals("Glasgow", it1.getFrom());
		assertEquals("Stirling",it2.getFrom());
	}
	@Test
	public void testTo()
	{
		assertEquals("Stirling", it1.getTo());
		assertEquals("Callais", it2.getTo());
	}
	@Test
	public void testNext()
	{
		assertEquals("London",it1.getNext());
		assertEquals("Glasgow", it2.getNext());
	}
}
