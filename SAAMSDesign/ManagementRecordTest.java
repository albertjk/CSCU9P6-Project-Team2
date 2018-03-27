import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

import org.junit.Before;
import org.junit.Test;

public class ManagementRecordTest {
	
	ManagementRecord[] MR;
	ManagementRecord mr1;
	ManagementRecord mr2;
	Itinerary it1;
	Itinerary it2;
	String flightCode1;
	String flightCode2;
	PassengerList PL1;
	PassengerList PL2;
	FlightDescriptor FD1;
	FlightDescriptor FD2;
	
	@Before
	public void setUp() {
		MR = new ManagementRecord[10];
		//	FROM / TO / NEXT
		it1 = new Itinerary("Glasgow", "Stirling", "London");
		it2 = new Itinerary("Paris", "Glasgow", "London");
		flightCode1 = "L33T";
		flightCode2 = "BH34";
		PL1 = new PassengerList();
		PL2 = new PassengerList();
		
		for(int i = 0; i < 5; i++) //Creates a bunch of entries in the first and second PL. They are each flipped so that you can tell which is which
		{
			PassengerDetails testS = new PassengerDetails("Sally");
			PassengerDetails testM = new PassengerDetails("Marty");
			PL1.addPassenger(testS);
			PL1.addPassenger(testM);
			PL2.addPassenger(testM);
			PL2.addPassenger(testS);
		}
		

		
		FD1 = new FlightDescriptor(flightCode1, it1, PL1);
		FD2 = new FlightDescriptor(flightCode2, it2, PL2);
		mr1 = new ManagementRecord();
		mr1.radarDetect(FD1);
		mr2 = new ManagementRecord();
		mr2.radarDetect(FD2);
		

	}

	  /*
	  0 = free / 1 = in transit / 2 = wanting to land / 3 = ground clearance granted /
	  4 = landing / 5 = landed / 6 = taxiing / 7 = unloading/ 8 = ready for clean and maintenance /
	  9 = faulty awaiting clean / 10 = clean and maintenance / 11 = ??? / 12 = awaiting repair /
	  13 = Ready, refuel / 14 = ready for passengers / 15 = ready to depart / 16 = awaiting taxi / 
	  17 = awaiting take off / 18 = departing through local airspace 
	  */
	  
	
	@Test
	public void testGetStatus() {

		//MR1 is going TO Stirling. Status should be 2 (WANTING TO LAND)
		
		int MR1status = mr1.getStatus();
		assertEquals(MR1status, 2);
		
		//MR2 is NOT going to Stirling. Status should be 1 (IN TRANSIT)
		
		int MR2status = mr2.getStatus();
		assertEquals(MR2status, 1);
	}
	
	
	@Test
	public void testSetStatus() {
		
		//Set status to 17
		int newStatus = 17;
		mr1.setStatus(newStatus);
		
		//check returned status is 17
		int status = mr1.getStatus();
		assertEquals(newStatus, status);
	}
	
	@Test
	public void testGetFlightCode() {
		//retrieve flight codes
		
		String FC1 = mr1.getFlightCode();
		String FC2 = mr2.getFlightCode();
		
		//check retrieved flight codes are correct.
		
		assertEquals(FC1, "L33T");
		assertEquals(FC2, "BH34");
	}
	
	@Test
	public void testRadarDetect() {
		 
		ManagementRecord mr3;
		mr3 = new ManagementRecord();
		
		ManagementRecord mr4;
		mr4 = new ManagementRecord();
				
		//SHOULD add Flight Code, Passenger List, Itinerary to the Management Record
		mr3.radarDetect(FD1);
		
		//Check that some Flight Code, Passenger List and Itinerary have been added to Management Record
		assertNotNull(mr3.getPassengerList().getPassengerList());
		assertNotNull(mr3.getFlightCode());
		assertNotNull(mr3.getItinerary());
		
		//This flight is going TO Stirling -- RadarDetect should change status to WANTING TO LAND (2)
		assertEquals(mr3.getStatus(), 2);
		
		mr4.radarDetect(FD2);
		
		//Check that some Flight Code, Passenger List and Itinerary have been added to Management Record
		assertNotNull(mr4.getPassengerList().getPassengerList());
		assertNotNull(mr4.getFlightCode());
		assertNotNull(mr4.getItinerary());
		
		//This flight is NOT going to Stirling -- RadarDetect should change status to IN TRANSIT (1)
		assertEquals(mr4.getStatus(), 1);
	}	

	@Test
	public void testRadarLostContact() {
		//Status must be either IN_TRANSIT(1) or DEPARTING_THROUGH_LOCAL_AIRSPACE(18), 
		//and becomes FREE (and the flight details are cleared).
		
		//MR2 should be IN TRANSIT (1)
		assertEquals(mr2.getStatus(), 1);
		
		//therefore, radarLostContact should change the status to FREE(0), and clear passenger list, itinerary, flight code	
		mr2.radarLostContact();
		assertEquals(mr2.getStatus(), 0);
				
		assertNull(mr2.getFlightCode());	
		
		//PassengerList + Itinerary should be NULL after radar lost contacts
		try {
			assertNull(mr2.getPassengerList().getPassengerList());
		} catch(NullPointerException e) {
			
		};
		
		try {
			assertNull(mr2.getItinerary());
		} catch(NullPointerException e) {	
		};	
		
		//status SHOULD be WANTING TO LAND (2)
		assertEquals(mr1.getStatus(), 2);
		
		//therefore, radarLostContact should output an error message -- and leave the status and flight details alone.
		mr1.radarLostContact();
		assertNotNull(mr1.getPassengerList().getPassengerList());
		assertNotNull(mr1.getFlightCode());
		assertNotNull(mr1.getItinerary());
		
		//status should still be WANTING TO LAND (2)
		assertEquals(mr1.getStatus(), 2);
	}	
	
	@Test
	public void testTaxiTo() {
		//  Status must be LANDED (5) -- Assign a gate number and set status to TAXIING (6)
		
		//mr1 status is not LANDED (5)
		//assertNotEquals(mr1.getStatus(), 5);
		//therefore, TaxiTo should output an error, and not assign a gate number 
		mr1.taxiTo(4);
		//gate number should be 0
		assertEquals(mr1.getGateNumber(), 0);
		
		//set mr2's status to LANDED(5)
		mr2.setStatus(5);
		assertEquals(mr2.getStatus(),5);
		
		//mr2's status is LANDED(5)
		//therefore, TaxiTo should set status to TAXIING (6), and assign the gate number (25).
		mr2.taxiTo(25);
		assertEquals(mr2.getStatus(), 6);
		assertEquals(mr2.getGateNumber(), 25);
	}
	
	@Test
	public void testFaultsFound() {
		mr1.setStatus(8); // set the status to READY_CLEAN_AND_MAINT
		assertEquals(mr1.getStatus(),8);
		mr1.faultsFound("Not Enough Beer");
		assertEquals(mr1.getStatus(),9); //the status code should now be 10
		mr2.setStatus(10); // set the status to CLEAN_AWAIT_MAINT
		assertEquals(mr2.getStatus(), 10);
		mr2.faultsFound("Too Much Beer");//The status should be set to AWAIT_REPAIR
		assertEquals(mr2.getStatus(), 12);
	}

	@Test
	public void testAddPassenger() {
		//These are two new Passengers being created to test the field
		 PassengerDetails test1 = new PassengerDetails("Martha");
		 PassengerDetails test2 = new PassengerDetails("Sammy");
		 //This sets the status to READY_PASSENGER
		 mr1.setStatus(14);
		 assertEquals(mr1.getStatus(), 14);
		 //These two should pass
		 mr1.addPassenger(test1);
		 mr1.addPassenger(test2);
		 //these two attempts will cause a error message which is what is expected
		 mr2.addPassenger(test1);
		 mr2.addPassenger(test2);
	}

	@Test
	public void testGetPassengerList() {
		assertEquals(mr2.getPassengerList(), PL2);
	}

	@Test
	public void testGetItinerary() {
		assertEquals(mr1.getItinerary().getFrom(), "Glasgow");
	}
}