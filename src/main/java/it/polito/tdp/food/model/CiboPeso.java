package it.polito.tdp.food.model;

public class CiboPeso implements Comparable<CiboPeso>{
	private Cibo c;
	private Double peso;
	public CiboPeso(Cibo c, Double peso) {
		super();
		this.c = c;
		this.peso = peso;
	}
	public Cibo getC() {
		return c;
	}
	public void setC(Cibo c) {
		this.c = c;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(CiboPeso o) {
		return this.peso.compareTo(o.peso);
	}
	

}
