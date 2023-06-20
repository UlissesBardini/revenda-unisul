package br.unisul.revendaunisul.view.cadastro;

import java.awt.event.ItemEvent;
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

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.entity.Venda;
import br.unisul.revendaunisul.enums.FormaDePagamento;
import br.unisul.revendaunisul.service.ClienteService;
import br.unisul.revendaunisul.service.ColaboradorService;
import br.unisul.revendaunisul.service.VeiculoService;
import br.unisul.revendaunisul.service.VendaService;

@Component
public class TelaCadastroVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtParcelas;
	private JComboBox<Veiculo> cbVeiculo;
	private JComboBox<Colaborador> cbColaborador;
	private JComboBox<FormaDePagamento> cbPagamento;
	private JComboBox<Cliente> cbCliente;
	private List<Veiculo> veiculos;
	private List<Colaborador> colaboradores;
	private List<Cliente> clientes;
	private Venda venda;
	
	@Autowired
	private VendaService service;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private ColaboradorService colaboradorService;
	
	@Autowired
	private ClienteService clienteService;
	

	public TelaCadastroVenda() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblVeiculo = new JLabel("Veículo:");
				
		cbVeiculo = new JComboBox<Veiculo>();
		
		JLabel lblColaborador = new JLabel("Colaborador:");
		
		cbColaborador = new JComboBox<Colaborador>();
		
		JLabel lblPagamento = new JLabel("Pagamento:");

		cbPagamento = new JComboBox<FormaDePagamento>();
		for (FormaDePagamento f : FormaDePagamento.values()) {
			cbPagamento.addItem(f);
		}
		
		cbPagamento.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (cbPagamento.getSelectedItem() == FormaDePagamento.A_VISTA) {
					edtParcelas.setEnabled(false);
					edtParcelas.setText("");
				} else {
					edtParcelas.setEnabled(true);
				}
			}
		});
		
		JLabel lblCliente = new JLabel("Cliente:");
		
		cbCliente = new JComboBox<Cliente>();
		
		JLabel lblParcelas = new JLabel("Parcelas:");
		
		edtParcelas = new JTextField();
		edtParcelas.setColumns(10);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(event -> {
			try {
				this.venda.setVeiculo((Veiculo) cbVeiculo.getSelectedItem());
				this.venda.setColaborador((Colaborador) cbColaborador.getSelectedItem());
				this.venda.setCliente((Cliente) cbCliente.getSelectedItem());
				this.venda.setFormaDePagamento((FormaDePagamento) cbPagamento.getSelectedItem());
				
				if (edtParcelas.getText().isBlank()) {					
					this.venda.setQuantidadeDeParcelas(0);
				} else {
					this.venda.setQuantidadeDeParcelas(Integer.parseInt(edtParcelas.getText().trim()));
				}
				
				if (this.venda.getId() != null) {
					this.venda = service.alterar(venda);
					JOptionPane.showMessageDialog(contentPane, "Venda alterada com sucesso!");
				} else {
					this.venda = service.inserir(venda);
					JOptionPane.showMessageDialog(contentPane, "Venda salva com sucesso!");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(contentPane, e.getMessage());
				e.printStackTrace();
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPagamento, Alignment.TRAILING)
						.addComponent(lblColaborador, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
						.addComponent(lblVeiculo, Alignment.TRAILING))
					.addGap(4)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(cbVeiculo, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cbColaborador, 0, 135, Short.MAX_VALUE)
								.addComponent(cbPagamento, Alignment.TRAILING, 0, 135, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblCliente)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbCliente, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblParcelas)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(edtParcelas, 0, 0, Short.MAX_VALUE))
								.addComponent(btnNewButton, Alignment.TRAILING))
							.addGap(89)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVeiculo)
						.addComponent(cbVeiculo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(16)
							.addComponent(lblColaborador))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbColaborador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbCliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCliente))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbPagamento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPagamento)
						.addComponent(lblParcelas)
						.addComponent(edtParcelas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void preencherCampos(Venda venda) {
		this.carregarOpcoes();
		cbVeiculo.setSelectedItem(venda.getVeiculo());
		cbColaborador.setSelectedItem(venda.getColaborador());
		cbCliente.setSelectedItem(venda.getCliente());
		cbPagamento.setSelectedItem(venda.getFormaDePagamento());
		edtParcelas.setText(String.valueOf(venda.getQuantidadeDeParcelas()));
	}
	
	private void limparCampos() {
		this.carregarOpcoes();
		cbVeiculo.setSelectedItem(0);
		cbColaborador.setSelectedItem(0);
		cbCliente.setSelectedItem(0);
		cbPagamento.setSelectedItem(0);
		edtParcelas.setText("");
	}
	
	private void carregarOpcoes() {
		veiculos = veiculoService.listarTodos();
		cbVeiculo.removeAllItems();
		for (Veiculo v : veiculos) {
			cbVeiculo.addItem(v);
		}
		
		colaboradores = colaboradorService.listarTodos();
		cbColaborador.removeAllItems();
		for (Colaborador c : colaboradores) {
			cbColaborador.addItem(c);
		}
		
		clientes = clienteService.listarTodos();
		cbCliente.removeAllItems();
		for (Cliente c : clientes) {
			cbCliente.addItem(c);
		}
	}

	public void colocarEmEdicao(Venda vendaSalva) {
		this.venda = vendaSalva;
		this.preencherCampos(vendaSalva);
		this.setVisible(true);
	}

	public void colocarEmInsercao() {
		this.venda = new Venda();
		this.limparCampos();
		this.setVisible(true);
	}
}
