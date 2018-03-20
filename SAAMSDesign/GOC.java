import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*; // For Observer

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	
	private JButton showFlightDetailsButton;
	private JButton grantGroundClearanceButton;
	private JButton allocateGateButton;
	private JButton grantTaxiingPermissionButton;
	private JButton quitButton;
	private JButton showGateStatusButton;
	
	// The display panel for aircrafts and flight details
	private JPanel flightsDisplayPanel;
	
	// The display panel for gates and their statuses
	private JPanel gatesDisplayPanel;
	
	// The list displaying all aircrafts
	private JList aircraftList = new JList(new DefaultListModel());
	
	// The list displaying the two gates
	private JList gateList = new JList(new DefaultListModel());
	
	// The list is embedded in a scrolling window
	private JScrollPane aircraftScrollList = new JScrollPane(aircraftList);
	//private JScrollPane gateScrollList = new JScrollPane(gateList);
	
	// The text area where the details of a selected flight are displayed
	// Rows: the number of items to display in each row about a flight
	// Column: enough space for data display
	private JTextArea flightDescriptionTextArea = new JTextArea(5,20);
	private JTextArea gateDescriptionTextArea = new JTextArea(5,20);
	
	// The number of the selected flight to display details of. None initially. 
	private int showingDetailsOfFlight = -1;
	
	// The number of the selected gate to display details of. None initially. 
	private int showingDetailsOfGate = -1;
	
	// Stores the flight codes of the aircrafts in the airport (not those which are free or are in transit or have departed through local airspace).
	private Vector<String> flightList = new Vector();
  
	/**
	 * Constructor of the GOC user interface
	 */
	public GOC(GateInfoDatabase gateInfoDatabase, AircraftManagementDatabase aircraftManagementDatabase, int locationX, int locationY) {
		
		this.gateInfoDatabase = gateInfoDatabase;
		this.aircraftManagementDatabase = aircraftManagementDatabase;
		
		// Set up the GUI	
		setTitle("Ground Operations Controller");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());		
		
		grantGroundClearanceButton = new JButton("Grant ground clearance");
		window.add(grantGroundClearanceButton);
		grantGroundClearanceButton.addActionListener(this);
		
		allocateGateButton = new JButton("Allocate gate");
		window.add(allocateGateButton);
		allocateGateButton.addActionListener(this);
		
		grantTaxiingPermissionButton = new JButton("Grant taxiing permission");
		window.add(grantTaxiingPermissionButton);
		grantTaxiingPermissionButton.addActionListener(this);
		
		showFlightDetailsButton = new JButton("Show flight details");		
		showFlightDetailsButton.addActionListener(this);
		
		quitButton = new JButton("Quit");
		window.add(quitButton);
		quitButton.addActionListener(this);
		
		// The user is not able to edit flight and gate details by typing text in the text area
		flightDescriptionTextArea.setEditable(false);
		gateDescriptionTextArea.setEditable(false);
		
		// Set up the display panel
		flightsDisplayPanel = new JPanel();
		flightsDisplayPanel.setBackground(Color.cyan);
		flightsDisplayPanel.setPreferredSize(new Dimension(500, 105));
		flightsDisplayPanel.add(new JLabel("Aircraft:"));
		aircraftList.setVisibleRowCount(5);
		flightsDisplayPanel.add(aircraftScrollList);
		flightsDisplayPanel.add(flightDescriptionTextArea);
		flightsDisplayPanel.add(showFlightDetailsButton);
		
		window.add(flightsDisplayPanel);
		
		showGateStatusButton = new JButton("Show gate status");
		showGateStatusButton.addActionListener(this);
		
		// Set the gates in the gate list
		Vector<String> gates = new Vector();
		gates.add("Gate 1");
		gates.add("Gate 2");
		gateList.setListData(gates);
		
		gatesDisplayPanel = new JPanel();
		gatesDisplayPanel.setBackground(Color.green);
		gatesDisplayPanel.setPreferredSize(new Dimension(500, 90));
		gatesDisplayPanel.add(new JLabel("Gate:"));
		gateList.setVisibleRowCount(2); // There are only two gates at the airport
		gatesDisplayPanel.add(gateList);
		gatesDisplayPanel.add(gateDescriptionTextArea);	
		gatesDisplayPanel.add(showGateStatusButton);
		
		
		window.add(gatesDisplayPanel);
		
		// Set up the flight display
		updateFlightList();
		
		// Display the frame
		// TODO: CHANGE NUMBERS TO CONSTANTS
		setSize(560, 290);
		setLocation(locationX, locationY);
		setVisible(true);
		
		// Subscribe to the GateInfoDatabase and AircraftManagementDatabase models
		gateInfoDatabase.addObserver(this);
		aircraftManagementDatabase.addObserver(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		/* Grant ground clearance (permission to land) to the currently selected aircraft
		This permission is determined by the user observing the airport. */
		if(e.getSource() == grantGroundClearanceButton) {
			
			// Get the mCode of the aircraft. If its status is WANTING_TO_LAND (status code 2), the permission is given to it to land
			int mCode = getSelectedFlightMCode();
			if(aircraftManagementDatabase.getStatus(mCode) == 2) {
				givePermissionToLand(mCode);
			} else {
				JOptionPane.showMessageDialog(this, "The aircraft is not awaiting to land yet.");
			}
		}
		// Allocate a gate to the currently selected aircraft
		else if(e.getSource() == allocateGateButton) {
			
			// If there is a free gate, allocate that to the selected flight			
			int gateNumber = findFreeGates();			
			if(gateNumber != -1) {
				int mCode = getSelectedFlightMCode();
				allocateGate(gateNumber, mCode);
			} else {
				
				// If there are no free gates, show a message dialog.
				JOptionPane.showMessageDialog(this, "Unfortunately, there are no free gates.");
			}
			
		}
		// Show the details of the currently selected flight
		else if(e.getSource() == showFlightDetailsButton) {
			int mCode = getSelectedFlightMCode();
			
			// If -1: nothing is selected
			if(mCode != -1) {
				showingDetailsOfFlight = mCode;
			} else {
				JOptionPane.showMessageDialog(this, "No aircraft is selected.");
			}		
		} 
		/* Grant taxiing permission to the currently selected departing aircraft.
		This permission is determined by the user observing the airport.  */
		else if(e.getSource() == grantTaxiingPermissionButton) {
			// Check if the status of the aircraft is AWAITING_TAXI (status code 16).
			// If so, look out the window and grant taxiing permission.
			int mCode = getSelectedFlightMCode();
			if(aircraftManagementDatabase.getStatus(mCode) == 16) {
				taxiAcrossTarmac(mCode);
			} else {
				JOptionPane.showMessageDialog(this, "The aircraft is not awaiting taxi yet.");
			}
			
		}
		// Close the GOC window
		else if(e.getSource() == quitButton) {
			System.exit(0);
		}
		
		updateFlightList();  // Repopulate the flight list so it remains up-to-date
		showFlightDetails(); // Update the flight details display
		
		showGateStatus();  // Update the gate status display
	}
	
	/**
	 *  Re-populate the displayed flight list from the AircraftManagementDatabase.
	 */
	private void updateFlightList() {
		
		// First clear the list of previous elements, then update the list.
		flightList.removeAllElements();
		
		/* If an aircraft's status code is between 2 (WANTING_TO_LAND) and 17 (AWAITING_TAKEOFF) inclusive, 
		display its flight code on the GOC screen, and also the details on selecting the aircraft. 
		Aircrafts with statuses FREE, IN_TRANSIT, and DEPARTING_THROUGH_LOCAL_AIRSPACE are not displayed. */
		for(int i = 2; i <= 17; i++) {
			// Get the aircrafts with the current status code and store their mCodes.			
			int[] mCodes = aircraftManagementDatabase.getWithStatus(i);
			
			/* Use the mCodes of the aircrafts to identify them and get their flight codes. 
			Add the flight codes to the flight list. This list will be displayed on the GOC screen. */
			for(int j = 0; j < mCodes.length; j++) {
				flightList.add(aircraftManagementDatabase.getFlightCode(mCodes[j]));
			}
		}
		// Update the entire content of aircraftList
		aircraftList.setListData(flightList);
	}
	
	/**
	 * This method checks if an aircraft wants to land at Stirling Airport.
	 * If so, then the data about the flight is added to flightList.
	 * This list is displayed on the GOC screen.
	 * True is returned if the aircraft wants to land, false otherwise.
	 * 2 is the status code for WANTING_TO_LAND in ManagementRecord.
	 * @param mCode
	 */
	public boolean checkIfWantingToLand(int mCode) {
		if(aircraftManagementDatabase.getStatus(mCode) == 2) {
			flightList.add(aircraftManagementDatabase.getFlightCode(mCode));
			return true;
		}
		return false;
	}

	/**
	 * The user verifies from the information displayed on the GOC screen that enough space is available on the ground for a landing aircraft.
	 * If so, then the permission is given to it to land. The status is updated to GROUND_CLEARANCE_GRANTED (status code 3).
	 * @param mCode the mCode of the aircraft wishing to land
	 * @return
	 */
	public void givePermissionToLand(int mCode) {		
			aircraftManagementDatabase.setStatus(mCode, 3);							
	}
	
	/**
	 * Show the details of the selected flight in the flightsDisplayPanel.
	 */
	private void showFlightDetails() {
		
		if(showingDetailsOfFlight == -1) {
			flightDescriptionTextArea.setText("");
		}
		/* If a flight was selected, get the data associated with it. 
		We can use showingDetailsOfFlight to get the data as this variable is equal to the mCode of the selected flight.
		(It was assigned after the showFlightDetailsButton was clicked in actionPerformed.) */
		else {			
			flightDescriptionTextArea.setText("Flight code: " + aircraftManagementDatabase.getFlightCode(showingDetailsOfFlight) + "\n"
					+ "mCode: " + showingDetailsOfFlight + "\n"
					+ "Flight status: " + aircraftManagementDatabase.getStatus(showingDetailsOfFlight) + "\n"
					+ "From: " + aircraftManagementDatabase.getItinerary(showingDetailsOfFlight).getFrom() + "\n"
					+ "To: " + aircraftManagementDatabase.getItinerary(showingDetailsOfFlight).getTo());	
		}
	}
	
	/**
	 * Show the status of the selected gate in the gatesDisplayPanel.
	 */
	private void showGateStatus() {
		if(showingDetailsOfGate == -1) {
			gateDescriptionTextArea.setText("");
		}
		/* If a gate was selected, get its status.
		We can use showingDetailsOfGate to get the status as this variable is equal to the array index of the selected gate in the list.
		(It was assigned after the showGateStatusButton was clicked in actionPerformed.) */
		else {
			gateDescriptionTextArea.setText("Status: " + gateInfoDatabase.getStatus(showingDetailsOfGate));
		}
	}
	
	/**
	 * This method returns the mCode of the selected flight in the flight list.
	 * If no flight is selected, -1 is returned.
	 * @return mCode
	 */
	public int getSelectedFlightMCode() {		
		if(aircraftList.getSelectedValue() != null) {
			return (int) aircraftList.getSelectedValue();
		}
		return -1;
	}

	/**
	 * This method checks the statuses of all gates, and finds the first free gate. 
	 * If none are free, -1 is returned.
	 * @return the number of the first free gate
	 */
	public int findFreeGates() {
		int[] gateStatuses = gateInfoDatabase.getStatuses();
		
		// This variable will store the number of the free gate
		int freeGateNumber = -1;
		
		// Find the first free gate, and return the gate number
		for(int i = 0; i < gateStatuses.length; i++) {
			if(gateStatuses[i] == 0) {
				freeGateNumber = i;
			}
		}
		return freeGateNumber;
	}
	
	/**
	 * Forward a status change request to the given gate identified by the gateNumber parameter. 
	 * Called to allocate a free gate to the aircraft identified by mCode. The gate becomes RESERVED.
	 * The aircraft enters TAXIING status, and appears on the GateConsole display.
	 */
	public void allocateGate(int gateNumber, int mCode) {
		gateInfoDatabase.allocate(gateNumber, mCode);
		taxiToGate(mCode, gateNumber);
		
		// TODO: MAKE THE AIRCRAFT APPEAR ON THE GATECONSOLE DISPLAY AFTER A GATE HAS BEEN ALLOCATED FOR IT!
		
	}
	
	/**
	 * Allocated the given gate to the aircraft with the given mCode supplied as a parameter for unloading passengers. The message is forwarded to the given MR for status update.
	 */
	public void taxiToGate(int mCode, int gateNumber) {
		aircraftManagementDatabase.taxiTo(mCode, gateNumber);
	}
	
	/**
	 * If an aircraft's status is AWAITING_TAXI (status code 16) and there is space on the tarmac for taxiing (determined by the user), the GOC grants taxiing permission.
	 * Then, the aircraft's status is updated to AWAITING_TAKEOFF (status code 17).
	 * @param mCode
	 */
	public void taxiAcrossTarmac(int mCode) {		
		aircraftManagementDatabase.setStatus(mCode, 17);		 
	}
  
	/**
	 * This method gets called when AircraftMangementDatabase updates its observers. Fetches information about the aircrafts and gates.	 * 
	 */
	public void update(Observable o, Object arg) {
		updateFlightList();
	}
  
  

}
