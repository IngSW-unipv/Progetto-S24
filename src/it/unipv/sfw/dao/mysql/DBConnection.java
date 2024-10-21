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
//	private static void init() {
//		Properties p = new Properties(System.getProperties());
//		try {
//			System.out.println("problema qui - @DBCONNECTION");
//			p.load(DBConnection.class.getClassLoader().getResourceAsStream("properties.properties"));
//			dbDriver = p.getProperty(PROPERTYDBDRIVER);
//			dbURL = p.getProperty(PROPERTYDBURL);
//
//			isInit = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	private static void init() {
	    Properties p = new Properties();
	    
	    try {
	        // Debugging per verificare se la risorsa viene trovata
	        InputStream inStream = DBConnection.class.getClassLoader().getResourceAsStream("properties.properties");
	        if (inStream == null) {
	            System.out.println("Errore: Il file properties/properties.properties non è stato trovato!");
	        } else {
	            System.out.println("File trovato, caricamento in corso...");
	            p.load(inStream);
	        }

	        // Verifica se le proprietà sono caricate correttamente
	        dbDriver = p.getProperty(PROPERTYDBDRIVER);
	        dbURL = p.getProperty(PROPERTYDBURL);

	        if (dbDriver == null || dbURL == null) {
	            System.out.println("Errore: Le proprietà DBDRIVER o DBURL non sono state caricate correttamente!");
	        } else {
	            System.out.println("Proprietà caricate con successo!");
	        }

	        isInit = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	private Connection conn;

	/**
	 * Crea una nuova connessione al db.
	 *
	 * @param schema Stringa che rappresenta uno schema valido del database.
	 */
	public DBConnection(String schema) {
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
	public void close() throws Exception {
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
