import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Generated by Together


/**
 * An interface to SAAMS:
 * Gate Control Console:
 * Inputs events from gate staff, and displays aircraft and gate information.
 * This class is a controller for the GateInfoDatabase and the AircraftManagementDatabase: sends messages when aircraft dock, have finished disembarking, and are fully embarked and ready to depart.
 * This class also registers as an observer of the GateInfoDatabase and the
 * AircraftManagementDatabase, and is notified whenever any change occurs in those <<model>> elements.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id1un8dcko4qme4cko4sw27
 * @url element://model:project::SAAMS/design:view:::id1jkohcko4qme4cko4svww
 * @url element://model:project::SAAMS/design:node:::id1un8dcko4qme4cko4sw27.node61
 * @author Albert Jozsa-Kiraly
 * Date: 20/03/2018
 */
public class GateConsole extends JFrame implements ActionListener, Observer  {  // This class is an Observer of GateInfoDatabase and AircraftManagementDatabase
	/**
	*  The GateConsole interface has access to the GateInfoDatabase.
	* @supplierCardinality 1
	* @clientCardinality 0..*
	* @label accesses/observes
	* @directed*/
	GateInfoDatabase gateInfoDatabase;

	/**
	*  The GateConsole interface has access to the AircraftManagementDatabase.
	* @supplierCardinality 1
	* @clientCardinality 0..*
	* @directed
	* @label accesses/observes*/
	private AircraftManagementDatabase aircraftManagementDatabase;

	/**
	* This gate's gateNumber
	* - for identifying this gate's information in the GateInfoDatabase.
	*/
	private int gateNumber;
	
	/* 
	 * Passenger list storing the names of passengers on the next departing flight from the current gate.
	 */
	PassengerList passengerList;
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private JButton quitButton;
	
	// These are the GUI elements on the aircraftPanel
	private JPanel aircraftPanel;
	
	// Stores the flight code of the aircraft at the current gate. Can be updated.
	private Vector<String> currentFlight = new Vector();
	
	// Displays the current aircraft. Can be updated.
	private JList aircraftList = new JList(new DefaultListModel());
	
	// Buttons for changing the status of the aircraft allocated to the nearby gate
	private JButton aircraftUnloadingButton;
	private JButton aircraftReadyCleanAndMaintButton;
	private JButton closeFlightButton;
	
	private JButton showFlightDetailsButton;
	
	// The text area where the details of a selected flight are displayed
	// Rows: the number of items to display in each row about a flight
	// Column: enough space for data display
	private JTextArea flightDescriptionTextArea = new JTextArea(5,20);
	
	
	
	
	
	// The number of the selected flight to display details of. None initially. 
	private int showingDetailsOfFlight = -1;	
	
	private JPanel gatePanel;
	
	// This vector will store the current nearby gate. Can be updated.
	Vector<String> currentGate = new Vector();
	
	
	// Displays the current gate. Can be updated.
	private JList gateList = new JList(new DefaultListModel());
	
	
	
	// The number of the selected gate to display details of. None initially. 
	private int showingDetailsOfGate = -1;
	
	// Buttons for changing the status of the nearby gate

	//JButton gateReserved;
	private JButton gateOccupiedAircraftUnloadingButton;
	private JButton gateFreedButton;
	private JButton showGateStatusButton;
	
	// Displays information about the nearby gate.
	private JTextArea gateDescriptionTextArea = new JTextArea(5,20);
	



	
	// These are the GUI elements on the addPassengerPanel
	private JPanel addPassengerPanel;
	private JTextField nameTextField;
	private JButton addPassengerButton;
	
	
	/**
	 * Constructor.
	 */
	public GateConsole(GateInfoDatabase gateInfoDatabase, AircraftManagementDatabase aircraftManagementDatabase, int locationX, int locationY) {
		
		this.gateInfoDatabase = gateInfoDatabase;
		this.aircraftManagementDatabase = aircraftManagementDatabase;
		
		passengerList = new PassengerList();

		// Set up the GUI
		setTitle("Gate Console");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());		
		
		quitButton = new JButton("Quit");
		window.add(quitButton);
		quitButton.addActionListener(this);
		
		// Set up the aircraft and gate tab
		// Set up the aircraft JPanel
		aircraftPanel = new JPanel();
		aircraftPanel.setBackground(Color.cyan);
		aircraftPanel.setPreferredSize(new Dimension(500, 160));
		aircraftPanel.add(new JLabel("Manage the aircraft allocated to this gate:"));
		currentFlight.addElement("LA 342");
		aircraftList.setListData(currentFlight);
		aircraftList.setVisibleRowCount(1);
		aircraftPanel.add(aircraftList);		
		
		showFlightDetailsButton = new JButton("Show flight details");
		aircraftPanel.add(showFlightDetailsButton);
		showFlightDetailsButton.addActionListener(this);
		
		//aircraftUnloadingButton = new JButton("Set to Unloading");
		//aircraftPanel.add(aircraftUnloadingButton);
		//aircraftUnloadingButton.addActionListener(this);

		aircraftReadyCleanAndMaintButton = new JButton("Set to Clean and Maintain");
		aircraftPanel.add(aircraftReadyCleanAndMaintButton);
		aircraftReadyCleanAndMaintButton.addActionListener(this);
		
		closeFlightButton = new JButton("Close departing flight");
		aircraftPanel.add(closeFlightButton);
		closeFlightButton.addActionListener(this);
		
		// The user is not able to edit flight details by typing text in the text area
		flightDescriptionTextArea.setEditable(false);
		
		aircraftPanel.add(flightDescriptionTextArea);
		window.add(aircraftPanel);
		
		// Set up the gate JPanel
		gatePanel = new JPanel();
		gatePanel.setBackground(Color.yellow);
		gatePanel.setPreferredSize(new Dimension(400, 160));
		gatePanel.add(new JLabel("Manage the gate:"));
		currentGate.addElement("Gate 1"); // DUMMY VALUE FOR TESTING
		gateList.setListData(currentGate);
		gateList.setVisibleRowCount(1);
		gatePanel.add(gateList);
		
		showGateStatusButton = new JButton("Show gate status");
		gatePanel.add(showGateStatusButton);
		showGateStatusButton.addActionListener(this);
		
		gateOccupiedAircraftUnloadingButton = new JButton("Set gate occupied and aircraft unloading");
		gatePanel.add(gateOccupiedAircraftUnloadingButton);
		gateOccupiedAircraftUnloadingButton.addActionListener(this);
		
		gateFreedButton = new JButton("Free the gate");
		gatePanel.add(gateFreedButton);
		gateFreedButton.addActionListener(this);
		
		// The user is not able to edit gate status by typing text in the text area
		gateDescriptionTextArea.setEditable(false);
		
		gatePanel.add(gateDescriptionTextArea);
		window.add(gatePanel);
		
		// Update the flight status
		//updateFlight();
		
		// Put the aircraftPanel and gatePanel together in a JPanel and add them to a tab		
		JPanel aircraftAndGatePanel = new JPanel();
		aircraftAndGatePanel.add(aircraftPanel);
		aircraftAndGatePanel.add(gatePanel);
		
		tabbedPane.addTab("Manage aircraft and gate status", null, aircraftAndGatePanel, "Click here to see and change the status of the nearby aircraft and the nearby gate.");
		
		// Set up the passenger tab
		addPassengerPanel = new JPanel();
		addPassengerPanel.setBackground(Color.orange);
		addPassengerPanel.setPreferredSize(new Dimension(400, 160));
		addPassengerPanel.add(new JLabel("Enter passenger name:"));
		nameTextField = new JTextField(20);
		addPassengerPanel.add(nameTextField);
		addPassengerButton = new JButton("Add passenger to current flight");
		addPassengerPanel.add(addPassengerButton);
		addPassengerButton.addActionListener(this);
		
		tabbedPane.addTab("Add passenger to flight", null, addPassengerPanel, "Click here to add a passenger to the departing flight.");
		
		
		/* TODO: If the aircraft's status is READY_PASSENGERS (status code 14),
		then make the addPassengerPanel editable so the gate staff
		can enter passenger details.
		Check passenger details against the passenger list! */
		
		window.add(tabbedPane);
		
		
		// Display the frame
		// TODO: CHANGE NUMBERS TO CONSTANTS
		setSize(945, 300);
		setLocation(locationX, locationY);
		setVisible(true);
		
		
		// Subscribe to the GateInfoDatabase and AircraftManagementDatabase models
		gateInfoDatabase.addObserver(this);
		aircraftManagementDatabase.addObserver(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == quitButton) {
			System.exit(0);
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
		// Set the status of the aircraft at the gate to UNLOADING (status code 7)
		//else if(e.getSource() == aircraftUnloadingButton) {			
			//aircraftManagementDatabase.setStatus(getSelectedFlightMCode(), 7);
		//}
		// Set the status of the aircraft at the gate to READY_CLEAN_AND_MAINT (status code 8)
		else if(e.getSource() == aircraftReadyCleanAndMaintButton) {
			aircraftManagementDatabase.setStatus(getSelectedFlightMCode(), 8);
		}
		/* When boarding is complete, or the gate staff decide that no more passengers 
		are presenting themselves, the gate staff "close the flight".
		The passenger list is uploaded from the aircraft's MR to the 
		aircraft's on-board computer by the radar/transceiver system.
		The aircraft's status becomes READY_DEPART (status code 15)  */
		else if(e.getSource() == closeFlightButton) {
			aircraftManagementDatabase.setStatus(getSelectedFlightMCode(), 15);
			
			// TODO RadarTransceiver should upload the passenger list!!
			
			
		}
		// Show the details of the nearby gate
		else if(e.getSource() == showGateStatusButton) {
			showingDetailsOfGate = gateNumber;
			showGateStatus();
		}
		/* When the aircraft arrived to the gate:
		Set the nearby gate to OCCUPIED (status code 2).
		Set the aircraft's status to UNLOADING (status code 7). */
		else if(e.getSource() == gateOccupiedAircraftUnloadingButton) {
			gateInfoDatabase.docked(gateNumber);
			aircraftManagementDatabase.setStatus(getSelectedFlightMCode(), 7);
		}
		// If the aircraft has departed and the user determines it is time to free the gate, then this button is clicked and the gate is freed.
		else if(e.getSource() == gateFreedButton) {
			gateInfoDatabase.departed(gateNumber);
		}
		// Add a passenger to the passenger list. Only add a passenger if the user has entered a name
		else if(e.getSource() == addPassengerButton && !nameTextField.getText().isEmpty()) {
		
			String name = nameTextField.getText();
			PassengerDetails passenger = new PassengerDetails(name);
			passengerList.addPassenger(passenger);
		}
		
		updateFlight();  // Update the selectable flight
		showFlightDetails(); // Update the flight details display
		
		showGateStatus(); // Update the gate status display
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
	 *  Re-populate the displayed flight list from the AircraftManagementDatabase.
	 */
	private void updateFlight() {
		
		// First clear the list of previous elements, then update the list.
		currentFlight.removeAllElements();
		
		/* If an aircraft's status code is between 2 (WANTING_TO_LAND) and 17 (AWAITING_TAKEOFF) inclusive, 
		display its flight code on the GOC screen, and also the details on selecting the aircraft. 
		Aircrafts with statuses FREE, IN_TRANSIT, and DEPARTING_THROUGH_LOCAL_AIRSPACE are not displayed. */
		for(int i = 2; i <= 17; i++) {
			// Get the aircrafts with the current status code and store their mCodes.			
			int[] mCodes = aircraftManagementDatabase.getWithStatus(i);
			
			/* Use the mCodes of the aircrafts to identify them and get their flight codes. 
			Add the flight codes to the flight list. This list will be displayed on the GOC screen. */
			for(int j = 0; j < mCodes.length; j++) {
				currentFlight.add(aircraftManagementDatabase.getFlightCode(mCodes[j]));
			}
		}
		// Update the entire content of aircraftList
		aircraftList.setListData(currentFlight);
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
  	 * Notified by the model when it is altered.
  	 */
	public void update(Observable o, Object arg) {
		// Update the flight status
		updateFlight();
		
	}

	

}
