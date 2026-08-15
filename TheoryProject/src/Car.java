//import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit; 
class Car extends Maintenance {
	// we can access Current time by timeNow
	LocalDate timeNowD = LocalDate.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	String timeNow = timeNowD.format(myFormatObj);
	// we can access Current time by timeNow
	
	
	Info info;

	Car(Info info) {
		this.info = info;
	}

   @Override
   public String TyreRotationHistory() {
   	int lastTyreRotated = info.getLastMileForRotation();
       String str = "Last Done at " + lastTyreRotated;
       return str;
   }
   @Override
   public String EngineOilHistory() {
   	int lastOilChanged = info.getLastMileForOil();
       String str = "Last Done at " + lastOilChanged;
       return str;
	    }
   @Override
   public String RegistationRenewalHistory() {
   	int lastRegistation = info.getLastDateForRegistation();
       String str = "Last Done at " + lastRegistation;
       return str;
   }
    @Override
   public String TyreRotationIsDue() {
    	int range = 1000;
    	if((info.odometer - info.getLastMileForRotation()) > range) {
    		return "Tyre rotation is Due";
    	}
    	else {
    		int gap = (info.getLastMileForRotation() + range) - info.odometer ;
    		return "Next change after " + gap;
    	}
   }
 
   @Override
   public String EngineOilIsDue() {
	   int range = 5000;
	   if((info.odometer - info.getLastMileForOil()) > range) {
		   return "Engine Oil Change is Due";
	   }
	   else {
		   int gap = (info.getLastMileForOil() + range) - info.odometer ;
		   return "Next change after " + gap;
	   }
   }
 
   @Override
   public String RegistationRenewalIsDue() {
	   int range = 365;
       LocalDate currentDate = LocalDate.now();
       LocalDate lastDate = LocalDate.of(2025, 8, 14);

       // Calculate total days between the dates
       long totalDays = ChronoUnit.DAYS.between(currentDate, lastDate);

       System.out.println("Total Days Difference: " + totalDays);
	   if(totalDays > range) {
		   return "Tyre rotation is Due";
	   }
	   else {
		   long gap = range - totalDays;
		   return "Next change after " + gap + " Days";
	   } 
   }
}
	// calculate the time or milage between last service and today.
	// find out if it is due or not.
	// if due tell the user it is due.
	// if due ask the user if it is done today.(by typing "done")
	// if it is not due tell the user it is not due.
	// if it is not due calculate the next date of service.
	// if it is not due show the users the next date of service.
	// what are the things we need?
	// 1)current time
	// 2)current mileage
	// 3)last mileage for Engine oil change
	// 4)last mileage for Tires rotation
	// 5)last date of Registration renewal
	// then what to out-source?
	//3)last mileage for Engine oil change -> lastMile
	//4)last mileage for Tires rotation -> lastMile
	//5)last date of Registration renewal -> lastDate
	// which of those we can source flexibly?
	//1)current time -> currentDate
	//2)current mileage -> currentMilage
	// currentMilage lastMile lastDate currentDate