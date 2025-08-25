package it.unipv.sfw.dao.interfacedao;

public interface IUserDAO {
    /**
     * Restituisce i campi della riga utente come array:
     * [0]=ID, [1]=PASSWORD, [2]=ROLE, [3]=NAME, [4]=SURNAME
     * Ritorna null se l'ID non esiste.
     */
    String[] selectRowFieldsById(String id);
}
