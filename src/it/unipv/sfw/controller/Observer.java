package it.unipv.sfw.controller;

/**
 * Interfaccia che definisce il contratto per un osservatore (Observer).
 * <p>
 * Gli oggetti che implementano questa interfaccia possono essere registrati
 * presso un oggetto osservabile (Observable) e verranno notificati
 * automaticamente quando lo stato dell'osservabile cambia.
 * </p>
 *
 * <p>
 * Questo pattern è utile per mantenere sincronizzate più componenti 
 * (es. viste o controller) rispetto a cambiamenti nello stato
 * dell'applicazione.
 * </p>
 *
 * @see Observable
 */
public interface Observer {

    /**
     * Metodo invocato dall'oggetto osservabile quando avviene un cambiamento
     * di stato rilevante.
     *
     * @param totalRequest numero totale aggiornato di richieste, fornito
     *                     dall'oggetto osservabile durante la notifica.
     */
    void update(int totalRequest);

}
