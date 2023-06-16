package br.unisul.revendaunisul.view.cadastro;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.service.MarcaService;

@Component
public class TelaCadastroMarca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;
	private Marca marca;
	
	@Autowired
	private MarcaService service;
	

	public TelaCadastroMarca() {
		setTitle("Cadastrar Marca");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 115);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		
		edtNome = new JTextField();
		edtNome.setColumns(10);
		
		JLabel LblNome = new JLabel("Nome: ");
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(event -> {
			try {
				this.marca.setNome(edtNome.getText().trim());
				if (this.marca.getId() != null) {
					this.marca = service.alterar(marca);
					JOptionPane.showMessageDialog(contentPane, "Marca alterada com sucesso!");
				} else {
					this.marca = service.inserir(marca);
					JOptionPane.showMessageDialog(contentPane, "Marca salva com sucesso!");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(contentPane, e.getMessage());
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(LblNome)
							.addGap(7)
							.addComponent(edtNome, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
						.addComponent(btnSalvar, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LblNome))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(btnSalvar))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void limparCampos() {
		this.edtNome.setText("");
	}
	
	public void colocarEmInsercao() {
		this.marca = new Marca();
		this.limparCampos();
		super.setVisible(true);
	}
	
	private void preencherCampos(Marca marca) {
		this.edtNome.setText(marca.getNome());
	}
	
	public void colocarEmEdicao(Marca marca) {
		this.marca = marca;
		this.preencherCampos(marca);
		super.setVisible(true);
	}
	
}
