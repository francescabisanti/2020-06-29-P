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
	List <Match> migliore;
	double pesoMax;
	PremierLeagueDAO dao;
	Map <Integer, Match> idMap;
	public Model() {
		dao= new PremierLeagueDAO();
		
	}
	
	public void creaGrafo(int minuti, int mese) {
		idMap= new HashMap <Integer, Match>();
		grafo= new SimpleWeightedGraph <Match, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		dao.getVertici(mese, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		List <Adiacenza> aaa= new ArrayList <>(dao.getAdiacenza(mese, idMap, minuti));
		for(Adiacenza a: aaa) {
			DefaultWeightedEdge e= this.grafo.getEdge(a.getM1(), a.getM2());
			if(e==null) {
				Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
			}
		}
	}
	
	public List<Adiacenza> connessioniMax(int minuti, int mese, Map <Integer, Match>idMap){
		List <Adiacenza> totali= new ArrayList <>(dao.getAdiacenza(mese, idMap, minuti));
		Adiacenza a1= totali.get(0);
		List <Adiacenza> result= new ArrayList <>();
		result.add(a1);
		for(Adiacenza aa: totali) {
			if(!aa.equals(a1)&& aa.getPeso()==a1.getPeso())
				result.add(aa);
				
		}
		
		
		
		return result;
	}
	
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List <Match> trovaPercorso (Match partenza, Match destinazione){
		migliore= new ArrayList<>();
		List <Match> parziale= new ArrayList <>();
		parziale.add(partenza);
		cerca(destinazione, parziale);
		pesoMax=0;
		return migliore;
		
	}
	
	
	
	private void cerca(Match destinazione, List<Match> parziale) {
		//caso Terminale
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			double peso=this.calcolaPeso(parziale);
			if(peso>pesoMax) {
				pesoMax=peso;
				migliore= new ArrayList<>(parziale);
				
			}
			return;
		}
		for(Match vicino: Graphs.neighborListOf(this.grafo,parziale.get( parziale.size()-1))) {
			if(!parziale.contains(vicino)&& isOkay(vicino, destinazione)) {
				parziale.add(vicino);
				cerca(destinazione, parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private boolean isOkay(Match vicino, Match destinazione) {
		int casaVicino= vicino.getTeamHomeID();
		int casaDest= destinazione.getTeamHomeID();
		int ospiteVicino= vicino.getTeamAwayID();
		int ospiteDest= destinazione.getTeamAwayID();
		if(casaVicino==casaDest || ospiteVicino== ospiteDest) {
			if(ospiteVicino==casaDest || ospiteVicino== casaVicino)
				return false;
		}
			
		return true;
	}

	public double calcolaPeso(List<Match> parziale) {
		double peso=0;
		for(int i=0;i<parziale.size()-1;i++) {
			Match primo=parziale.get(i);
			Match secondo=parziale.get(i+1);
			peso+=grafo.getEdgeWeight(grafo.getEdge(primo, secondo));
		}
		return peso;
	}
	
	
	

	public SimpleWeightedGraph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
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
