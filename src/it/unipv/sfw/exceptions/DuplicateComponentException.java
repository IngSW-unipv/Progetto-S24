package it.unipv.sfw.exceptions;

public class DuplicateComponentException extends Exception{
	private String nameCompo;

	/**
	 * @param id Identificativo del pilota richiesto ma inesistente.
	 */
	public DuplicateComponentException(String name) {
		super("Il componente " + name+ " è già inserito");
		nameCompo = name;
	}

	/**
	 * @return id identificativo del pilota richiesto ma inesistente.
	 */
	public String getNameCompo() {
		return nameCompo;
	}

}
