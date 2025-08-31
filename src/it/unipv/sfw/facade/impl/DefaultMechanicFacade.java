package it.unipv.sfw.facade.impl;

import java.util.Objects;

import it.unipv.sfw.dao.interfacedao.IMechanicDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.facade.AddComponentResult;
import it.unipv.sfw.facade.MechanicFacade;

/**
 * Implementazione di default della {@link MechanicFacade}.
 * <p>
 * Incapsula la logica applicativa ad alto livello per le operazioni del meccanico,
 * delegando le persistenze e le verifiche a {@link IMechanicDAO}.
 * Le responsabilità includono:
 * <ul>
 *   <li>Associazione veicolo/pilota al meccanico</li>
 *   <li>Aggiunta/rimozione componenti con validazioni</li>
 *   <li>Creazione richieste di sostituzione/manutenzione</li>
 *   <li>Registrazione eventi di log</li>
 * </ul>
 * </p>
 */
public class DefaultMechanicFacade implements MechanicFacade {

    private final IMechanicDAO md;

    /**
     * Costruttore.
     *
     * @param mechanicDao implementazione di {@link IMechanicDAO} da utilizzare
     * @throws NullPointerException se {@code mechanicDao} è {@code null}
     */
    public DefaultMechanicFacade(IMechanicDAO mechanicDao) {
        this.md = Objects.requireNonNull(mechanicDao);
    }

    // === VEHICLE / PILOT ===

    /**
     * Assegna un veicolo ad un meccanico e ad un pilota, effettuando
     * le opportune verifiche di esistenza e registrando un evento di log.
     *
     * @param mechanicId      ID del meccanico
     * @param pilotId         ID del pilota
     * @param vehicleMsnUpper MSN del veicolo (atteso già in maiuscolo; viene comunque normalizzato)
     * @throws PilotNotFoundException   se il pilota non esiste
     * @throws VehicleNotFoundException se il veicolo non esiste
     */
    @Override
    public void assignVehicleToMechanicAndPilot(String mechanicId,
                                                String pilotId,
                                                String vehicleMsnUpper)
            throws PilotNotFoundException, VehicleNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();

        md.checkPilot(pilotId);
        md.checkVehicle(msn);

        md.insertPilotOnVehicle(pilotId, msn);
        md.insertMeccOnVehicle(msn, mechanicId);

        md.insertLogEvent(mechanicId, "INSERT VEHICLE : " + msn);
    }

    /**
     * Collega un pilota ad un veicolo esistente, validando i dati e registrando un log.
     *
     * @param mechanicId        ID del meccanico
     * @param vehicleMsnUpper   MSN del veicolo (atteso già in maiuscolo; viene comunque normalizzato)
     * @param pilotId           ID del pilota
     * @param pilotNameUpper    nome pilota (atteso in maiuscolo; viene comunque normalizzato)
     * @param pilotSurnameUpper cognome pilota (atteso in maiuscolo; viene comunque normalizzato)
     * @param pilotNumber       numero del pilota
     * @throws PilotNotFoundException se il pilota non è trovato
     */
    @Override
    public void linkPilotToVehicle(String mechanicId,
                                   String vehicleMsnUpper,
                                   String pilotId,
                                   String pilotNameUpper,
                                   String pilotSurnameUpper,
                                   String pilotNumber)
            throws PilotNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = pilotNameUpper == null ? null : pilotNameUpper.toUpperCase();
        String surname = pilotSurnameUpper == null ? null : pilotSurnameUpper.toUpperCase();

        md.selectP(pilotId, name, surname, pilotNumber);
        md.insertPilotOnVehicle(pilotId, msn);
        md.insertLogEvent(mechanicId, "INSERT ID PILOT : " + pilotId);
    }

    /**
     * Scollega un pilota dal veicolo e registra l'evento di log.
     *
     * @param mechanicId ID del meccanico
     * @param pilotId    ID del pilota da rimuovere
     */
    @Override
    public void unlinkPilotFromVehicle(String mechanicId, String pilotId) {
        md.removePilot(pilotId);
        md.insertLogEvent(mechanicId, "REMOVE PILOT : " + pilotId);
    }

    // === COMPONENTS ===

    /**
     * Aggiunge un componente ad un veicolo con le dovute verifiche.
     * <p>
     * Flusso semplificato:
     * <ol>
     *   <li>Normalizza gli input e valida campi obbligatori</li>
     *   <li>Verifica esistenza componente tramite {@link IMechanicDAO#checkCompo(String, String, String)}</li>
     *   <li>Se è presente un valore di usura dal Model: inserisce il componente e aggiorna l'usura</li>
     *   <li>Se lo stato dichiarato è {@code WORN}: suggerisce richiesta di sostituzione</li>
     *   <li>In caso contrario: ritorna esito di input non valido</li>
     * </ol>
     * </p>
     *
     * @param mechanicId          ID del meccanico
     * @param vehicleMsnUpper     MSN del veicolo (in maiuscolo; viene comunque normalizzato)
     * @param componentId         ID del componente
     * @param componentNameUpper  nome del componente (in maiuscolo; viene comunque normalizzato)
     * @param declaredStatusUpper stato dichiarato del componente ("NEW"/"USED"/"WORN"; viene normalizzato)
     * @param wearFromModelOrNull livello di usura calcolato dal Model, se disponibile; altrimenti {@code null}
     * @return risultato dell'operazione con esito {@link AddComponentResult.Outcome}
     * @throws ComponentNotFoundException se il componente non è trovato/valido secondo DAO
     */
    @Override
    public AddComponentResult addComponent(String mechanicId,
                                           String vehicleMsnUpper,
                                           int componentId,
                                           String componentNameUpper,
                                           String declaredStatusUpper,
                                           Integer wearFromModelOrNull)
            throws ComponentNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = componentNameUpper == null ? null : componentNameUpper.toUpperCase();
        String status = declaredStatusUpper == null ? null : declaredStatusUpper.toUpperCase();

        if (msn == null || msn.isBlank()
                || name == null || name.isBlank()
                || status == null || status.isBlank()) {
            return new AddComponentResult(AddComponentResult.Outcome.INVALID_INPUT);
        }

        md.checkCompo(String.valueOf(componentId), name, status);

        if (wearFromModelOrNull != null) {
            boolean ok = md.insertComponent(String.valueOf(componentId), msn);
            if (ok) {
                md.updateWear(wearFromModelOrNull, String.valueOf(componentId));
            }
            md.insertLogEvent(mechanicId, "INSERT COMPONENT ID: " + componentId);
            return new AddComponentResult(AddComponentResult.Outcome.INSERTED_OK);
        }

        if ("WORN".equalsIgnoreCase(status)) {
            md.insertLogEvent(mechanicId, "REQUEST REPLACEMENT SUGGESTED FOR COMPONENT ID: " + componentId);
            return new AddComponentResult(AddComponentResult.Outcome.NEEDS_REPLACEMENT);
        }

        return new AddComponentResult(AddComponentResult.Outcome.INVALID_INPUT);
    }

    /**
     * Rimuove un componente da un veicolo dopo averne verificato l'esistenza
     * e registra l'operazione a log.
     *
     * @param mechanicId         ID del meccanico
     * @param vehicleMsnUpper    MSN del veicolo (in maiuscolo; viene comunque normalizzato)
     * @param componentId        ID del componente da rimuovere
     * @param componentNameUpper nome del componente (in maiuscolo; viene comunque normalizzato)
     * @throws ComponentNotFoundException se il componente non è trovato/valido secondo DAO
     */
    @Override
    public void removeComponent(String mechanicId,
                                String vehicleMsnUpper,
                                int componentId,
                                String componentNameUpper)
            throws ComponentNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = componentNameUpper == null ? null : componentNameUpper.toUpperCase();

        md.checkCompo(String.valueOf(componentId), name, "USED");
        md.removeComponent(String.valueOf(componentId), msn);
        md.insertLogEvent(mechanicId, "REMOVE COMPONENT ID: " + componentId);
    }

    // === REQUESTS ===

    /**
     * Crea una richiesta di manutenzione/sostituzione componente.
     * <p>
     * Esegue:
     * <ul>
     *   <li>Validazione preliminare dell'ID staff digitato</li>
     *   <li>Verifica esistenza componente e richiesta</li>
     *   <li>Verifica presenza e validità dell'MSN</li>
     *   <li>Inserimento richiesta e log</li>
     * </ul>
     * </p>
     *
     * @param mechanicId       ID del meccanico
     * @param staffIdTyped     ID staff digitato in UI
     * @param componentId      ID del componente
     * @param vehicleMsnUpper  MSN del veicolo (in maiuscolo; viene comunque normalizzato)
     * @param descriptionUpper descrizione della richiesta (in maiuscolo; viene normalizzata)
     * @throws WrongIDException          se l'ID staff non coincide o non è valido
     * @throws ComponentNotFoundException se il componente non è trovato
     * @throws WrongRequestException     se la richiesta non è valida o già presente
     * @throws VehicleNotFoundException  se l'MSN è mancante/non valido o il veicolo non esiste
     */
    @Override
    public void createComponentRequest(String mechanicId,
                                       String staffIdTyped,
                                       String componentId,
                                       String vehicleMsnUpper,
                                       String descriptionUpper)
            throws WrongIDException, ComponentNotFoundException,
                   WrongRequestException, VehicleNotFoundException {

        if (mechanicId == null || !mechanicId.equals(staffIdTyped)) {
            throw new WrongIDException();
        }

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String desc = descriptionUpper == null ? null : descriptionUpper.toUpperCase();
        String staffUp = staffIdTyped.toUpperCase();

        md.checkIdCompo(componentId);
        md.checkRequest(componentId);

        if (msn == null || msn.isBlank()) {
            throw new VehicleNotFoundException("Missing or empty MSN");
        }
        md.checkVehicle(msn);

        md.insertRequest(desc, staffUp, componentId, msn);
        md.insertLogEvent(mechanicId, "INSERT REQUEST FOR COMPONENT: " + componentId);
    }

    /**
     * Registra un evento di log associato al meccanico.
     *
     * @param mechanicId  ID del meccanico
     * @param description descrizione dell'evento
     */
    @Override
    public void log(String mechanicId, String description) {
        md.insertLogEvent(mechanicId, description);
    }
}
