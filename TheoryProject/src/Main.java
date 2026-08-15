//import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResetCounter reset = new ResetCounter();
		do {
			reset.bool = true;
			Info info = new Info();
			Maintenance car = new Car(info);
			
			//body
			//info
			info.getOdometer();
			
			//Engine Oil
			System.out.println(car.EngineOilHistory());
			System.out.println(car.EngineOilIsDue());
			
			//Tyre Rotation
			System.out.println(car.TyreRotationHistory());
			System.out.println(car.TyreRotationIsDue());
			
			//Registration Renewal
			System.out.println(car.RegistationRenewalHistory());
			System.out.println(car.RegistationRenewalIsDue());
			 
			
			// main will directly call car.TyreRotationHistory() for the string to display on screen.
			
			
			reset.bool = true;
		} while(reset.reset());
	}
}