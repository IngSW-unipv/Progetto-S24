package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando un ID fornito (ad esempio, ID utente)
 * non è valido o non rispetta il formato atteso.
 */
public class WrongIDException extends Exception {

    /**
     * Costruttore dell'eccezione.
     * Il messaggio predefinito indica che l'ID non è conforme.
     */
    public WrongIDException() {
        super("ID non conforme, riprovare");
    }
}