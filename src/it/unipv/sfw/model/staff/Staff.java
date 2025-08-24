package it.unipv.sfw.model.staff;

import it.unipv.sfw.exceptions.EmptyFieldException;
import it.unipv.sfw.exceptions.WrongIDException;

/**
 * Classe astratta che rappresenta un generico membro dello staff della scuderia.
 */
public abstract class Staff {

    /**
     * Enumerazione che definisce i possibili tipi di membri dello staff.
     */
    public enum TypeRole{
        STRATEGIST, MECHANIC, WAREHOUSEMAN;
    }

    private String id;
    private String pwd;

    /**
     * Costruttore della classe Staff.
     * @param id L'ID del membro dello staff.
     * @param pwd La password del membro dello staff.
     */
    public Staff(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    /**
     * Controlla che i dati dell'utente siano validi.
     * @throws EmptyFieldException Se uno dei campi obbligatori è vuoto.
     * @throws WrongIDException Se l'ID non è nel formato corretto.
     */
    public void checkValidity() throws WrongIDException, EmptyFieldException {
        // ...
    }

    /**
     * Controlla che l'ID passato sia valido.
     * @param id L'ID da controllare.
     * @throws WrongIDException Se l'ID non è nel formato corretto.
     */
    public static void checkID(String id) throws WrongIDException {
        // ...
    }

    /**
     * Restituisce l'ID del membro dello staff.
     * @return L'ID del membro dello staff.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Restituisce la password del membro dello staff.
     * @return La password del membro dello staff.
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     * Restituisce il tipo di utente.
     * @return Il tipo di utente (un valore dell'enumerazione {@link TypeController}).
     */
    public abstract TypeRole getType();
}