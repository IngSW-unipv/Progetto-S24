package it.unipv.sfw.controller;

/**
 * Interfaccia che definisce il contratto per un osservatore (Observer).
 * Gli oggetti che implementano questa interfaccia possono essere registrati
 * con un oggetto osservabile (Observable) e saranno notificati quando
 * lo stato dell'oggetto osservabile cambia.
 */
public interface Observer {

    /**
     * Metodo chiamato quando l'oggetto osservabile notifica un cambiamento.
     *
     * @param totalRequest Il numero totale di richieste.  Questo valore
     *                     viene fornito dall'oggetto osservabile durante
     *                     la notifica.
     */
    void update(int totalRequest);

}