package it.unipv.sfw.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa il ruolo di soggetto osservabile (Observable)
 * nel pattern Observer.
 * <p>
 * Mantiene una lista di {@link Observer} registrati e fornisce metodi per:
 * <ul>
 *   <li>Aggiungere un osservatore</li>
 *   <li>Rimuovere un osservatore</li>
 *   <li>Notificare tutti gli osservatori di un cambiamento di stato</li>
 * </ul>
 * </p>
 *
 * @see Observer
 */
public class Observable {

    /**
     * Lista degli osservatori registrati a questo oggetto Observable.
     */
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Registra un nuovo osservatore.
     *
     * @param observer l'osservatore da aggiungere alla lista
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore già presente.
     *
     * @param observer l'osservatore da rimuovere
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori registrati di un cambiamento.
     * <p>
     * Ogni osservatore riceve l'informazione del numero totale di richieste.
     * </p>
     *
     * @param totalRequests numero totale aggiornato di richieste da propagare
     *                      agli osservatori
     */
    public void notifyObservers(int totalRequests) {
        for (Observer observer : observers) {
            observer.update(totalRequests);
        }
    }
}
