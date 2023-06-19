package appconsole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import modelo.*;
import regras_negocio.Fachada;

public class Testes_Ian {

	public static void main(String[] args) {
		Individual p1 = new Individual("George", "123", false);
		p1.setNome("Baka");
		
		System.out.println(p1.getNome());
		
		String s = "30/10/2022 16:13:49";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
		System.out.println(dateTime.format(formatter) + ";");
		
		TreeMap<String, Participante> participantes = new TreeMap<>(); //Nome é a chave
		
		Individual p2 = new Individual("Marcos", "123", false);
		Individual p3 = new Individual("José", "123", false);
		Individual p4 = new Individual("Mário", "123", false);
		
		participantes.put(p1.getNome(), p1);
		participantes.put(p2.getNome(), p2);
		participantes.put(p3.getNome(), p3);
		participantes.put(p4.getNome(), p4);
		
		Grupo g1 = new Grupo("É eu");
		Grupo g2 = new Grupo("É tu");
		Grupo g3 = new Grupo("É ele");
		Grupo g4 = new Grupo("É nóis");
		Grupo g5 = new Grupo("É vós");
		Grupo g6 = new Grupo("É eles");
		
		participantes.put(g1.getNome(), g1);
		participantes.put(g2.getNome(), g2);
		participantes.put(g3.getNome(), g3);
		participantes.put(g4.getNome(), g4);
		participantes.put(g5.getNome(), g5);
		participantes.put(g6.getNome(), g6);
		
		
		for (Participante p : participantes.values()) {
			if (p instanceof Individual) {
				System.out.println(p.getNome());
			}
		}
	}

}
