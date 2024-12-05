package it.unipv.sfw.controller;

public class ControllerFactory {

    /**
     * Factory method per creare il controller corretto in base al tipo specificato.
     *
     * @param type Il tipo di controller da creare.
     * @return Un'istanza di AbsController.
     * @throws IllegalArgumentException se il tipo di controller non è valido.
     */
    public static AbsController createController(AbsController.TypeController type) {
        switch (type) {
            case LOGIN:
                return new LoginController();
            case STRATEGA:
                return new StrategistController();
            case MECCANICO:
                return new MeccanicoController();
            case MAGAZZINIERE:
                return new MagazziniereController();
            default:
                throw new IllegalArgumentException("Tipo di controller sconosciuto: " + type);
        }
    }
}
