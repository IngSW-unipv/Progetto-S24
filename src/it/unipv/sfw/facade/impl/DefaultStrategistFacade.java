package it.unipv.sfw.facade.impl;

import java.util.Objects;

import it.unipv.sfw.dao.interfacedao.IStrategistDAO;
import it.unipv.sfw.dao.interfacedao.IVehicleDAO;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.facade.StrategistFacade;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Implementazione della {@link StrategistFacade}.
 * <p>
 * Delega le operazioni al livello DAO ed esegue esclusivamente effetti su DB;
 * non modifica né interroga il <em>Model</em> di dominio.
 * </p>
 *
 * @see StrategistFacade
 * @see IStrategistDAO
 * @see IVehicleDAO
 */
public class DefaultStrategistFacade implements StrategistFacade {

    private final IStrategistDAO strategistDAO;
    private final IVehicleDAO    vehicleDAO; 

    /**
     * Costruttore.
     *
     * @param strategistDAO implementazione di {@link IStrategistDAO} per operazioni su strategist/associazioni
     * @param vehicleDAO    implementazione di {@link IVehicleDAO} per la persistenza dei tempi di settore
     * @throws NullPointerException se uno dei parametri è {@code null}
     */
    public DefaultStrategistFacade(IStrategistDAO strategistDAO, IVehicleDAO vehicleDAO) {
        this.strategistDAO = Objects.requireNonNull(strategistDAO);
        this.vehicleDAO    = Objects.requireNonNull(vehicleDAO);
    }

    /**
     * Associa un veicolo allo strategist indicato, validando l'esistenza del veicolo
     * e registrando l'associazione a livello di persistenza.
     *
     * @param staffId ID dello strategist
     * @param msn     MSN del veicolo (può essere in qualunque forma; verrà normalizzato)
     * @throws VehicleNotFoundException se il veicolo non esiste a DB
     */
    @Override
    public void bindVehicleToStrategist(String staffId, String msn) throws VehicleNotFoundException {
        String normMsn = msn == null ? "" : msn.toUpperCase().trim();

        // Validazione esistenza veicolo
        strategistDAO.checkVehicle(normMsn);

        // Persistenza associazione
        strategistDAO.insertStrategistOnVehicle(normMsn, staffId);

        // Il controller si occuperà di creare/agganciare il Vehicle nel model.
    }

    /**
     * Persiste i tempi di settore dell'istanza di {@link Vehicle} fornita.
     * 
     * @param vehicle veicolo i cui tempi di settore devono essere salvati
     */
    @Override
    public void persistSectorTimes(Vehicle vehicle) {
        // Il controller ha già aggiornato i tempi nel model (v.setTimeSect()).
    	// usa IVehicleDAO (implementato da VehicleDAO)
        vehicleDAO.timeSector(vehicle); 
    }

    /**
     * Registra un evento di log relativo allo strategist.
     *
     * @param staffId     ID dello strategist
     * @param description descrizione sintetica dell'evento
     */
    @Override
    public void log(String staffId, String description) {
        strategistDAO.insertLogEvent(staffId, description);
    }
}
