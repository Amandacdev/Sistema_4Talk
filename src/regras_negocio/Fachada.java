package regras_negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
		Individual ind = repositorio.localizarIndividual(nome);	
		if(ind == null) 
			throw new Exception("listar mensagens enviadas - individuo nao existe: " + nome);
		
		return ind.getEnviadas();	
	}

	public static ArrayList<Mensagem> listarMensagensRecebidas(String nome) throws Exception {
		Participante p = repositorio.localizarParticipante(nome);	
		if(p == null) 
			throw new Exception("listar mensagens recebidas - nome nao existe: " + nome);
		
		return p.getRecebidas();	
	}

	public static void criarIndividuo(String nome, String senha) throws  Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia");
		
		Participante p = repositorio.localizarParticipante(nome);	
		if(p != null) 
			throw new Exception("criar individual - nome ja existe: " + nome);


		Individual novoInd = new Individual(nome, senha, false);
		repositorio.addParticipante(novoInd);
		repositorio.salvarObjetos();
	}

	public static void criarAdministrador(String nome, String senha) throws  Exception{
		if(nome.isEmpty()) 
			throw new Exception("criar administrador - nome vazio");
		if(senha.isEmpty()) 
			throw new Exception("criar administrador - senha vazia");
		
		Participante p = repositorio.localizarParticipante(nome);	
		if(p != null) 
			throw new Exception("criar administrador - nome ja existe: " + nome);


		Individual novoAdm = new Individual(nome, senha, true);
		repositorio.addParticipante(novoAdm);
		repositorio.salvarObjetos();
	}


	public static void criarGrupo(String nome) throws  Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar grupo - nome vazio");
		
		Participante p = repositorio.localizarParticipante(nome);	
		if(p != null) 
			throw new Exception("criar grupo - nome ja existe: " + nome);
		
		Grupo grupo = new Grupo(nome);
		repositorio.addParticipante(grupo);
		repositorio.salvarObjetos();
	}

	public static void inserirGrupo(String nomeindividuo, String nomegrupo) throws  Exception {
		//Verifica se o indivíduo existe
		Individual ind = repositorio.localizarIndividual(nomeindividuo);
		if(ind == null) 
			throw new Exception("inserir Grupo - individuo não existe: " + nomeindividuo);
		
		//Verifica se o grupo existe
		Grupo g = repositorio.localizarGrupo(nomegrupo);
		if(g == null) 
			throw new Exception("inserir Grupo - grupo não existe: " + nomegrupo);
		
		//Obtendo a lista de indivíduos do grupo
		ArrayList<Individual> individuos = g.getIndividuos();
		
		//Verifica se o indivíduo já está no grupo
		for(Individual ind2 : individuos){
			if((ind2.getNome()).equals(nomeindividuo))
				throw new Exception("inserir Grupo - individuo já está no grupo");
		}
		
		//Insere o indivíduo ind na lista de indivíduos grupo g
		g.addIndividual(ind);
		
		//Insere o grupo g na lista de grupos do indivíduo ind
		ind.addGrupo(g);
		repositorio.salvarObjetos();
	}

	public static void removerGrupo(String nomeindividuo, String nomegrupo) throws  Exception {
		
		//Verifica se o indivíduo existe
		Individual ind = repositorio.localizarIndividual(nomeindividuo);
		if(ind == null) 
			throw new Exception("remover Grupo - individuo não existe: " + nomeindividuo);
		
		//Verifica se o grupo existe
		Grupo g = repositorio.localizarGrupo(nomegrupo);
		if(g == null) 
			throw new Exception("remover Grupo - grupo não existe: " + nomegrupo);
		
		//Obtendo a lista de indivíduos do grupo
		ArrayList<Individual> individuos = g.getIndividuos();
		
		//Verifica se o indivíduo não está no grupo
		if(!individuos.contains(ind))
			throw new Exception("remover Grupo - individuo não está no grupo: " + nomeindividuo);
	
		//Remove o indivíduo ind da lista de indivíduos grupo g
		g.delIndividual(ind);
		
		//Remove o grupo g da lista de grupos do indivíduo ind
		ind.delGrupo(g);
		repositorio.salvarObjetos();
	}


	public static void criarMensagem(String nomeemitente, String nomedestinatario, String texto) throws Exception{
		if(texto.isEmpty()) 
			throw new Exception("criar mensagem - texto vazio");

		Individual emitente = repositorio.localizarIndividual(nomeemitente);	
		if(emitente == null) 
			throw new Exception("criar mensagem - emitente nao existe: " + nomeemitente);

		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("criar mensagem - destinatario nao existe: " + nomeemitente);

		if(destinatario instanceof Grupo g && !emitente.pertenceGrupo(g.getNome()))
			throw new Exception("criar mensagem - emitente não está inserido no grupo: " + nomedestinatario);


		//cont.
		//gerar id no repositorio para a mensagem
		int id = repositorio.gerarID();
		
		//criar mensagem
		Mensagem m = new Mensagem(id, texto, emitente, destinatario, LocalDateTime.now().withNano(0));
		
		//adicionar mensagem ao emitente e destinatario
		emitente.addEnviada(m);
		destinatario.addRecebida(m);
		
		//adicionar mensagem ao repositorio
		repositorio.addMensagem(m);
		
		//Se o destinatário for um grupo, envia cópias da mensagem (com mesmo id) do grupo para os membros desse grupo 
		//(exceto para emitente que criou a mensagem original), concatenando ao texto o nome do emitente, na forma “nome/texto”.
		//Adicionar as copias no repositório
		if(destinatario instanceof Grupo) {
			Grupo g = (Grupo) destinatario;
			for(Individual ind : g.getIndividuos()) {
				if(!ind.equals(emitente)) {
					String novoTexto = emitente.getNome() + "/" + texto;
					Mensagem copia = new Mensagem(id, novoTexto, g, ind, m.getDatahora());
					g.addEnviada(copia);
					ind.addRecebida(copia);
					repositorio.addMensagem(copia);
				}
			}
		}
		repositorio.salvarObjetos();
	}

	public static ArrayList<Mensagem> obterConversa(String nomeemitente, String nomedestinatario) throws Exception{
		//localizar emitente no repositorio
		Individual emitente = repositorio.localizarIndividual(nomeemitente);	
		if(emitente == null) 
			throw new Exception("obter conversa - emitente nao existe: " + nomeemitente);
		
		//localizar destinatario no repositorio
		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("obter conversa - destinatario nao existe: " + nomeemitente);
		
		//obter do emitente a lista  enviadas
		ArrayList<Mensagem> enviadas = emitente.getEnviadas();
		
		//obter do emitente a lista  recebidas
		ArrayList<Mensagem> recebidas = emitente.getRecebidas();
		
		//criar a lista conversa
		ArrayList<Mensagem> conversa = new ArrayList<>();
		
		//Adicionar na conversa as mensagens da lista enviadas cujo destinatario é igual ao parametro destinatario
		for (Mensagem m : enviadas) {
			if(m.getDestinatario().equals(destinatario))
				conversa.add(m);
		}
		
		//Adicionar na conversa as mensagens da lista recebidas cujo emitente é igual ao parametro destinatario
		for (Mensagem m : recebidas) {
			if(m.getEmitente().equals(destinatario))
				conversa.add(m);
		}
		
		//ordenar a lista conversa pelo id das mensagens
		conversa.sort(new Comparator<Mensagem>() {
			@Override
			public int compare(Mensagem m1, Mensagem m2) {
				return Integer.compare(m1.getId(), m2.getId());
			}
		});
		
		//retornar a lista conversa
		return conversa;
	}

	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe: " + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo: " + id);

		emitente.delEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.delRecebida(m);
		repositorio.delMensagem(m);	

		if(destinatario instanceof Grupo g) {
			ArrayList<Mensagem> lista = destinatario.getEnviadas();
			lista.removeIf(new Predicate<Mensagem>() {
				@Override
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().delRecebida(t);
						repositorio.delMensagem(t);	
						return true;		//apaga mensagem da lista
					}
					else
						return false;
				}

			});

		}
		repositorio.salvarObjetos();
	}

	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) throws Exception{
		//localizar individuo no repositorio
		Individual admin = repositorio.localizarIndividual(nomeadministrador);
		if(admin == null) 
			throw new Exception("espionar mensagem - individuo não existe: " + nomeadministrador);
		
		//verificar se individuo é administrador
		if(!admin.getAdministrador())
			throw new Exception("espionar mensagem - individuo não é administrador: " + nomeadministrador);
		
		//listar as mensagens que contem o termo no texto
		if(termo.isEmpty())
			return repositorio.getMensagens();
		
		ArrayList<Mensagem> mensagensEspionadas = new ArrayList<>();
		for(Mensagem m : repositorio.getMensagens()) {
			if(m.getTexto().contains(termo))
				mensagensEspionadas.add(m);
		}
		return mensagensEspionadas;
	}

	public static ArrayList<String> ausentes(String nomeadministrador) throws Exception{
		//localizar individuo no repositorio
		Individual admin = repositorio.localizarIndividual(nomeadministrador);
		if(admin == null) 
			throw new Exception("ausentes - individuo não existe: " + nomeadministrador);
		
		//verificar se individuo é administrador
		if(!admin.getAdministrador())
			throw new Exception("ausentes - individuo não é administrador: " + nomeadministrador);
		
		//listar os nomes dos participante que nao enviaram mensagens
		ArrayList<String> ausentes = new ArrayList<>();
		for (Participante p : repositorio.getParticipantes()) {
			if(p.getEnviadas().isEmpty())
				ausentes.add(p.getNome());
		}
		return ausentes;
	}

	public static Individual validarIndividuo(String nomeindividuo, String senha) throws Exception {
		Individual ind = repositorio.localizarIndividual(nomeindividuo);
		if(ind != null) {
			if(!ind.getSenha().equals(senha))
				throw new Exception("validar individuo - senha incorreta para indivíduo: " + nomeindividuo);
		
			return ind;
		}
		return null;
	}
	
}
