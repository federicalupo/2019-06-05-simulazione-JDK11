package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Distretto {
	private Integer id;
	private LatLng centro;
	
	public Distretto(Integer id, LatLng centro) {
		super();
		this.id = id;
		this.centro = centro;
	}

	public LatLng getCentro() {
		return centro;
	}

	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	public Integer getId() {
		return id;
	}
	
	
	

}
