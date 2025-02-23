package it.unipv.sfw.model.staff;

import it.unipv.sfw.dao.DAOFactory;
import it.unipv.sfw.dao.mysql.WarehousemanDAO;
import it.unipv.sfw.exceptions.AccountNotFoundException;
import it.unipv.sfw.exceptions.WrongPasswordException;
import it.unipv.sfw.model.vehicle.Vehicle;

/**
 * Classe che rappresenta una sessione utente.
 * Gestisce le informazioni relative all'utente loggato, come il tipo, le credenziali,
 * i dati personali e le relative istanze di {@link Mechanic}, {@link Warehouseman},
 * {@link Strategist} e {@link Vehicle}. Implementa il pattern Singleton.
 */
public class Session {

	private static Session istance = null;

	private String operation = "";
	private String strategy = "";
	private String id_pilot;
	private String id_staff;
	private String pwd_staff;
	private WarehousemanDAO mgd;

	private String name = "", surname = "";
	private Mechanic m;
	private Warehouseman wh;
	private Strategist s;
	private Vehicle v;

    /**
     * Restituisce l'istanza corrente della sessione. Se non esiste, ne crea una nuova.
     * @return L'istanza della sessione.
     */
	public static Session getIstance() {

		if (istance == null) {
			istance = new Session();
		}
		return istance;
	}

	private Staff currentUser;

	// Costruttore privato per impedire l'instanziazione diretta.
	private Session() {
		currentUser = null;
		mgd = new WarehousemanDAO();
		m = new Mechanic(id_staff, pwd_staff);
		v = new Vehicle(m.getMSN());
		wh = new Warehouseman(id_staff, pwd_staff);
		s = new Strategist(id_staff, pwd_staff);
	}

	/**
     * Effettua il login dell'utente.
     * @param id L'ID dell'utente.
     * @param pwd La password dell'utente (array di caratteri).
     * @throws WrongPasswordException Se la password è errata.
     * @throws AccountNotFoundException Se l'account non esiste.
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
			Mechanic mc = (Mechanic) user;
			this.setCurrentUser(user);

			break;

		case "STRATEGA":
			Strategist stg = (Strategist) user;
			this.setCurrentUser(user);

			break;

		case "MAGAZZINIERE":
			Warehouseman mg = (Warehouseman) user;
			this.setCurrentUser(user);

			break;

		}
	}

    /**
     * Restituisce l'utente corrente (loggato).
     * @return L'utente corrente.
     */
	public Staff getCurrentUser() {
		return currentUser;
	}
	
    /**
     * Imposta l'utente corrente (loggato).
     * @param user L'utente da impostare come corrente.
     */
	public void setCurrentUser(Staff user) {
		currentUser = user;
	}
	
    /**
     * Restituisce l'operazione corrente.
     * @return L'operazione corrente.
     */
	public String getOperation() {
		return operation;
	}
	
    /**
     * Imposta l'operazione corrente.
     * @param operation L'operazione da impostare.
     */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
    /**
     * Restituisce l'ID del pilota.
     * @return L'ID del pilota.
     */
	public String getId_pilot() {
		return id_pilot;
	}

	/**
     * Imposta l'ID del pilota.
     * @param id_pilot L'ID del pilota da impostare.
     */
	public void setId_pilot(String id_pilot) {
		this.id_pilot = id_pilot;
	}
	
    /**
     * Restituisce l'ID dello staff.
     * @return L'ID dello staff.
     */
	public String getId_staff() {
		return id_staff;
	}
	
    /**
     * Imposta l'ID dello staff.
     * @param id_staff L'ID dello staff da impostare.
     */
	public void setId_staff(String id_staff) {
		this.id_staff = id_staff;
	}
	
    /**
     * Restituisce la password dello staff.
     * @return La password dello staff.
     */
	public String getPwd_staff() {
		return pwd_staff;
	}
	
    /**
     * Imposta la password dello staff.
     * @param pwd_staff La password dello staff da impostare.
     */
	public void setPwd_staff(String pwd_staff) {
		this.pwd_staff = pwd_staff;
	}
	
    /**
     * Restituisce il nome dell'utente.
     * @return Il nome dell'utente.
     */
	public String getName() {
		return name;
	}
	
    /**
     * Imposta il nome dell'utente.
     * @param name Il nome dell'utente da impostare.
     */
	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * Restituisce il cognome dell'utente.
     * @return Le cognome dell'utente.
     */
	public String getSurname() {
		return surname;
	}
	
    /**
     * Imposta il cognome dell'utente.
     * @param surname Il cognome dell'utente da impostare.
     */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
    /**
     * Simula l'impostazione di 8 tempi di pit stop casuali per il meccanico.
     */
	public void getTps() {
		// inizializzo l'array
		int i = 0;

		for (i = 0; i < 8; i++)
			m.setTimePS();
		// il vettore ha 8 valori inseriti

	}
	
    /**
     * Recupera e imposta le richieste per il magazziniere.
     */
	public void getRequest() {
		wh.setRequest(mgd.selectAllRequest());
	}
	
    /**
     * Simula l'impostazione dei tempi di settore per il veicolo.
     */
	public void getTS() {
		v.setTimeSect();
	}
	
    /**
     * Restituisce l'istanza di {@link Vehicle}.
     * @return L'istanza di {@link Vehicle}.
     */
	public Vehicle getV() {
		return v;
	}
	
    /**
     * Imposta l'istanza di {@link Vehicle}.
     * @param v L'istanza di {@link Vehicle} da impostare.
     */
	public void setV(Vehicle v) {
		this.v = v;
	}
	
    /**
     * Restituisce l'istanza di {@link Mechanic}.
     * @return L'istanza di {@link Mechanic}.
     */
	public Mechanic getM() {
		return m;
	}
	
    /**
     * Imposta l'istanza di {@link Mechanic}.
     * @param m L'istanza di {@link Mechanic} da impostare.
     */
	public void setM(Mechanic m) {
		this.m = m;
	}
	
    /**
     * Restituisce l'istanza di {@link Warehouseman}.
     * @return L'istanza di {@link Warehouseman}.
     */
	public Warehouseman getWh() {
		return wh;
	}
	
    /**
     * Imposta l'istanza di {@link Warehouseman}.
     * @param wh L'istanza di {@link Warehouseman} da impostare.
     */
	public void setWh(Warehouseman wh) {
		this.wh = wh;
	}
	
    /**
     * Restituisce l'istanza di {@link Strategist}.
     * @return L'istanza di {@link Strategist}.
     */
	public Strategist getS() {
		return s;
	}
	
    /**
     * Imposta l'istanza di {@link Strategist}.
     * @param s L'istanza di {@link Strategist} da impostare.
     */
	public void setS(Strategist s) {
		this.s = s;
	}
	
    /**
     * Restituisce la strategia selezionata.
     * @return La strategia selezionata.
     */
	public String getStrategy() {
		return strategy;
	}
	
    /**
     * Imposta la strategia selezionata.
     * @param strategy La strategia da impostare.
     */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	

}
