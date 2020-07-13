package it.polito.tdp.food.model;

public class Cibo {
private Integer ID;
private String nome;
public Cibo(Integer iD, String nome) {
	super();
	ID = iD;
	this.nome = nome;
}
public Integer getID() {
	return ID;
}
public void setID(Integer iD) {
	ID = iD;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ID == null) ? 0 : ID.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Cibo other = (Cibo) obj;
	if (ID == null) {
		if (other.ID != null)
			return false;
	} else if (!ID.equals(other.ID))
		return false;
	return true;
}
@Override
public String toString() {
	return "["+ID+"] "+nome;
}

}
