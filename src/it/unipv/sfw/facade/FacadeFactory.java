package it.unipv.sfw.facade;

import it.unipv.sfw.dao.interfacedao.IMechanicDAO;
import it.unipv.sfw.dao.interfacedao.IStrategistDAO;
import it.unipv.sfw.dao.interfacedao.IVehicleDAO;
import it.unipv.sfw.dao.interfacedao.IWarehousemanDAO;

import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.dao.mysql.StrategistDAO;
import it.unipv.sfw.dao.mysql.VehicleDAO;
import it.unipv.sfw.dao.mysql.WarehousemanDAO;

import it.unipv.sfw.facade.impl.DefaultMechanicFacade;
import it.unipv.sfw.facade.impl.DefaultStrategistFacade;
import it.unipv.sfw.facade.impl.DefaultWarehousemanFacade;

/**
 * Factory per ottenere le facciate
 * (Mechanic, Strategist, Warehouseman) senza che i Controller debbano conoscere i DAO
 *
 * I controller dipendono solo dalle interfacce delle facciate e richiedono
 * un'istanza tramite questi metodi factory.
 *
 *Ogni metodo costruisce una nuova istanza della facciata
 * e del relativo DAO.
 */
public final class FacadeFactory {

    /**
     * Costruttore privato per impedire l'instanziazione.
     */
    private FacadeFactory() { }

    /**
     * Restituisce una facciata predefinita per le operazioni del ruolo
     * Mechanic
     *
     * La facciata incapsula la logica applicativa e orchestra il DAO
     * senza esporre dettagli di persistenza ai controller.
     *
     * @return un'istanza non {@code null} di {@link MechanicFacade}
     *         basata su {@link DefaultMechanicFacade} e {@link MechanicDAO}.
     */
    public static MechanicFacade mechanic() {
        IMechanicDAO dao = new MechanicDAO();
        return new DefaultMechanicFacade(dao);
    }

    /**
     * Restituisce una facciata predefinita per le operazioni del ruolo
     *Strategist
     *
     * La facciata incapsula la logica applicativa e orchestra il DAO
     * senza esporre dettagli di persistenza ai controller.
     *
     * @return un'istanza non {@code null} di {@link StrategistFacade}
     *         basata su {@link DefaultStrategistFacade} e {@link StrategistDAO}.
     */
    public static StrategistFacade strategist() {
        IStrategistDAO daoS = new StrategistDAO();
        IVehicleDAO daoV = new VehicleDAO();
        return new DefaultStrategistFacade(daoS, daoV);
    }

    /**
     * Restituisce una facciata predefinita per le operazioni del ruolo
     * Warehouseman.
     *
     * La facciata incapsula la logica applicativa e orchestra il DAO
     * senza esporre dettagli di persistenza ai controller.
     *
     * @return un'istanza non {@code null} di {@link WarehousemanFacade}
     *         basata su {@link DefaultWarehousemanFacade} e {@link WarehousemanDAO}.
     */
    public static WarehousemanFacade warehouseman() {
        IWarehousemanDAO dao = new WarehousemanDAO();
        return new DefaultWarehousemanFacade(dao);
    }
}
