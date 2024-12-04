package it.unipv.sfw.exceptions;

/**
 * Eccezione utilizzata quando l'ID dell'utente è errato.
 *
 */
public class WrongRequestException extends Exception {
	String id;

	public WrongRequestException(String idc) {
		super("Richiesta per il componente ID: " + idc + " , riprovare");
		
		id = idc;
	}

	public String getId() {
		return id;
	}

}