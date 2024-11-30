package it.unipv.sfw.exceptions;

public class ComponentNotFoundException  extends Exception {
	String nameCompo;
	
	public ComponentNotFoundException(String name) {
			super("Componente "+name+ "non trovato, riprovare");
			nameCompo = name;
		}
	
	/**
	 * @return id identificativo del pilota richiesto ma inesistente.
	 */
	public String getNameCompo() {
		return nameCompo;
	}
	
}
