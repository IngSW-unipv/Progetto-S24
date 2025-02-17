package it.unipv.sfw.model.vehicle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.DuplicateComponentException;
import it.unipv.sfw.model.component.Components;

public class Vehicle {

	/*
	 * il tempo è considerato in millisecondi
	 */
	private int timeSect1 = 0, timeSect2 = 0, timeSect3 = 0;

	private String timeLap;

	private String MSN;

	// Set perchè non permetto di avere duplicati
	private Set<Components> component;

	public Vehicle(String MSN) {
		this.MSN = MSN;
		component = new HashSet<>();
	} 

	public int addComponent(Components cmp) throws DuplicateComponentException {
	    int result = 0;

	    // Controllo preliminare per nomi duplicati
	    for (Components existingComponent : component) {
	        if (existingComponent.getName().equals(cmp.getName())) {
	            throw new DuplicateComponentException("Component with name '" + cmp.getName() + "' already exists.");
	        }
	    }

	    // stringhe per debug
	    System.out.println(component.toString());
	    System.out.println("" + cmp.getIdComponent() + "- " + cmp.getName() + "- STATUS " + cmp.getReplacementStatus());

	    cmp.setWear(cmp.calculateWear(MSN));

	    System.out.println("wear =  " + cmp.getWear() + "@vehicle");

	    int cond = cmp.getWear();

	    if (cond == 100 || cond >= 80) {
	        component.add(cmp);
	        System.out.println("Componente ottime condizioni");
	        System.out.println("Componente inserito con successo");
	        System.out.println(component.toString());
	        result = 1;
	    } else if (cond > 80 || cond >= 50) {
	        System.out.println("Componente buone condizioni");
	        System.out.println("Componente inserito con successo");
	        component.add(cmp);
	        System.out.println(component.toString());
	        result = 2;
	    } else {
	        System.out.println("Componente usurato --> NON UTILIZZABILE DA SMONTARE");
	        result = 3;
	    }

	    return result;
	}


	public void removeComponent(Components cmp) throws ComponentNotFoundException {

	    boolean found = false;
	    Iterator<Components> iterator = component.iterator();

	    while (iterator.hasNext()) {
	        Components comp = iterator.next();
	        if (comp.getName().equals(cmp.getName())) {
	            iterator.remove(); // Rimuove l'elemento corrente
	            found = true;
	            break; // Interrompe la ricerca dopo la rimozione
	        }
	    }

	    if (!found) {
	        throw new ComponentNotFoundException(cmp.getName());
	    }
	}

	public void getComponentByName(String name){

		for (Components c : component) {
			if (c.getName().equalsIgnoreCase(name)) {
				// provvisorio
				System.out.println(c);
				return;
			}

		}
	}

	public void setTimeSect() {

		Random random = new Random();

		int min = 25000, max = 30000;

		timeSect1 = random.nextInt((max - min) + 1) + min;
		timeSect2 = random.nextInt((max - min) + 1) + min;
		timeSect3 = random.nextInt((max - min) + 1) + min;

	}

	public String getMSN() {
		return MSN;
	}

	public void setMSN(String mSN) {
		MSN = mSN;
	}

	public String getTimeLap() {
		return timeLap;
	}

	public int getTimeSect1() {
		return timeSect1;
	}

	public int getTimeSect2() {
		return timeSect2;
	}

	public int getTimeSect3() {
		return timeSect3;
	}

	public Set<Components> getComponent() {
		return component;
	}

	public void setComponents(Set<Components> component) {
		this.component = component;
	}

}
