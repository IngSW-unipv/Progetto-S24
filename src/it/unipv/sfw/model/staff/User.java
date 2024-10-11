package it.unipv.sfw.model.staff;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import it.unipv.sfw.exceptions.EmptyFieldException;
import it.unipv.sfw.exceptions.WrongIDException;

public abstract class User {
	public enum Type{
		Magazziniere, Meccanico, Responsabile_Log, Stratega
	}
	
	protected String id,
							password;
	
	public User(String password) {
		this.password = password;
	}
	
	/**
	 * Controlla che l'user sia valido, altrimenti lancia un'eccezione.
	 *
	 * @throws WrongEmailFormatException
	 * @throws EmptyFieldException
	 */
	public void checkValidity() throws WrongIDException, EmptyFieldException{
		
		final Predicate<String> isValid = s -> {
			return s.chars().allMatch(c -> Character.isWhitespace(c)) && s.length() < 256;
		};

		// Controlla che non vi siano campi vuoti
		if ( !isValid.test(password))
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
	
	public abstract Type getType();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
