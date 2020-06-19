package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Agente {

	private Integer id;
	private boolean libero;
	private Integer distretto;
	
	public Agente(Integer id, boolean libero,  Integer distretto) {
		super();
		this.id = id;
		this.libero = libero;
		this.distretto = distretto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}

	public Integer getDistretto() {
		return distretto;
	}

	public void setDistretto(Integer distretto) {
		this.distretto = distretto;
	}
	
	
	
}
