package it.unipv.sfw.exceptions;

public class ComponentNotFoundException  extends Exception {
	
	public ComponentNotFoundException(String name) {
			super("Componente non trovato, riprovare");
		}
	
}
