package it.unipv.sfw.dao.interfacedao;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;

/**
 * Interfaccia DAO che definisce le operazioni di accesso ai dati
 * relative all'attività del {@code Mechanic}.
 * <p>
 * Fornisce metodi per:
 * <ul>
 *   <li>Verificare l'esistenza di staff, veicoli, piloti e componenti</li>
 *   <li>Gestire associazioni tra meccanico, veicolo e pilota</li>
 *   <li>Inserire, aggiornare o rimuovere componenti</li>
 *   <li>Gestire richieste di manutenzione e log degli eventi</li>
 * </ul>
 * </p>
 */
public interface IMechanicDAO {

    /**
     * Verifica che lo staff con l'ID indicato esista.
     *
     * @param id identificativo staff
     * @throws WrongIDException se lo staff non esiste
     */
    void checkStaff(String id) throws WrongIDException;

    /**
     * Verifica che il veicolo con l'MSN indicato esista.
     *
     * @param msn MSN del veicolo
     * @throws VehicleNotFoundException se il veicolo non esiste
     */
    void checkVehicle(String msn) throws VehicleNotFoundException;

    /**
     * Inserisce l'associazione meccanico-veicolo.
     *
     * @param msn MSN del veicolo
     * @param id  ID del meccanico
     * @return {@code true} se l'inserimento ha avuto successo
     */
    boolean insertMeccOnVehicle(String msn, String id);

    /**
     * Verifica che il pilota con l'ID indicato esista.
     *
     * @param id_p ID del pilota
     * @throws PilotNotFoundException se il pilota non esiste
     */
    void checkPilot(String id_p) throws PilotNotFoundException;

    /**
     * Inserisce l'associazione pilota-veicolo.
     *
     * @param id_p ID del pilota
     * @param msn  MSN del veicolo
     * @return {@code true} se l'inserimento ha avuto successo
     */
    boolean insertPilotOnVehicle(String id_p, String msn);

    /**
     * Rimuove il pilota indicato.
     *
     * @param idp ID del pilota
     * @return {@code true} se la rimozione ha avuto successo
     */
    boolean removePilot(String idp);

    /**
     * Seleziona un pilota tramite i suoi dati.
     *
     * @param id      ID del pilota
     * @param name    nome
     * @param surname cognome
     * @param number  numero
     * @throws PilotNotFoundException se il pilota non è trovato
     */
    void selectP(String id, String name, String surname, String number) throws PilotNotFoundException;

    /**
     * Verifica se un pilota è associato ad un veicolo.
     *
     * @param idp ID del pilota
     * @return {@code true} se il pilota è già associato
     */
    boolean checkPilotOnVehicle(String idp);

    /**
     * Verifica l'esistenza di un componente con dati specifici.
     *
     * @param id_c  ID componente
     * @param name  nome del componente
     * @param status stato (es. NEW/USED)
     * @throws ComponentNotFoundException se il componente non esiste
     */
    void checkCompo(String id_c, String name, String status) throws ComponentNotFoundException;

    /**
     * Verifica l'esistenza di un componente tramite ID.
     *
     * @param id_c ID componente
     * @throws ComponentNotFoundException se il componente non esiste
     */
    void checkIdCompo(String id_c) throws ComponentNotFoundException;

    /**
     * Inserisce un componente su un veicolo.
     *
     * @param id  ID del componente
     * @param msn MSN del veicolo
     * @return {@code true} se l'inserimento ha avuto successo
     */
    boolean insertComponent(String id, String msn);

    /**
     * Aggiorna il livello di usura di un componente.
     *
     * @param wear valore di usura (0–100)
     * @param id   ID del componente
     * @return {@code true} se l'aggiornamento ha avuto successo
     */
    boolean updateWear(int wear, String id);

    /**
     * Rimuove un componente da un veicolo.
     *
     * @param id_c ID del componente
     * @param id_v ID del veicolo
     */
    void removeComponent(String id_c, String id_v);

    /**
     * Inserisce una richiesta di manutenzione.
     *
     * @param desc descrizione della richiesta
     * @param id_s ID dello staff che inserisce
     * @param id_c ID del componente
     * @param id_v ID del veicolo
     * @return {@code true} se l'inserimento ha avuto successo
     */
    boolean insertRequest(String desc, String id_s, String id_c, String id_v);

    /**
     * Verifica l'esistenza di una richiesta tramite ID componente.
     *
     * @param id_c ID del componente
     * @throws WrongRequestException se la richiesta non è valida o non esiste
     */
    void checkRequest(String id_c) throws WrongRequestException;

    /**
     * Inserisce un evento di log.
     *
     * @param id_staff ID staff che genera l'evento
     * @param desc     descrizione dell'evento
     */
    void insertLogEvent(String id_staff, String desc);
}
