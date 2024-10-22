import java.util.*; // For Observable

// Generated by Together


/**
 * A central database ("model" class):
 * It is intended that there will be only one instance of this class.
 * Maintains an array of Gates.
 * Each gate's number is its index in the array (0..)
 * GateConsoles and GroundOperationsControllers are controllers of this class: sending it messages when the gate status is to be changed.
 * GateConsoles and GroundOperationsControllers also register as observers of this class. Whenever a change occurs to any gate, the observers are notified.
 * @stereotype model
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 * @url element://model:project::SAAMS/design:node:::id1un8dcko4qme4cko4sw27.node61
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * CSCU9P6 Project Group 2
 * Student ID: 2421468
 * Date: 12/03/2018
 */
public class GateInfoDatabase extends Observable {
	
	/**
	* Holds one gate object per gate in the airport.
	* @clientCardinality 1
	* @directed true
	* @label contains
	* @link aggregationByValue
	* @supplierCardinality 0..*
	*/
	private Gate[] gates;

	/**
	*  A constant: the number of aircraft gates at the airport.
	*/
	public static final int MAX_GATE_NUMBER = 2;
  
	/**
	* Constructor which creates a new GateInfoDatabase object, initialises the gates array,
	* and adds two Gate objects to it.
	*/
	public GateInfoDatabase() {
		gates = new Gate[MAX_GATE_NUMBER];
		gates[0] = new Gate(0);
		gates[1] = new Gate(1);
	}

	/**
 	* Obtain and return the status of the given gate identified by the gateNumber parameter.
 	*/
	public int getStatus(int gateNumber){
		return gates[gateNumber].getStatus();
	}

	/**
 	* Returns an array containing the status of all gates.
 	* For data collection by the GOC.
 	*/
	public int[] getStatuses(){
	  
		// This array will store the status of all gates
		int[] statuses = new int[MAX_GATE_NUMBER];
	  
		// Get the status of each Gate object in the gates array, and copy these to the statuses array
		for(int i = 0; i < gates.length; i++) {
			statuses[i] = gates[i].getStatus();
		}	  	  
		return statuses;
  }

	/**
	* Forward a status change request to the given gate identified by the gateNumber parameter. Called to allocate a free gate to the aircraft identified by mCode.
	*/
	public void allocate(int gateNumber, int mCode){
		gates[gateNumber].allocate(mCode);
	}

	/**
 	* Forward a status change request to the given gate identified by the gateNumber parameter. Called to indicate that the expected aircraft has arrived at the gate.
 	*/
	public void docked(int gateNumber){
		gates[gateNumber].docked();
	}

	/**
	* Forward a status change request to the given gate identified by the gateNumber parameter. Called to indicate that the aircraft has departed and that the gate is now free.
	*/
	public void departed(int gateNumber){
		gates[gateNumber].departed();
  	}
}