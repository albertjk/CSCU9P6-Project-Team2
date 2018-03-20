import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class AircarftDBTest {

	AircraftManagementDatabase airDB;
	ManagementRecord[] MRs;
	Itinerary testIt;
	PassengerList testPass;
	FlightDescriptor testFD;
	String tailNumber;
	
	
	
	@Before
	public void setUp() {
			
		MRs = new ManagementRecord[10];
		testIt = new Itinerary("London", "Paris", "Tokyo");
		testPass = new PassengerList();
		airDB = new AircraftManagementDatabase();
		
		for(int i = 0; i < 10; i++) {
			
			Random name = new Random();
			int nameNum = name.nextInt(3);
			String nameTest = nameChoose(nameNum);
			PassengerDetails test = new PassengerDetails(nameTest);
			testPass.addPassenger(test);
			
		}
		
		testFD = new FlightDescriptor(tailNumber, testIt, testPass);
		airDB.radarDetect(testFD);
		
		
	}
	
	
	
	
	
	public String nameChoose(int name) { //for more names to be created
		
		switch(name) {
		
		case 0:
			return "Mark";
			
		case 1:
			return "Dave";
			
		case 2: 
			return "I'm trapped in the basement HELP";
		
		case 3:
			return "Gertrude";
		}
		
		return "Broken";
			
	}
}
