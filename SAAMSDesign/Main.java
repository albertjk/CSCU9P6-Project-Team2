 
 
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
   
   
   
 
   
    GOC GC1 = new GOC(GDB, ADB, 200, 200);
    GC1.setLocation(1200,25);
   
    RadarTransceiver RT1 = new RadarTransceiver(ADB, 1000, 250);
    RT1.setLocation(45,50);
   
    CleaningSupervisor CS1 = new CleaningSupervisor(ADB);
    CS1.setLocation(50,225);
   
    MaintenanceInspector MI1 = new MaintenanceInspector(ADB);
    MI1.setTitle("Maintenance Inspector");
    MI1.setLocation(50,650);
   
    PublicInfo PI1 = new PublicInfo(ADB);
    PI1.setLocation(450,50);;
   
    RefuellingSupervisor RS1 = new RefuellingSupervisor(ADB);
    RS1.setLocation(350,650);
   
    GateConsole gateConsole1 = new GateConsole(GDB, ADB, 500, 200, 0);
    gateConsole1.setLocation(950,450);
    GateConsole gateConsole2 = new GateConsole(GDB, ADB, 500, 500, 1);
    gateConsole2.setLocation(950,750);
   
    LATC latc = new LATC(ADB);
    latc.setLocation(450,250);
   
    testInterface TEST = new testInterface(ADB);
    TEST.setLocation(675, 650);
   
  }
 
}