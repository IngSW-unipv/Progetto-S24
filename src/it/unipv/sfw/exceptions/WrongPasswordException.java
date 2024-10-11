package it.unipv.sfw.exceptions;

/**
 * Eccezione che interviene nel momento in cui la password inserita dall'utente,
 * relativa al suo account, sia sbagliata.
 *
 */
public class WrongPasswordException extends Exception{
	private String accountId;

	/**
	 * @param ID, la stringa identificativa relativa all'account è errata
	 */
	public WrongPasswordException(String id) {
		super("La password relativa all' account " + id + " è sbagliata");
		accountId = id;
	}

	/**
	 * @return La email relativa all'account dell'utente che ha inserito la password
	 *         ma è errata.
	 */
	public String getAccountId() {
		return accountId;
	}
}
