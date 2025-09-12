package it.unipv.sfw.facade;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Facade che incapsula le operazioni ad alto livello eseguibili da un {@code Mechanic}.
 * <p>
 * Espone metodi per:
 * <ul>
 *   <li>Gestione veicoli e piloti associati</li>
 *   <li>Aggiunta/rimozione componenti</li>
 *   <li>Creazione richieste di manutenzione</li>
 *   <li>Registrazione eventi di log</li>
 * </ul>
 * L'obiettivo è semplificare l'interazione dei Controller con i DAO,
 * centralizzando la logica applicativa e le validazioni.
 * </p>
 */
public interface MechanicFacade {

    // === VEHICLE / PILOT ===
	 /**
     *Crea un veicolo
     *
     * @param msn			numero seriale della vettura
     */
    Vehicle createVehicle(
            String msn
    );
    
    /**
     * Assegna un veicolo ad un meccanico e ad un pilota.
     *
     * @param mechanicId     ID del meccanico
     * @param pilotId        ID del pilota
     * @param vehicleMsnUpper MSN del veicolo (in maiuscolo)
     * @throws PilotNotFoundException   se il pilota non esiste
     * @throws VehicleNotFoundException se il veicolo non esiste
     */
    void assignVehicleToMechanicAndPilot(
            String mechanicId,
            String pilotId,
            String vehicleMsnUpper
    ) throws PilotNotFoundException, VehicleNotFoundException;

    /**
     * Collega un pilota ad un veicolo esistente.
     *
     * @param mechanicId       ID del meccanico
     * @param vehicleMsnUpper  MSN del veicolo (in maiuscolo)
     * @param pilotId          ID del pilota
     * @param pilotNameUpper   nome del pilota (in maiuscolo)
     * @param pilotSurnameUpper cognome del pilota (in maiuscolo)
     * @param pilotNumber      numero di gara del pilota
     * @throws PilotNotFoundException se il pilota non è trovato
     */
    void linkPilotToVehicle(
            String mechanicId,
            String vehicleMsnUpper,
            String pilotId,
            String pilotNameUpper,
            String pilotSurnameUpper,
            String pilotNumber
    ) throws PilotNotFoundException;

    /**
     * Scollega un pilota da un veicolo.
     *
     * @param mechanicId ID del meccanico
     * @param pilotId    ID del pilota da rimuovere
     */
    void unlinkPilotFromVehicle(String mechanicId, String pilotId);

    // === COMPONENTS ===

    /**
     * Aggiunge un componente ad un veicolo.
     *
     * @param mechanicId         ID del meccanico
     * @param vehicleMsnUpper    MSN del veicolo (in maiuscolo)
     * @param componentId        ID del componente
     * @param componentNameUpper nome del componente (in maiuscolo)
     * @param declaredStatusUpper stato dichiarato ("NEW"/"USED"/"WORN")
     * @param wearFromModelOrNull livello di usura calcolato dal model, se disponibile
     * @return risultato dell'operazione con esito {@link AddComponentResult.Outcome}
     * @throws ComponentNotFoundException se il componente non è trovato
     */
    AddComponentResult addComponent(
            String mechanicId,
            String vehicleMsnUpper,
            int componentId,
            String componentNameUpper,
            String declaredStatusUpper,
            Integer wearFromModelOrNull
    ) throws ComponentNotFoundException;

    /**
     * Rimuove un componente da un veicolo.
     *
     * @param mechanicId         ID del meccanico
     * @param vehicleMsnUpper    MSN del veicolo (in maiuscolo)
     * @param componentId        ID del componente
     * @param componentNameUpper nome del componente (in maiuscolo)
     * @throws ComponentNotFoundException se il componente non è trovato
     */
    void removeComponent(
            String mechanicId,
            String vehicleMsnUpper,
            int componentId,
            String componentNameUpper
    ) throws ComponentNotFoundException;

    // === REQUESTS ===

    /**
     * Crea una richiesta di manutenzione per un componente.
     *
     * @param mechanicId        ID del meccanico
     * @param staffIdTyped      ID dello staff digitato dall'utente in UI
     * @param componentId       ID del componente
     * @param vehicleMsnUpper   MSN del veicolo (in maiuscolo)
     * @param descriptionUpper  descrizione della richiesta (in maiuscolo)
     * @throws WrongIDException         se l'ID staff non è valido
     * @throws ComponentNotFoundException se il componente non è trovato
     * @throws WrongRequestException    se la richiesta non è valida
     * @throws VehicleNotFoundException se il veicolo non è trovato
     */
    void createComponentRequest(
            String mechanicId,
            String staffIdTyped,
            String componentId,
            String vehicleMsnUpper,
            String descriptionUpper
    ) throws WrongIDException, ComponentNotFoundException,
             WrongRequestException, VehicleNotFoundException;

    // === LOGGING ===

    /**
     * Registra un evento di log relativo al meccanico.
     *
     * @param mechanicId   ID del meccanico
     * @param description  descrizione dell'evento
     */
    void log(String mechanicId, String description);
}
