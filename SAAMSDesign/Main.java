 
 
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
    GOC goc = new GOC(gdb, adb, 200, 200);
    goc.setLocation(1200,25);
   
    RadarTransceiver rt1 = new RadarTransceiver(adb, 1000, 250);
    rt1.setLocation(45,50);
   
    CleaningSupervisor cs1 = new CleaningSupervisor(adb);
    cs1.setLocation(50,225);
   
    MaintenanceInspector mt1 = new MaintenanceInspector(adb);
    mt1.setTitle("Maintenance Inspector");
    mt1.setLocation(50,650);
   
    PublicInfo pi1 = new PublicInfo(adb);
    pi1.setLocation(450,50);;
   
    RefuellingSupervisor rs1 = new RefuellingSupervisor(adb);
    rs1.setLocation(350,650);
   
    GateConsole gateConsole1 = new GateConsole(gdb, adb, 500, 200, 0);
    gateConsole1.setLocation(950,450);
    GateConsole gateConsole2 = new GateConsole(gdb, adb, 500, 500, 1);
    gateConsole2.setLocation(950,750);
   
    LATC latc = new LATC(adb);
    latc.setLocation(450,250);
   
    testInterface test = new testInterface(adb);
    test.setLocation(675, 650);
  } 
}