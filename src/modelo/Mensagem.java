package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private int id;
	private String texto;
	private Participante participante; //está correto?
	private LocalDateTime datahora;

	public Mensagem(int id, String texto, LocalDateTime datahora) {
		//super();
		this.id = id;
		this.texto = texto;
		this.datahora = datahora;
	}
	
	//Para adicionar participante, chamado no repositorio
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	

}
