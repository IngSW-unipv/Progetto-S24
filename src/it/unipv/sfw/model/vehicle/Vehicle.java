package it.unipv.sfw.model.vehicle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.WrongReplacementStatusException;
import it.unipv.sfw.model.component.Components;

public class Vehicle {

	/*
	 * il tempo è considerato in millisecondi
	 */
	protected int timeSect1 = 0, timeSect2 = 0, timeSect3 = 0;

	protected String timeLap;

	private String MSN;

	// Set perchè non permetto di avere duplicati
	protected Set<Components> component;

	public Vehicle(String MSN) {
		this.MSN = MSN;
		component = new HashSet<>();
	}

	public int addComponent(Components cmp) throws WrongReplacementStatusException {

		int result = 0;

		// stringhe per debug
		System.out.println(component.toString());
		System.out.println("" + cmp.getIdComponent() + "- " + cmp.getName() + "- STATUS " + cmp.getReplacementStatus());

		if (!cmp.getReplacementStatus().equals("NEW") && !cmp.getReplacementStatus().equals("USED"))
			throw new WrongReplacementStatusException(cmp.getReplacementStatus());

		cmp.setWear(calcWear(cmp));
		System.out.println("wear =  " + cmp.getWear() + "@vehicle");

		int cond = cmp.getWear();

		if (cond == 100 || cond >= 80) {

			component.add(cmp);
			System.out.println("Componente ottime condizioni");
			System.out.println("Componente inserito con successo");

			System.out.println(component.toString());

			result = 1;

			return result;

		} else if (cond > 80 || cond >= 50) {
			System.out.println("Componente buone condizioni");
			System.out.println("Componente inserito con successo");

			component.add(cmp);
			System.out.println(component.toString());

			result = 2;

			return result;

		} else {
			System.out.println("Componente usurato --> NON UTILIZZABILE DA SMONTARE");
			return 3;
		}

	}

	public void removeComponent(Components cmp) throws ComponentNotFoundException {
	    System.out.println("SONO QUI - @VEHICLE");

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


	public int calcWear(Components c) {

		int wear = 0;

		return wear = c.calculateWear(c.getReplacementStatus());

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

	public void showComponent() {
		for(Components c : component) {
			
			System.out. println(" ID: "+c.getIdComponent()+ " NAME: "+ c.getName()+ " Replacement: "+c.getReplacementStatus()+" WEAR : "+c.getWear());
			 
		}
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
