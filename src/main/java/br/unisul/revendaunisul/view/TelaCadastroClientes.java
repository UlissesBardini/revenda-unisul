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
	private Cliente cliente;
	
	@Autowired
	private ClienteService service;

	public TelaCadastroClientes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 141);
		//Define 0 nas coordenadas X e Y fazendo que a tela fique centralizada
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
			} else {
				this.cliente = service.inserir(cliente);
				JOptionPane.showMessageDialog(contentPane, "Cliente salvo com sucesso!");
			}
			
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblCpf, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtCpf, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTelefone, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtTelefone, 157, 157, 157))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtNome, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDtNascimento, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtDataDeNascimento, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNome)
						.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtDataDeNascimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDtNascimento))
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
