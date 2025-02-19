package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando una richiesta specifica non viene trovata.
 */
public class RequestNotFoundException extends Exception {

    /**
     * Costruttore dell'eccezione.
     * Il messaggio predefinito indica che la richiesta non è stata trovata.
     */
    public RequestNotFoundException() {
        super("Richiesta non trovata, riprovare");
    }

}
