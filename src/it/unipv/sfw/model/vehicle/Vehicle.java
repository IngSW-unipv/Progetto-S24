package it.unipv.sfw.model.vehicle;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.WrongReplacementStatusException;
import it.unipv.sfw.model.component.Components;

public class Vehicle {

	/*
	 * il tempo è considerato in millisecondi
	 */
	protected long timeSect1, timeSect2, timeSect3;

	protected String timeLap;

	private String MSN;
	// Set perchè non permetto di avere duplicati
	protected Set<Components> components;

	public Vehicle(String MSN) {
		this.MSN = MSN;
		components = new HashSet<>();
	}

	public int addComponent(Components cmp) throws WrongReplacementStatusException {

		int result = 0;

		if (cmp.getReplacementStatus() != "NEW" || cmp.getReplacementStatus() != "USED")
			throw new WrongReplacementStatusException(cmp.getReplacementStatus());

		calcWear(cmp);
		int cond = cmp.getWear();

		if (cond == 99 || cond >= 80) {
			System.out.println("Componente ottime condizioni");
			System.out.println("Componente inserito con successo");

			if(components.add(cmp))
				System.out.println("Componente già inserito");
			return result = 1;

		} else if (cond < 80 || cond >= 50) {
			System.out.println("Componente buone condizioni");
			System.out.println("Componente inserito con successo");

			components.add(cmp);

			return result = 2;

		} else {
			System.out.println("Componente usurato --> NON UTILIZZABILE DA SMONTARE");
			return 3;
		}

	}

	public void removeComponent(Components cmp) throws ComponentNotFoundException {

		for (Components comp : components) {
			if (comp.getName().equalsIgnoreCase(cmp.getName()))
				throw new ComponentNotFoundException(cmp.getName());

		}

		components.remove(cmp);

	}

	public int calcWear(Components c) {

		int wear = 0;

		return wear = c.calculateWear(c.getReplacementStatus());

	}

	public void getComponentByName(String name) throws ComponentNotFoundException {

		for (Components c : components) {
			if (c.getName().equalsIgnoreCase(name)) {
				// provvisorio
				System.out.println(c);
				return;
			}

		}
	}

	public String setTimeLap() {

		Random random = new Random();

		int min = 25000, max = 30000;

		long tmeLp = 0;

		timeSect1 = random.nextInt((min - max) + 1) + min;
		timeSect2 = random.nextInt((min - max) + 1) + min;
		timeSect3 = random.nextInt((min - max) + 1) + min;

		tmeLp = timeSect1 + timeSect2 + timeSect3;

		long minutes = tmeLp / 60000;

		long seconds = (tmeLp % 60000) / 1000;

		long milliseconds = tmeLp % 1000;

		return timeLap = minutes + ":" + seconds + ":" + milliseconds;
	}

	public String getTimeLap() {
		return timeLap;
	}

	public long getTimeSect1() {
		return timeSect1;
	}

	public long getTimeSect2() {
		return timeSect2;
	}

	public long getTimeSect3() {
		return timeSect3;
	}

	public Set<Components> getComponents() {
		return components;
	}

	public void setComponents(Set<Components> components) {
		this.components = components;
	}

}
