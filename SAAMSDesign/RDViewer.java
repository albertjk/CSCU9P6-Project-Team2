import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RDViewer extends JPanel implements Observer {
	  private AircraftManagementDatabase airCraftDB;
	  private RadarTransceiver RDT;
	  private JTextField display;
	  
	  //This is the constructor
	public RDViewer(RadarTransceiver rd, AircraftManagementDatabase airCraftDB)
	{
		//Sets refences to the controller and the AirCraftDB
		this.RDT = rd;
		this.airCraftDB = airCraftDB;
		
		//Add a bit of UI
		setBackground(Color.white);
		add(new JLabel("Last Created Entry"));
		display = new JTextField(" ", 15);
		add(display);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		
	}
}