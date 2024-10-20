package it.unipv.sfw.model.staff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.vehicle.Vehicle;

public class Session {

	private static Session istance = null;

	private String operation,
							msn;
	
	// settare id pilota da query -> meccanico controller -> meccanico view 
	private String id_pilot;
	
	private ArrayList<String> tps;
	private Set<Components> c;

	private Meccanico m;
	private Vehicle v;

	/**
	 * @return L'istanza corrente di {@link Sessione}, nel caso non esista la crea.
	 */
	public static Session getIstance() {

		if (istance == null) {
			istance = new Session();
		}
		return istance;
	}

	private Staff currentUser;

	// costruttore privato per evitare che vengano istanziati oggetti di tipo
	// session
	private Session() {
		currentUser = null;
		c = new HashSet<>();
		tps = new ArrayList<>();
		v = new Vehicle(msn);
	}

	/**
	 * Funzione che verifica nel database se l'utente specificato esiste
	 * controllandone anche la password.
	 * 
	 * @param ID  Identificativo dell'utente di cui si vuole eseguire il login.
	 * @param PWD Password dell'utente di cui si vuole eseguire il login.
	 * 
	 * @throws WrongPasswordException   Lanciata nel caso in cui la password sia
	 *                                  errata.
	 * @throws AccountNotFoundException Lanciata nel caso in cui l'account non
	 *                                  esista.
	 */

	public void login(String id, char[] pwd) throws AccountNotFoundException, WrongPasswordException {

		String strPwd = new String(pwd);
		
		Staff user = DAOFactory.createUserDAO().selectById(id);

		if (user == null)
			throw new AccountNotFoundException(id);

		if (!user.getPwd().equals(strPwd))
			throw new WrongPasswordException(strPwd);
		System.out.println(user.getType());

		// SWITCH
		switch ("" + user.getType()) {

		case "MECCANICO":
			Meccanico mc = (Meccanico) user;
			this.setCurrentUser(user);

			break;

		case "STRATEGA":
			Stratega stg = (Stratega) user;
			this.setCurrentUser(user);

			break;

		case "MAGAZZINIERE":
			Magazziniere mg = (Magazziniere) user;
			
			this.setCurrentUser(user);

			break;

		}
	}

	/**
	 * @return {@link Utente} della sessione corrente.
	 */
	public Staff getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Staff user) {
		currentUser = user;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getId_pilot() {
		return id_pilot;
	}

	public void setId_pilot(String id_pilot) {
		this.id_pilot = id_pilot;
	}

	public ArrayList<String> getTps() {
		// inizializzo l'array
		int i = 0;

		for (i = 0; i < 8; i++)
			tps.add(m.setTimePS());

		// il vettore tps ha 8 valori inseriti
		return tps;
	}

	public Set<Components> getC() {
		return c;
	}

	public void setC() {
		// TODO Auto-generated method stub
		c.addAll(v.getComponents());
	}

}
