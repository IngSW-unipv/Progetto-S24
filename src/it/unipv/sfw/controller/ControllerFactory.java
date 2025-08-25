package it.unipv.sfw.controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory centralizzata per creare istanze di {@link AbsController}
 * a partire dal loro {@link AbsController.TypeController}.
 * 
 * Implementata tramite mappa di {@link Supplier}, 
 * 
 */
public final class ControllerFactory {

    private static final Map<AbsController.TypeController, Supplier<? extends AbsController>> SUPPLIERS =
            new EnumMap<>(AbsController.TypeController.class);

    static {
        SUPPLIERS.put(AbsController.TypeController.LOGIN,       LoginController::new);
        SUPPLIERS.put(AbsController.TypeController.STRATEGIST,  StrategistController::new);
        SUPPLIERS.put(AbsController.TypeController.MECHANIC,    MechanicController::new);
        SUPPLIERS.put(AbsController.TypeController.WAREHOUSEMAN, WarehousemanController::new);
    }

    private ControllerFactory() { }

    /**
     * Crea una nuova istanza del controller indicato.
     *
     * @param type il tipo di controller da creare (non {@code null})
     * @return istanza concreta di {@link AbsController}
     * @throws IllegalArgumentException se il tipo non è supportato
     */
    public static AbsController createController(AbsController.TypeController type) {
        Supplier<? extends AbsController> s = SUPPLIERS.get(type);
        if (s == null) {
            throw new IllegalArgumentException("Tipo controller non supportato: " + type);
        }
        return s.get();
    }
}
