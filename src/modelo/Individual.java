package modelo;

import java.util.ArrayList;

public class Individual extends Participante {
	private String senha;
	private boolean administrador;
	private ArrayList<Grupo> grupos = new ArrayList<>();
	
	public Individual(String nome, String senha, boolean administrador) {
		super(nome);
		this.senha = senha;
		this.administrador = administrador;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getAdministrador() {
		return administrador;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}
	
	public void addGrupo(Grupo g) {
		grupos.add(g);
	}
	
	public void delGrupo(Grupo g) {
		grupos.remove(g);
	}
	
	public boolean pertenceGrupo(String nomeGrupo) {
		for (Grupo g : grupos) {
			if(g.getNome().equals(nomeGrupo))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String s = super.toString() + "\n Grupos: ";
		
		if(grupos.isEmpty())
			s += "sem grupo";
		
		else {
			for(Grupo g : grupos) {
				s += "\n  --> " + g.getNome();
			}
		}
		return s;
	}
	
}

