import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
 
public class Car extends Maintenance {
 
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
 
	private static final int OIL_RANGE_MILES = 5000;
	private static final int ROTATION_RANGE_MILES = 1000;
	private static final int REGISTRATION_RANGE_DAYS = 365;
 
	private final Info info;
 
	public Car(Info info) {
		this.info = info;
	}
 
	//  history 
 
	@Override
	public String TyreRotationHistory() {
		return "Last done at " + info.getLastMileForRotation() + " mi";
	}
 
	@Override
	public String EngineOilHistory() {
		return "Last done at " + info.getLastMileForOil() + " mi";
	}
 
	@Override
	public String RegistationRenewalHistory() {
		String date = info.getLastDateForRegistration();
		return (date == null || date.isEmpty()) ? "No record yet" : "Last done on " + date;
	}
 
	//  due checks 
 
	@Override
	public String TyreRotationIsDue() {
		int sinceLast = info.getOdometer() - info.getLastMileForRotation();
		if (sinceLast > ROTATION_RANGE_MILES) {
			return "Tyre rotation is due";
		}
		return "Next rotation in " + (ROTATION_RANGE_MILES - sinceLast) + " mi";
	}
 
	@Override
	public String EngineOilIsDue() {
		int sinceLast = info.getOdometer() - info.getLastMileForOil();
		if (sinceLast > OIL_RANGE_MILES) {
			return "Engine oil change is due";
		}
		return "Next change in " + (OIL_RANGE_MILES - sinceLast) + " mi";
	}
 
	@Override
	public String RegistationRenewalIsDue() {
		String lastDateStr = info.getLastDateForRegistration();
		if (lastDateStr == null || lastDateStr.isEmpty()) {
			return "No registration date on record";
		}
		try {
			LocalDate lastDate = LocalDate.parse(lastDateStr, DATE_FORMAT);
			LocalDate currentDate = LocalDate.now();
			long daysSinceLast = ChronoUnit.DAYS.between(lastDate, currentDate);
			if (daysSinceLast > REGISTRATION_RANGE_DAYS) {
				return "Registration renewal is due";
			}
			return "Next renewal in " + (REGISTRATION_RANGE_DAYS - daysSinceLast) + " days";
		} catch (DateTimeParseException e) {
			return "Invalid registration date on record";
		}
	}
}