package it.unipv.sfw.dao;

import it.unipv.sfw.dao.interfacedao.IMechanicDAO;
import it.unipv.sfw.dao.interfacedao.IStrategistDAO;
import it.unipv.sfw.dao.interfacedao.IUserDAO;
import it.unipv.sfw.dao.interfacedao.IVehicleDAO;
import it.unipv.sfw.dao.interfacedao.IWarehousemanDAO;
import it.unipv.sfw.dao.mysql.MechanicDAO;
import it.unipv.sfw.dao.mysql.StrategistDAO;
import it.unipv.sfw.dao.mysql.UserDAO;
import it.unipv.sfw.dao.mysql.VehicleDAO;
import it.unipv.sfw.dao.mysql.WarehousemanDAO;

/**
 * Factory centralizzata per la creazione delle istanze DAO.
 * <p>
 * Implementa il pattern Singleton: esiste una sola istanza di {@code DAOFactory}
 * per l'intera applicazione. A seconda del tipo di database specificato,
 * fornisce i DAO corrispondenti.
 * </p>
 */
public final class DAOFactory {

    /**
     * Tipi di database supportati.
     */
    public enum DbType {
        MYSQL
    }

    // --- Singleton ---
    private static DAOFactory instance = null;

    private final DbType dbType;

    /**
     * Crea l'istanza singleton di {@code DAOFactory}.
     * <p>
     * Deve essere invocato una sola volta all'avvio dell'applicazione; ulteriori
     * chiamate produrranno una {@link RuntimeException}.
     * </p>
     *
     * @param dbType tipo di database da utilizzare
     * @throws RuntimeException se l'istanza è già stata creata
     */
    public static void createInstance(DbType dbType) {
        if (instance != null) {
            throw new RuntimeException("Istanza DAOFactory già creata.");
        }
        instance = new DAOFactory(dbType);
    }

    /**
     * Restituisce l'istanza singleton di {@code DAOFactory}.
     *
     * @return istanza già inizializzata
     * @throws RuntimeException se {@link #createInstance(DbType)} non è mai stato chiamato
     */
    public static DAOFactory getInstance() {
        if (instance == null) {
            throw new RuntimeException("Istanza DAOFactory non inizializzata.");
        }
        return instance;
    }
    
    /**
     * Restituisce un DAO per la gestione degli utenti.
     *
     * @return implementazione di {@link IUserDAO}
     * @throws RuntimeException se il tipo di database non è supportato
     */
    public IUserDAO createUserDAO() {
        return switch (dbType) {
            case MYSQL -> new UserDAO();
        };
    }
    
    /**
     * Restituisce un DAO per la gestione dei meccanici.
     *
     * @return implementazione di {@link IMechanicDAO}
     * @throws RuntimeException se il tipo di database non è supportato
     */
    public IMechanicDAO createMechanicDAO() {
        return switch (dbType) {
            case MYSQL -> new MechanicDAO();
        };
    }

    /**
     * Restituisce un DAO per la gestione degli strateghi.
     *
     * @return implementazione di {@link IStrategistDAO}
     * @throws RuntimeException se il tipo di database non è supportato
     */
    public IStrategistDAO createStrategistDAO() {
        return switch (dbType) {
            case MYSQL -> new StrategistDAO();
        };
    }

    /**
     * Restituisce un DAO per la gestione dei veicoli.
     *
     * @return implementazione di {@link IVehicleDAO}
     * @throws RuntimeException se il tipo di database non è supportato
     */
    public IVehicleDAO createVehicleDAO() {
        return switch (dbType) {
            case MYSQL -> new VehicleDAO();
        };
    }

    /**
     * Restituisce un DAO per la gestione dei magazzinieri.
     *
     * @return implementazione di {@link IWarehousemanDAO}
     * @throws RuntimeException se il tipo di database non è supportato
     */
    public IWarehousemanDAO createWarehousemanDAO() {
        return switch (dbType) {
            case MYSQL -> new WarehousemanDAO();
        };
    }


    // --- Costruttore privato ---
    /**
     * Costruttore privato: impedisce la creazione diretta di istanze
     * dall'esterno della classe.
     *
     * @param dbType tipo di database selezionato
     */
    private DAOFactory(DbType dbType) {
        this.dbType = dbType;
    }
}
