package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Evento.EventType;

public class Simulatore {
	private Model model;
	private Graph<Cibo, DefaultWeightedEdge> grafo;

	private Cibo sorgente;
	private Integer numeroStazioni = 5;

	private Integer numeroCibiPreparati=0;
	private Double tempoPreparazioneTOT=0.0;

	private List<Cibo> cibiTOT;
	private Queue<Evento> coda;

	public Simulatore(Model model, Graph<Cibo, DefaultWeightedEdge> grafo) {
		this.model = model;
		this.grafo = grafo;
	}

	public void init(Cibo sorgente, Integer k) {
		this.sorgente = sorgente;
		this.numeroStazioni = k;

		this.cibiTOT = new ArrayList<>(this.grafo.vertexSet());
		this.coda = new PriorityQueue<Evento>();
		

		Evento e = new Evento(EventType.Lavorando, this.sorgente, 1, null);
		this.coda.add(e);
		List<CiboPeso> viciniPesati = this.getMinVicini(this.sorgente);
		if (this.numeroStazioni < viciniPesati.size()) {
			Integer counter = 0;
			for (int i = 2; i <= this.numeroStazioni; i++) {
				Evento e1 = new Evento(EventType.Lavorando, viciniPesati.get(counter).getC(), i, null);
				this.coda.add(e1);
				counter++;
			}
		} else {
			Integer counter = 0;
			for (int i = 2; i <= viciniPesati.size(); i++) {
				Evento e1 = new Evento(EventType.Lavorando, viciniPesati.get(counter).getC(), i, null);
				this.coda.add(e1);
				counter++;
			}
		}

	}

	public void run() {
		Evento e;
		while((e=this.coda.poll())!=null) {
			switch(e.getTipo()) {
			case Lavorando:
				this.numeroCibiPreparati++;
				this.cibiTOT.remove(e.getCibo());
				List<CiboPeso> viciniPesati = this.getMinVicini(e.getCibo());
				Cibo prescelto = null;
				Boolean valido=false;
				Integer counter=0;
				if(viciniPesati.size()>0) {
					while(valido==false) {
						if(this.cibiTOT.contains(viciniPesati.get(counter).getC())) {
							prescelto = viciniPesati.get(counter).getC();
							valido=true;
						}
						counter++;
					}
				}
				if(prescelto!=null) {
					Double pesoArco = this.grafo.getEdgeWeight(this.grafo.getEdge(e.getCibo(), prescelto));
					this.tempoPreparazioneTOT += pesoArco;
					
					Evento e1= new Evento(EventType.Lavorando,prescelto, e.getStazione(), pesoArco );
					this.coda.add(e1);
				}
				break;
			}
		}
		
	}

	public List<CiboPeso> getMinVicini(Cibo sorgente) {
		List<CiboPeso> viciniPesati = new ArrayList<>();
		for (DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(sorgente)) {
			Double peso = this.grafo.getEdgeWeight(e);
			if (this.cibiTOT.contains(this.grafo.getEdgeTarget(e))) {
				viciniPesati.add(new CiboPeso(this.grafo.getEdgeTarget(e), peso));
			}
		}
		Collections.sort(viciniPesati);
		return viciniPesati;
	}

	public Integer getNumeroCibiPreparati() {
		return numeroCibiPreparati;
	}

	public Double getTempoPreparazioneTOT() {
		return tempoPreparazioneTOT;
	}

}
