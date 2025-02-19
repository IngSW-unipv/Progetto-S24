package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando una richiesta non è valida o non può essere
 * soddisfatta per qualche motivo (ad esempio, se il componente richiesto non
 * è disponibile).
 */
public class WrongRequestException extends Exception {
    private String id;

    /**
     * Costruttore dell'eccezione.
     * @param idc L'identificativo del componente per il quale la richiesta non è valida.
     */
    public WrongRequestException(String idc) {
        super("Richiesta per il componente ID: " + idc + " , riprovare");
        id = idc;
    }

    /**
     * Restituisce l'identificativo del componente per il quale la richiesta non è valida.
     * @return L'identificativo del componente.
     */
    public String getId() {
        return id;
    }

}