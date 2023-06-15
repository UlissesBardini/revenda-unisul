package br.unisul.revendaunisul.view;

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
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.enums.Perfil;
import br.unisul.revendaunisul.service.ColaboradorService;
import br.unisul.revendaunisul.utils.Mascara;

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
	private Colaborador colaborador;

	@Autowired
	private ColaboradorService service;
	
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
		edtSenha.setText(colaborador.getUsuario().getLogin());
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
	
	
	public TelaCadastroColaboradores() {
		setTitle("Cadastro Colaborador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 190);
		//difine 0 nas coordenadas X e Y fazendo que a tela fique centralizada
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
				
				String[] camposDaData = edtDataNascimento.getText().split("/");			
				this.colaborador.setDataDeNascimento(
						LocalDate.of(
								Integer.parseInt(camposDaData[2]),
								Integer.parseInt(camposDaData[1]),
								Integer.parseInt(camposDaData[0])));
				
				Usuario usuario = this.colaborador.getUsuario();
				usuario.setPerfil((Perfil) cbPerfil.getSelectedItem());
				usuario.setLogin(edtLogin.getText());
				usuario.setSenha(new String(edtSenha.getPassword()));

				if (this.colaborador.getId() != null) {
					this.colaborador = service.alterar(colaborador);
					JOptionPane.showMessageDialog(contentPane, "Colaborador alterado com sucesso!");
				} else {
					this.colaborador = service.inserir(colaborador);
					JOptionPane.showMessageDialog(contentPane, "Colaborador salvo com sucesso!");
				}

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
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtNome, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDtNascimento)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblCpf, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSenha)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(edtSenha, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblPerfil)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbPerfil, 0, 167, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(btnSalvar, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNome)
							.addComponent(edtDataNascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblDtNascimento))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbPerfil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCpf)
						.addComponent(lblTelefone)
						.addComponent(lblPerfil))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLogin)
						.addComponent(edtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSenha))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSalvar)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
