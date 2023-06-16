package br.unisul.revendaunisul.view.listagem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.service.MarcaService;
import br.unisul.revendaunisul.view.TelaPrincipal;
import br.unisul.revendaunisul.view.cadastro.TelaCadastroMarca;
import br.unisul.revendaunisul.view.tables.MarcaTableModel;

@Component
public class TelaListagemMarca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable table;

	@Autowired
	private MarcaService service;

	@Autowired
	private TelaCadastroMarca cadastro;

	@Autowired
	@Lazy
	private TelaPrincipal telaPrincipal;

	public TelaListagemMarca() {
		setTitle("Gerenciar Marcas");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				telaPrincipal.setVisible(true);
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		edtFiltro = new JTextField();
		edtFiltro.setColumns(10);

		JLabel LblNome = new JLabel("Nome: ");

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Marca> marcas = service.listarPor(edtFiltro.getText());
			MarcaTableModel model = new MarcaTableModel(marcas);
			table.setModel(model);
			TableColumnModel cm = table.getColumnModel();
			cm.getColumn(0).setPreferredWidth(45);
			table.updateUI();
		});

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(e -> {
			cadastro.colocarEmInsercao();
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(event -> {
			int linhaSelecionada = table.getSelectedRow();
			MarcaTableModel model = (MarcaTableModel) table.getModel();
			Marca marcaSalva = model.getBy(linhaSelecionada);

			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane,
					"Deseja realmente remover?", "Confirmação", JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				try {
					service.excluirPor(marcaSalva.getId());
					model.removeBy(linhaSelecionada);
					table.updateUI();
					JOptionPane.showMessageDialog(contentPane, "Marca removida com sucesso!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			MarcaTableModel model = (MarcaTableModel) table.getModel();
			Marca marcaSalva = model.getBy(linhaSelecionada);
			cadastro.colocarEmEdicao(marcaSalva);
		});

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404,
										Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(LblNome)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(edtFiltro, GroupLayout.DEFAULT_SIZE, 295,
												Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnBuscar))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 65,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(edtFiltro, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LblNome).addComponent(btnBuscar))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNovo).addComponent(btnExcluir)
								.addComponent(btnEditar))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}
}
