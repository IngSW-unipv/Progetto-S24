package it.unipv.sfw.model.staff;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import it.unipv.sfw.exceptions.EmptyFieldException;
import it.unipv.sfw.exceptions.WrongIDException;

/**
 * Classe astratta che rappresenta un generico membro dello staff della scuderia
 *
 */

public abstract class Staff {
	
	public enum TypeController{
		
		STRATEGA, MECCANICO, MAGAZZINIERE;
		
	}
	
	private String id,
							pwd;
	

	public Staff(String id, String pwd ) {

		this.id = id;
		this.pwd = pwd;
	}
	
	/**
	 * Controlla che l'utente sia valido, altrimenti lancia un'eccezione.
	 *
	 * @throws EmptyFieldException
	 */
	
	public void checkValidity() throws  WrongIDException, EmptyFieldException {
		final Predicate<String> isValid = s -> {
			return s.chars().allMatch(c -> Character.isWhitespace(c)) && s.length() < 256;
		};

		// Controlla che non vi siano campi vuoti
		if (isValid.test(id) || isValid.test(pwd))
			throw new EmptyFieldException();

		// Controlla validità ID
		checkID(id);
	}
	
	/**
	 * Controlla che l'ID passato sia valido.
	 *
	 * @param ID
	 * @throws WrongIDException
	 */
	public static void checkID(String id) throws WrongIDException {
		
		/*
		 * ^\\d+$  è l'espressione regolare che verifica se la stringa contiene solo cifre numeriche
		 *  (\\d rappresenta qualsiasi cifra da 0 a 9, + indica che ci deve essere almeno una cifra).
		 */
		
		final Predicate<String> isID = Pattern.compile("^\\d+$").asPredicate();

		if (!isID.test(id)) {
			throw new WrongIDException();
		}
	}
	
	/**
	 * @return La password dell'utente.
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * @return La password dell'utente.
	 */
	public String getPwd() {
		return this.pwd;
	}

	/**
	 * @return Il tipo di utente
	 */
	public abstract TypeController getType();
}
