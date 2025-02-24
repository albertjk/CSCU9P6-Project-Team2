import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

// Generated by Together


/**
 * An interface to SAAMS:
 * Cleaning Supervisor Screen:
 * Inputs events from the Cleaning Supervisor, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 */
public class CleaningSupervisor extends JFrame implements Observer, ActionListener{
/**
  * The Cleaning Supervisor Screen interface has access to the AircraftManagementDatabase.
  * @clientCardinality 1
  * @supplierCardinality 1
  * @label accesses/observes
  * @directed*/
	
  // Global variables for use in the class
  private AircraftManagementDatabase airDB;
  private JScrollPane viewDirty;
  private JList displayDirty;
  private JButton changeStat;
  private JButton quit;
  private ArrayList<Integer> tracker = new ArrayList();
  private int index;
  private DefaultListModel dirtyModel = new DefaultListModel();
  
  public CleaningSupervisor(AircraftManagementDatabase DB) { //constructor sets up the window and sets DB
	  
	  this.airDB = DB;
	  DB.addObserver(this);
	  
	  
	//Window Properties
	  setTitle("Cleaning Supervisor Interface");
	  setSize(400,410);
	  setResizable(false);
	  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	  Container content = getContentPane();
	  content.setLayout(new FlowLayout());
	  	  
	  //vanity label
	  JLabel desc = new JLabel("Cleaning Supervisor Interface");
	  content.add(desc);
	  
	  //quit button and listener
	  quit = new JButton("Exit");
	  quit.addActionListener(this);
	  content.add(quit);
	  
	  
	  //adding the list to the interface
	  displayDirty = new JList(dirtyModel);
	  viewDirty = new JScrollPane();
	  viewDirty.setViewportView(displayDirty);
	  viewDirty.setPreferredSize(new Dimension(350,300));
	  displayDirty.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	  displayDirty.setBackground(Color.WHITE);
	  displayDirty.setVisibleRowCount(10);
	  
	  //getting list items from airDB
	  checkList();
	   
	  content.add(viewDirty);
	  
	  //change button and listener
	  changeStat = new JButton("Set Cleaned");
	  changeStat.addActionListener(this);
	  content.add(changeStat);
	 
	  
	  setVisible(true);
	  
  }
  

  
@Override
public void update(Observable arg0, Object arg1) {
	checkList(); //updates the list with the most up to data cleaning requirements
	
}

/**
 * updates the list in the UI with planes that need cleaning
 */
@SuppressWarnings("unchecked")
public void checkList() {
	
	dirtyModel.removeAllElements(); //clears current content of the list
	tracker.removeAll(tracker); //reset the tracker
	index = 0; //reset the index
	
	for(int i = 0; i < airDB.maxMRs; i++) { //loops through the MR's and finds the matching status codes
		
		if(airDB.getStatus(i) == 8 || airDB.getStatus(i) == 11 || airDB.getStatus(i) == 9) { //only adds planes with a gate assigned, to the list
			
			//this is used to match the index in the list and the index in the management record array
			index = i; //sets index to current index in the array
			tracker.add(index); //adds current index to the tracker
			
			dirtyModel.addElement("Flight " + airDB.getFlightCode(i) + " at Gate " + airDB.getGateNum(i) + "  Awating Cleaning"); //adds the list item
		}
	}
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource().equals(quit)) {
		System.exit(0); //exits the program 12
		
	}
	else if(e.getSource().equals(changeStat) && displayDirty.isSelectionEmpty() == false) {
		
		index = displayDirty.getSelectedIndex(); //gets the value held in current index in JList
		int trace = tracker.get(index); //matches the indexes of JList and index of MR array
		
		if(airDB.getStatus(trace) == 11) { //checks current MR in trace to see if the status is 11
			
			airDB.setStatus(trace, 13); //changes the status of current MR to 13
		}
		else if(airDB.getStatus(trace) == 9) {
			
			airDB.setStatus(trace, 13);
		}
		else if(airDB.getStatus(trace) == 8) {
			
			airDB.setStatus(trace, 10);
		}
		
	}
	else if(e.getSource().equals(changeStat) && displayDirty.isSelectionEmpty() == true) { //prompts for item selection
		
		
		JOptionPane.showMessageDialog(this, "Please select an item from the list");
		
		checkList();//refreshes the list
	}
}

}