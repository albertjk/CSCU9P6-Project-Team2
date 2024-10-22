import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.*;  // For Observer
 
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
 
// Generated by Together
 
 
/**
 * An interface to SAAMS:
 * Maintenance Inspector Screen:
 * Inputs events from the Maintenance Inspector, and displays aircraft information.
 * This class is a controller for the AircraftManagementDatabase: sending it messages to change the aircraft status information.
 * This class also registers as an observer of the AircraftManagementDatabase, and is notified whenever any change occurs in that <<model>> element.
 * See written documentation.
 * @stereotype boundary/view/controller
 * @url element://model:project::SAAMS/design:node:::id4tg7xcko4qme4cko4swuu.node146
 * @url element://model:project::SAAMS/design:view:::id15rnfcko4qme4cko4swib
 * @url element://model:project::SAAMS/design:view:::id4tg7xcko4qme4cko4swuu
 * @url element://model:project::SAAMS/design:node:::id15rnfcko4qme4cko4swib.node107
 * @url element://model:project::SAAMS/design:view:::id3y5z3cko4qme4cko4sw81
 */
public class MaintenanceInspector extends JFrame
                                  implements ActionListener, Observer {
   
/**  The Maintenance Inspector Screen interface has access to the AircraftManagementDatabase.
  * @clientCardinality 1
  * @supplierCardinality 1
  * @label accesses/observes
  * @directed*/
   
   
//variables
private AircraftManagementDatabase DB;
private JButton quit;
private JButton maintButton;
private JButton repairButton;
private ArrayList<Integer> tracker = new ArrayList<Integer>();

String[] flights;
DefaultListModel<String> flightListModel = new DefaultListModel<>();
int index;
 
JScrollPane scrollPane = new JScrollPane();
JList<String> flightList;
 
   
//constructor
 public MaintenanceInspector(AircraftManagementDatabase DB) {    
     //reference to database
     this.DB = DB;
     
     //subscribe to DB
     DB.addObserver(this);
     
     //UI stuff
     this.setLayout(new BorderLayout());
     setTitle("MaintenanceInspector");
     setLocation(40,40);
     setSize(300,325);  
     setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
     Container window = getContentPane();
     window.setLayout(new FlowLayout());     // The default is that JFrame uses BorderLayout  
   
     //flightList stuff
     
     flightList = new JList<String>(flightListModel);
     scrollPane.setViewportView(flightList);
     flightList.setFixedCellWidth(250);
     flightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     flightList.setBorder(BorderFactory.createTitledBorder("FLIGHT CODE || STATUS || COMMENTS"));
     
     //populate FlightList
     getFlights();
     window.add(scrollPane);
     //window.add(flightList);
 
     //maintain button
     maintButton = new JButton("MAINTAIN");
     window.add(maintButton);
     maintButton.addActionListener(this);
   
     //repair button
     repairButton = new JButton("REPAIR");
     window.add(repairButton);
     repairButton.addActionListener(this);
     
     //quit button
     quit = new JButton("QUIT");
     window.add(quit, BorderLayout.SOUTH);
     quit.addActionListener(this);
     
     //display the frame
     setVisible(true);
 
 }
 
//This method populates the JList -- called from update method too.
public void getFlights() {
   
    //clear list first
    flightListModel.removeAllElements();
    tracker.removeAll(tracker);
    index = 0;
   
    //re-populate -- with comments for certain status codes
    for(int i = 0; i < DB.maxMRs; i++) {
        if(DB.getStatus(i) == 8 || DB.getStatus(i) == 10) 
        {
            flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " REQUIRES MAINTENANCE");  
            index = i;
            tracker.add(index);
        }
        else if(DB.getStatus(i) == 11 || DB.getStatus(i) == 13) 
        {
            flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " REPORTED OK"); 
            index = i;
            tracker.add(index);
        }
        else if(DB.getStatus(i) == 12 || DB.getStatus(i) == 9) 
        {
            String faultString = DB.getFaultDescription(i);
            flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " REQUIRES REPAIR "  +'"'+faultString+'"');
            index = i;
            tracker.add(index);
        }
    }
}
 
//ACTIONS
@Override
public void actionPerformed(ActionEvent e) {
   
    //QUIT
    if (e.getSource() == quit) {    
        System.exit(0);
    }
       
    //maintenance button
    else if(e.getSource() == maintButton) {
            //if something is selected
            if(flightList.isSelectionEmpty() != true) {
                //get selected item's position in list -- essentially the flight's position in MR array
            	
                index = flightList.getSelectedIndex();
                int trace = tracker.get(index);
                String[] options = {"REPORT OK","REPORT FAULTY"};
                JPanel panel = new JPanel();
                //pop up a window with 2 options -- report flight OK -- report flight faulty
                int selectedOption = JOptionPane.showOptionDialog(null, panel, "MAINTENANCE", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options , options[0]);
               
                //if user reports OK
                if(selectedOption == 0) {
                    //if status is 8 (READY CLEAN AND MAINT) or 10 (CLEAN AWAIT MAINT) -- set status to 11 (OK AWAIT CLEAN)
                    if(DB.getStatus(trace) == 8){
                        DB.setStatus(trace, 11);
                    }
                   
                    //if status is 10(CLEAN AWAIT MAIN) -- set status to 13 (READY FOR REFUEL)
                    if(DB.getStatus(trace) == 10){
                        DB.setStatus(trace, 13);
                    }
                }
               
                //if user reports faulty
                else if(selectedOption ==1) {
                    if(DB.getStatus(trace) == 8 || DB.getStatus(trace) == 10){                 
                        //pop up window with text box for inputting fault description
                        String fault = JOptionPane.showInputDialog("FAULT DESCRIPTION");
                        if(fault != null) {
                            //call faultsFound method -- add the fault description that the user entered in pop up window
                            DB.faultsFound(trace, fault);
                        }
                        else {
                            //user must enter a fault description
                            JOptionPane.showMessageDialog(null, "Please enter a fault description");
                        }
                    }
                }
            }  
        }
       
        //repair button
        else if(e.getSource() == repairButton) {
            //if something is selected
            if(flightList.isSelectionEmpty() != true) {
                //get selected item's position in list -- essentially the flight's position in MR array
               
            	index = flightList.getSelectedIndex();
                int trace = tracker.get(index);
                //if status is 9(FAULTY AWAIT CLEAN) or 12 (AWAIT REPAIR) -- set status to 11 (OK_AWAIT_CLEAN)
                if(DB.getStatus(trace) == 9 || DB.getStatus(trace) == 12){
                    DB.setStatus(trace, 11);
                }
       
            }
        }
                       
}
       
       
 
 
//this method is called when AircraftMangementDatabase updates it's observers.
@Override
public void update(Observable o, Object arg1) {
    // TODO Auto-generated method stub
    //populate flight list
    getFlights();
   
}
}