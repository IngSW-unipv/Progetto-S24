package it.unipv.sfw.dao.mysql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection implements AutoCloseable {
	// Constants
	private static final String PROPERTYDBDRIVER = "DBDRIVER";
	private static final String PROPERTYDBURL = "DBURL";
	// Statics
	private static String dbDriver  = null;
	private static String dbURL = null;
	private static boolean isInit = false;

	/**
	 * Funzione interna che viene chiamata solo la prima volta che viene creata una
	 * class DBConnection.
	 */

	private static void init() {
	    Properties p = new Properties();

	    try (InputStream inStream = DBConnection.class.getClassLoader().getResourceAsStream("properties.properties")) {
	        if (inStream == null) {
	            throw new RuntimeException("Errore: Il file properties.properties non è stato trovato!");
	        }

	        p.load(inStream);

	        dbDriver = p.getProperty(PROPERTYDBDRIVER);
	        dbURL = p.getProperty(PROPERTYDBURL);

	        if (dbDriver == null || dbURL == null) {
	            throw new RuntimeException("Errore: Le proprietà DBDRIVER o DBURL non sono state caricate correttamente!");
	        }

	        isInit = true;
	    } catch (Exception e) {
	        throw new RuntimeException("Errore durante l'inizializzazione della connessione al database.", e);
	    }
	}


	private Connection conn;

	/**
	 * Crea una nuova connessione al db.
	 *
	 * @param schema Stringa che rappresenta uno schema valido del database.
	 */
	public DBConnection(String schema) throws SQLException{
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

	@Override
	public void close() throws SQLException {
		if (conn == null)
			return;

		conn.close();
		conn = null;
	}

	/**
	 * @return Connesione aperta al db.
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * @return Stato della connessione.
	 */
	public boolean isOpen() {
		return !(conn == null);
	}
}
