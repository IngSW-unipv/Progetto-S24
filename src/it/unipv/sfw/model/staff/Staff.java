package it.unipv.sfw.model.staff;

import java.util.function.Predicate;

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
