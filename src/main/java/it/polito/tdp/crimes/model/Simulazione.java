package it.polito.tdp.crimes.model;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Evento.EventType;

public class Simulazione {

	//mondo
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private Integer centrale;
	private List<Agente> agenti;
	private final Double VELOCITA =1.0;  //60km/h => 60/60 = 1km/min
	
	//input
	
	private Integer agentiDisponibili;
	
	
	//coda
	private PriorityQueue<Evento> coda;
	
	//output
	private Integer nMalGestiti;
	private Integer cont;
	
	
	
 
	public Integer getnMalGestiti() {
		return nMalGestiti;
	}
	public Integer getCont() {
		return cont;
	}
	
	public void init(Graph<Integer,DefaultWeightedEdge> grafo, PriorityQueue<Evento> coda, Integer agentiDisponibili, Integer distrettoC) {
		this.grafo =grafo;
		
		nMalGestiti=0;
		cont=0;
		
		this.agentiDisponibili = agentiDisponibili;
		this.centrale = distrettoC;
		
		this.coda = coda;
		agenti = new LinkedList<>();
		
		for(int i=0; i<this.agentiDisponibili;i++) {
			agenti.add(new Agente(i, true, centrale ));
		}
		
		
	}
	public void run() {
		
		while(!coda.isEmpty()) {
			processEvent(coda.poll());
		}
		
	}
	
	private void processEvent(Evento e) {
		
		switch(e.getTipo()) {
		
		case CRIMINE:
			
			Double min = 1000000000000.0; //alternativa?
			Double distanza;
			Agente provvisorio=null;
			
		
			for(Agente a: agenti) {
				if(a.isLibero()) {
					//controllo che sorgente != destinazione????
					if(a.getDistretto().compareTo(e.getDistretto())==0) {
						distanza=0.0;
					}else {
					  distanza=this.grafo.getEdgeWeight(this.grafo.getEdge(a.getDistretto(), e.getDistretto()));
					}
					
					if(distanza.compareTo(min)<0) {
						min=distanza;
						provvisorio=a;
						
					}
				}
			}
			
			if(provvisorio!=null) //perchè potrebbero essere tutti occupati
				{
					double tempo = min/VELOCITA; //minuti
					if(tempo > 15) {
						//mal gestito
						this.nMalGestiti++;
						provvisorio.setDistretto(e.getDistretto()); //agente cambia comunque distretto? direi di si dubbio
						
					}else {
						provvisorio.setLibero(false);
						provvisorio.setDistretto(e.getDistretto());
						//non serve impostare nell'evento corrente l'agente, bisogna impostarlo nell'evento agentelibero
						
						if(e.getOffense_category_id().compareTo("all_other_crimes")==0) { //cosa vuol dire uguale probabilità????
							
							if(Math.random()<0.5) {
								Evento temp = new Evento(e.getOffense_category_id(), e.getReported_date().plusHours(1), e.getDistretto(), EventType.AGENTELIBERO);
								temp.setAgente(provvisorio);
								this.coda.add(temp);
								
							}else {
								Evento temp = new Evento(e.getOffense_category_id(), e.getReported_date().plusHours(2), e.getDistretto(), EventType.AGENTELIBERO);
								temp.setAgente(provvisorio);
								this.coda.add(temp);
							}
							
							
						}else {
							Evento temp = new Evento(e.getOffense_category_id(), e.getReported_date().plusHours(2), e.getDistretto(), EventType.AGENTELIBERO);
							temp.setAgente(provvisorio);
							this.coda.add(temp);
						}
					}
				}
			
			if(provvisorio==null)
				cont++;
			//se tutti occupati cosa succede?
			
			
			break;
			
		case AGENTELIBERO:
			Agente a = e.getAgente();
			a.setLibero(true);
			
			
			
			break;
		
		
		
		}
		
	}
}
