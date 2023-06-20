package appconsole;

import modelo.*;
import repositorio.*;

public class Teste_Ian {
	
	public static void main(String[] args) {	
		//testando o lerDados(), executar apenas depois de executar os testes do professor
		System.out.println("*******Carregando dados*******");
		Repositorio r = new Repositorio();
		r.carregarObjetos();
		System.out.println("Verificando os dados:\n");
		
		System.out.println("Individuos\n");
		
		for (Individual ind : r.getIndividuos()) {
			System.out.println(ind + "\n");
		}
		System.out.println("\n");
		
		System.out.println("Grupos\n");
		
		for (Grupo g : r.getGrupos()) {
			System.out.println(g + "\n");
		}
		System.out.println("\n");
		
		System.out.println("Mensagens\n");
		
		for (Mensagem m : r.getMensagens()) {
			System.out.println(m);
		}
	}
}
