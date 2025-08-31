package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Interfaccia DAO che definisce le operazioni di accesso ai dati
 * relative ai {@link Vehicle}.
 * <p>
 * Permette di aggiornare i tempi di settore di un veicolo nel database.
 * </p>
 */
public interface IVehicleDAO {

    /**
     * Aggiorna i tempi di settore per il veicolo specificato.
     *
     * @param v istanza di {@link Vehicle} contenente i tempi da salvare
     * @return {@code true} se l'aggiornamento ha avuto successo,
     *         {@code false} in caso contrario
     */
    boolean timeSector(Vehicle v);
}
