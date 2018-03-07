import java.util.ArrayList;

public class flightDescriptorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		ArrayList PassangerList = new ArrayList();
		PassengerList pl1 = new PassengerList();
		
		Itinerary it1 = new Itinerary("Glasgow", "Stirling", "London");
		Itinerary it2 = new Itinerary("Stirling", "Callais", "Glasgow");
		
		ArrayList testList = new ArrayList(); //to store the return function
		
		FlightDescriptor fd1 = new FlightDescriptor("BE111", it1, pl1 );

	
		for(int i = 0; i < 10; i++) { //adds 10 passengers called mark to the ArrayList		
			
			PassengerDetails test = new PassengerDetails("Mark"); //Creates a new test object called mark
				pl1.addPassenger(test); //adds and new passenger to the array
			
				testList = pl1.getPassengerList(); //add the contents of the passList1 to a testable array
		}
		
	}

}
