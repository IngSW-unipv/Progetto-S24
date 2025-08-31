package it.unipv.sfw.dao.interfacedao;

import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.RequestNotFoundException;
import it.unipv.sfw.model.request.Request;

/**
 * Interfaccia DAO che definisce le operazioni di accesso ai dati
 * relative all'attività del {@code Warehouseman}.
 * <p>
 * Fornisce metodi per:
 * <ul>
 *   <li>Gestire le richieste di manutenzione (selezione, rimozione, validazione)</li>
 *   <li>Aggiornare i componenti in magazzino (usura, stato)</li>
 *   <li>Contare i componenti disponibili</li>
 *   <li>Registrare eventi di log</li>
 * </ul>
 * </p>
 */
public interface IWarehousemanDAO {

    /**
     * Restituisce tutte le richieste presenti nel sistema.
     *
     * @return insieme di {@link Request}
     */
    Set<Request> selectAllRequest();

    /**
     * Rimuove la richiesta associata al componente indicato.
     *
     * @param idc ID del componente
     */
    void removeRequest(String idc);

    /**
     * Aggiorna i dati di un componente (usura e stato).
     *
     * @param id     ID del componente
     * @param wear   livello di usura (0-100)
     * @param status stato del componente (es. NEW/USED)
     * @return {@code true} se l'aggiornamento ha avuto successo
     */
    boolean updateComponent(String id, String wear, String status);

    /**
     * Conta il numero totale di componenti in magazzino.
     *
     * @return numero di componenti
     */
    int countElement();

    /**
     * Conta il numero di componenti in magazzino filtrati per tipo.
     *
     * @param select nome del componente da filtrare
     * @return numero di componenti di quel tipo
     */
    int countElementBySelect(String select);

    /**
     * Verifica che una richiesta specifica esista.
     *
     * @param id_s ID staff che ha inserito
     * @param id_c ID componente
     * @param id_v ID veicolo
     * @throws RequestNotFoundException se la richiesta non è trovata
     */
    void checkRequest(String id_s, String id_c, String id_v) throws RequestNotFoundException;

    /**
     * Verifica che un componente con ID specifico esista.
     *
     * @param id_c ID del componente
     * @throws ComponentNotFoundException se il componente non è trovato
     */
    void checkCompo(String id_c) throws ComponentNotFoundException;

    /**
     * Inserisce un evento di log.
     *
     * @param id_staff ID dello staff che genera l'evento
     * @param desc     descrizione dell'evento
     */
    void insertLogEvent(String id_staff, String desc);
}
