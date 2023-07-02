package appswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import modelo.Grupo;
import modelo.Individual;
import regras_negocio.Fachada;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGerenciarGrupos {

	private JFrame frameGerenciarGrupos;
	JComboBox<String> individuosComboBox;
	JComboBox<String> gruposComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaGerenciarGrupos window = new TelaGerenciarGrupos();
					window.frameGerenciarGrupos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaGerenciarGrupos() {
		initialize();
		frameGerenciarGrupos.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameGerenciarGrupos = new JFrame();
		frameGerenciarGrupos.setResizable(false);
		frameGerenciarGrupos.setTitle("Gerenciar Grupos");
		frameGerenciarGrupos.setBounds(100, 100, 538, 323);
		frameGerenciarGrupos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frameGerenciarGrupos.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblSelecioneUmGrupo = new JLabel("Selecione um grupo:");
		lblSelecioneUmGrupo.setBounds(48, 109, 128, 16);
		panel.add(lblSelecioneUmGrupo);
		
		gruposComboBox = new JComboBox<>();
		gruposComboBox.setBounds(6, 137, 215, 27);
		panel.add(gruposComboBox);
		
		JLabel lblSelecioneUmIndividuo = new JLabel("Selecione um individuo:");
		lblSelecioneUmIndividuo.setBounds(340, 109, 150, 16);
		panel.add(lblSelecioneUmIndividuo);
		
		JPanel panelOperacao = new JPanel();
		panelOperacao.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Opera\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelOperacao.setBounds(152, 16, 222, 37);
		panel.add(panelOperacao);
		panelOperacao.setLayout(new GridLayout(0, 2));
		
		JRadioButton radioInserir = new JRadioButton("inserir");
		radioInserir.setSelected(true);
		panelOperacao.add(radioInserir);
		
		JRadioButton radioRemover = new JRadioButton("remover");
		radioRemover.setSelected(true);
		panelOperacao.add(radioRemover);
		
		ButtonGroup grupobotoes = new ButtonGroup();
		grupobotoes.add(radioInserir);
		grupobotoes.add(radioRemover);
		
		individuosComboBox = new JComboBox<>();
		individuosComboBox.setBounds(317, 137, 215, 27);
		panel.add(individuosComboBox);
		
		JLabel lblMensagem = new JLabel("");
		lblMensagem.setForeground(Color.RED);
		lblMensagem.setBounds(30, 273, 478, 16);
		panel.add(lblMensagem);
		
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int indexGrupo = gruposComboBox.getSelectedIndex();
					int indexIndividuos = individuosComboBox.getSelectedIndex();
					if (indexGrupo < 0 || indexIndividuos < 0)
						lblMensagem.setText("Selecione tanto um grupo quanto um indivíduo");
					else 
						if (radioInserir.isSelected()) {
							String nomeGrupo = (String) gruposComboBox.getSelectedItem();
							String nomeIndividuo = (String) individuosComboBox.getSelectedItem();
							Fachada.inserirGrupo(nomeIndividuo, nomeGrupo);
							lblMensagem.setText("Indivíduo " + nomeIndividuo + " inserido com sucesso no grupo " + nomeGrupo);
						} else 
							if(radioRemover.isSelected()) {
								String nomeGrupo = (String) gruposComboBox.getSelectedItem();
								String nomeIndividuo = (String) individuosComboBox.getSelectedItem();
								Fachada.removerGrupo(nomeIndividuo, nomeGrupo);
								lblMensagem.setText("Indivíduo " + nomeIndividuo + " removido com sucesso do grupo " + nomeGrupo);
							}
				} catch (Exception ex) {
					lblMensagem.setText(ex.getMessage());
				}
			}
		});
		btnExecutar.setBounds(204, 232, 117, 29);
		panel.add(btnExecutar);
		
		frameGerenciarGrupos.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				carregarComboBoxes();
			}
		});
	}
	
	public void carregarComboBoxes() {
		ArrayList<Grupo> listaGrupos = Fachada.listarGrupos();
		ArrayList<Individual> listaIndividuos = Fachada.listarIndividuos();

		DefaultComboBoxModel<String> modelGrupos = new DefaultComboBoxModel<>();
		for (Grupo g : listaGrupos) {
			modelGrupos.addElement(g.getNome());
		}
		
		DefaultComboBoxModel<String> modelIndividuos = new DefaultComboBoxModel<>();
		for (Individual ind : listaIndividuos) {
			modelIndividuos.addElement(ind.getNome());
		}
		
		individuosComboBox.setModel(modelIndividuos);
		gruposComboBox.setModel(modelGrupos);
		individuosComboBox.setSelectedIndex(-1);
		gruposComboBox.setSelectedIndex(-1);
	}
}
