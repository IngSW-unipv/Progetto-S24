package it.unipv.sfw.model.staff;


public class Stratega extends Staff{
	
	private int timeLap = 0;
	
	public Stratega(String id, String pwd) {
		super(id, pwd);
	}
	
	@Override
	public TypeController getType() {
		return Staff.TypeController.STRATEGA;
	}

	public int getTimeLap() {
		return timeLap;
	}

	public void setTimeLap(int timeLap) {
		this.timeLap = timeLap;
	}
	

}
