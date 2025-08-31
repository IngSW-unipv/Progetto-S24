package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.exceptions.VehicleNotFoundException;

/**
 * Interfaccia DAO che definisce le operazioni di accesso ai dati
 * relative all'attività dello {@code Strategist}.
 * <p>
 * Fornisce metodi per:
 * <ul>
 *   <li>Registrare eventi di log</li>
 *   <li>Verificare l'esistenza di un veicolo</li>
 *   <li>Associare uno strategist ad un veicolo</li>
 * </ul>
 * </p>
 */
public interface IStrategistDAO {

    /**
     * Inserisce un evento di log per uno strategist.
     *
     * @param id_staff ID dello staff (strategist) che genera l'evento
     * @param desc     descrizione dell'evento
     */
    void insertLogEvent(String id_staff, String desc);

    /**
     * Verifica che il veicolo con l'MSN indicato esista.
     *
     * @param msn MSN del veicolo
     * @throws VehicleNotFoundException se il veicolo non esiste
     */
    void checkVehicle(String msn) throws VehicleNotFoundException;

    /**
     * Inserisce l'associazione strategist-veicolo.
     *
     * @param msn MSN del veicolo
     * @param id  ID dello strategist
     */
    void insertStrategistOnVehicle(String msn, String id);
}
