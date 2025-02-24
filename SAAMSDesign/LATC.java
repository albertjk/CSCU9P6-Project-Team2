import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

// Generated by Together


/**
 * An interface to SAAMS:
 * Local Air Traffic Controller Screen:
 * Inputs events from LATC (a person), and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:view:::id2fh3ncko4qme4cko4swe5
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::idwwyucko4qme4cko4sgxi
 */
public class LATC extends JFrame implements Observer, ActionListener {
/**
  *  The Local Air Traffic Controller Screen interface has access to the AircraftManagementDatabase.
  * @supplierCardinality 1
  * @clientCardinality 1
  * @label accesses/observes
  * @directed*/
	
	//The buttons and lists for the UI
  private AircraftManagementDatabase airDB;
  private JButton landingPermission;  //allows a plane to land
  private JButton confirmLanding; //switches a plane from LANDING to LANDED
  private JButton allocateAirSlot;
  private JButton takeOff;
  private JButton lostContact;
  private JList<String> waitForLanding;
  private JList<String> inTransit;
  private JList<String> awaitTakeoff;
  private JScrollPane landingPane = new JScrollPane();
  private JScrollPane TransitPane = new JScrollPane();
  private JScrollPane TakeoffPane = new JScrollPane();
  
  
  //Variables
  DefaultListModel<String> incomingFlightModel = new DefaultListModel<>();
  DefaultListModel<String> inTransitFlightModel = new DefaultListModel<>();
  DefaultListModel<String> outGoingFlightModel = new DefaultListModel<>();
  private int indexL;
  private int indexT;
  private int indexTO;
  private ArrayList<Integer> trackerL = new ArrayList<Integer>();
  private ArrayList<Integer> trackerT = new ArrayList<Integer>();
  private ArrayList<Integer> trackerTO = new ArrayList<Integer>();
  
  
  
  public LATC(AircraftManagementDatabase DB) {
	  //Set the local instance of AirDb to the main one
	  airDB = DB;
	  airDB.addObserver(this); // adds it as a observer
	  //sets up the window
	  setTitle("LATC UI");
	  setSize(700,400);
	  setLocation(500,500);
	  setResizable(true);
	  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	  Container content = getContentPane();
	  content.setLayout(new FlowLayout());
	  //add the button for granding landing permission
	  landingPermission = new JButton("Grant Landing Permission");
	  content.add(landingPermission);
	  landingPermission.addActionListener(this);
	  //Adds the button to confirm landing
	  confirmLanding = new JButton("Confirm Landing");
	  content.add(confirmLanding);
	  confirmLanding.addActionListener(this);
	  //Adds the button to allocate air slot 
	  allocateAirSlot = new JButton("Allocate Air Slot");
	  content.add(allocateAirSlot);
	  allocateAirSlot.addActionListener(this);
	  //adds the button to set the plan to having took off
	  takeOff = new JButton("Take Off");
	  content.add(takeOff);
	  takeOff.addActionListener(this);
	  
	  lostContact = new JButton("Lost Contact");
	  content.add(lostContact);
	  lostContact.addActionListener(this);
	  
	  //Adding Panels 
	  //landing panel
	  waitForLanding = new JList<String>(incomingFlightModel);
	  landingPane.setViewportView(waitForLanding);
	  waitForLanding.setFixedCellWidth(250);
	  waitForLanding.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  waitForLanding.setBorder(BorderFactory.createTitledBorder("FLIGHT CODE || STATUS || IN THE AIR"));
	  
	  //inTransitPanel
	  inTransit = new JList<String>(inTransitFlightModel);
	  TransitPane.setViewportView(inTransit);
	  inTransit.setFixedCellWidth(250);
	  inTransit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  inTransit.setBorder(BorderFactory.createTitledBorder("FLIGHT CODE || STATUS || LANDING"));
	  
	  //Takeoff Panel
	  awaitTakeoff = new JList<String>(outGoingFlightModel);
	  TakeoffPane.setViewportView(awaitTakeoff);
	  awaitTakeoff.setFixedCellWidth(250);
	  awaitTakeoff.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  awaitTakeoff.setBorder(BorderFactory.createTitledBorder("FLIGHT CODE || STATUS || TAKING OFF"));
	  //Populates the flight list
	  getFlightInfo();
	  content.add(landingPane);
	  content.add(TransitPane);
	  content.add(TakeoffPane);
	  setVisible(true);
  }

  /**
   * Checks the current status of the AirDB and alocates the relavent entries to the correct view
   */
private void getFlightInfo() {
	//clear the lists
	incomingFlightModel.removeAllElements();
	inTransitFlightModel.removeAllElements();
	outGoingFlightModel.removeAllElements();
	//clear trackers and indexs
	trackerL.removeAll(trackerL);
	trackerT.removeAll(trackerT);
	trackerTO.removeAll(trackerTO);
	indexL = 0;
	indexT = 0;
	indexTO = 0;
	
	//scan through the list fills in the needed entries to the list.
	for(int i = 0; i < airDB.maxMRs;i++)
	{
		if(airDB.getStatus(i) == 3 || airDB.getStatus(i) == 1 || airDB.getStatus(i) == 18 || airDB.getStatus(i) == 2)
		{
			incomingFlightModel.addElement(airDB.getFlightCode(i) + "      " + airDB.getStatus(i));
			indexL = i; // set the index to where in the list the item has been added
			trackerL.add(indexL);//Add that item to the tracker array to be used later
		}
		else if(airDB.getStatus(i) == 3 || airDB.getStatus(i) == 4 || airDB.getStatus(i) == 5)
		{
			inTransitFlightModel.addElement(airDB.getFlightCode(i) + "      " + airDB.getStatus(i));
			indexT = i;
			trackerT.add(indexT);
		}
		else if(airDB.getStatus(i) == 17 || airDB.getStatus(i) == 15 || airDB.getStatus(i) == 16)
		{
			outGoingFlightModel.addElement(airDB.getFlightCode(i) +  "       " + airDB.getStatus(i));
			indexTO = i;
			trackerTO.add(indexTO);
		}
	}
	
}

@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource().equals(landingPermission) && waitForLanding.isSelectionEmpty() == false)
	{
		indexL = waitForLanding.getSelectedIndex();//get the index number of the item that was selected from the list.
		int trace = trackerL.get(indexL); //use that index number to get the actual mCode of the item selected. This way you are always sure to get the correct entry from the main DB no matter where it is in the list
		
		if(airDB.getStatus(trace) == 3) //GOC has given ground clearance
		{
			airDB.setStatus(trace, 4); //LATC gives approach clearance
		}
	}
	else if(e.getSource().equals(confirmLanding) && inTransit.isSelectionEmpty() == false) {
		
		indexT = inTransit.getSelectedIndex();
		int trace = trackerT.get(indexT);
		
		if(airDB.getStatus(trace) == 4) {
			
			airDB.setStatus(trace, 5);
		}
	}
	else if(e.getSource().equals(lostContact) && waitForLanding.isSelectionEmpty() == false) {
		
		indexL = waitForLanding.getSelectedIndex();
		int trace = trackerL.get(indexL);
		
		airDB.radarLostContact(trace);
	}
	else if(e.getSource().equals(lostContact) && awaitTakeoff.isSelectionEmpty() == false)
	{
		indexT = awaitTakeoff.getSelectedIndex();
		int trace = trackerTO.get(indexT);
		
		airDB.radarLostContact(trace);
	}
	else if(e.getSource().equals(takeOff) && awaitTakeoff.isSelectionEmpty() == false) {
		
		indexT = awaitTakeoff.getSelectedIndex();
		int trace = trackerTO.get(indexT);
		
		if(airDB.getStatus(trace) == 17) {
			
			airDB.setStatus(trace, 18);
		}
	}
	else if(e.getSource().equals(allocateAirSlot) && awaitTakeoff.isSelectionEmpty() == false)
	{
		indexT = awaitTakeoff.getSelectedIndex();
		int trace = trackerTO.get(indexT);
		
		if(airDB.getStatus(trace) == 15)
		{
			airDB.setStatus(trace, 16);
		}
	}
}

@Override
public void update(Observable arg0, Object arg1) {
	getFlightInfo();
}
}
