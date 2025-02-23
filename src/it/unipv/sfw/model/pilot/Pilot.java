package it.unipv.sfw.model.pilot;

/**
 * Classe che rappresenta un pilota.
 * Contiene informazioni sull'ID, il nome, il cognome e il numero.
 */
public class Pilot {

    private String id_pilot;
    private String name;
    private String surname;
    private int number;

    /**
     * Costruttore della classe Pilota.
     * @param id_pilot L'ID del pilota.
     * @param name Il nome del pilota.
     * @param surname Il cognome del pilota.
     * @param number Il numero del pilota.
     */
    public Pilot(String id_pilot, String name, String surname, int number) {
        this.id_pilot = id_pilot;
        this.name = name;
        this.surname = surname;
        this.number = number;

    }

    /**
     * Restituisce l'ID del pilota.
     * @return L'ID del pilota.
     */
    public String getId_pilot() {
        return id_pilot;
    }

    /**
     * Imposta l'ID del pilota.
     * @param id_pilot L'ID del pilota.
     */
    public void setId_pilot(String id_pilot) {
        this.id_pilot = id_pilot;
    }

    /**
     * Restituisce il nome del pilota.
     * @return Il nome del pilota.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del pilota.
     * @param name Il nome del pilota.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il cognome del pilota.
     * @return Il cognome del pilota.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Imposta il cognome del pilota.
     * @param surname Il cognome del pilota.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Restituisce il numero del pilota.
     * @return Il numero del pilota.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Imposta il numero del pilota.
     * @param number Il numero del pilota.
     */
    public void setNumber(int number) {
        this.number = number;
    }

}