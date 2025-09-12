package it.unipv.sfw.facade;

import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Factory concreta per creare istanze di {@link Vehicle}
 *
 */
public final class VehicleFactory {

    /**
     * Crea un nuovo {@link Vehicle} a partire dall'MSN fornito.
     * <ul>
     *   <li>Normalizza l'input</li>
     *   <li>Istanzia il {@code Vehicle}</li>
     * </ul>
     *
     * @param rawMsn MSN inserito (può contenere spazi/minuscole)
     * @return istanza di {@link Vehicle} con MSN normalizzato
     * @throws IllegalArgumentException se l'MSN è nullo, vuoto o non conforme al pattern
     */
    public Vehicle create(String rawMsn) {
        String msn = normalize(rawMsn);
        return new Vehicle(msn);
    }

    // --- helpers ---

    private static String normalize(String msn) {
        if (msn == null) throw new IllegalArgumentException("MSN nullo");
        String norm = msn.trim().toUpperCase();
        if (norm.isEmpty()) throw new IllegalArgumentException("MSN vuoto");
        return norm;
    }
}
