package it.unipv.sfw.exceptions;

public class PilotNotFoundException extends Exception{
	private String pilotId;

	/**
	 * @param id Identificativo del pilota richiesto ma inesistente.
	 */
	public PilotNotFoundException(String id) {
		super("Il pilota" + id + " è inesistente.");
		pilotId = id;
	}

	/**
	 * @return id identificativo del pilota richiesto ma inesistente.
	 */
	public String getAccountIdl() {
		return pilotId;
	}
}
