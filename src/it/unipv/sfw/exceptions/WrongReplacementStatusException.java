package it.unipv.sfw.exceptions;

public class WrongReplacementStatusException extends Exception {
	
	public WrongReplacementStatusException(String replacementStatus) {
		super("Stato componente non riconosciuto");
	}
}
