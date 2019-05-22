package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.alg.ConnectivityInspector;

import it.polito.tdp.borders.db.BordersDAO;

public class Model 
{
	// Grafo non orientato e non pesato
	private Graph<Country, DefaultEdge> grafo;
	// idMap stato
	private Map<Integer, Country> countryIdMap;
	
	private List<Country>countries;
	private List<Country> contiguousCountries;

	public Model() 
	{
		BordersDAO dao = new BordersDAO();
		this.countries = dao.loadAllCountries();
		
		this.countryIdMap = new HashMap<>();
		for (Country c : countries)
			countryIdMap.put(c.getcCode(), c);
	
	}
	
	public List<Country> loadCountries()
	{
		return countries;
	}

	public String calcolaConfini(int anno)
	{
		// Creo e riempio il grafo
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		BordersDAO dao = new BordersDAO();
		List<Border> borders = dao.getCountryPairs(anno);
		contiguousCountries = new ArrayList<Country>();
		for (Border b : borders)
		{
			//if (!contiguousCountries.contains(b.getState1no()))
				contiguousCountries.add(countryIdMap.get(b.getState1no()));
			//if (!contiguousCountries.contains(b.getState2no()))
				contiguousCountries.add(countryIdMap.get(b.getState2no()));
		}
		
		// Aggiungo vertici
		Graphs.addAllVertices(this.grafo, contiguousCountries);
		
		// Aggiungo gli archi
		for (Border b : borders)
		{
			if (b.getConttype() == 1)
				this.grafo.addEdge(countryIdMap.get(b.getState1no()), countryIdMap.get(b.getState2no()));
		}
		
		String result = "";
		// Stampa, per ogni stato stampo il numero di stati confinanti (considero tutti gli stati)
		for (Country c : countries)
		{
			result += "Stato: " + c + " numero di stati confinanti: " + this.grafo.degreeOf(c) + "\n";
		}
		
		//componenti connesse
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(this.grafo); 
		result += "\n Numero di componenti connesse: " + ci.connectedSets().size();
		return result;
		
		
	}

	// fare controlli su precedenze
	public String displayNeighbours(Country country)
	{
		// m0 - jgrapht method - mi trova solo i vicini di primo livello
		/*List<Country> vicini = new ArrayList<Country>();
		vicini.addAll(Graphs.neighborListOf(this.grafo, country));
		
		if (vicini.size() > 0)
			return "Vicini di " + country + ": " +vicini.toString();
		else
			return "Il nodo: " + country + " non ha vicini";*/
		
		//m1 - visita in profondità
		/*List<Country> visited = new LinkedList<Country>();

		// qui però mi trova tutta la componente connessa
		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<Country, DefaultEdge>(grafo,
				country);
		while (bfv.hasNext()) {
			visited.add(bfv.next());
		}
		return visited.toString();*/
		
		//m2 versione ricorsiva - da vedere bene
		//return displayNeighboursRecursive(country).toString();
		
		//m3 versione iterativa
		return displayNeighboursIterative(country).toString();
	}

	private List<Country> displayNeighboursRecursive(Country country) 
	{
		List<Country> visited = new LinkedList<Country>(); 
		recursion(country, visited);
		return visited;
	}

	private void recursion(Country country, List<Country> visited)
	{
		visited.add(country);
		
		for (Country c : Graphs.neighborListOf(this.grafo, country))
		{
			if (!visited.contains(c))
				recursion(c, visited);
		}
		
	}
	
	private List<Country> displayNeighboursIterative(Country country) 
	{
		List<Country> visitati = new LinkedList<Country>(); 
		List<Country> daVisitare = new LinkedList<Country>(); 
		
		visitati.add(country);
		daVisitare.addAll(Graphs.neighborListOf(this.grafo, country));
		
		while (daVisitare.size() > 0)
		{
			Country tmp = daVisitare.remove(0);
			visitati.add(tmp);
			List<Country>listaVicini = (Graphs.neighborListOf(this.grafo, tmp));
			listaVicini.removeAll(visitati);
			listaVicini.removeAll(daVisitare);
			daVisitare.addAll(listaVicini);
		}
		return visitati;
	}

}
