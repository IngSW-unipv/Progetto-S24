package it.unipv.sfw.facade;

import it.unipv.sfw.model.staff.Staff;

/**
 * Risultato dell'operazione di autenticazione.
 * <p>
 * Incapsula l'utente autenticato come istanza tipizzata di {@link Staff}
  *e alcuni metadati leggeri utili alla UI (nome, cognome).
 * </p>
 *
 *   <li>Il riferimento a {@link Staff} consente ai {@code Controller} di impostare
 *       la {@link it.unipv.sfw.model.staff.Session} senza dover interrogare direttamente la Facade.</li>
 *   <li>Per evitare inconsistenze, <b>ID</b> e <b>ruolo</b> vengono <b>derivati</b> dall'istanza di {@code Staff}
 *       (non sono duplicati nei campi).</li>
 *   <li>L'oggetto è <b>immutabile</b> e non contiene la password.</li>
 *
 */
public final class AuthResult {

    /** Utente autenticato */
    private final Staff user;

    /** Metadati leggeri per la UI. */
    private final String name;
    private final String surname;

    /**
     * Costruisce un nuovo risultato di autenticazione.
     *
     * @param user    istanza autenticata di {@link Staff} (non {@code null}); non contiene la password
     * @param name    nome utente (accetta {@code null} → stringa vuota)
     * @param surname cognome utente (accetta {@code null} → stringa vuota)
     * @throws NullPointerException se {@code user} è {@code null}
     */
    public AuthResult(Staff user, String name, String surname) {
        this.user = user;
        this.name = (name != null ? name : "");
        this.surname = (surname != null ? surname : "");
    }

	/**
     * Restituisce l'istanza autenticata di {@link Staff}.
     * <p>Può essere usata per aggiornare la {@link it.unipv.sfw.model.staff.Session}.</p>
     *
     * @return utente autenticato
     */
    public Staff getUser() {
        return user;
    }

    /**
     * Restituisce l'ID dell'utente autenticato.
     * <p>Derivato da {@link Staff#getID()} per evitare duplicazioni.</p>
     *
     * @return ID utente (mai {@code null})
     */
    
    public String getUserId() {
        return user.getID();
    }

    /**
     * Restituisce il ruolo dell'utente autenticato.
     * <p>Derivato da {@link Staff#getType()} per evitare duplicazioni.</p>
     *
     * @return ruolo dell'utente (mai {@code null})
     */
    public Staff.TypeRole getRole() {
        return user.getType();
    }

    /**
     * Restituisce il nome dell'utente autenticato.
     *
     * @return nome (mai {@code null}; eventualmente stringa vuota)
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce il cognome dell'utente autenticato.
     *
     * @return cognome (mai {@code null}; eventualmente stringa vuota)
     */
    public String getSurname() {
        return surname;
    }
}
