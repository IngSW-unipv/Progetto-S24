package it.unipv.sfw.model.staff;


public class Stratega extends Staff{
	
	protected long timeLap;
	
	public Stratega(String id, String pwd) {
		super(id, pwd);
	}
	
	@Override
	public TypeController getType() {
		return Staff.TypeController.STRATEGA;
	}

}
