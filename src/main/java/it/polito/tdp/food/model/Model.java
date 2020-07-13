package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private FoodDao dao;
	private Graph<Cibo, DefaultWeightedEdge> grafo;

	public Model() {
		this.dao = new FoodDao();
	}

	public String creaGrafo(Integer calorie) {
		this.grafo = new SimpleDirectedWeightedGraph<Cibo, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getNodi(calorie));
		List<Adiacenza> adiacenze = new ArrayList<>(this.dao.getArchi());
		for (Adiacenza ad : adiacenze) {
			if (this.grafo.containsVertex(ad.getC1()) && this.grafo.containsVertex(ad.getC2())) {
				if (ad.getPeso() > 0) {
					Graphs.addEdge(this.grafo, ad.getC1(), ad.getC2(), ad.getPeso());
				} else {
					Double peso = (-1) * ad.getPeso();
					Graphs.addEdge(this.grafo, ad.getC2(), ad.getC1(), peso);
				}
			}
		}
		return String.format("Grafo creato: (%s) nodi e (%s) archi", this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size());
	}

	public List<Cibo> getCibi() {
		List<Cibo> lista = new ArrayList<>(this.grafo.vertexSet());
		return lista;
	}

	public List<CiboPeso> getViciniMin(Cibo sorgente) {
		List<CiboPeso> viciniPesati = new ArrayList<>();
		for (DefaultWeightedEdge c : this.grafo.outgoingEdgesOf(sorgente)) {
			Double pesoArco = this.grafo.getEdgeWeight(c);

			Cibo cb = this.grafo.getEdgeTarget(c);
			viciniPesati.add(new CiboPeso(cb, pesoArco));

		}
		Collections.sort(viciniPesati);
		return viciniPesati;
	}

	private Simulatore sim;
	private Integer numeroCibiTOT;
	private Double tempoPrepTOT;
	public void doSimulazione(Cibo sorgente, Integer k) {
		this.sim = new Simulatore(this, this.grafo);
		
		this.sim.init(sorgente, k);
		this.sim.run();
		
		this.numeroCibiTOT = this.sim.getNumeroCibiPreparati();
		this.tempoPrepTOT = this.sim.getTempoPreparazioneTOT();
		
	}

	public Integer getNumeroCibiTOT() {
		return numeroCibiTOT;
	}

	public Double getTempoPrepTOT() {
		return tempoPrepTOT;
	}

}
