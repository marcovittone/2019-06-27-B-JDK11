package it.polito.tdp.crimes.model;

import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

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
	
	
	public List<Connessione> creaGrafo(String categoria, int month) {
		
		
		Graphs.addAllVertices(this.grafo, this.dao.getTypeGivenMontAndCategory(categoria,month));
		
		
		List<Connessione> connessioni = this.dao.getEdges(categoria, month);
		
		double media=0;
		
		for(Connessione c: connessioni)
			{
				Graphs.addEdge(this.grafo, c.getA(), c.getB(), c.getPeso());
				media+= c.getPeso();
			}
		
		media = media/connessioni.size();
		
		List<Connessione> risultato = new ArrayList<Connessione>();
		
		
		for(Connessione c: connessioni)
		{
			if(c.getPeso()> media)
				risultato.add(c);
				
		}
		
		System.out.println("GRAFO CRATO CON "+this.grafo.vertexSet().size()+" VERTICI");
		System.out.println("GRAFO CRATO CON "+this.grafo.edgeSet().size()+" ARCHI");
		
		return risultato;
		
		
		
		
	}


	public List<String> getAllCategories() {

		return this.dao.getAllCategories();
	}


	public List<Integer> getAllMonths() {
		return this.dao.getAllMonths();
	}
	
}
