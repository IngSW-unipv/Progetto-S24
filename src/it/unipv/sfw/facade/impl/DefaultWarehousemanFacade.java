package it.unipv.sfw.facade.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.unipv.sfw.dao.interfacedao.IWarehousemanDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.facade.WarehousemanFacade;
import it.unipv.sfw.model.request.Request;

/**
 * Implementazione della Facade del magazziniere.
 * <p>
 * Delega le operazioni al livello DAO e restituisce dati/esiti verso i Controller;
 * non modifica direttamente il Model di dominio.
 * </p>
 *
 * @see WarehousemanFacade
 * @see IWarehousemanDAO
 */
public class DefaultWarehousemanFacade implements WarehousemanFacade {

    private final IWarehousemanDAO wd;

    /**
     * Costruttore.
     *
     * @param dao implementazione di {@link IWarehousemanDAO} da utilizzare
     * @throws NullPointerException se {@code dao} è {@code null}
     */
    public DefaultWarehousemanFacade(IWarehousemanDAO warehousemanDao) {
    	this.wd = warehousemanDao;
    }

    // === REQUESTS ===

    /**
     * Carica tutte le richieste
     * <p>
     * Restituisce una <b>copia modificabile</b> dell'insieme, così che
     * il Controller possa eventualmente rimuovere elementi dal proprio set
     * senza influenzare la collezione originale del DAO.
     * </p>
     *
     * @return insieme di {@link Request} correnti
     */
    @Override
    public Set<Request> loadRequests() {
        // Ritorno una copia modificabile (utile al controller per eventuali rimozioni)
        return new HashSet<>(wd.selectAllRequest());
    }

    /**
     * Elimina una richiesta individuata da staff, componente e MSN veicolo.
     *
     * @param staffId    ID dello staff che ha inserito la richiesta
     * @param componentId ID del componente collegato alla richiesta
     * @param msn        MSN del veicolo; verrà normalizzato in maiuscolo
     * @throws RequestNotFoundException se la richiesta non è presente/valida
     */
    @Override
    public void deleteRequest(String staffId, String componentId, String msn)
            throws RequestNotFoundException {
        String msnUp = (msn == null) ? "" : msn.toUpperCase();
        wd.checkRequest(staffId, componentId, msnUp);
        wd.removeRequest(componentId);
      
    }

    // === COMPONENTS ===

    /**
     * Aggiorna un componente (usura e stato) e registra un evento di log.
     *
     * @param idComp  ID del componente
     * @param wear    livello di usura (0-100)
     * @param status  stato dichiarato (es. {@code NEW}/{@code USED});
     * @param staffId ID del magazziniere che effettua l’operazione (per il log)
     * @throws ComponentNotFoundException se il componente non esiste
     */
    @Override
    public void updateComponent(String idComp, int wear, String status, String staffId)
            throws ComponentNotFoundException {
        String normStatus = status == null ? "" : status.trim().toUpperCase();
        wd.checkCompo(idComp);
        wd.updateComponent(idComp, String.valueOf(wear), normStatus);
        wd.insertLogEvent(staffId, "UPDATE COMPONENT: " + idComp);
    }

    // === COUNTS / REPORT ===

    /**
     * Conta il numero totale di componenti presenti in magazzino.
     *
     * @return numero totale di componenti
     */
    @Override
    public int countAllComponentsInWarehouse() {
        return wd.countElement();
    }

    /**
     * Conta il numero di componenti presenti in magazzino filtrando per nome/tipo.
     *
     * @param name nome del componente da filtrare
     * @return numero di componenti corrispondenti
     */
    @Override
    public int countComponentsInWarehouseByName(String name) {
        return wd.countElementBySelect(name);
    }

    // === LOG ===

    /**
     * Registra un evento di log a nome del magazziniere.
     *
     * @param staffId     ID dello staff
     * @param description descrizione sintetica dell’evento
     */
    @Override
    public void log(String staffId, String description) {
    	wd.insertLogEvent(staffId, description);
    }
}
