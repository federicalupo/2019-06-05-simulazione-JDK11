package it.polito.tdp.crimes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Distretto> distretti;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public List<Integer> anni(){
		return dao.anni();
	}
	
	public void creaGrafo(Integer anno){
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.distretti = new LinkedList<>(); //salva distretto + centro per quell'anno
		
		Graphs.addAllVertices(this.grafo, dao.vertici());
		
		//distretti+centri
		dao.listaDistretti(this.distretti, anno);
		
		
		
		for(Distretto d1 : this.distretti) {
			for(Distretto d2 : this.distretti) {
				if(d1.getId().compareTo(d2.getId())<0) {
					Double peso = LatLngTool.distance(d1.getCentro(), d2.getCentro(), LengthUnit.KILOMETER);
					Graphs.addEdge(this.grafo, d1.getId(), d2.getId(), peso);
				}
			}
		}
		
		
	}
	
	
	public List<Arco> distrettiAdiacenti(int distretto){
		
		List<Integer> adiacenti = Graphs.neighborListOf(this.grafo, distretto);
		
		List<Arco> distrettiA = new LinkedList<>();
		
		for(Integer i : adiacenti) {
			Arco a = new Arco(distretto, i, this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, i)));
			distrettiA.add(a);
		}
		Collections.sort(distrettiA);
		return distrettiA;

	}
	
	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
}
