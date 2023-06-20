package appconsole;

import modelo.*;
import regras_negocio.*;

public class Teste_Ian {
	
	public static void main(String[] args) {	
		//testando o lerDados()
		System.out.println("*******Lendo dados*******");
		Fachada.lerDados();
		System.out.println("Verificando os dados:\n");
		
		System.out.println("Individuos\n");
		
		for (Individual ind : Fachada.listarIndividuos()) {
			System.out.println(ind + "\n");
		}
		System.out.println("\n");
		
		System.out.println("Grupos\n");
		
		for (Grupo g : Fachada.listarGrupos()) {
			System.out.println(g + "\n");
		}
		System.out.println("\n");
		
		System.out.println("Mensagens\n");
		
		for (Mensagem m : Fachada.listarMensagens()) {
			System.out.println(m);
		}
	}
}
