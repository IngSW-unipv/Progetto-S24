package it.unipv.sfw.exceptions;

/**
 * Eccezione utilizzata quando l'ID dell'utente è errato.
 *
 */
public class WrongIDException extends Exception {

		public WrongIDException() {
			super("ID non conforme, riprovare");
		}
}
