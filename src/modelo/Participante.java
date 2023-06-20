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
	
	public Mensagem localizarEnviada(int id) {
		for (Mensagem m : enviadas) {
			if(m.getId() == id)
				return m;
		}
		return null;
	}

	@Override
	public String toString() {
		String s = "Nome=" + nome + "\n Mensagens enviadas: ";
		
		if(enviadas.isEmpty())
			s += "sem mensagens enviadas\n";
		
		else {
			for(Mensagem m : enviadas) {
				s += "\n  --> " + m;
			}
			s += "\n";
		}
		
		s += " Mensagens recebidas: ";
		
		if(recebidas.isEmpty())
			s += "sem mensagens recebidas";
		
		else {
			for(Mensagem m : recebidas) {
				s += "\n  --> " + m;
			}
		}
		return s;
	}
	
}
