package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private int id;
	private String texto;
	private Participante emitente;
	private Participante destinatario;
	private LocalDateTime datahora;
	
	public Mensagem(int id, String texto, Participante emitente, Participante destinatario, LocalDateTime datahora) {
		this.id = id;
		this.texto = texto;
		this.emitente = emitente;
		this.destinatario = destinatario;
		this.datahora = datahora;
	}

	public int getId() {
		return id;
	}

	public String getTexto() {
		return texto;
	}

	public Participante getEmitente() {
		return emitente;
	}

	public Participante getDestinatario() {
		return destinatario;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}
}
