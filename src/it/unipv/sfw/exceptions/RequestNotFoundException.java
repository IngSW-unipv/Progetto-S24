package it.unipv.sfw.exceptions;

public class RequestNotFoundException extends Exception {

	public RequestNotFoundException() {
		super("Richiesta non trovata, riprovare");

	}

}
