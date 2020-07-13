package it.polito.tdp.food.model;

public class Evento implements Comparable<Evento>{
	public enum EventType{
		Lavorando, Finito
	}
	private EventType tipo;
	private Cibo cibo;
	private Integer stazione;
	private Double tempoPrep;
	public Evento(EventType tipo, Cibo cibo, Integer stazione, Double tempoPrep) {
		super();
		this.tipo = tipo;
		this.cibo = cibo;
		this.stazione = stazione;
		this.tempoPrep = tempoPrep;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	public Cibo getCibo() {
		return cibo;
	}
	public void setCibo(Cibo cibo) {
		this.cibo = cibo;
	}
	public Integer getStazione() {
		return stazione;
	}
	public void setStazione(Integer stazione) {
		this.stazione = stazione;
	}
	public Double getTempoPrep() {
		return tempoPrep;
	}
	public void setTempoPrep(Double tempoPrep) {
		this.tempoPrep = tempoPrep;
	}
	@Override
	public int compareTo(Evento o) {
		return this.stazione.compareTo(o.stazione);
	}
	
	

}
