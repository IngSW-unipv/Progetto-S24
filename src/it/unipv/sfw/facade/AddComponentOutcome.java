package it.unipv.sfw.facade;

public enum AddComponentOutcome {
    INSERTED_OK,        // inserito e wear aggiornato
    NEEDS_REPLACEMENT,  // componente usurato ("apri richiesta")
    INVALID_INPUT       // campi mancanti/errati
}
