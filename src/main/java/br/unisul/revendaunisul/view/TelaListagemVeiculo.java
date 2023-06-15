package br.unisul.revendaunisul.view;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.service.MarcaService;
import br.unisul.revendaunisul.service.ModeloService;
import br.unisul.revendaunisul.service.VeiculoService;
import br.unisul.revendaunisul.view.tables.VeiculoTableModel;

@Component
public class TelaListagemVeiculo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private List<Marca> marcas;
	private List<Modelo> modelos;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Modelo> cbModelo;

	@Autowired
	private VeiculoService service;
	
	@Autowired
	private ModeloService modeloService;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private TelaCadastroVeiculo cadastro;
	
	public TelaListagemVeiculo() {
		setTitle("Gerenciar Veículos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Veiculo> veiculos = service.listarPor(
					(Modelo) cbModelo.getSelectedItem());
			
			VeiculoTableModel model = new VeiculoTableModel(veiculos);
			table.setModel(model);
			TableColumnModel cm = table.getColumnModel();
			cm.getColumn(0).setPreferredWidth(25);
			cm.getColumn(2).setPreferredWidth(42);
			cm.getColumn(3).setPreferredWidth(60);
			table.updateUI();
		});

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(e -> {
			cadastro.colocarEmInsercao();
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			VeiculoTableModel model = (VeiculoTableModel) table.getModel();
			Veiculo veiculoSalvo = model.getBy(linhaSelecionada);
			service.excluirPor(veiculoSalvo.getId());
			model.removeBy(linhaSelecionada);
			table.updateUI();
			JOptionPane.showMessageDialog(contentPane, "Veículo removido com sucesso!");

		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			VeiculoTableModel model = (VeiculoTableModel) table.getModel();
			Veiculo veiculoSalvo = model.getBy(linhaSelecionada);
			cadastro.colocarEmEdicao(veiculoSalvo);
		});

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblMarca = new JLabel("Modelo:");
		
		cbModelo = new JComboBox<Modelo>();

		JLabel LblMarca = new JLabel("Marca:");

		cbMarca = new JComboBox<Marca>();
		
		cbMarca.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				cbModelo.removeAllItems();
				modelos = modeloService.listarPor((Marca) cbMarca.getSelectedItem());
				for (Modelo m : modelos) {
					cbModelo.addItem(m);
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(LblMarca, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMarca)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbModelo, 0, 153, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuscar))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(LblMarca)
								.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMarca))
							.addPreferredGap(ComponentPlacement.RELATED, 3, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnBuscar)
							.addComponent(cbModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNovo)
						.addComponent(btnExcluir)
						.addComponent(btnEditar))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	@Override
	public void setVisible(boolean b) {
		this.carregarOpcoes();
		super.setVisible(b);
	}
	
	private void carregarOpcoes() {
		marcas = marcaService.listarTodos();
		cbMarca.removeAllItems();
		for (Marca m : marcas) {
			cbMarca.addItem(m);
		}
	}
	
}
