

/**
 * The Main class.
 *
 * The principal component is the usual main method required by Java application to launch the application.
 *
 * Instantiates the databases.
 * Instantiates and shows all the system interfaces as Frames.
 * @stereotype control
 */
public class Main {


/**
 * Launch SAAMS.
 */

public static void main(String[] args) {
	
	// Instantiate databases
	AircraftManagementDatabase DB = new AircraftManagementDatabase(); //create shared database
    
    // Instantiate and show all interfaces as Frames
    // Instantiate and show all interfaces as Frames
    MaintenanceInspector MI1 = new MaintenanceInspector(DB);
    MI1.setTitle("Maintenance Inspector");
    MI1.setLocation(50,50);
    RadarTransceiver RT1 = new RadarTransceiver(DB, 250, 250);
  }

}