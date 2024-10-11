package it.unipv.sfw.dao;

public class DAOFactory {

	public enum DBType {
		MYSQL
	}
	
	private static DAOFactory instance = null;
	
	/**
	 * Metodo che crea un'istanza della classe DAOFactory se non è già stata creata,
	 * altrimenti lancia una RuntimeException.
	 *
	 * @param dbType Parametro che specifica il database a cui collegarsi.
	 */
	public static void createInstance (DBType  dbType) {
		if(instance != null)
			throw new RuntimeException("Instanza DAOFactory già creata.");
		
		instance  = new DAOFactory(dbType);
	}
	
	private DBType  dbType;

	private DAOFactory(DBType dbType) {
		this.dbType = dbType;
	}
}
