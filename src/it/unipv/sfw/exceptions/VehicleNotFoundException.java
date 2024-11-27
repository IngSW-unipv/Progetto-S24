package it.unipv.sfw.exceptions;

public class VehicleNotFoundException extends Exception{
	private String msnV;

	/**
	 * @param id Identificativo del pilota richiesto ma inesistente.
	 */
	public VehicleNotFoundException(String msn) {
		super("La vettura " +msn+ " è inesistente");
		msnV = msn;
	}

	/**
	 * @return id identificativo del pilota richiesto ma inesistente.
	 */
	public String getAccountIdl() {
		return msnV;
	}
}
