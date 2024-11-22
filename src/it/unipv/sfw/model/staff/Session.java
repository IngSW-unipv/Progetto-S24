package it.unipv.sfw.model.staff;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.dao.mysql.MagazziniereDAO;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.vehicle.Vehicle;

public class Session {

	private static Session istance = null;

	private String operation = "";
	private String strategy = "";
	private String id_pilot;
	private String id_staff;
	private String pwd_staff;
	private MagazziniereDAO md;

	private String name = "", surname = "";
	private Meccanico m;
	private Magazziniere wh;
	private Stratega s;
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
		md = new MagazziniereDAO();
		m = new Meccanico(id_staff, pwd_staff);
		v = new Vehicle(m.getMSN());
		wh = new Magazziniere(id_staff, pwd_staff);
		s = new Stratega(id_staff, pwd_staff);
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

		id_staff = new String(id);
		pwd_staff = new String(pwd);

		Staff user = DAOFactory.createUserDAO().selectById(id);

		if (user == null)
			throw new AccountNotFoundException(id);

		if (!user.getPwd().equals(pwd_staff))
			throw new WrongPasswordException(pwd_staff);
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

	public String getId_pilot() {
		return id_pilot;
	}

	public void setId_pilot(String id_pilot) {
		this.id_pilot = id_pilot;
	}

	public String getId_staff() {
		return id_staff;
	}

	public void setId_staff(String id_staff) {
		this.id_staff = id_staff;
	}

	public String getPwd_staff() {
		return pwd_staff;
	}

	public void setPwd_staff(String pwd_staff) {
		this.pwd_staff = pwd_staff;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void getTps() {
		// inizializzo l'array
		int i = 0;

		for (i = 0; i < 8; i++)
			m.setTimePS();
		// il vettore ha 8 valori inseriti

	}
	
	public void getRequest() {
		wh.setRequest(md.selectAllRequest());
	}

	public void getTS() {
		v.setTimeSect();
	}
	
	public Vehicle getV() {
		return v;
	}

	public void setV(Vehicle v) {
		this.v = v;
	}

	public Meccanico getM() {
		return m;
	}

	public void setM(Meccanico m) {
		this.m = m;
	}

	public Magazziniere getWh() {
		return wh;
	}

	public void setWh(Magazziniere wh) {
		this.wh = wh;
	}

	public Stratega getS() {
		return s;
	}

	public void setS(Stratega s) {
		this.s = s;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	

}
