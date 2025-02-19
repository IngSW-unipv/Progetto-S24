package it.unipv.sfw.exceptions;

/**
 * Eccezione che viene lanciata quando si tenta di inserire un componente
 * che esiste già nel sistema (duplicato).
 */
public class DuplicateComponentException extends Exception {
    private String nameCompo;

    /**
     * Costruttore dell'eccezione.
     * @param name Il nome del componente duplicato.
     */
    public DuplicateComponentException(String name) {
        super("Il componente " + name + " è già inserito");
        nameCompo = name;
    }

    /**
     * Restituisce il nome del componente duplicato.
     * @return Il nome del componente.
     */
    public String getNameCompo() {
        return nameCompo;
    }

}