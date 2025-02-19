package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando un componente non viene trovato.
 */
public class ComponentNotFoundException extends Exception {
    private String nameCompo;

    /**
     * Costruttore dell'eccezione.
     * @param name Il nome del componente non trovato.
     */
    public ComponentNotFoundException(String name) {
        super("Componente " + name + " non trovato, riprovare");
        nameCompo = name;
    }

    /**
     * Restituisce il nome del componente non trovato.
     * @return Il nome del componente.
     */
    public String getNameCompo() {
        return nameCompo;
    }

}