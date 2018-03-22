import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
 
public class testInterface extends JFrame
implements ActionListener, Observer{
       
    //variables
    private AircraftManagementDatabase DB;
    private JButton quit;
   
    private ArrayList<Integer> tracker = new ArrayList<Integer>();
 
    String[] flights;
    DefaultListModel<String> flightListModel = new DefaultListModel<>();
    int index;
     
    JScrollPane scrollPane = new JScrollPane();
    JList<String> flightList;
     
       
    //constructor
     public testInterface(AircraftManagementDatabase DB) {    
         //reference to database
         this.DB = DB;
         
         //subscribe to DB
         DB.addObserver(this);
         
         //UI stuff
         this.setLayout(new BorderLayout());
         setTitle("Test Interface");
         setLocation(40,40);
         setSize(650,325);  
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         Container window = getContentPane();
         window.setLayout(new FlowLayout());     // The default is that JFrame uses BorderLayout  
       
         //flightList stuff
         
         flightList = new JList<String>(flightListModel);
         scrollPane.setViewportView(flightList);
         flightList.setFixedCellWidth(550);
         flightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         flightList.setBorder(BorderFactory.createTitledBorder("FLIGHT CODE || STATUS || COMMENTS"));
         
         //populate FlightList
         getFlights();
         window.add(scrollPane);
         //window.add(flightList);
     
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
            if(DB.getStatus(i) == 0) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " FREE");  
            }
            else if(DB.getStatus(i) == 1) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " IN TRANSIT");  
            }
            else if(DB.getStatus(i) == 2) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " WAITING TO LAND (GRANT GROUND CLEARANCE)");  
            }
            else if(DB.getStatus(i) == 3) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " GROUND CLEARANCE GRANTED (GRANT LANDING PERMISSION)");  
            }
            else if(DB.getStatus(i) == 4) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " LANDING (CONFIRM LANDING)");  
            }
            else if(DB.getStatus(i) == 5) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " LANDED (SET TO TAXI)");  
            }
            else if(DB.getStatus(i) == 6) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " TAXIING (DOCK AIRCRAFT / SET OCCUPIED * UNLOADING)");  
            }
            else if(DB.getStatus(i) == 7) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " UNLOADING (SET TO CLEAN AND MAINT)");  
            }
            else if(DB.getStatus(i) == 8) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " READY CLEAN AND MAINT ");  
            }
            else if(DB.getStatus(i) == 9) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " FAULTY, AWAIT CLEAN (REPAIR)");  
            }
            else if(DB.getStatus(i) == 11) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " OK AWAIT CLEAN (CLEAN)");  
            }
            else if(DB.getStatus(i) == 12) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " AWAIT REPAIR (REPAIR)");  
            }
            else if(DB.getStatus(i) == 13) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " READY FOR REFUEL (REFUEL)");  
            }
            else if(DB.getStatus(i) == 14) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " READY FOR PASSENGERS (LOAD PASSENGERS / CLOSE FLIGHT)");  
            }
            else if(DB.getStatus(i) == 15) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " READY TO DEPART (ALLOCATE AIR SLOT)");  
            }
            else if(DB.getStatus(i) == 16) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " AWAITING TAXI (GRANT TAXI PERMISSION)");  
            }
            else if(DB.getStatus(i) == 17) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " AWAITING TAKEOFF (PERMIT TAKEOFF)");  
            }
            else if(DB.getStatus(i) == 18) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " DEPARTING THROUGH LOCAL AIRSPACE (LOSE CONTACT)");  
            }
            else if(DB.getStatus(i) == 18) {
                flightListModel.addElement(DB.getFlightCode(i) + " " + DB.getStatus(i) + " ");  
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
    }
           
 
    //this method is called when AircraftMangementDatabase updates it's observers.
    @Override
    public void update(Observable o, Object arg1) {
        // TODO Auto-generated method stub
        //populate flight list
        getFlights();
       
    }
}