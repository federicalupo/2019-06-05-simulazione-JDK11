package it.polito.tdp.crimes.model;

public class Arco implements Comparable<Arco>{

	private Integer d1;
	private Integer d2;
	private Double distanza;
		
	public Arco(Integer d1, Integer d2, Double distanza) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.distanza = distanza;
	}

	


	public Integer getD1() {
		return d1;
	}


	public Integer getD2() {
		return d2;
	}


	public Double getDistanza() {
		return distanza;
	}


	@Override
	public int compareTo(Arco o) {
		return this.distanza.compareTo(o.getDistanza());
	}
	
	public String toString() {
		return this.d2+"  "+this.distanza;
	}
	
}
