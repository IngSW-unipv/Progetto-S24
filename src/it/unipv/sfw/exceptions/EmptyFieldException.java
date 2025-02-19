package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando si tenta di eseguire un'operazione
 * (ad esempio, l'invio di un form) senza aver compilato tutti i campi
 * obbligatori.
 */
public class EmptyFieldException extends Exception {

    /**
     * Costruttore dell'eccezione.
     * Il messaggio predefinito indica che alcuni campi sono vuoti.
     */
    public EmptyFieldException() {
        super("Alcuni campi sono vuoti.");
    }
}