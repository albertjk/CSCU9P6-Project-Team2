 
 
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
    AircraftManagementDatabase adb = new AircraftManagementDatabase(); //create shared database
    GateInfoDatabase gdb = new GateInfoDatabase();
   
    // Instantiate and show all interfaces as Frames
    GOC goc = new GOC(gdb, adb, 1000, 25);
   
    RadarTransceiver rt1 = new RadarTransceiver(adb, 30, 50);
   
    CleaningSupervisor cs1 = new CleaningSupervisor(adb);
    cs1.setLocation(30,225);
   
    MaintenanceInspector mt1 = new MaintenanceInspector(adb);
    mt1.setTitle("Maintenance Inspector");
    mt1.setLocation(30,650);
   
    PublicInfo pi1 = new PublicInfo(adb);
    pi1.setLocation(400,50);
   
    RefuellingSupervisor rs1 = new RefuellingSupervisor(adb);
    rs1.setLocation(350,650);
   
    GateConsole gateConsole1 = new GateConsole(gdb, adb, 650, 300, 0);
    GateConsole gateConsole2 = new GateConsole(gdb, adb, 650, 600, 1);
   
    LATC latc = new LATC(adb);
    latc.setLocation(430,250);
   
    testInterface test = new testInterface(adb);
    test.setLocation(675, 650);
  } 
}