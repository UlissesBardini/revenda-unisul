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
import br.unisul.revendaunisul.view.tables.ModeloTableModel;

@Component
public class TelaListagemModelo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable table;
	private JComboBox<Marca> cbMarca;
	private JComboBox<TipoDeVeiculo> cbTipo;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);

		JLabel LblNome = new JLabel("Nome:");

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Modelo> modelos = service.listarPor(
					edtFiltro.getText(),
					(Marca) cbMarca.getSelectedItem(),
					(TipoDeVeiculo) cbTipo.getSelectedItem());
			
			ModeloTableModel model = new ModeloTableModel(modelos);
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
			ModeloTableModel model = (ModeloTableModel) table.getModel();
			Modelo modeloSalvo = model.getBy(linhaSelecionada);
			service.excluirPor(modeloSalvo.getId());
			model.removeBy(linhaSelecionada);
			table.updateUI();
			JOptionPane.showMessageDialog(contentPane, "Modelo removido com sucesso!");

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
		List<Marca> marcas = marcaService.listarTodos();
		for (Marca m : marcas) {
			cbMarca.addItem(m);
		}

		JLabel lblTipo = new JLabel("Tipo:");

		cbTipo = new JComboBox<TipoDeVeiculo>();
		for (TipoDeVeiculo t : TipoDeVeiculo.values()) {
			cbTipo.addItem(t);
		}

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404,
										Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(LblNome).addComponent(LblMarca,
														GroupLayout.PREFERRED_SIZE, 34,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(cbMarca,
																GroupLayout.PREFERRED_SIZE, 130,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblTipo,
																GroupLayout.PREFERRED_SIZE, 28,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(cbTipo, 0, 129,
																Short.MAX_VALUE))
												.addComponent(edtFiltro, Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE, 295,
														Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnBuscar)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LblNome).addComponent(btnBuscar))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(LblMarca)
								.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbTipo, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTipo))
						.addGap(14)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNovo).addComponent(btnExcluir)
								.addComponent(btnEditar))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}
}
