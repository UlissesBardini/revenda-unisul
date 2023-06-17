package br.unisul.revendaunisul.view.listagem;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Venda;
import br.unisul.revendaunisul.service.VendaService;
import br.unisul.revendaunisul.utils.Mascara;
import br.unisul.revendaunisul.view.cadastro.TelaCadastroVenda;
import br.unisul.revendaunisul.view.tables.VendaTableModel;

@Component
public class TelaListagemVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField edtDataFim;
	private JTextField edtDataInicio;

	@Autowired
	private VendaService service;

	@Autowired
	private TelaCadastroVenda cadastro;

	public TelaListagemVenda() {
		setTitle("Gerenciar Vendas");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> {
			List<Venda> vendas = service.listarPor(null, null);

			VendaTableModel model = new VendaTableModel(vendas);
			table.setModel(model);
			TableColumnModel cm = table.getColumnModel();
			DefaultTableCellRenderer centralize = new DefaultTableCellRenderer();
			centralize.setHorizontalAlignment(SwingConstants.CENTER);
			cm.getColumn(0).setCellRenderer(centralize);
			cm.getColumn(3).setCellRenderer(centralize);
			cm.getColumn(0).setPreferredWidth(25);
			cm.getColumn(1).setPreferredWidth(180);
			cm.getColumn(2).setPreferredWidth(115);
			cm.getColumn(3).setPreferredWidth(80);
			table.updateUI();
		});

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(e -> {
			cadastro.colocarEmInsercao();
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(event -> {
			int linhaSelecionada = table.getSelectedRow();
			VendaTableModel model = (VendaTableModel) table.getModel();
			Venda vendaSalva = model.getBy(linhaSelecionada);

			int opcaoSelecionada = JOptionPane.showConfirmDialog(contentPane,
					"Deseja realmente remover?", "Confirmação", JOptionPane.YES_NO_OPTION);

			if (opcaoSelecionada == JOptionPane.YES_OPTION) {
				try {
					service.excluirPor(vendaSalva.getId());
					model.removeBy(linhaSelecionada);
					table.updateUI();
					JOptionPane.showMessageDialog(contentPane, "Venda removida com sucesso!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(e -> {
			int linhaSelecionada = table.getSelectedRow();
			VendaTableModel model = (VendaTableModel) table.getModel();
			Venda vendaSalva = model.getBy(linhaSelecionada);
			cadastro.colocarEmEdicao(vendaSalva);
		});

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);		
		
		JLabel lblDataInicio = new JLabel("Data Início:");
		
		edtDataInicio = new JFormattedTextField(Mascara.criar("##/##/####"));
		edtDataInicio.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Data Fim:");
		
		edtDataFim = new JFormattedTextField(Mascara.criar("##/##/####"));
		edtDataFim.setColumns(10);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnEditar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblDataInicio)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(edtDataInicio, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addGap(6)
							.addComponent(edtDataFim, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBuscar)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnBuscar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDataInicio)
								.addComponent(edtDataFim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(edtDataInicio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNovo)
						.addComponent(btnEditar)
						.addComponent(btnExcluir))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

}
