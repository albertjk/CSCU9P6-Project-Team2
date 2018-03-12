import static org.junit.Assert.*;
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
	public void setUp()
	{
		MR = new ManagementRecord[10];
		it1 = new Itinerary("Glasgow", "Stirling", "London");
		it2 = new Itinerary("Paris", "Glasgow", "London");
		flightCode1 = "L33T";
		flightCode2 = "BH34";
		PL1 = new PassengerList();
		PL2 = new PassengerList();
		for(int i = 0; i < 30; i++) //Creates a bunch of entries in the first and second PL. They are each flipped so that you can tell which is which
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
		mr2 = new ManagementRecord();
		mr1.radarDetect(FD1);
		mr2.radarDetect(FD2);
		int counter = 1;
		for(int i = 0;i < MR.length;i++)//So this fills the array with instances of mr1 and 2. At this point they all have the status of FREE
		{
			if(counter == 2 || counter == 4 || counter == 6)
			{
			MR[i] = mr1;
			counter++;
			}
			else
			{
				MR[i] = mr2;
				counter++;
			}
		}
		//This then goes through and tries to overwrite entries. It should be blocked by all of them.
		for(int i = 0; i < MR.length;i++)
		{
			System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
			MR[i].radarDetect(FD1);
			System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
		}
	}
	//@Test
	//public void testRadarDetect()
	//{	
		//int counter = 1;
		//for(int i = 0;i < MR.length;i++)//So this fills the array with instances of mr1 and 2. At this point they all have the status of FREE
		//{
		//	if(counter == 2)
		//	{
		//	MR[i] = mr1;
		//	counter++;
		//	}
			//else
			//{
		//		MR[i] = mr2;
			//	counter++;
			//}
		//}
		//for(int i = 0; i < MR.length;i++)//This loop then steps through and changes the status of some of the entries.
		//{
		//		MR[i].setStatus(0);	
		//}
		//Now that half of the entries are set to something other than free we can test the rejection. We should expect 5 error messages to be printed out on screen. 
		//for(int i = 0; i < MR.length;i++)
		//{
		//	System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
		//	MR[i].radarDetect(FD1);
		//}
//	}
	
	@Test
	public void testRadarLostContact()
	{
		for(int i = 0; i < MR.length;i++) //This should set the records that 
		{
			MR[i].radarLostContact();
			System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
		}
	}
	
	@Test
	public void testTaxiTo()
	{
		int counter = 1;
		for(int i = 0; i < MR.length;i++) {
			System.out.println("Statis of MR " + i + " at the start of taxiTo Test is " + MR[i].getStatus());
		}
		for(int i = 0; i < MR.length;i++)
		{
			if(counter > 5)
			{
				MR[i].setStatus(5);
				counter++;
				System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
				
			}
			counter++;
		}
		for(int i = 0;i < MR.length;i++)
		{
			MR[i].taxiTo(i);
			System.out.println("Current status of MR " + i + " " + MR[i].getStatus());
		}
	}
}
