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
	PremierLeagueDAO dao;
	Map <Integer, Match> idMap;
	List <Match>migliore;
	SimpleWeightedGraph <Match, DefaultWeightedEdge> grafo;
	public Model() {
		dao= new PremierLeagueDAO();
		idMap= new HashMap <Integer, Match>();
		dao.listAllMatches(idMap);
	}
	
	public void creaGrafo(int mese, int minuti) {
		grafo= new SimpleWeightedGraph <Match, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(idMap, mese));
		for(Adiacenza a: dao.getAdiacenza(idMap, mese, minuti)) {
			if(grafo.containsVertex(a.getM1())&& grafo.containsVertex(a.getM2())) {
				Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), a.getPeso());
			}
		}
	}
	
	public List <DefaultWeightedEdge> trovaConnMax(){
		Double pesoMax=0.0;
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>pesoMax)
				pesoMax=grafo.getEdgeWeight(e);
		}
		List <DefaultWeightedEdge> result=new ArrayList<>();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)==pesoMax)
				result.add(e);
		}
		return result;
		}
	
	public List <Match> trovaPercorsoMigliore(Match m1, Match m2){
		migliore= new ArrayList<>();
		List<Match> parziale= new ArrayList<>();
		parziale.add(m1);
		cerca(parziale, m2);
		return migliore;
	}
	
	private void cerca(List<Match> parziale, Match m2) {
		Match ultimo= parziale.get(parziale.size()-1);
		if(ultimo.equals(m2)) {
			if(calcolaPeso(parziale)>calcolaPeso(migliore)) {
				migliore= new ArrayList<>(parziale);
				return;
			}
		}
		for(Match m: Graphs.neighborListOf(grafo, ultimo)) {
			if(aggiuntaValida(m, parziale)&& !parziale.contains(m)) {
				parziale.add(m);
				cerca(parziale, m2);
				parziale.remove(m);
			}
		}
		
	}

	private boolean aggiuntaValida(Match m, List<Match> parziale) {
		boolean valido=true;
		Match ultimo=parziale.get(parziale.size()-1);
		if(ultimo.getTeamAwayID()==m.getTeamAwayID()|| ultimo.getTeamHomeID()==m.getTeamHomeID() || ultimo.getTeamHomeID()==m.getTeamAwayID())
			valido=false;
		return valido;
	}

	private int calcolaPeso(List<Match> parziale) {
		int peso=0;
		for(int i=1; i<parziale.size(); i++) {
			Match m1= parziale.get(i-1);
			Match m2=parziale.get(i);
			peso=(int) (peso+grafo.getEdgeWeight(grafo.getEdge(m1, m2)));
		}
		return peso;
	}

	public int getNVertici() {
		return grafo.vertexSet().size();
	}
	public int getNArchi() {
		return grafo.edgeSet().size();
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public Map<Integer, Match> getIdMap() {
		return idMap;
	}

	public SimpleWeightedGraph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
}
