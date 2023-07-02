
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.*;



public class Repositorio {
	private TreeMap<String, Participante> participantes = new TreeMap<>(); //Nome é a chave
	private	ArrayList<Mensagem> mensagens = new ArrayList<>();
	
	public Repositorio() {
		this.carregarObjetos();
	}
	
	public void addParticipante(Participante p) {
		participantes.put(p.getNome(), p);
	}
	
	public void delParticipante(Participante p) {
		participantes.remove(p.getNome());
	}
	
	public void addMensagem(Mensagem m) {
		mensagens.add(m);
	}
	
	public void delMensagem(Mensagem m) {
		mensagens.remove(m);
	}
	
	public ArrayList<Individual> getIndividuos() {
		ArrayList<Individual> individuos = new ArrayList<>();
		for (Participante p : participantes.values()) {
			if (p instanceof Individual) {
				Individual ind = (Individual) p;
				individuos.add(ind);
			}
		}
		return individuos;
	}
	
	public ArrayList<Grupo> getGrupos() {
		ArrayList<Grupo> grupos = new ArrayList<>();
		for (Participante p : participantes.values()) {
			if (p instanceof Grupo) {
				Grupo g = (Grupo) p;
				grupos.add(g);
			}
		}
		return grupos;
	}
	
	public ArrayList<Participante> getParticipantes() {
		ArrayList<Participante> parts = new ArrayList<>();
		for(Participante p : participantes.values()) {
			parts.add(p);
		}
		return parts;
	}
	
	public ArrayList<Mensagem> getMensagens() {
		ArrayList<Mensagem> mens = new ArrayList<>();
		mens.addAll(mensagens);
		return mens;
	}
	
	public Participante localizarParticipante(String nome) {
		return participantes.get(nome);
	}
	
	public Individual localizarIndividual(String nome) {
		Participante p = participantes.get(nome);
		if (p instanceof Individual) {
			Individual ind = (Individual) p;
			return ind;
		}
		return null;
	}
	
	public Grupo localizarGrupo(String nome) {
		Participante p = participantes.get(nome);
		if (p instanceof Grupo) {
			Grupo g = (Grupo) p;
			return g;
		}
		return null;
	}
	
	public int gerarID() {
		try {
			int id = mensagens.get(mensagens.size() - 1).getId() + 1;
			return id;
		} catch (IndexOutOfBoundsException e) {
			return 1;
		}
	}
	
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File("./mensagens.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File("./individuos.csv").getCanonicalPath() ) ; 
			File f3 = new File( new File("./grupos.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() || !f3.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				FileWriter arquivo3 = new FileWriter(f3); arquivo3.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	

		try	{
			String nome,senha,administrador;
			File f = new File( new File("./individuos.csv").getCanonicalPath())  ;
			Scanner arquivo1 = new Scanner(f);	 //  pasta do projeto
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				senha = partes[1];
				administrador = partes[2];
				Individual ind = new Individual(nome,senha,Boolean.parseBoolean(administrador));
				this.addParticipante(ind);
			}
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}

		try	{
			String nome;
			Grupo grupo;
			Individual individuo;
			File f = new File( new File("./grupos.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 //  pasta do projeto
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				grupo = new Grupo(nome);
				if(partes.length>1)
					for(int i=1; i< partes.length; i++) {
						individuo = this.localizarIndividual(partes[i]);
						grupo.addIndividual(individuo);
						individuo.addGrupo(grupo);
					}
				this.addParticipante(grupo);
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de grupos:"+ex.getMessage());
		}


		try	{
			String nomeemitente, nomedestinatario,texto, datahoraString;
			int id;
			LocalDateTime datahora;
			Mensagem m;
			Participante emitente,destinatario;
			File f = new File( new File("./mensagens.csv").getCanonicalPath() )  ;
			Scanner arquivo3 = new Scanner(f);	 //  pasta do projeto
			while(arquivo3.hasNextLine()) 	{
				linha = arquivo3.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				id = Integer.parseInt(partes[0]);
				texto = partes[1];
				nomeemitente = partes[2];
				nomedestinatario = partes[3];
				datahoraString = partes[4];
				
				DateTimeFormatter estruturaDatahora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				datahora = LocalDateTime.parse(datahoraString, estruturaDatahora);
				
				emitente = this.localizarParticipante(nomeemitente);
				destinatario = this.localizarParticipante(nomedestinatario);
				m = new Mensagem(id,texto,emitente,destinatario, datahora);
				this.addMensagem(m);
				emitente.addEnviada(m);
				destinatario.addRecebida(m);
			} 
			arquivo3.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
		}
	}


	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que estão no repositório
		try	{
			File f = new File( new File("./mensagens.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			DateTimeFormatter estruturaDatahora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			for(Mensagem m : mensagens) 	{
				arquivo1.write(	m.getId()+";"+
						m.getTexto()+";"+
						m.getEmitente().getNome()+";"+
						m.getDestinatario().getNome()+";"+
						m.getDatahora().format(estruturaDatahora)+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  mensagens "+e.getMessage());
		}

		try	{
			File f = new File( new File("./individuos.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			for(Individual ind : this.getIndividuos()) {
				arquivo2.write(ind.getNome() +";"+ ind.getSenha() +";"+ ind.getAdministrador() +"\n");	
			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  individuos "+e.getMessage());
		}

		try	{
			File f = new File( new File("./grupos.csv").getCanonicalPath())  ;
			FileWriter arquivo3 = new FileWriter(f) ; 
			for(Grupo g : this.getGrupos()) {
				String texto="";
				for(Individual ind : g.getIndividuos())
					texto += ";" + ind.getNome();
				arquivo3.write(g.getNome() + texto + "\n");	
			} 
			arquivo3.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  grupos "+e.getMessage());
		}
	}
}

