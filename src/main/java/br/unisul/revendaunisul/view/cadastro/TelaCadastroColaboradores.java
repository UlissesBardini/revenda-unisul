package br.unisul.revendaunisul.view.cadastro;

import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.enums.Perfil;
import br.unisul.revendaunisul.service.ColaboradorService;
import br.unisul.revendaunisul.utils.Mascara;
import br.unisul.revendaunisul.view.listagem.TelaListagemColaboradores;

@Component
public class TelaCadastroColaboradores extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;
	private JTextField edtDataNascimento;
	private JTextField edtCpf;
	private JTextField edtTelefone;
	private JTextField edtLogin;
	private JPasswordField edtSenha;
	private JComboBox<Perfil> cbPerfil;
	private Colaborador colaborador = new Colaborador();
	private Usuario usuario;

	@Autowired
	private ColaboradorService service;
	
	@Autowired
	@Lazy
	private TelaListagemColaboradores telaListagemColaboradores;
	
	private void limparCampos() {
		edtNome.setText("");
		edtDataNascimento.setText("");
		edtCpf.setText("");
		cbPerfil.setSelectedItem(0);
		edtTelefone.setText("");
		edtLogin.setText("");
		edtSenha.setText("");
	}

	private void preencherCampos(Colaborador colaborador) {
		edtNome.setText(colaborador.getNomeCompleto());
		edtDataNascimento.setText(colaborador.getDataDeNascimentoFormatada());
		edtCpf.setText(colaborador.getCpf());
		cbPerfil.setSelectedItem(colaborador.getUsuario().getPerfil());
		edtTelefone.setText(colaborador.getTelefone());
		edtLogin.setText(colaborador.getUsuario().getLogin());
		edtSenha.setText(colaborador.getUsuario().getSenha());
	}

	public void colocarEmInsercao() {
		this.colaborador = new Colaborador();
		this.colaborador.setUsuario(new Usuario());
		this.limparCampos();
		setVisible(true);
	}

	public void colocarEmEdicao(Colaborador colaborador) {
		this.colaborador = colaborador;
		this.preencherCampos(colaborador);
		setVisible(true);
	}
	
	public void colocarEmEdicao(Usuario usuario) {
		Colaborador colaboradorLogado = service.buscarPor(usuario);
		colocarEmEdicao(colaboradorLogado);
	}
	
	public TelaCadastroColaboradores() {
		setTitle("Cadastro Colaborador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 190);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblNome = new JLabel("Nome:");
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		
		JLabel lblDtNascimento = new JLabel("Data de Nascimento:");
		
		edtDataNascimento = new JFormattedTextField(Mascara.criar("##/##/####"));
		edtDataNascimento.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF:");
		
		edtCpf = new JFormattedTextField(Mascara.criar("###.###.###-##"));
		edtCpf.setColumns(10);
		
		JLabel lblPerfil = new JLabel("Perfil:");
		
		cbPerfil = new JComboBox<Perfil>();
		cbPerfil.addItem(Perfil.FUNCIONARIO);
		cbPerfil.addItem(Perfil.GERENTE);
		cbPerfil.setToolTipText("Selecione...");
		
		edtTelefone = new JFormattedTextField(Mascara.criar("(##)#####-####"));
		edtTelefone.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		
		JLabel lblSenha = new JLabel("Senha:");
		
		edtLogin = new JTextField();
		edtLogin.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login:");
		
		edtSenha = new JPasswordField();
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(event -> {
			try {
				
				this.colaborador.setCpf(edtCpf.getText());
				this.colaborador.setNomeCompleto(edtNome.getText());
				this.colaborador.setTelefone(edtTelefone.getText());
				try {
				String[] camposDaData = edtDataNascimento.getText().split("/");			
				this.colaborador.setDataDeNascimento(
						LocalDate.of(
								Integer.parseInt(camposDaData[2]),
								Integer.parseInt(camposDaData[1]),
								Integer.parseInt(camposDaData[0])));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(contentPane, "A data de nascimento inserida é inválida");
				}
				
				if(this.colaborador.getUsuario().getId() != null ) {
					this.usuario = colaborador.getUsuario();
					this.usuario.setLogin(edtLogin.getText());
					this.usuario.setSenha(new String(edtSenha.getPassword()));
					this.usuario.setPerfil((Perfil) cbPerfil.getSelectedItem());
				} else {
					Usuario usuario = new Usuario();
					usuario.setLogin(edtLogin.getText());
					usuario.setSenha(new String(edtSenha.getPassword()));
					usuario.setPerfil((Perfil) cbPerfil.getSelectedItem());
					this.colaborador.setUsuario(usuario);
				}
				

				if (this.colaborador.getId() != null) {
					this.colaborador = service.alterar(colaborador);
					JOptionPane.showMessageDialog(contentPane, "Colaborador alterado com sucesso!");
					telaListagemColaboradores.atualizarTabela();
				} else {
					this.colaborador = service.inserir(colaborador);
					JOptionPane.showMessageDialog(contentPane, "Colaborador salvo com sucesso!");
				}

				setVisible(false);
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(contentPane, e.getMessage());
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLogin, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
								.addComponent(lblCpf, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSenha)
									.addGap(6)
									.addComponent(edtSenha, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblPerfil)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbPerfil, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edtNome, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblDtNascimento)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome)
						.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDtNascimento))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTelefone)
						.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCpf)
						.addComponent(lblPerfil)
						.addComponent(cbPerfil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLogin)
						.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSenha))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
