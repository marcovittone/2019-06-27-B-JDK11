package it.polito.tdp.crimes.model;

import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	
	Graph<String,DefaultWeightedEdge> grafo;
	EventsDao dao;
	
	public Model(){
		
		 this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		 this.dao = new EventsDao();
		
	}
	
	
	public void creaGrafo(String categoria, int month) {
		
		
		Graphs.addAllVertices(this.grafo, this.dao.getTypeGivenMontAndCategory(categoria,month));
		 
		
		
		 
	}


	public List<String> getAllCategories() {

		return this.dao.getAllCategories();
	}


	public List<Integer> getAllMonths() {
		return this.dao.getAllMonths();
	}
	
}
