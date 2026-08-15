import java.io.*;
import java.util.*;
 

public class Info {
 
	private static final String FILE_PATH = "CarFile.txt";
 
	private int odometer;
	private int lastMileForOil;
	private int lastMileForRotation;
	private String lastDateForRegistration;
 
	public Info() {
		load();
	}
 

 
	private void load() {
		Properties props = new Properties();
		File file = new File(FILE_PATH);
		if (file.exists()) {
			try (FileReader reader = new FileReader(file)) {
				props.load(reader);
			} catch (IOException e) {
				System.out.println("Error reading " + FILE_PATH + ": " + e.getMessage());
			}
		}
		odometer = parseIntSafe(props.getProperty("odometer", "0"));
		lastMileForOil = parseIntSafe(props.getProperty("lastMileForOil", "0"));
		lastMileForRotation = parseIntSafe(props.getProperty("lastMileForRotation", "0"));
		lastDateForRegistration = props.getProperty("lastDateForRegistration", "");
	}
 
	private void save() {
		Properties props = new Properties();
		props.setProperty("odometer", String.valueOf(odometer));
		props.setProperty("lastMileForOil", String.valueOf(lastMileForOil));
		props.setProperty("lastMileForRotation", String.valueOf(lastMileForRotation));
		props.setProperty("lastDateForRegistration",
				lastDateForRegistration == null ? "" : lastDateForRegistration);
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			props.store(writer, null);
		} catch (IOException e) {
			System.out.println("Error writing " + FILE_PATH + ": " + e.getMessage());
		}
	}
 
	private int parseIntSafe(String value) {
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
 
 
	public int getOdometer() {
		return odometer;
	}
 

	
	public boolean setOdometer(int reading) {
		if (reading < lastMileForOil || reading < lastMileForRotation) {
			return false;
		}
		this.odometer = reading;
		save();
		return true;
	}
 
	//  engine oil 
 
	public int getLastMileForOil() {
		return lastMileForOil;
	}
 
	public void setLastMileForOil(int mile) {
		this.lastMileForOil = mile;
		save();
	}
 
	//  tire rotation 
 
	public int getLastMileForRotation() {
		return lastMileForRotation;
	}
 
	public void setLastMileForRotation(int mile) {
		this.lastMileForRotation = mile;
		save();
	}
 
	//  registration renewal 
 
	public String getLastDateForRegistration() {
		return lastDateForRegistration;
	}
 
	public void setLastDateForRegistration(String date) {
		this.lastDateForRegistration = date;
		save();
	}
}