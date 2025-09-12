package it.unipv.sfw.model.staff;

import java.util.ArrayList;
import java.util.Random;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Classe che rappresenta un meccanico, un tipo di membro dello staff.
 * Estende la classe {@link Staff} e contiene informazioni sul veicolo
 * assegnato, i tempi di pit stop e le anomalie riscontrate.
 */
public class Mechanic extends Staff {

    private Vehicle vehicle;

    private ArrayList<Integer> allTimePitStop = new ArrayList<>();
    private ArrayList<Integer> anomalyTime = new ArrayList<>();

    /**
     * Costruttore della classe Meccanico.
     * @param id L'ID del meccanico.
     * @param pwd La password del meccanico.
     */
    public Mechanic(String id, String pwd) {
        super(id, pwd);
        
    }

    /**
     * Aggiunge un nuovo veicolo.
     * Sarà il veicolo attribuito al meccanico
     * @param v L'oggetto {@link Vehicle} da associare al meccanico
     */
    public  void addVehicle(Vehicle v) {
        setVehicle(v);

    }

    /**
     * Aggiunge un componente a un veicolo.
     * @param v L'oggetto {@link Vehicle} a cui aggiungere il componente.
     * @param c L'oggetto {@link Components} da aggiungere.
     * @return Un codice che indica l'esito dell'operazione.
     * @throws DuplicateComponentException Se si tenta di aggiungere un componente già presente.
     */
    public int addComponent(Vehicle v, Components c) throws DuplicateComponentException {

        int mode = 0;

        mode = v.addComponent(c);

        return mode;
    }

    /**
     * Rimuove un componente da un veicolo.
     * @param v L'oggetto {@link Vehicle} da cui rimuovere il componente.
     * @param c L'oggetto {@link Components} da rimuovere.
     * @throws ComponentNotFoundException Se si tenta di rimuovere un componente non presente.
     */
    public void removeComponent(Vehicle v, Components c) throws ComponentNotFoundException {

        v.removeComponent(c);

    }

    /**
     * Imposta un tempo di pit stop casuale.
     * Genera un tempo casuale tra 2000 e 4000 (millisecondi) e lo verifica.
     * @return Il tempo di pit stop generato.
     */
    public int setTimePS() {

        Random random = new Random();

        int min = 2000, max = 4000, tmePs = 0;

        tmePs = random.nextInt((max - min) + 1) + min;

        checkPS(tmePs);

        allTimePitStop.add(tmePs);

        return tmePs;

    }

    /**
     * Verifica il tempo di pit stop.
     * Se il tempo è inferiore a 3000ms, lo considera valido.
     * Altrimenti, lo considera un'anomalia.
     * @param timePS Il tempo di pit stop da verificare.
     */
    private void checkPS(int timePS) {

        if (timePS == 2000 || timePS < 3000) {
            System.out.println("Tempo pit stop valido");
        } else {
            anomalyTime.add(timePS);
            System.out.println("Tempo pit stop eccessivo --> verificata anomalia");
        }

    }

    /**
     * Converte una stringa di tempo (mm:ss.SSS) in secondi decimali.
     * @param tps La stringa di tempo da convertire.
     * @return Il tempo in secondi decimali.
     * @throws NumberFormatException Se la stringa di tempo non è nel formato corretto.
     */
    public double convertTime(String tps) throws NumberFormatException {

        String[] minSec = tps.split(":"), secMilli = tps.split("\\."); // Escaping "."

        int minutes = Integer.parseInt(minSec[0]);
        int seconds = Integer.parseInt(secMilli[0]);
        int milliseconds = Integer.parseInt(secMilli[1]);

        return (minutes * 60) + seconds + (milliseconds / 1000.0);

    }

    /**
     * Restituisce il tipo di membro dello staff.
     * @return Il tipo di membro dello staff ({@link Staff.TypeController.MECCANICO}).
     */
    @Override
    public TypeRole getType() {

        return Staff.TypeRole.MECHANIC;
    }

    /**
     * Restituisce il veicolo assegnato al meccanico.
     * @return Il veicolo assegnato.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Imposta il veicolo assegnato al meccanico.
     * @param vehicles Il veicolo da assegnare.
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Restituisce la lista di tutti i tempi di pit stop registrati.
     * @return La lista dei tempi di pit stop.
     */
    public ArrayList<Integer> getAllTimePitStop() {
        return allTimePitStop;
    }

    /**
     * Imposta la lista di tutti i tempi di pit stop registrati.
     * @param allTimePitStop La lista dei tempi di pit stop.
     */
    public void setAllTimePitStop(ArrayList<Integer> allTimePitStop) {
        this.allTimePitStop = allTimePitStop;
    }

    /**
     * Restituisce la lista dei tempi di pit stop considerati anomalie.
     * @return La lista dei tempi di pit stop anomali.
     */
    public ArrayList<Integer> getAnomalyTime() {
        return anomalyTime;
    }

    /**
     * Imposta la lista dei tempi di pit stop considerati anomalie.
     * @param anomalyTime La lista dei tempi di pit stop anomali.
     */
    public void setAnomalyTime(ArrayList<Integer> anomalyTime) {
        this.anomalyTime = anomalyTime;
    }

}