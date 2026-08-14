//import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResetCounter reset = new ResetCounter();
		do {
			reset.bool = true;
			
			
			Info info = new Info();
			info.getOdometer();
			
			
			
			// main will directly call car.TyreRotationHistory() for the string to display on screen.
			
			
			reset.bool = true;
		} while(reset.reset());
	}
}