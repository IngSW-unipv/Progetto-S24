package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando la password fornita per un account
 * non è corretta.
 */
public class WrongPasswordException extends Exception {
    private String accountId;

    /**
     * Costruttore dell'eccezione.
     * @param id L'identificativo dell'account per il quale la password è errata.
     */
    public WrongPasswordException(String id) {
        super("La password relativa all' account " + id + " è sbagliata");
        accountId = id;
    }

    /**
     * Restituisce l'identificativo dell'account per il quale la password è errata.
     * @return L'identificativo dell'account.
     */
    public String getAccountId() {
        return accountId;
    }
}