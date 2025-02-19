package it.unipv.sfw.model.request;

/**
 * Classe che rappresenta una richiesta.
 * Contiene informazioni sulla descrizione, l'ID del richiedente, l'ID del veicolo,
 * l'ID della richiesta e l'ID del componente.
 */
public class Request {

    private String description;
    private String id_s; // ID dello staff che ha effettuato la richiesta
    private String id_v; // ID del veicolo
    private int id_r;   // ID della richiesta
    private int id_c;   // ID del componente

    /**
     * Costruttore della classe Request.
     * @param id_r L'ID della richiesta.
     * @param description La descrizione della richiesta.
     * @param id_s L'ID dello staff che ha effettuato la richiesta.
     * @param id_c L'ID del componente.
     * @param id_v L'ID del veicolo.
     */
    public Request(int id_r, String description, String id_s, int id_c, String id_v) {
        this.id_r = id_r;
        this.description = description;
        this.id_s = id_s;
        this.id_c = id_c;
        this.id_v = id_v;

    }

    /**
     * Restituisce l'ID della richiesta.
     * @return L'ID della richiesta.
     */
    public int getId_r() {
        return id_r;
    }

    /**
     * Imposta l'ID della richiesta.
     * @param id_r L'ID della richiesta.
     */
    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    /**
     * Restituisce la descrizione della richiesta.
     * @return La descrizione della richiesta.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione della richiesta.
     * @param description La descrizione della richiesta.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Restituisce l'ID dello staff che ha effettuato la richiesta.
     * @return L'ID dello staff.
     */
    public String getId_s() {
        return id_s;
    }

    /**
     * Imposta l'ID dello staff che ha effettuato la richiesta.
     * @param id_s L'ID dello staff.
     */
    public void setId_s(String id_s) {
        this.id_s = id_s;
    }

    /**
     * Restituisce l'ID del componente.
     * @return L'ID del componente.
     */
    public int getId_c() {
        return id_c;
    }

    /**
     * Imposta l'ID del componente.
     * @param id_c L'ID del componente.
     */
    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    /**
     * Restituisce l'ID del veicolo.
     * @return L'ID del veicolo.
     */
    public String getId_v() {
        return id_v;
    }

    /**
     * Imposta l'ID del veicolo.
     * @param id_v L'ID del veicolo.
     */
    public void setId_v(String id_v) {
        this.id_v = id_v;
    }

}