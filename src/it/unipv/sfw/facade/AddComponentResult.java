package it.unipv.sfw.facade;

/**
 * Risultato dell'operazione di aggiunta di un componente.
 * <p>
 * Incapsula l'esito dell'operazione tramite {@link Outcome} e,
 * opzionalmente, ulteriori dati (es. messaggi o livello di usura).
 * </p>
 */
public class AddComponentResult {

    /**
     * Esito possibile dell'operazione di aggiunta componente.
     * 
     *  Outcome = Risultato
     */
    public enum Outcome {
        /** Componente inserito correttamente e usura aggiornata */
        INSERTED_OK,

        /** Componente usurato, necessario aprire richiesta di sostituzione */
        NEEDS_REPLACEMENT,

        /** Input non valido (campi mancanti o errati) */
        INVALID_INPUT
    }

    private final Outcome outcome;

    /**
     * Costruttore base: inizializza un risultato con il solo esito.
     *
     * @param outcome esito dell'operazione
     */
    public AddComponentResult(Outcome outcome) {
        this.outcome = outcome;
    }

    /**
     * Restituisce l'esito dell'operazione.
     *
     * @return {@link Outcome} associato
     */
    public Outcome getOutcome() {
        return outcome;
    }
}
