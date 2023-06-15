package br.unisul.revendaunisul.view;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.enums.Perfil;

@Component
public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar opcoes;
	private JMenu mnMarca;
	private JMenu mnModelo;
	private JMenu mnVeiculo;
	private JMenu mnColaborador;
	private JMenu mnCliente;
	private JMenu mnVenda;
	
	private Perfil perfil;
	
	@Autowired
	private TelaListagemMarca listagemMarca;

	@Autowired
	private TelaListagemModelo listagemModelo;

	@Autowired
	private TelaListagemVeiculo listagemVeiculo;

	public void abrir(Perfil perfil) {
		this.perfil = perfil;
		if (this.perfil == Perfil.GERENTE) {
			mnMarca.setVisible(true);
			mnModelo.setVisible(true);
			mnVeiculo.setVisible(true);
			mnColaborador.setVisible(true);
			mnCliente.setVisible(true);
			mnVenda.setVisible(true);
		} else {
			mnMarca.setVisible(false);
			mnModelo.setVisible(false);
			mnVeiculo.setVisible(false);
			mnColaborador.setVisible(true);
			mnCliente.setVisible(true);
			mnVenda.setVisible(true);
		}
	}
	
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		opcoes = new JMenuBar();
		setJMenuBar(opcoes);
		
		mnMarca = new JMenu("Marca");
		opcoes.add(mnMarca);
		
		JMenuItem itListagemMarca = new JMenuItem("Listagem");
		itListagemMarca.addActionListener(e -> {
			listagemMarca.setVisible(true);
		});
		mnMarca.add(itListagemMarca);
		
		mnModelo = new JMenu("Modelo");
		opcoes.add(mnModelo);
		
		JMenuItem itListagemModelo = new JMenuItem("Listagem");
		itListagemModelo.addActionListener(e -> {
			listagemModelo.setVisible(true);
		});
		mnModelo.add(itListagemModelo);
		
		mnVeiculo = new JMenu("Veículo");
		opcoes.add(mnVeiculo);
		
		JMenuItem itListagemVeiculo = new JMenuItem("Listagem");
		itListagemVeiculo.addActionListener(e -> {
			listagemVeiculo.setVisible(true);
		});
		mnVeiculo.add(itListagemVeiculo);
		
		mnColaborador = new JMenu("Colaborador");
		opcoes.add(mnColaborador);
		
		JMenuItem itListagemColaborador = new JMenuItem(this.perfil == Perfil.GERENTE ? "Listagem" : "Cadastro");
		itListagemColaborador.addActionListener(e -> {
			if (this.perfil == Perfil.GERENTE) {
				//TODO abrir tela de listagem de colaboradores
			} else {
				//TODO abrir tela de cadastro de colaboradores em modo de edição com o registro do colaborador logado
			}
		});
		mnColaborador.add(itListagemColaborador);
		
		mnCliente = new JMenu("Cliente");
		opcoes.add(mnCliente);
		
		JMenuItem itListagemCliente = new JMenuItem("Listagem");
		mnCliente.add(itListagemCliente);
		
		mnVenda = new JMenu("Venda");
		opcoes.add(mnVenda);
		
		JMenuItem itListagemVenda = new JMenuItem("Listagem");
		mnVenda.add(itListagemVenda);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 251, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}

}