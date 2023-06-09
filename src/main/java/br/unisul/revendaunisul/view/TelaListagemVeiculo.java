package br.unisul.revendaunisul.view;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Veiculo> veiculos = service.listarPor(
					(Modelo) cbModelo.getSelectedItem());
			
			VeiculoTableModel model = new VeiculoTableModel(veiculos);
			table.setModel(model);
			TableColumnModel cm = table.getColumnModel();
			cm.getColumn(0).setPreferredWidth(25);
			cm.getColumn(2).setPreferredWidth(117);
			cm.getColumn(3).setPreferredWidth(90);
			table.updateUI();
		});

		JButton btnNovo = new JButton("Novo");

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			VeiculoTableModel model = (VeiculoTableModel) table.getModel();
			Veiculo veiculoSalvo = model.getBy(linhaSelecionada);
			modeloService.excluirPor(veiculoSalvo.getId());
			model.removeBy(linhaSelecionada);
			table.updateUI();
			JOptionPane.showMessageDialog(contentPane, "Modelo removido com sucesso!");

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

		JLabel LblMarca = new JLabel("Marca:");

		cbMarca = new JComboBox<Marca>();
		for (Marca m : marcaService.listarTodos()) {
			cbMarca.addItem(m);
		}

		JLabel lblTipo = new JLabel("Modelo:");

		cbModelo = new JComboBox<Modelo>();
		for (Modelo m : modeloService.listarPor((Marca) cbMarca.getSelectedItem())) {
			cbModelo.addItem(m);
		}

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
							.addComponent(lblTipo)
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
								.addComponent(lblTipo))
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
}
