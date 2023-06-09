package br.unisul.revendaunisul.view.cadastro;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.enums.Combustivel;
import br.unisul.revendaunisul.enums.TipoDeVeiculo;
import br.unisul.revendaunisul.enums.Transmissao;
import br.unisul.revendaunisul.service.MarcaService;
import br.unisul.revendaunisul.service.ModeloService;

@Component
public class TelaCadastroModelo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;
	private Modelo modelo;
	private List<Marca> marcas;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Combustivel> cbCombustivel;
	private JComboBox<TipoDeVeiculo> cbTipo;
	private JComboBox<Transmissao> cbTransmissao;;
	
	@Autowired
	private ModeloService service;
	
	@Autowired
	private MarcaService marcaService;
	
	public TelaCadastroModelo() {
		setTitle("Cadastrar Modelo");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 500, 193);
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
				this.modelo.setNome(edtNome.getText().trim());
				this.modelo.setMarca((Marca) cbMarca.getSelectedItem());
				this.modelo.setCombustivel((Combustivel) cbCombustivel.getSelectedItem());
				this.modelo.setTipo((TipoDeVeiculo) cbTipo.getSelectedItem());
				this.modelo.setTransmissao((Transmissao) cbTransmissao.getSelectedItem());
				
				if (this.modelo.getId() != null) {
					this.modelo = service.alterar(modelo);
					JOptionPane.showMessageDialog(contentPane, "Modelo alterado com sucesso!");
				} else {
					this.modelo = service.inserir(modelo);
					JOptionPane.showMessageDialog(contentPane, "Modelo salvo com sucesso!");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(contentPane, e.getMessage());
			}
		});
		
		JLabel lblMarca = new JLabel("Marca:");

		cbMarca = new JComboBox<Marca>();
		
		JLabel lblCombustivel = new JLabel("Combustível:");
		
		cbCombustivel = new JComboBox<Combustivel>();
		for (Combustivel c : Combustivel.values()) {
			cbCombustivel.addItem(c);
		}
		
		JLabel lblTipo = new JLabel("Tipo:");
		
		cbTipo = new JComboBox<TipoDeVeiculo>();
		for (TipoDeVeiculo t : TipoDeVeiculo.values()) {
			cbTipo.addItem(t);
		}
		
		JLabel lblTransmissao = new JLabel("Transmissão:");

		cbTransmissao = new JComboBox<Transmissao>();
		for (Transmissao t : Transmissao.values()) {
			cbTransmissao.addItem(t);
		}
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(LblNome, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
									.addGap(6))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblMarca, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addComponent(lblTipo)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cbMarca, 0, 141, Short.MAX_VALUE)
										.addComponent(cbTipo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblCombustivel)
										.addComponent(lblTransmissao))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(cbCombustivel, 0, 143, Short.MAX_VALUE)
										.addComponent(cbTransmissao, Alignment.LEADING, 0, 143, Short.MAX_VALUE)))
								.addComponent(edtNome, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)))
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbCombustivel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMarca)
						.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCombustivel))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(cbTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTipo)
							.addComponent(lblTransmissao))
						.addComponent(cbTransmissao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSalvar)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void carregarOpcoes() {
		this.marcas = marcaService.listarTodos();
		this.cbMarca.removeAllItems();
		for (Marca m : marcas) {
			cbMarca.addItem(m);
		}
	}
	
	private void limparCampos() {
		this.carregarOpcoes();
		edtNome.setText("");
		cbMarca.setSelectedItem(0);
		cbCombustivel.setSelectedItem(0);
		cbTipo.setSelectedItem(0);
		cbTransmissao.setSelectedItem(0);
	}
	
	private void preencherCampos(Modelo modelo) {
		this.carregarOpcoes();
		edtNome.setText(modelo.getNome());
		cbMarca.setSelectedItem(modelo.getMarca());
		cbCombustivel.setSelectedItem(modelo.getCombustivel());
		cbTipo.setSelectedItem(modelo.getTipo());
		cbTransmissao.setSelectedItem(modelo.getTransmissao());
	}
	
	public void colocarEmInsercao() {
		this.modelo = new Modelo();
		this.limparCampos();
		super.setVisible(true);
	}
	
	public void colocarEmEdicao(Modelo modelo) {
		this.modelo = modelo;
		this.preencherCampos(modelo);
		super.setVisible(true);
	}
}
