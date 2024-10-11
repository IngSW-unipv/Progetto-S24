package it.unipv.sfw.exceptions;

/**
 * Eccezione utilizzata nel caso in cui sia richiesto un account inesistente.
 *
 *
 */
public class AccountNotFoundException extends Exception {

	private String accountId;

	/**
	 * @param id Identificativo dell'account richiesto ma inesistente.
	 */
	public AccountNotFoundException(String id) {
		super("L'account " + id + " è inesistente.");
		accountId = id;
	}

	/**
	 * @return id identificativ dell'account richiesto ma inesistente.
	 */
	public String getAccountIdl() {
		return accountId;
	}
}
