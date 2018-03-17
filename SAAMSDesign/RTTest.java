import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class RTTest {
	
	
	public static void main(String args[])
	{
		AircraftManagementDatabase airDB = new AircraftManagementDatabase();
		RadarTransceiver RTTest = new RadarTransceiver(airDB, 250, 250);
	}
	
	public void addInboundTest()
	{
		
	}
}