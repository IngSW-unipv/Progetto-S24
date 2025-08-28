package it.unipv.sfw.facade;

import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Facade per le operazioni dello Strategist:
 * - incapsula accesso ai DAO e validazioni;
 * - NON modifica oggetti Model (Strategist/Vehicle)
 * - espone operazioni atomiche per il controller.
 */
public interface StrategistFacade {

    /**
     * Verifica l'esistenza del veicolo e associa lo Strategist al veicolo (DB).
     * NON crea/imposta alcun Vehicle nel Model: il controller gestisce il model.
     *
     * @param staffId ID dello stratega
     * @param msn     MSN del veicolo
     * @throws VehicleNotFoundException se il veicolo non esiste su DB
     */
    void bindVehicleToStrategist(String staffId, String msn) throws VehicleNotFoundException;

    /**
     * Persiste i tempi settore del veicolo su DB.
     * (Il controller deve prima averli generati sul model con Vehicle#setTimeSect()).
     *
     * @param vehicle Vehicle con i tempi già valorizzati nel model
     */
    void persistSectorTimes(Vehicle vehicle);

    /**
     * Inserisce una riga di log evento.
     */
    void log(String staffId, String description);
}
