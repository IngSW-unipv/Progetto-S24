package it.unipv.sfw.model.component;

import java.util.Random;

public class Components {
	
	/*
	 * Wear = percentuale di usura del componente
	 * Replacement status = è stato sostituito o meno
	 */
	
	protected int idComponent;
	protected String name,
								  replacementStatus;
	
	protected int wear;
	
	public Components(int id_c,String name) {
		this.idComponent = id_c;
		this.name = name;
		
	}
	
	public int calculateWear(String replacementStatus){
			
		Random random = new Random();
		
		int min = 1,
			 wear = 99;
		
		if(replacementStatus.equals("NEW") ){
			wear = 100;
			this.replacementStatus = "USED";
			
		}else{
			wear =  random.nextInt((wear - min) + 1 ) + min;	

		}
		
		return wear;
	}
	
	public int getIdComponent() {
		return idComponent;
	}

	public void setIdComponent(int idComponent) {
		this.idComponent = idComponent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReplacementStatus() {
		return replacementStatus;
	}

	public void setReplacementStatus(String replacementStatus) {
		this.replacementStatus = replacementStatus;
	}

	public int getWear() {
		return wear;
	}

	public void setWear(int wear) {
		this.wear = wear;
	}

}
