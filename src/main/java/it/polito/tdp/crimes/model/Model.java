package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.*;


public class Model {
	
	private EventsDao dao;
	private Graph<String,DefaultWeightedEdge> graph;
	private List<String> best;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public List<String> getOffensesId(){
		return dao.getOffensesId();
	}
	
	public List<Integer> getMonths(){
		return dao.getMonths();
	}

	public void createGraph(String offense, Integer month) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<String> vertici = dao.getVertexes(offense, month);
		//Graphs.addAllVertices(this.graph, vertici);
		List<Adiacenza> adiacenze = dao.getAdiacenza(offense, month);
		for(Adiacenza a : adiacenze) {
			if(!this.graph.containsVertex(a.getO1()))
				this.graph.addVertex(a.getO1());
			if(!this.graph.containsVertex(a.getO2()))
				this.graph.addVertex(a.getO2());
			if(!this.graph.containsEdge(a.getO1(), a.getO2()))
				Graphs.addEdgeWithVertices(this.graph, a.getO1(), a.getO2(), a.getWeight());
		}
		
		
	}
	
	public int numEdges() {
		return this.graph.edgeSet().size();
	}
	public int numVertexes() {
		return this.graph.vertexSet().size();
	}
	public List<Adiacenza> getAdiacenze(){
		double avg = this.calculateAvg();
		List<Adiacenza> lista = new ArrayList<>();
		for(DefaultWeightedEdge e : this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(e)>avg) {
				lista.add(new Adiacenza(this.graph.getEdgeSource(e), this.graph.getEdgeTarget(e),(int)this.graph.getEdgeWeight(e)));
			}
		}
		return lista;
		
	}
	public double calculateAvg() {
		double weight = 0.0;
		int n = 0;
		for(DefaultWeightedEdge e : this.graph.edgeSet()) {
			weight += this.graph.getEdgeWeight(e);
			n++;
		}
		return weight/n;
	}
	
	public List<String> findPath(String source, String target){
		List<String> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		parziale.add(source);
		recursive(parziale,target);
		return best;
	}

	private void recursive(List<String> parziale, String target) {
		//caso terminale
		if(parziale.get(parziale.size()-1).compareTo(target)==0) {
			if(parziale.size()>best.size()) {
				this.best = new ArrayList<>(parziale);
				return;
			}
			return;
		}
		//guardo i vicini dell'ultimo vertice
		for(String s : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				recursive(parziale,target);
				parziale.remove(parziale.size()-1);
			}
			
			
		}
		
	}
	
	
	
	
}
