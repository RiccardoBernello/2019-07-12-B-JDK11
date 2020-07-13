package it.polito.tdp.food.model;

public class Adiacenza {
	private Cibo c1;
	private Cibo c2;
	private Double peso;
	public Adiacenza(Cibo c1, Cibo c2, Double peso) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.peso = peso;
	}
	public Cibo getC1() {
		return c1;
	}
	public void setC1(Cibo c1) {
		this.c1 = c1;
	}
	public Cibo getC2() {
		return c2;
	}
	public void setC2(Cibo c2) {
		this.c2 = c2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	

}
