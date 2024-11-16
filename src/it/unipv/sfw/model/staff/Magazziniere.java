package it.unipv.sfw.model.staff;

import java.util.HashSet;
import java.util.Set;

import it.unipv.sfw.model.request.Request;

public class Magazziniere extends Staff{
	
	protected Set<Request> request = new HashSet<>();

	public Magazziniere(String id, String pwd) {
		super(id, pwd);
		
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
	
	public int totalRequest() {
		return request.size();
	}

}
