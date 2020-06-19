package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	public enum EventType{
		CRIMINE, AGENTELIBERO
		
	}
	
	private String offense_category_id;
	private LocalDateTime reported_date;
	private Integer distretto;
	private EventType tipo;
	private Agente agente;

	public Evento(String offense_category_id, LocalDateTime reported_date, Integer distretto, EventType tipo) {
		super();
		this.offense_category_id = offense_category_id;
		this.reported_date = reported_date;
		this.distretto = distretto;
		this.tipo = tipo;
	}


	public LocalDateTime getReported_date() {
		return reported_date;
	}


	
	public String getOffense_category_id() {
		return offense_category_id;
	}


	public Integer getDistretto() {
		return distretto;
	}


	public EventType getTipo() {
		return tipo;
	}

	

	public Agente getAgente() {
		return agente;
	}


	public void setAgente(Agente agente) {
		this.agente = agente;
	}


	@Override
	public int compareTo(Evento o) {
		return this.reported_date.compareTo(o.getReported_date());
	}
	
	
	
}
