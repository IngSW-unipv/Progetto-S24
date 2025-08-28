package it.unipv.sfw.facade.impl;

import java.util.Objects;

import it.unipv.sfw.dao.interfacedao.IStrategistDAO;
import it.unipv.sfw.dao.interfacedao.IVehicleDAO;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.facade.StrategistFacade;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Implementazione "pura" della Facade dello Strategist.
 * Delega al DAO e restituisce/esegue solo effetti su DB; non tocca il Model
 */
public class DefaultStrategistFacade implements StrategistFacade {

    private final IStrategistDAO strategistDAO;
    private final IVehicleDAO    vehicleDAO; 

    public DefaultStrategistFacade(IStrategistDAO strategistDAO, IVehicleDAO vehicleDAO) {
        this.strategistDAO = Objects.requireNonNull(strategistDAO);
        this.vehicleDAO    = Objects.requireNonNull(vehicleDAO);
    }

    @Override
    public void bindVehicleToStrategist(String staffId, String msn) throws VehicleNotFoundException {
        String normMsn = msn == null ? "" : msn.toUpperCase().trim();

        // Validazione esistenza veicolo
        strategistDAO.checkVehicle(normMsn);

        // Persistenza associazione
        strategistDAO.insertStrategistOnVehicle(normMsn, staffId);

        // Il controller si occuperà di creare/agganciare il Vehicle nel model.
    }

    @Override
    public void persistSectorTimes(Vehicle vehicle) {
        // Il controller ha già aggiornato i tempi nel model (v.setTimeSect()).
    	// usa IVehicleDAO (implementato da VehicleDAO)
        vehicleDAO.timeSector(vehicle); 
    }

    @Override
    public void log(String staffId, String description) {
        strategistDAO.insertLogEvent(staffId, description);
    }
}
