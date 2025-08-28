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
 * Delega al DAO e restituisce dati/esiti; non modifica il Model.
 */
public class DefaultWarehousemanFacade implements WarehousemanFacade {

    private final IWarehousemanDAO dao;

    public DefaultWarehousemanFacade(IWarehousemanDAO dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    // === REQUESTS ===

    @Override
    public Set<Request> loadRequests() {
        // Ritorno una copia modificabile (utile al controller per eventuali rimozioni)
        return new HashSet<>(dao.selectAllRequest());
    }

    @Override
    public void deleteRequest(String staffId, String componentId, String msn)
            throws RequestNotFoundException {
        String msnUp = (msn == null) ? "" : msn.toUpperCase();
        dao.checkRequest(staffId, componentId, msnUp);
        dao.removeRequest(componentId);
      
    }

    // === COMPONENTS ===

    @Override
    public void updateComponent(String idComp, int wear, String status, String staffId)
            throws ComponentNotFoundException {
        String normStatus = status == null ? "" : status.trim().toUpperCase();
        dao.checkCompo(idComp);
        dao.updateComponent(idComp, String.valueOf(wear), normStatus);
        dao.insertLogEvent(staffId, "UPDATE COMPONENT: " + idComp);
    }

    // === COUNTS / REPORT ===

    @Override
    public int countAllComponentsInWarehouse() {
        return dao.countElement();
    }

    @Override
    public int countComponentsInWarehouseByName(String name) {
        return dao.countElementBySelect(name);
    }

    // === LOG ===

    @Override
    public void log(String staffId, String description) {
        dao.insertLogEvent(staffId, description);
    }
}
