package it.unipv.sfw.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe rappresenta un soggetto osservabile (Observable).
 * Mantiene una lista di osservatori (Observer) e fornisce metodi per
 * aggiungere, rimuovere e notificare gli osservatori quando si verifica
 * un cambiamento nel suo stato.
 */
public class Observable {
    /**
     * Lista degli osservatori registrati con questo oggetto Observable.
     */
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Aggiunge un osservatore alla lista degli osservatori.
     *
     * @param observer L'osservatore da aggiungere.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Rimuove un osservatore dalla lista degli osservatori.
     *
     * @param observer L'osservatore da rimuovere.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica tutti gli osservatori registrati di un cambiamento.
     *
     * @param totalRequests Il numero totale di richieste, che viene
     *                      passato agli osservatori come informazione
     *                      sul cambiamento avvenuto.
     */
    public void notifyObservers(int totalRequests) {
        for (Observer observer : observers) {
            observer.update(totalRequests);
        }
    }
}