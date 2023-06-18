package modelo;

import java.util.ArrayList;

public class Participante {
	private String nome;
	private ArrayList<Mensagem> recebidas = new ArrayList<>();
	private ArrayList<Mensagem> enviadas = new ArrayList<>();

	public Participante(String nome) {
		this.nome = nome;
	}
	
	//Para adicionar uma mensagem recebida
	public void addRecebida(Mensagem mr) {
		recebidas.add(mr);
	}
	
	//Para remover uma mensagem recebida
	public void delRecebida(Mensagem mr) {
		recebidas.remove(mr);
	}
	
	//Para adicionar uma mensagem enviada
	public void addEnviada(Mensagem me) {
		enviadas.add(me);
	}
	
	//Para remover uma mensagem enviada
	public void delEnviada(Mensagem me) {
		enviadas.remove(me);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Mensagem> getRecebidas() {
		return recebidas;
	}

	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}
}
