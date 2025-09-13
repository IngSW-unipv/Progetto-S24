package it.unipv.sfw.model.vehicle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;

/**
 * Classe che rappresenta un veicolo.
 * Contiene informazioni sui tempi di settore, il numero di serie (MSN)
 * e l'insieme dei componenti installati.
 */
public class Vehicle {

    /*
     * il tempo è considerato in millisecondi
     */
    private int timeSect1 = 0, timeSect2 = 0, timeSect3 = 0;

    private String timeLap; // Tempo sul giro

    private String MSN; // Numero di serie del veicolo

    // Insieme di componenti installati nel veicolo (Set per evitare duplicati)
    private Set<Components> component;

    /**
     * Costruttore della classe Vehicle.
     * @param MSN Il numero di serie del veicolo.
     */
    public Vehicle(String MSN) {
        this.MSN = MSN;
        component = new HashSet<>();
    }

    /**
     * Aggiunge un componente al veicolo.
     * @param cmp Il componente da aggiungere.
     * @return Un codice che indica l'esito dell'operazione:
     *         1 se il componente è in ottime condizioni ed è stato aggiunto,
     *         2 se il componente è in buone condizioni ed è stato aggiunto,
     *         3 se il componente è usurato e non è stato aggiunto.
     * @throws DuplicateComponentException Se si tenta di aggiungere un componente con lo stesso nome di uno già presente.
     */
    public int addComponent(Components cmp) throws DuplicateComponentException {
        int result = 0;

        // Controllo preliminare per nomi duplicati
        for (Components existingComponent : component) {
            if (existingComponent.getName().equals(cmp.getName())) {
                throw new DuplicateComponentException("Component with name '" + cmp.getName() + "' already exists.");
            }
        }

        // stringhe per debug
        System.out.println(component.toString());
        System.out.println("" + cmp.getIdComponent() + "- " + cmp.getName() + "- STATUS " + cmp.getReplacementStatus());

        cmp.setWear(cmp.calculateWear(MSN));

        System.out.println("wear =  " + cmp.getWear() + "@vehicle");

        int cond = cmp.getWear();

        if (cond >= 80) {
            component.add(cmp);
            System.out.println("Componente ottime condizioni");
            System.out.println("Componente inserito con successo");
            System.out.println(component.toString());
            result = 1;
        } else if (cond >= 50) {
            System.out.println("Componente buone condizioni");
            System.out.println("Componente inserito con successo");
            component.add(cmp);
            System.out.println(component.toString());
            result = 2;
        } else {
            System.out.println("Componente usurato --> NON UTILIZZABILE DA SMONTARE");
            result = 3;
        }

        return result;
    }

    /**
     * Rimuove un componente dal veicolo.
     * @param cmp Il componente da rimuovere.
     * @throws ComponentNotFoundException Se il componente da rimuovere non è presente nel veicolo.
     */
    public void removeComponent(Components cmp) throws ComponentNotFoundException {

        boolean found = false;
        Iterator<Components> iterator = component.iterator();

        while (iterator.hasNext()) {
            Components comp = iterator.next();
            if (comp.getName().equals(cmp.getName())) {
                iterator.remove(); // Rimuove l'elemento corrente
                found = true;
                break; // Interrompe la ricerca dopo la rimozione
            }
        }

        if (!found) {
            throw new ComponentNotFoundException(cmp.getName());
        }
    }

    /**
     * Restituisce un componente in base al nome.
     * @param name Il nome del componente da cercare.
     */
    public void getComponentByName(String name) {

        for (Components c : component) {
            if (c.getName().equalsIgnoreCase(name)) {

                System.out.println(c);
                return;
            }

        }
    }

    /**
     * Imposta i tempi di settore casualmente.
     */
    public void setTimeSect() {

        Random random = new Random();

        int min = 25000, max = 30000;

        timeSect1 = random.nextInt((max - min) + 1) + min;
        timeSect2 = random.nextInt((max - min) + 1) + min;
        timeSect3 = random.nextInt((max - min) + 1) + min;

    }
    
    /**
     * Controllo validità MSN
     */
    public boolean hasValidMsn() {
     return getMSN() != null && !getMSN().isBlank();
	}


    /**
     * Restituisce il numero di serie (MSN) del veicolo.
     * @return Il numero di serie del veicolo.
     */
    public String getMSN() {
        return MSN;
    }

    /**
     * Imposta il numero di serie (MSN) del veicolo.
     * @param mSN Il numero di serie del veicolo.
     */
    public void setMSN(String mSN) {
        MSN = mSN;
    }

    /**
     * Restituisce il tempo sul giro.
     * @return Il tempo sul giro.
     */
    public String getTimeLap() {
        return timeLap;
    }

    /**
     * Restituisce il tempo del primo settore.
     * @return Il tempo del primo settore.
     */
    public int getTimeSect1() {
        return timeSect1;
    }

    /**
     * Restituisce il tempo del secondo settore.
     * @return Il tempo del secondo settore.
     */
    public int getTimeSect2() {
        return timeSect2;
    }

    /**
     * Restituisce il tempo del terzo settore.
     * @return Il tempo del terzo settore.
     */
    public int getTimeSect3() {
        return timeSect3;
    }

    /**
     * Restituisce l'insieme dei componenti installati nel veicolo.
     * @return L'insieme dei componenti.
     */
    public Set<Components> getComponent() {
        return component;
    }

    /**
     * Imposta l'insieme dei componenti installati nel veicolo.
     * @param component L'insieme dei componenti.
     */
    public void setComponents(Set<Components> component) {
        this.component = component;
    }

}