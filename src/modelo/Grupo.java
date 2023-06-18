package modelo;

import java.util.ArrayList;

public class Grupo extends Participante {
	private ArrayList<Individual> individuos = new ArrayList<>();
	
	public Grupo(String nome) {
		super(nome);
	}

	public ArrayList<Individual> getIndividuos() {
		return individuos;
	}
	
	public void addIndividual(Individual i) {
		individuos.add(i);
	}
	
	public void delIndividual(Individual i) {
		individuos.remove(i);
	}
}
