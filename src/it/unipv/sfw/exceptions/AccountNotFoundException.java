package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando si tenta di accedere a un account
 * che non esiste.
 */
public class AccountNotFoundException extends Exception {

    private String accountId;

    /**
     * Costruttore dell'eccezione.
     * @param id L'identificativo dell'account inesistente.
     */
    public AccountNotFoundException(String id) {
        super("L'account " + id + " è inesistente.");
        accountId = id;
    }

    /**
     * Restituisce l'identificativo dell'account inesistente.
     * @return L'identificativo dell'account.
     */
    public String getAccountIdl() {
        return accountId;
    }
}