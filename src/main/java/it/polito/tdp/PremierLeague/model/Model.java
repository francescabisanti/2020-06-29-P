package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	SimpleWeightedGraph <Match, DefaultWeightedEdge> grafo;
	
	PremierLeagueDAO dao;
	Map <Integer, Match> idMap;
	List<Match> migliore;
	double pesoMax;
	public Model() {
		dao= new PremierLeagueDAO();
		idMap= new HashMap <Integer, Match>();
		dao.listAllMatches(idMap);
		
	}
	
	public void creaGrafo (int minuti, int mese) {
		grafo= new SimpleWeightedGraph <Match, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, mese));
		for(Adiacenza a :dao.getAdiacenze(idMap, mese, minuti)) {
			if(grafo.containsVertex(a.getM1())&& grafo.containsVertex(a.getM2()))
				Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
		
	}
	
	public List<Adiacenza>getAdiacenza (int minuti, int mese) {
		List <Adiacenza>result=new ArrayList <Adiacenza>();
		
		double pesoMax=0;
		for(Adiacenza aa: dao.getAdiacenze(idMap, mese, minuti)) {
			if(aa.getPeso()>pesoMax)
				pesoMax=aa.getPeso();
		}
		for(Adiacenza a: dao.getAdiacenze(idMap, mese, minuti)) {
			if(a.getPeso()==pesoMax)
				result.add(a);
		}
		
		return result;
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}

	public SimpleWeightedGraph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Match> trovaPercorso( Match m1, Match m2){
		this.migliore= new ArrayList <Match>();
		this.pesoMax=0.0;
		List <Match> parziale= new ArrayList<>();
		parziale.add(m1);
		cerca(m2, parziale);
		return migliore;
	}

	private void cerca(Match m2, List<Match> parziale) {
		if(parziale.get(parziale.size()-1).equals(m2)) {
		if(calcolaPeso(parziale)>this.pesoMax) {
			this.migliore= new ArrayList<>(parziale);
			this.pesoMax=calcolaPeso(parziale);
		}	
		}
		Match ultimo= parziale.get(parziale.size()-1);
		for(Match m: Graphs.neighborListOf(this.grafo, ultimo)) {
			if(!parziale.contains(m)&& m.teamHomeID!=ultimo.teamHomeID && m.teamAwayID!=ultimo.teamAwayID && m.teamAwayID!=ultimo.teamHomeID) {
				parziale.add(m);
				cerca(m2,parziale);
				parziale.remove(parziale.size()-1);
				
			}
		}
		
	}

	private double calcolaPeso(List<Match> parziale) {
		double pesoTot=0.0;
		for(int i=1; i<parziale.size(); i++) {
			Match a= parziale.get(i-1);
			Match b=parziale.get(i);
			pesoTot=pesoTot+grafo.getEdgeWeight(grafo.getEdge(a, b));
		}
		return pesoTot;
	}

	public void setGrafo(SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public void setDao(PremierLeagueDAO dao) {
		this.dao = dao;
	}

	public Map<Integer, Match> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Integer, Match> idMap) {
		this.idMap = idMap;
	}
	
	
}
