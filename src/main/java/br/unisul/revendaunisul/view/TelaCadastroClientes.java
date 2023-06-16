package br.unisul.revendaunisul.view;

import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.service.ClienteService;
import br.unisul.revendaunisul.utils.Mascara;

@Component
public class TelaCadastroClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;
	private JTextField edtDataDeNascimento;
	private JTextField edtCpf;
	private JTextField edtTelefone;
	private JButton btnSalvar;	
	private Cliente cliente = new Cliente();
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private TelaListagemClientes telaListagemClientes;

	public TelaCadastroClientes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 150);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		
		JLabel lblDtNascimento = new JLabel("Data de Nascimento:");
		
		edtDataDeNascimento = new JFormattedTextField(Mascara.criar("##/##/####"));
		edtDataDeNascimento.setColumns(10);
		
		edtCpf = new JFormattedTextField(Mascara.criar("###.###.###-##"));
		edtCpf.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF:");
		
		edtTelefone = new JFormattedTextField(Mascara.criar("(##)#####-####"));
		edtTelefone.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(e -> {
			
			this.cliente.setCpf(edtCpf.getText());
			this.cliente.setNomeCompleto(edtNome.getText());
			this.cliente.setTelefone(edtTelefone.getText());
			
			String[] camposDaData = edtDataDeNascimento.getText().split("/");
			this.cliente.setDataDeNascimento(
					LocalDate.of(
							Integer.parseInt(camposDaData[2]),
							Integer.parseInt(camposDaData[1]),
							Integer.parseInt(camposDaData[0])));
			
			if (this.cliente.getId() != null) {
				this.cliente = service.alterar(cliente);
				JOptionPane.showMessageDialog(contentPane, "Cliente alterado com sucesso!");
				telaListagemClientes.atualizarTabela();
			} else {
				this.cliente = service.inserir(cliente);
				JOptionPane.showMessageDialog(contentPane, "Cliente salvo com sucesso!");
			}
			
			setVisible(false);
			
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCpf, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDtNascimento)
							.addGap(6)
							.addComponent(edtDataDeNascimento, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(12)
									.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
								.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtDataDeNascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDtNascimento)
						.addComponent(lblNome))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTelefone)
						.addComponent(lblCpf))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSalvar)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	private void limparCampos() {
		edtNome.setText("");
		edtDataDeNascimento.setText("");
		edtCpf.setText("");
		edtTelefone.setText("");
	}

	private void preencherCampos(Cliente colaborador) {
		edtNome.setText(colaborador.getNomeCompleto());
		edtDataDeNascimento.setText(colaborador.getDataDeNascimentoFormatada());
		edtCpf.setText(colaborador.getCpf());
		edtTelefone.setText(colaborador.getTelefone());
	}

	public void colocarEmInsercao() {
		this.cliente = new Cliente();
		this.limparCampos();
		setVisible(true);
	}

	public void colocarEmEdicao(Cliente colaborador) {
		this.cliente = colaborador;
		this.preencherCampos(colaborador);
		setVisible(true);
	}

}
