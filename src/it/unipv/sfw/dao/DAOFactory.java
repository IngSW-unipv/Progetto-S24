package it.unipv.sfw.dao;

import it.unipv.sfw.dao.mysql.UserDAO;

public class DAOFactory {

	public enum DbType {
		MYSQL
	}
	
	private static DAOFactory instance = null;
	
	/**
	 * Metodo che crea un'istanza della classe DAOFactory se non è già stata creata,
	 * altrimenti lancia una RuntimeException.
	 *
	 * @param dbType Parametro che specifica il database a cui collegarsi.
	 */
	public static void createInstance (DbType  dbType) {
		if(instance != null)
			throw new RuntimeException("Instanza DAOFactory già creata.");
		
		instance  = new DAOFactory(dbType);
	}
	
	/**
	 * Metodo che crea il DAO realitivo agli utenti.
	 *
	 * @throws Lancia una RuntimeException nel caso in cui l'istanza di DAOFactory
	 *                non sia stata inizializzata.
	 * @return Un'istanza di UtenteDAO.
	 */
	public static UserDAO createUserDAO() {
		switch (instance.dbType) {
		case MYSQL:
			return new UserDAO();
		}
		throw new RuntimeException("Instanza DAOFactory non inizializzata.");
	}
	
	private DbType  dbType;

	private DAOFactory(DbType dbType) {
		this.dbType = dbType;
	}
}
