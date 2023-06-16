package br.unisul.revendaunisul.view.listagem;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.enums.TipoDeVeiculo;
import br.unisul.revendaunisul.service.MarcaService;
import br.unisul.revendaunisul.service.ModeloService;
import br.unisul.revendaunisul.view.cadastro.TelaCadastroModelo;
import br.unisul.revendaunisul.view.tables.ModeloTableModel;

@Component
public class TelaListagemModelo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable table;
	private JComboBox<Marca> cbMarca;
	private JComboBox<TipoDeVeiculo> cbTipo;
	private List<Marca> marcas;

	@Autowired
	private ModeloService service;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private TelaCadastroModelo cadastro;

	public TelaListagemModelo() {
		setTitle("Gerenciar Modelos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 325);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);

		JLabel LblNome = new JLabel("Nome:");

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Modelo> modelos = service.listarPor(edtFiltro.getText(),
					(Marca) cbMarca.getSelectedItem(), (TipoDeVeiculo) cbTipo.getSelectedItem());

			ModeloTableModel model = new ModeloTableModel(modelos);
			table.setModel(model);
			TableColumnModel cm = table.getColumnModel();
			cm.getColumn(0).setPreferredWidth(25);
			cm.getColumn(2).setPreferredWidth(117);
			cm.getColumn(3).setPreferredWidth(90);
			table.updateUI();
		});

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(e -> {
			cadastro.colocarEmInsercao();
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			ModeloTableModel model = (ModeloTableModel) table.getModel();
			Modelo modeloSalvo = model.getBy(linhaSelecionada);

			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane,
					"Deseja realmente remover?", "Confirmação", JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				try {
					service.excluirPor(modeloSalvo.getId());
					model.removeBy(linhaSelecionada);
					JOptionPane.showMessageDialog(contentPane, "Modelo removido com sucesso!");
					table.updateUI();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}

		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			ModeloTableModel model = (ModeloTableModel) table.getModel();
			Modelo modeloSalvo = model.getBy(linhaSelecionada);
			cadastro.colocarEmEdicao(modeloSalvo);
		});

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel LblMarca = new JLabel("Marca:");

		cbMarca = new JComboBox<Marca>();

		JLabel lblTipo = new JLabel("Tipo:");

		cbTipo = new JComboBox<TipoDeVeiculo>();
		cbTipo.addItem(null);
		for (TipoDeVeiculo t : TipoDeVeiculo.values()) {
			cbTipo.addItem(t);
		}

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnEditar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(LblNome)
								.addComponent(LblMarca))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTipo, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(cbTipo, 0, 86, Short.MAX_VALUE))
								.addComponent(edtFiltro, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBuscar)
						.addComponent(LblNome)
						.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTipo)
						.addComponent(LblMarca))
					.addGap(14)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNovo)
						.addComponent(btnExcluir)
						.addComponent(btnEditar))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

	private void carregarOpcoes() {
		marcas = marcaService.listarTodos();
		cbMarca.removeAllItems();
		cbMarca.addItem(null);
		for (Marca m : marcas) {
			cbMarca.addItem(m);
		}
	}

	@Override
	public void setVisible(boolean b) {
		this.carregarOpcoes();
		this.cbMarca.setSelectedItem(0);
		super.setVisible(b);
	}

}
