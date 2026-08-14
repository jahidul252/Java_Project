import java.util.*;


public class Info {
	Scanner sc = new Scanner(System.in);
	int odometer;
	
	//Odometer reading
	public void getOdometer() { 
		System.out.print("Enter Odometer Reading: ");
		this.odometer = sc.nextInt();
	}
   //Oil Change-----
   public int getLastMileForOil() {
       return 0;
   }
   public int setLastMileForOil() {
	   return 0;
   }
   //Rotation------
   public int getLastMileForRotation() {
   	return 0;
   }
   public int setLastMileForRotation() {
	   return 0;
   }
   //Registation-----
   public int getLastDateForRegistation() {
       return 0;
   }
   public int setLastDateForRegistation() {
       return 0;
   }
  
}

