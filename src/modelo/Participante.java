package modelo;

import java.util.ArrayList;

public class Participante {
	
	private String nome;
	private ArrayList<Mensagem> recebidas = new ArrayList<Mensagem>();
	private ArrayList<Mensagem> enviadas = new ArrayList<Mensagem>();

	public Participante(String nome){
		this.nome = nome;
	}
	
	//Para adicionar uma mensagem recebida
	public void adicionar(Mensagem mr){
		recebidas.add(mr);
		mr.setParticipante(this);
	}
	
	//Para remover uma mensagem recebida
	public void remover(Mensagem mr){
		recebidas.remove(mr);
		mr.setParticipante(null);
	}
	
}
