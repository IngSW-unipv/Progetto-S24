package it.unipv.sfw.controller;

import it.unipv.sfw.model.staff.Staff;
import java.util.EnumMap;
import java.util.Map;

/**
 * Mapping centralizzato tra i ruoli applicativi ({@link Staff.TypeController})
 * e i tipi di controller UI ({@link AbsController.TypeController}).
 * Non crea istanze, restituisce solo il tipo da caricare.
 */
public final class ControllerRole {

    private static final Map<Staff.TypeRole, AbsController.TypeController> MAP =
            new EnumMap<>(Staff.TypeRole.class);

    static {
        MAP.put(Staff.TypeRole.MECHANIC,     AbsController.TypeController.MECHANIC);
        MAP.put(Staff.TypeRole.STRATEGIST,   AbsController.TypeController.STRATEGIST);
        MAP.put(Staff.TypeRole.WAREHOUSEMAN, AbsController.TypeController.WAREHOUSEMAN);
    }

    private ControllerRole() { }

    /**
     * Restituisce il tipo di controller associato al ruolo.
     * @param role ruolo autenticato (non null)
     * @return tipo controller da caricare
     * @throws IllegalArgumentException se il ruolo non è mappato
     */
    public static AbsController.TypeController toControllerType(Staff.TypeRole role) {
        AbsController.TypeController t = MAP.get(role);
        if (t == null) {
            throw new IllegalArgumentException("Ruolo non supportato: " + role);
        }
        return t;
    }

    /**
     * Tipo controller da usare quando NON c'è un utente autenticato.
     * @return {@link AbsController.TypeController#LOGIN}
     */
    public static AbsController.TypeController unauthenticated() {
        return AbsController.TypeController.LOGIN;
    }
}
