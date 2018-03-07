import java.util.ArrayList;

// Generated by Together


/**
 * Contains an array of PassengerDetails objects - one per passenger on a flight.
 * Incoming flights supply their passenger list in their flight descriptor, and the ManagementRecord for the flight extracts the PassengerList and holds it separately.
 * Outbound flights have PassengerLists built from passenger details supplied by the gate consoles, and the list is uploaded to the aircraft as it departs in a newly built FlightDescriptor.
 * @stereotype entity
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 */
public class PassengerList {
  /**
 * The array of PassengerDetails objects.
 * @byValue
 * @clientCardinality 1
 * @directed true
 * @label contains
 * @shapeType AggregationLink
 * @supplierCardinality 0..*
 */
  //private PassengerDetails[] details = new PassengerDetails[189]; //number of seats in a boeing 727
  
  private ArrayList<PassengerDetails> details = new ArrayList<PassengerDetails>(); 

/**
 * The given passenger is boarding.
 * Their details are recorded, in the passenger list.
 * @preconditions Status is READY_PASSENGERS
 */
  public void addPassenger(PassengerDetails details){
	  
	  this.details.add(details);
	  
  }
  
  public ArrayList<PassengerDetails> getPassengerList() {
	  
	  return this.details;
  }
}
