package br.unisul.revendaunisul.view.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import br.unisul.revendaunisul.view.listagem.TelaListagemClientes;

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
		setBounds(100, 100, 465, 150);
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
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalvar.addActionListener(e -> {
			try {
			this.cliente.setNomeCompleto(edtNome.getText().trim());

			if (edtCpf.getText().equals("___.___.___-__")) {
				throw new IllegalArgumentException("O CPF é obrigatório");
			} else {
				this.cliente.setCpf(edtCpf.getText());
			}

			if (edtTelefone.getText().equals("(__)_____-____")) {
				throw new IllegalArgumentException("O telefone é obrigatório");
			} else {
				this.cliente.setTelefone(edtTelefone.getText());
			}

			String[] camposDaData = edtDataDeNascimento.getText().split("/");
			this.cliente.setDataDeNascimento(LocalDate.of(Integer.parseInt(camposDaData[2]),
					Integer.parseInt(camposDaData[1]), Integer.parseInt(camposDaData[0])));

			if (this.cliente.getId() != null) {
				this.cliente = service.alterar(cliente);
				JOptionPane.showMessageDialog(contentPane, "Cliente alterado com sucesso!");
				telaListagemClientes.atualizarTabela();
			} else {
				this.cliente = service.inserir(cliente);
				JOptionPane.showMessageDialog(contentPane, "Cliente salvo com sucesso!");
			}

			setVisible(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(contentPane, ex.getMessage());
			}

		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblCpf, GroupLayout.PREFERRED_SIZE, 25,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNome, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, 179,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 57,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, 114,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblDtNascimento)))
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(edtDataDeNascimento, GroupLayout.PREFERRED_SIZE,
																107, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(
														edtTelefone, GroupLayout.PREFERRED_SIZE, 114,
														GroupLayout.PREFERRED_SIZE)))))
						.addGap(17)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblNome)
								.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(edtTelefone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTelefone))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblCpf)
								.addComponent(edtCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDtNascimento).addComponent(edtDataDeNascimento,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSalvar)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
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

	public void colocarEmEdicao(Cliente cliente) {
		this.cliente = cliente;
		this.preencherCampos(cliente);
		setVisible(true);
	}

}
