abstract class Maintenance {
	public void doo() {
		System.out.println("done");
	}
	public abstract String TyreRotationHistory();
	public abstract String EngineOilHistory();
	public abstract String RegistationRenewalHistory();
	
	public abstract String TyreRotationIsDue();
	public abstract String EngineOilIsDue();
	public abstract String RegistationRenewalIsDue();
	
	
	// this class will also provide  currentMilage lastMile lastDate currentDate  to car class through getter and setter.
}

