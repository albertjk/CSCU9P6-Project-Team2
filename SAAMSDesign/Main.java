

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
	AircraftManagementDatabase ADB = new AircraftManagementDatabase(); //create shared database
	GateInfoDatabase GDB = new GateInfoDatabase();
    
    // Instantiate and show all interfaces as Frames
    // Instantiate and show all interfaces as Frames
    MaintenanceInspector MI1 = new MaintenanceInspector(ADB);
    MI1.setTitle("Maintenance Inspector");
    MI1.setLocation(50,50);
    RadarTransceiver RT1 = new RadarTransceiver(ADB, 250, 250);
    PublicInfo PI1 = new PublicInfo(ADB);
    RefuellingSupervisor RS1 = new RefuellingSupervisor(ADB);
    GOC GC1 = new GOC(GDB, ADB, 200, 200);
    CleaningSupervisor CS1 = new CleaningSupervisor(ADB);
    
    GateConsole gateConsole = new GateConsole(GDB, ADB, 500, 200);
    LATC latc = new LATC(ADB);
  }

}