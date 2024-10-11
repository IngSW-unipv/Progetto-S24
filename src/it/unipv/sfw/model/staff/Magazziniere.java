package it.unipv.sfw.model.staff;

import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;

public class Magazziniere extends Staff{
	
	protected Set<Request> request;

	public Magazziniere(String name, String surname, String id, String pwd) {
		super(name, surname, id, pwd);
		request = new HashSet<>();
	}

	@Override
	public TypeController getType() {
		return Staff.TypeController.MAGAZZINIERE;
	}

	public Set<Request> getRequest() {
		return request;
	}

	public void setRequest(Set<Request> request) {
		this.request = request;
	}

}
