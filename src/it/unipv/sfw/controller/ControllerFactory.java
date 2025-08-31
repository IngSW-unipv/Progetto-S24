package it.unipv.sfw.controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory centralizzata per la creazione di istanze di {@link AbsController}.
 * <p>
 * Utilizza una mappa di {@link Supplier} per associare ogni valore
 * dell'enumerazione {@link AbsController.TypeController} 
 * al rispettivo costruttore del controller concreto.
 * </p>
 * <p>
 * Questo approccio consente di isolare la logica di creazione dei controller
 * e di aggiungere facilmente nuovi tipi senza modificare il codice esistente
 * </p>
 *
 * @see AbsController
 * @see AbsController.TypeController
 */
public final class ControllerFactory {

    /**
     * Mappa che associa ciascun tipo di controller a un {@link Supplier}
     * responsabile della sua creazione.
     */
    private static final Map<AbsController.TypeController, Supplier<? extends AbsController>> SUPPLIERS =
            new EnumMap<>(AbsController.TypeController.class);

    static {
        SUPPLIERS.put(AbsController.TypeController.LOGIN,       LoginController::new);
        SUPPLIERS.put(AbsController.TypeController.STRATEGIST,  StrategistController::new);
        SUPPLIERS.put(AbsController.TypeController.MECHANIC,    MechanicController::new);
        SUPPLIERS.put(AbsController.TypeController.WAREHOUSEMAN, WarehousemanController::new);
    }

    /**
     * Costruttore privato per impedire l'istanziazione della classe,
     * in quanto fornisce solo metodi statici.
     */
    private ControllerFactory() { }

    /**
     * Crea una nuova istanza del controller corrispondente al tipo richiesto.
     *
     * @param type il tipo di controller da creare
     * @return una nuova istanza concreta di {@link AbsController}
     * @throws IllegalArgumentException se il tipo richiesto non è supportato
     */
    public static AbsController createController(AbsController.TypeController type) {
        Supplier<? extends AbsController> s = SUPPLIERS.get(type);
        if (s == null) {
            throw new IllegalArgumentException("Tipo controller non supportato: " + type);
        }
        return s.get();
    }
}
