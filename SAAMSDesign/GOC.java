import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*; // For Observer

import javax.swing.JButton;
import javax.swing.JFrame;

// Generated by Together


/**
 * An interface to SAAMS:
 * A Ground Operations Controller Screen:
 * Inputs events from GOC (a person), and displays aircraft and gate information.
 * This class is a controller for the GateInfoDatabase and the AircraftManagementDatabase: sending them messages to change the gate or aircraft status information.
 * This class also registers as an observer of the GateInfoDatabase and the AircraftManagementDatabase, and is notified whenever any change occurs in those <<model>> elements.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:node:::id2wdkkcko4qme4cko4svm2.node36
 * @url element://model:project::SAAMS/design:view:::id2wdkkcko4qme4cko4svm2
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1bl79cko4qme4cko4sw5j
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @author Albert Jozsa-Kiraly
 * Date: 18/03/2018
 */
public class GOC extends JFrame implements ActionListener, Observer { // This class is an Observer of GateInfoDatabase and AircraftManagementDatabase
	/** The Ground Operations Controller Screen interface has access to the GateInfoDatabase.
	* @clientCardinality 1
	* @supplierCardinality 1
	* @label accesses/observes
	* @directed*/
	private GateInfoDatabase gateInfoDatabase;
	/**
	* The Ground Operations Controller Screen interface has access to the AircraftManagementDatabase.
	* @clientCardinality 1
	* @supplierCardinality 1
	* @label accesses/observes
	* @directed*/
	private AircraftManagementDatabase aircraftManagementDatabase;
	
	private JButton grantGroundClearanceButton;
	private JButton allocateGateButton;
  
	/**
	 * Constructor
	 */
	public GOC(GateInfoDatabase gateInfoDatabase, AircraftManagementDatabase aircraftManagementDatabase) {
		
		this.gateInfoDatabase = gateInfoDatabase;
		this.aircraftManagementDatabase = aircraftManagementDatabase;
		
		
		// Subscribe to the GateInfoDatabase and AircraftManagementDatabase models
		gateInfoDatabase.addObserver(this);
		aircraftManagementDatabase.addObserver(this);
	}
	
	// TODO: BEFORE ALLOCATION: NEED TO CHECK THAT A GATE IS AVAILABLE
	
	/**
	 * Forward a status change request to the given gate identified by the gateNumber parameter. 
	 * Called to allocate a free gate to the aircraft identified by mCode.
	 */
	public void allocate(int gateNumber, int mCode) {
		gateInfoDatabase.allocate(gateNumber, mCode);
	}
	
	/**
	 * Allocated the given gate to the aircraft with the given mCode supplied as a parameter for unloading passengers. The message is forwarded to the given MR for status update.
	 */
	public void taxiTo(int mCode, int gateNumber) {
		aircraftManagementDatabase.taxiTo(mCode, gateNumber);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
  
	/**
	 * Notified by the model when it is altered. Fetch information about the aircrafts and gates.
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
  
  

}
