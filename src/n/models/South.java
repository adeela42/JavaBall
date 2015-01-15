package n.models;

public class South implements Area {
	
	boolean travel;
	
	public South() {
		this.travel = false;
	}
	
	public South(boolean travel) {
		this.travel = travel;
	}

	@Override
	public boolean getTravel() {
		return travel;
	}
	@Override
	public void setTravel(boolean travel) {
		this.travel = travel;
		
	}
	@Override
	public String toString() {
		String[] tokens = java.lang.invoke.MethodHandles.lookup()
				.lookupClass().getName().split("\\.");
		return tokens[tokens.length-1];
	}
	
	@Override
	public String getTravelFlag() {
		return getTravel() ? "Y" : "N";
	}
}
