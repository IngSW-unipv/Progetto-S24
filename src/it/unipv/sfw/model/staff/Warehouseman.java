package it.unipv.sfw.model.staff;

import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;

/**
 * Classe che rappresenta un magazziniere, un tipo di membro dello staff.
 * Estende la classe {@link Staff} e contiene informazioni sulle richieste
 * gestite dal magazziniere.
 */
public class Warehouseman extends Staff {

    private Set<Request> request = new HashSet<>();

    /**
     * Costruttore della classe Magazziniere.
     * @param id L'ID del magazziniere.
     * @param pwd La password del magazziniere.
     */
    public Warehouseman(String id, String pwd) {
        super(id, pwd);

    }

    /**
     * Restituisce il tipo di membro dello staff.
     * @return Il tipo di membro dello staff ({@link Staff.TypeController.MAGAZZINIERE}).
     */
    @Override
    public TypeController getType() {
        return Staff.TypeController.WAREHOUSEMAN;
    }

    /**
     * Restituisce l'insieme delle richieste gestite dal magazziniere.
     * @return L'insieme delle richieste.
     */
    public Set<Request> getRequest() {
        return request;
    }

    /**
     * Imposta l'insieme delle richieste gestite dal magazziniere.
     * @param request L'insieme delle richieste.
     */
    public void setRequest(Set<Request> request) {
        this.request = request;
    }

    /**
     * Restituisce il numero totale di richieste gestite dal magazziniere.
     * @return Il numero di richieste.
     */
    public int totalRequest() {
        return request.size();
    }

}