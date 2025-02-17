package it.unipv.sfw.model.pilot;

public class Pilota {
	
	private String id_pilot,
						    name,
							surname;
	private int number;
	
	public Pilota(String id_pilot, String name, String surname, int number) {
		this.id_pilot = id_pilot;
		this.name = name;
		this.surname = surname;
		this.number = number;
		
	}

	public String getId_pilot() {
		return id_pilot;
	}

	public void setId_pilot(String id_pilot) {
		this.id_pilot = id_pilot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
