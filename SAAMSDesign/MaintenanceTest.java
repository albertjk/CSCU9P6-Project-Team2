import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MaintenanceTest {

	AircraftManagementDatabase airDB;
	int randNum = 0;
	String randName;
	String randFCode;
	Itinerary randIT;
	PassengerDetails randPD;
	PassengerList randPL;
	FlightDescriptor randFD;
	
	public void main(String args0){
		
		airDB = new AircraftManagementDatabase();
		
		MaintenanceInspector mi1 = new MaintenanceInspector(airDB);
		
		
		randPL = new PassengerList();
		for(int i = 0; i < 5;i++)
		{
			randNum = i;
			randName = "Mary" + randNum;
			randPD = new PassengerDetails(randName);
			randPL.addPassenger(randPD);
		}
		Random rand = new Random();
		randFCode = "F" + rand.nextInt(100) + "GZ";
		randIT = new Itinerary("Glasgow", "Stirling","London");
		randFD = new FlightDescriptor(randFCode, randIT, randPL);
		airDB.radarDetect(randFD);
		
		for(int i= 0; i < airDB.maxMRs; i++ ) {
			
			airDB.setStatus(i, 9);
			
		}
		
	}
	
	public void test() {
		
		

	}

}
