package it.unipv.sfw.facade;

import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.request.Request;

/**
 * Facade per il ruolo Warehouseman:
 * - incapsula accesso ai DAO e validazioni;
 * - NON modifica oggetti Model
 * - ritorna dati/esiti che i controller applicano al Model.
 */
public interface WarehousemanFacade {

    // === REQUESTS ===

    /** Carica tutte le richieste dal DB (non tocca il Model). */
    Set<Request> loadRequests();

    /**
     * Verifica ed elimina una richiesta (per id componente) sul DB.
     * NON aggiorna il Model: sarà il chiamante a rimuovere dal proprio Set.
     * @throws RequestNotFoundException se la richiesta non esiste
     */
    void deleteRequest(String staffId, String componentId, String msn)
            throws RequestNotFoundException;

    // === COMPONENTS ===

    /**
     * Aggiorna wear e status di un componente esistente.
     * Esegue validazioni e logging.
     * @throws ComponentNotFoundException se il componente non esiste
     */
    void updateComponent(String idComp, int wear, String status, String staffId)
            throws ComponentNotFoundException;

    // === COUNTS / REPORT ===

    /** @return numero componenti a magazzino (WAREHOUSE=1). */
    int countAllComponentsInWarehouse();

    /** @return numero componenti a magazzino per nome selezionato. */
    int countComponentsInWarehouseByName(String name);

    // === LOG ===

    /** Inserisce una riga di log evento. */
    void log(String staffId, String description);
}
