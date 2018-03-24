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
import javax.swing.ListSelectionModel;

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
	private AircraftManagementDatabase aircraftDB;
	
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
	
	// Stores the flight codes of the aircrafts in the airport (not those which are free or are in transit or have departed through local airspace).
	private DefaultListModel<String> flightList = new DefaultListModel();
	
	// The list displaying all aircrafts
	private JList<String> aircraftList;
	// The list is embedded in a scrolling window
	private JScrollPane aircraftScrollList = new JScrollPane();
	
	
	private DefaultListModel<Gate> gatesList = new DefaultListModel();
	
	// The list displaying the two gates
	private JList<Gate> gateList;
	
	private JScrollPane gateScrollList = new JScrollPane();
	
	
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
	
	
  
	// Stores the position where a list item is in the MR array
	private ArrayList<Integer> tracker = new ArrayList();
	
	private int trackerIndex;
	
	/**
	 * Constructor of the GOC user interface
	 */
	public GOC(GateInfoDatabase gateInfoDatabase, AircraftManagementDatabase aircraftDB, int locationX, int locationY) {
		
		this.gateInfoDatabase = gateInfoDatabase;
		this.aircraftDB = aircraftDB;
		
		// Set up the GUI	
		setTitle("Ground Operations Controller");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(new FlowLayout());		
		
		aircraftList = new JList<String>(flightList);
		aircraftScrollList.setViewportView(aircraftList);
		aircraftScrollList.setPreferredSize(new Dimension(200, 100));
		aircraftList.setFixedCellWidth(70);
		aircraftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//window.add(aircraftScrollList);
		
		
		gateList = new JList<Gate>(gatesList);
		gateScrollList.setViewportView(gateList);
		gateScrollList.setPreferredSize(new Dimension(200, 100));
		gateList.setFixedCellWidth(100);
		gateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//window.add(gateScrollList);
		
		
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
		flightsDisplayPanel.setPreferredSize(new Dimension(500, 150));
		flightsDisplayPanel.add(new JLabel("Aircraft:"));
		aircraftList.setVisibleRowCount(5);
		flightsDisplayPanel.add(aircraftScrollList);
		flightsDisplayPanel.add(flightDescriptionTextArea);
		flightsDisplayPanel.add(showFlightDetailsButton);
		
		window.add(flightsDisplayPanel);
		
		showGateStatusButton = new JButton("Show gate status");
		showGateStatusButton.addActionListener(this);
		
		// Set the gates in the gate list
		
		Gate[] gates = new Gate[2];
		Gate gate1 = new Gate(0);
		Gate gate2 = new Gate(1);
		
		gates[0] = gate1;
		gates[1] = gate2;
		gateList.setListData(gates);
		
		
		
		gatesDisplayPanel = new JPanel();
		gatesDisplayPanel.setBackground(Color.green);
		gatesDisplayPanel.setPreferredSize(new Dimension(400, 130));
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
		setSize(580, 380);
		setLocation(locationX, locationY);
		setVisible(true);
		
		// Subscribe to the GateInfoDatabase and AircraftManagementDatabase models
		gateInfoDatabase.addObserver(this);
		aircraftDB.addObserver(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == grantGroundClearanceButton && !aircraftList.isSelectionEmpty()) {
		
			trackerIndex = aircraftList.getSelectedIndex();
			int trace = tracker.get(trackerIndex);
			
			if(aircraftDB.getStatus(trace) == 2) {
				aircraftDB.setStatus(trace, 3);
			}
		}
		else if(e.getSource() == allocateGateButton && !aircraftList.isSelectionEmpty()) {
		
			trackerIndex = aircraftList.getSelectedIndex();
			int trace = tracker.get(trackerIndex);
			
			// If there is a free gate, allocate that to the selected flight			
			int gateNumber = findFreeGates();			
			if(gateNumber != -1) {				
				allocateGate(gateNumber, trace);
			} else {
					
				// If there are no free gates, show a message dialog.
				JOptionPane.showMessageDialog(this, "Unfortunately, there are no free gates.");
			}	
		}
		else if(e.getSource() == grantTaxiingPermissionButton && !aircraftList.isSelectionEmpty()) {
		
			trackerIndex = aircraftList.getSelectedIndex();
			int trace = tracker.get(trackerIndex);
			
			if(aircraftDB.getStatus(trace) == 16) {
				aircraftDB.setStatus(trace, 17);	
			} else {
				JOptionPane.showMessageDialog(this, "The aircraft is not awaiting taxi yet.");
			}

		}
		else if(e.getSource() == quitButton) {
					System.exit(0);
		}
		else if(e.getSource() == showFlightDetailsButton && !aircraftList.isSelectionEmpty()) {
		
			trackerIndex = aircraftList.getSelectedIndex();
			int trace = tracker.get(trackerIndex);
		
		
			flightDescriptionTextArea.setText("Flight code: " + aircraftDB.getFlightCode(trace) + "\n"
			+ "mCode: " + trace + "\n"  // trace is the mCode
			+ "Flight status: " + aircraftDB.getStatus(trace) + "\n"
			+ "From: " + aircraftDB.getItinerary(trace).getFrom() + "\n"
			+ "To: " + aircraftDB.getItinerary(trace).getTo());
		}
		else if(e.getSource() == showGateStatusButton && !gateList.isSelectionEmpty()) {
		
			trackerIndex = gateList.getSelectedIndex();
			int trace = tracker.get(trackerIndex);			
			
		
			gateDescriptionTextArea.setText("Status: " + gateInfoDatabase.getStatus(trace) + "");
			
			
		}
	}
	
	/**
	 *  Re-populate the displayed flight list from the AircraftManagementDatabase.
	 */
	private void updateFlightList() {
		
		// First clear the list of previous elements, then update the list.
		flightList.removeAllElements();
		
		tracker.clear(); // Reset the tracker array list		
		trackerIndex = 0; // Reset the tracker index
		
		for(int i = 0; i < aircraftDB.maxMRs; i++) {
			
			if(aircraftDB.getStatus(i) >= 2 || aircraftDB.getStatus(i) <= 17) {
				
				trackerIndex = i; // Set the index to the current index in the MR array
				// Adds the current index to the tracker
				tracker.add(trackerIndex);
				
				// Add the list item
				flightList.addElement(aircraftDB.getFlightCode(i));				
			}			
		}
	}
	


	/**
	 * The user verifies from the information displayed on the GOC screen that enough space is available on the ground for a landing aircraft.
	 * If so, then the permission is given to it to land. The status is updated to GROUND_CLEARANCE_GRANTED (status code 3).
	 * @param mCode the mCode of the aircraft wishing to land
	 * @return
	 */
	public void givePermissionToLand(int mCode) {		
			aircraftDB.setStatus(mCode, 3);							
	}
	
	/**
	 * This method returns the mCode of the selected flight in the flight list.
	 * If no flight is selected, -1 is returned.
	 * @return mCode
	 */
	public int getSelectedFlightMCode() {		
		
		//System.out.println("getSelectedFlightMCode: " + aircraftList.getSelectedIndex());
		
		
		
		trackerIndex = aircraftList.getSelectedIndex();
		System.out.println("INDEX: " + trackerIndex);
		return trackerIndex;
		
		//int trace = 
		//}
		//return -1;
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
		aircraftDB.taxiTo(mCode, gateNumber);
		
		// TODO: MAKE THE AIRCRAFT APPEAR ON THE GATECONSOLE DISPLAY AFTER A GATE HAS BEEN ALLOCATED FOR IT!
		
	}

  
	/**
	 * This method gets called when AircraftMangementDatabase updates its observers. Fetches information about the aircrafts and gates.	 * 
	 */
	public void update(Observable o, Object arg) {
		updateFlightList(); 
	}
  
  

}
