package br.unisul.revendaunisul.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.enums.Perfil;
import br.unisul.revendaunisul.view.cadastro.TelaCadastroColaboradores;
import br.unisul.revendaunisul.view.listagem.TelaListagemClientes;
import br.unisul.revendaunisul.view.listagem.TelaListagemColaboradores;
import br.unisul.revendaunisul.view.listagem.TelaListagemMarca;
import br.unisul.revendaunisul.view.listagem.TelaListagemModelo;
import br.unisul.revendaunisul.view.listagem.TelaListagemVeiculo;
import br.unisul.revendaunisul.view.listagem.TelaListagemVenda;

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
	
	private Usuario usuarioLogado;
	
	@Autowired
	@Lazy
	private TelaLogin telaLogin;
	
	@Autowired
	private TelaListagemMarca listagemMarca;

	@Autowired
	private TelaListagemModelo listagemModelo;

	@Autowired
	private TelaListagemVeiculo listagemVeiculo;
	
	@Autowired
	private TelaListagemColaboradores listagemColaboradores;
	
	@Autowired
	private TelaListagemClientes listagemClientes;
	
	@Autowired
	private TelaListagemVenda listagemVenda;
	
	@Autowired
	private TelaCadastroColaboradores cadastroColaboradores;

	public void abrir(Usuario usuario) {
		this.usuarioLogado = usuario;
		if (this.usuarioLogado.getPerfil() == Perfil.GERENTE) {
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
		setVisible(true);
		telaLogin.setVisible(false);
	}
	
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		//captura o evento de click no X e redireciona para a tela de login
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						telaLogin.setVisible(true);
						setVisible(false);
					}
				});
		
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

		JMenuItem itListagemColaborador = new JMenuItem("Listagem");
		
		itListagemColaborador.addActionListener(e -> {
			if (this.usuarioLogado.getPerfil() == Perfil.GERENTE) {
				listagemColaboradores.setVisible(true);
				setVisible(false);
			} else {
				cadastroColaboradores.colocarEmEdicao(this.usuarioLogado);
			}
		});
		mnColaborador.add(itListagemColaborador);
		
		mnCliente = new JMenu("Cliente");
		opcoes.add(mnCliente);
		
		JMenuItem itListagemCliente = new JMenuItem("Listagem");
		itListagemCliente.addActionListener(e -> {
			listagemClientes.setVisible(true);
			setVisible(false);
		});
		mnCliente.add(itListagemCliente);
		
		mnVenda = new JMenu("Venda");
		opcoes.add(mnVenda);
		
		JMenuItem itListagemVenda = new JMenuItem("Listagem");
		itListagemVenda.addActionListener(e -> {
			listagemVenda.setVisible(true);
		});
		
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
