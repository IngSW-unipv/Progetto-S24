package it.unipv.sfw.model.staff;

/**
 * Classe di dominio che rappresenta lo <b>Strategist</b>,
 * un tipo specifico di membro dello staff.
 * <p>
 * Oltre ai dati ereditati da {@link Staff}, gestisce lo stato
 * relativo ai tempi sul giro e ai minimi dei settori, offrendo
 * un metodo di aggiornamento atomico per registrare un nuovo giro
 * e ottenere un riepilogo dei valori aggiornati.
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
     * <p>
     * Viene restituito da {@link #registerLapTimes(int, int, int)} per permettere
     * al Controller/View di leggere i valori necessari alla presentazione
     * senza dover accedere direttamente allo stato interno.
     * </p>
     */
    public static final class LapStats {
        private final int t1;
        private final int t2;
        private final int t3;
        private final int lap;
        private final int minT1;
        private final int minT2;
        private final int minT3;

        /**
         * Costruisce un riepilogo dei tempi.
         *
         * @param t1    tempo settore 1 (ms)
         * @param t2    tempo settore 2 (ms)
         * @param t3    tempo settore 3 (ms)
         * @param lap   tempo sul giro (ms)
         * @param minT1 minimo settore 1 aggiornato (ms)
         * @param minT2 minimo settore 2 aggiornato (ms)
         * @param minT3 minimo settore 3 aggiornato (ms)
         */
        public LapStats(int t1, int t2, int t3, int lap, int minT1, int minT2, int minT3) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.lap = lap;
            this.minT1 = minT1;
            this.minT2 = minT2;
            this.minT3 = minT3;
        }

        /** @return tempo settore 1 (ms) */
        public int getT1() { return t1; }
        /** @return tempo settore 2 (ms) */
        public int getT2() { return t2; }
        /** @return tempo settore 3 (ms) */
        public int getT3() { return t3; }
        /** @return tempo sul giro (ms) */
        public int getLap() { return lap; }
        
        /** @return minimo settore 1 aggiornato (ms) */
        public int getMinT1() { return minT1; }
        /** @return minimo settore 2 aggiornato (ms) */
        public int getMinT2() { return minT2; }
        /** @return minimo settore 3 aggiornato (ms) */
        public int getMinT3() { return minT3; }
    }

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
     * aggiorna il tempo sul giro e i minimi dei settori, restituendo
     * un riepilogo pronto all'uso per la presentazione.
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
     * @return {@link LapStats} con tempi del giro e minimi aggiornati
     * @throws IllegalArgumentException se uno dei tempi è negativo
     */
    public LapStats registerLapTimes(int t1, int t2, int t3) {
        if (t1 < 0 || t2 < 0 || t3 < 0) {
            throw new IllegalArgumentException("I tempi di settore non possono essere negativi.");
        }

        int lap = t1 + t2 + t3;
        this.timeLap = lap;

        // inizializzazione dei minimi al primo giro
        if (minT1 == 0 && minT2 == 0 && minT3 == 0) {
            minT1 = t1; minT2 = t2; minT3 = t3;
        }

        // aggiornamento minimi
        minT1 = Math.min(minT1, t1);
        minT2 = Math.min(minT2, t2);
        minT3 = Math.min(minT3, t3);

        return new LapStats(t1, t2, t3, lap, minT1, minT2, minT3);
    }
}
