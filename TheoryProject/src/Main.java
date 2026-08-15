import javax.swing.*;
 
public class Main {
 
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			CarMaintenanceGUI gui = new CarMaintenanceGUI();
			gui.setVisible(true);
		});
	}
}