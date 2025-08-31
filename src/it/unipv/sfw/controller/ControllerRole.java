package it.unipv.sfw.controller;

import it.unipv.sfw.model.staff.Staff;
import java.util.EnumMap;
import java.util.Map;

/**
 * Classe di utilità che centralizza il mapping tra i ruoli applicativi
 * ({@link Staff.TypeRole}) e i corrispondenti tipi di controller UI
 * ({@link AbsController.TypeController}).
 * <p>
 * Non istanzia direttamente i controller, ma fornisce il tipo corretto
 * da caricare in base al ruolo utente autenticato.
 * </p>
 *
 * @see Staff.TypeRole
 * @see AbsController.TypeController
 */
public final class ControllerRole {

    /**
     * Mappa di corrispondenza ruolo -> tipo controller.
     */
    private static final Map<Staff.TypeRole, AbsController.TypeController> MAP =
            new EnumMap<>(Staff.TypeRole.class);

    static {
        MAP.put(Staff.TypeRole.MECHANIC,     AbsController.TypeController.MECHANIC);
        MAP.put(Staff.TypeRole.STRATEGIST,   AbsController.TypeController.STRATEGIST);
        MAP.put(Staff.TypeRole.WAREHOUSEMAN, AbsController.TypeController.WAREHOUSEMAN);
    }

    /**
     * Costruttore privato per impedire l’istanziazione della classe.
     * <p>
     * La classe espone solo metodi statici.
     * </p>
     */
    private ControllerRole() { }

    /**
     * Restituisce il tipo di controller UI associato al ruolo utente.
     *
     * @param role ruolo autenticato (non {@code null})
     * @return il {@link AbsController.TypeController} da caricare
     * @throws IllegalArgumentException se il ruolo non è mappato
     */
    public static AbsController.TypeController toControllerType(Staff.TypeRole role) {
        AbsController.TypeController tr= MAP.get(role);
        if (tr == null) {
            throw new IllegalArgumentException("Ruolo non supportato: " + role);
        }
        return tr;
    }

    /**
     * Restituisce il tipo di controller da usare quando non c’è
     * un utente autenticato.
     *
     * @return {@link AbsController.TypeController#LOGIN}
     */
    public static AbsController.TypeController unauthenticated() {
        return AbsController.TypeController.LOGIN;
    }
}
