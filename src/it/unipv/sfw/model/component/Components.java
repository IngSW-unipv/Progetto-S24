package it.unipv.sfw.model.component;

import java.util.Random;

/**
 * Classe che rappresenta un componente di un veicolo.
 * Contiene informazioni sull'ID, il nome, lo stato di sostituzione e l'usura.
 */
public class Components {

    /*
     * Wear = percentuale di usura del componente
     * Replacement status = è stato sostituito o meno
     */

    private int idComponent;
    private String name;
    private String replacementStatus; // "NEW" o "USED"

    private int wear;

    /**
     * Costruttore della classe Components.
     * @param id_c L'ID del componente.
     * @param name Il nome del componente.
     */
    public Components(int id_c, String name) {
        this.idComponent = id_c;
        this.name = name;
        this.replacementStatus = "NEW"; // Imposta lo stato iniziale a "NEW"
        this.wear = 100; // Imposta l'usura iniziale a 100
    }

    /**
     * Calcola l'usura del componente.
     * Se lo stato è "NEW", l'usura è impostata a 100 e lo stato viene cambiato in "USED".
     * Altrimenti, viene generato un valore casuale tra 1 e 99.
     * @param replacementStatus Lo stato di sostituzione del componente ("NEW" o "USED").
     * @return La percentuale di usura del componente.
     */
    public int calculateWear(String replacementStatus) {

        Random random = new Random();

        int min = 1;
        int maxWear = 99; // Usato maxWear per chiarezza

        if (replacementStatus.equals("NEW")) {
            wear = 100;
            this.replacementStatus = "USED";

        } else {
            wear = random.nextInt((maxWear - min) + 1) + min;
        }

        return wear;
    }

    /**
     * Restituisce l'ID del componente.
     * @return L'ID del componente.
     */
    public int getIdComponent() {
        return idComponent;
    }

    /**
     * Imposta l'ID del componente.
     * @param idComponent L'ID del componente.
     */
    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    /**
     * Restituisce il nome del componente.
     * @return Il nome del componente.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del componente.
     * @param name Il nome del componente.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce lo stato di sostituzione del componente.
     * @return Lo stato di sostituzione del componente ("NEW" o "USED").
     */
    public String getReplacementStatus() {
        return replacementStatus;
    }

    /**
     * Imposta lo stato di sostituzione del componente.
     * @param replacementStatus Lo stato di sostituzione del componente ("NEW" o "USED").
     */
    public void setReplacementStatus(String replacementStatus) {
        this.replacementStatus = replacementStatus;
    }

    /**
     * Restituisce l'usura del componente.
     * @return La percentuale di usura del componente.
     */
    public int getWear() {
        return wear;
    }

    /**
     * Imposta l'usura del componente.
     * @param wear La percentuale di usura del componente.
     */
    public void setWear(int wear) {
        this.wear = wear;
    }

}