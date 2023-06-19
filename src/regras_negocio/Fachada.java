package regras_negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private Fachada() {}

	private static Repositorio repositorio = new Repositorio();


	public static ArrayList<Individual> listarIndividuos() {
		return repositorio.getIndividuos();	
	}
	
	public static ArrayList<Grupo> listarGrupos() {
		return repositorio.getGrupos();
	}
	
	public static ArrayList<Mensagem> listarMensagens() {
		return repositorio.getMensagens();
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nome) throws Exception {
		Individual ind = repositorio.localizarIndividuo(nome);	
		if(ind == null) 
			throw new Exception("listar mensagens enviadas - individuo nao existe:" + nome);
		
		return ind.getEnviadas();	
	}

	public static ArrayList<Mensagem> listarMensagensRecebidas(String nome) throws Exception {
		Individual ind = repositorio.localizarIndividuo(nome);	
		if(ind == null) 
			throw new Exception("listar mensagens recebidas - individuo nao existe:" + nome);
		
		return ind.getRecebidas();	
	}

	public static void criarIndividuo(String nome, String senha) throws  Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia:");
		
		Individual ind = repositorio.localizarIndividuo(nome);	
		if(ind != null) 
			throw new Exception("criar individual - individuo ja existe:" + nome);


		Individual novoInd = new Individual(nome,senha, false);
		repositorio.addParticipante(novoInd);		
	}

	public static void criarAdministrador(String nome, String senha) throws  Exception{
		if(nome.isEmpty()) 
			throw new Exception("criar administrador - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar administrador - senha vazia:");
		
		Individual ind = repositorio.localizarIndividuo(nome);	
		if(ind != null) 
			throw new Exception("criar administrador - individuo ja existe:" + nome);


		Individual novoAdm = new Individual(nome,senha, true);
		repositorio.addParticipante(novoAdm);	
	}


	public static void criarGrupo(String nome) throws  Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar grupo - nome vazio:");
		
		Grupo g = repositorio.localizarGrupo(nome);	
		if(g != null) 
			throw new Exception("criar grupo - grupo ja existe:" + nome);
		
		Grupo grupo = new Grupo(nome);
		repositorio.addParticipante(grupo);
	}

	public static void inserirGrupo(String nomeindividuo, String nomegrupo) throws  Exception {
		//localizar nomeindividuo no repositorio
		//localizar nomegrupo no repositorio
		//verificar se individuo nao esta no grupo	
		//adicionar individuo com o grupo e vice-versa
	}

	public static void removerGrupo(String nomeindividuo, String nomegrupo) throws  Exception {
		//localizar nomeindividuo no repositorio
		//localizar nomegrupo no repositorio
		//verificar se individuo ja esta no grupo	
		//remover individuo com o grupo e vice-versa
	}


	public static void criarMensagem(String nomeemitente, String nomedestinatario, String texto) throws Exception{
		if(texto.isEmpty()) 
			throw new Exception("criar mensagem - texto vazio:");

		Individual emitente = repositorio.localizarIndividual(nomeemitente);	
		if(emitente == null) 
			throw new Exception("criar mensagem - emitente nao existe:" + nomeemitente);

		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("criar mensagem - destinatario nao existe:" + nomeemitente);

		if(destinatario instanceof Grupo g && emitente.localizarGrupo(g.getNome())==null)
			throw new Exception("criar mensagem - grupo nao permitido:" + nomedestinatario);


		//cont.
		//gerar id no repositorio para a mensagem
		//criar mensagem
		//adicionar mensagem ao emitente e destinatario
		//adicionar mensagem ao repositorio
		//
		//caso destinatario seja tipo Grupo então criar copias da mensagem, tendo o grupo como emitente e cada membro do grupo como destinatario, 
		//  usando mesmo id e texto, e adicionar essas copias no repositorio
		
	}

	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) throws Exception{
		//localizar emitente no repositorio
		//localizar destinatario no repositorio
		//obter do emitente a lista  enviadas
		//obter do emitente a lista  recebidas
		
		//criar a lista conversa
		//Adicionar na conversa as mensagens da lista enviadas cujo destinatario é igual ao parametro destinatario
		//Adicionar na conversa as mensagens da lista recebidas cujo emitente é igual ao parametro destinatario
		//ordenar a lista conversa pelo id das mensagens
		//retornar a lista conversa
	}

	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);

		emitente.removerEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.removerRecebida(m);
		repositorio.remover(m);	

		if(destinatario instanceof Grupo g) {
			ArrayList<Mensagem> lista = destinatario.getEnviadas();
			lista.removeIf(new Predicate<Mensagem>() {
				@Override
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().removerRecebida(t);
						repositorio.remover(t);	
						return true;		//apaga mensagem da lista
					}
					else
						return false;
				}

			});

		}
	}

	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) throws Exception{
		//localizar individuo no repositorio
		//verificar se individuo é administrador
		//listar as mensagens que contem o termo no texto
	}

	public static ArrayList<String> ausentes(String nomeadministrador) throws Exception{
		//localizar individuo no repositorio
		//verificar se individuo é administrador
		//listar os nomes dos participante que nao enviaram mensagens
	}

}
