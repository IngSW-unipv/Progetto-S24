package it.unipv.sfw.dao.mysql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe che gestisce la connessione al database.
 * Implementa l'interfaccia {@link AutoCloseable} per garantire la chiusura
 * automatica della connessione.
 */
public class DBConnection implements AutoCloseable {
    // Costanti
    private static final String PROPERTYDBDRIVER = "DBDRIVER";
    private static final String PROPERTYDBURL = "DBURL";
    // Variabili statiche
    private static String dbDriver = null;
    private static String dbURL = null;
    private static boolean isInit = false;

    /**
     * Metodo statico interno che viene chiamato solo la prima volta
     * che viene creata un'istanza di DBConnection.
     * Carica le proprietà di connessione dal file properties.properties.
     * @throws RuntimeException Se il file properties.properties non viene trovato
     *                          o se le proprietà DBDRIVER o DBURL non sono
     *                          caricate correttamente.
     */
    private static void init() {
        Properties p = new Properties();

        try (InputStream inStream = DBConnection.class.getClassLoader()
                .getResourceAsStream("properties.properties")) {
            if (inStream == null) {
                throw new RuntimeException("Errore: Il file properties.properties non è stato trovato!");
            }

            p.load(inStream);

            dbDriver = p.getProperty(PROPERTYDBDRIVER);
            dbURL = p.getProperty(PROPERTYDBURL);

            if (dbDriver == null || dbURL == null) {
                throw new RuntimeException(
                        "Errore: Le proprietà DBDRIVER o DBURL non sono state caricate correttamente!");
            }

            isInit = true;
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'inizializzazione della connessione al database.", e);
        }
    }

    private Connection conn;

    /**
     * Costruttore che crea una nuova connessione al database.
     *
     * @param schema Stringa che rappresenta uno schema valido del database.
     * @throws SQLException Se si verifica un errore durante l'apertura della connessione.
     */
    public DBConnection(String schema) throws SQLException {
        if (!isInit)
            init();

        try {
            dbURL = String.format(dbURL, schema);

            // Apertura connessione
            conn = DriverManager.getConnection(dbURL);

            // Verifica se la connessione è avvenuta con successo
            if (conn != null && conn.isValid(2)) {
                System.out.println("Connessione al database valida e attiva!");
            } else {
                System.out.println("Connessione non valida o fallita.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chiude la connessione al database.
     *
     * @throws SQLException Se si verifica un errore durante la chiusura della connessione.
     */
    @Override
    public void close() throws SQLException {
        if (conn == null)
            return;

        conn.close();
        conn = null;
    }

    /**
     * Restituisce la connessione aperta al database.
     *
     * @return La connessione al database.
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Verifica se la connessione al database è aperta.
     *
     * @return `true` se la connessione è aperta, `false` altrimenti.
     */
    public boolean isOpen() {
        return !(conn == null);
    }
}