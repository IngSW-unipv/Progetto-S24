package it.unipv.sfw.dao;

import it.unipv.sfw.dao.mysql.UserDAO;

/**
 * Questa classe utilizza il pattern Factory per fornire un'interfaccia
 * centralizzata per la creazione di istanze dei DAO, in base al tipo di
 * database specificato.
 */
public class DAOFactory {

    /**
     * Enumerazione che definisce i tipi di database supportati.
     */
    public enum DbType {
        MYSQL
    }

    private static DAOFactory instance = null;

    /**
     * Metodo statico per creare un'istanza della classe DAOFactory.
     * Se un'istanza è già stata creata, viene lanciata una RuntimeException.
     *
     * @param dbType Il tipo di database a cui connettersi.
     * @throws RuntimeException Se un'istanza di DAOFactory è già stata creata.
     */
    public static void createInstance(DbType dbType) {
        if (instance != null)
            throw new RuntimeException("Instanza DAOFactory già creata.");

        instance = new DAOFactory(dbType);
    }

    /**
     * Metodo statico per creare un'istanza del DAO relativo agli utenti.
     *
     * @return Un'istanza di {@link UserDAO}.
     * @throws RuntimeException Se l'istanza di DAOFactory non è stata inizializzata.
     */
    public static UserDAO createUserDAO() {
        if (instance == null) {
            throw new RuntimeException("Instanza DAOFactory non inizializzata.");
        }
        switch (instance.dbType) {
            case MYSQL:
                return new UserDAO();
            default:
                throw new RuntimeException("Tipo di database non supportato."); // Gestione di altri tipi di DB
        }
    }

    private DbType dbType;

    /**
     * Costruttore privato per impedire la creazione diretta di istanze.
     *
     * @param dbType Il tipo di database.
     */
    private DAOFactory(DbType dbType) {
        this.dbType = dbType;
    }
}