package it.unipv.sfw.model.staff;

/**
 * Classe di dominio che rappresenta lo <b>Strategist</b>,
 * un tipo specifico di membro dello staff.
 * <p>
 * Oltre ai dati ereditati da {@link Staff}, gestisce lo stato
 * relativo al tempo sul giro e ai minimi dei settori.
 * 
 * L’aggiornamento dei tempi avviene tramite un metodo atomico
 * che registra un nuovo giro aggiornando lo stato interno; i
 * valori aggiornati sono poi esposti tramite i relativi getter.
 * </p>
 *
 * @see Staff
 */
public class Strategist extends Staff {

    // Tempo dell'ultimo giro (ms)
    private int timeLap = 0;

    // Minimi di settore (ms) registrati finora; 0 indica "non ancora inizializzato"
    private int minT1 = 0;
    private int minT2 = 0;
    private int minT3 = 0;

    /**
     * Costruttore della classe {@code Strategist}.
     *
     * @param id  identificativo univoco dello strategist
     * @param pwd password associata all'account
     */
    public Strategist(String id, String pwd) {
        super(id, pwd);
    }

    /**
     * Restituisce il tipo di ruolo associato a questo membro dello staff.
     *
     * @return {@link Staff.TypeRole#STRATEGIST}
     */
    @Override
    public TypeRole getType() {
        return Staff.TypeRole.STRATEGIST;
    }

    /**
     * Restituisce il tempo dell'ultimo giro registrato.
     *
     * @return tempo sul giro in millisecondi
     */
    public int getTimeLap() {
        return timeLap;
    }

    /**
     * Imposta il tempo sul giro più recente.
     *
     * @param timeLap tempo sul giro in millisecondi da registrare
     */
    public void setTimeLap(int timeLap) {
        this.timeLap = timeLap;
    }

    /**
     * Restituisce il minimo corrente del settore 1 (ms).
     *
     * @return minimo settore 1, oppure 0 se non inizializzato
     */
    public int getMinT1() {
        return minT1;
    }

    /**
     * Restituisce il minimo corrente del settore 2 (ms).
     *
     * @return minimo settore 2, oppure 0 se non inizializzato
     */
    public int getMinT2() {
        return minT2;
    }

    /**
     * Restituisce il minimo corrente del settore 3 (ms).
     *
     * @return minimo settore 3, oppure 0 se non inizializzato
     */
    public int getMinT3() {
        return minT3;
    }

    /**
     * Registra in modo atomico i tempi dei tre settori per un nuovo giro,
     * aggiornando il tempo sul giro e i minimi dei settori.
     * <p>
     * Convenzioni:
     * <ul>
     *   <li>I parametri non devono essere negativi.</li>
     *   <li>I minimi sono inizializzati al primo giro (quando valgono 0).</li>
     * </ul>
     * </p>
     *
     * @param t1 tempo settore 1 (ms)
     * @param t2 tempo settore 2 (ms)
     * @param t3 tempo settore 3 (ms)
     * @throws IllegalArgumentException se uno dei tempi è negativo
     */
    public void registerLapTimes(int t1, int t2, int t3) {
        if (t1 < 0 || t2 < 0 || t3 < 0) {
            throw new IllegalArgumentException("I tempi di settore non possono essere negativi.");
        }

        // aggiorna tempo sul giro
        this.timeLap = t1 + t2 + t3;

        // inizializzazione dei minimi al primo giro
        if (minT1 == 0 && minT2 == 0 && minT3 == 0) {
            minT1 = t1; minT2 = t2; minT3 = t3;
        }

        // aggiornamento minimi
        minT1 = Math.min(minT1, t1);
        minT2 = Math.min(minT2, t2);
        minT3 = Math.min(minT3, t3);
    }
}
