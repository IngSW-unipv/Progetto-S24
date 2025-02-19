package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando un veicolo specifico non viene trovato.
 */
public class VehicleNotFoundException extends Exception {
    private String msnV;

    /**
     * Costruttore dell'eccezione.
     * @param msn Il numero di telaio (MSN) del veicolo non trovato.
     */
    public VehicleNotFoundException(String msn) {
        super("La vettura " + msn + " è inesistente");
        msnV = msn;
    }

    /**
     * Restituisce il numero di telaio (MSN) del veicolo non trovato.
     * @return Il numero di telaio del veicolo.
     */
    public String getAccountIdl() {
        return msnV;
    }
}