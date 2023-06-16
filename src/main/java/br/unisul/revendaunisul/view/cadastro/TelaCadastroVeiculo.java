package br.unisul.revendaunisul.view.cadastro;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.enums.StatusDoVeiculo;
import br.unisul.revendaunisul.service.MarcaService;
import br.unisul.revendaunisul.service.ModeloService;
import br.unisul.revendaunisul.service.VeiculoService;
import br.unisul.revendaunisul.utils.Mascara;

@Component
public class TelaCadastroVeiculo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Veiculo veiculo;
	private JTextField edtAno;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Modelo> cbModelo;
	private List<Marca> marcas;
	private List<Modelo> modelos;
	private JTextField edtChassi;
	private JTextField edtQuilometragem;
	private JTextField edtPlaca;
	private JTextField edtCor;
	private JTextField edtValor;

	@Autowired
	private VeiculoService service;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private ModeloService modeloService;

	public TelaCadastroVeiculo() {
			setTitle("Cadastrar Veículo");
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setBounds(100, 100, 539, 205);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setLocationRelativeTo(null);
			
			setContentPane(contentPane);

			JLabel lblModelo = new JLabel("Modelo:");

			cbModelo = new JComboBox<Modelo>();

			JLabel lblMarca = new JLabel("Marca:");

			cbMarca = new JComboBox<Marca>();
			cbMarca.addItemListener(e -> {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					modelos = modeloService
							.listarPor((Marca) cbMarca.getSelectedItem());
					cbModelo.removeAllItems();
					for (Modelo m : modelos) {
						cbModelo.addItem(m);
					}
				}
			});
			
			JLabel lblAno = new JLabel("Ano:");

			edtAno = new JFormattedTextField(Mascara.criar("####", false));
			edtAno.setColumns(4);
			
			edtChassi = new JFormattedTextField(Mascara.criar("AAAAAAAAAAAAA####"));
			edtChassi.setColumns(17);

			JLabel lblChassi = new JLabel("Chassi:");

			JLabel lblQuilometragem = new JLabel("Quilometragem:");

			NumberFormatter nf = new NumberFormatter();
			nf.setAllowsInvalid(false);
			nf.setMinimum(0);
			
			edtQuilometragem = new JFormattedTextField(nf);
			edtQuilometragem.setColumns(4);

			JLabel lblPlaca = new JLabel("Placa:");
			
			edtPlaca = new JFormattedTextField(Mascara.criar("UUU-#A##"));
			edtPlaca.setColumns(8);
			
			JLabel lblCor = new JLabel("Cor:");

			edtCor = new JTextField();
			edtCor.setColumns(8);

			JLabel lblValor = new JLabel("Valor:");

			edtValor = new JTextField();
			edtValor.setColumns(8);

			JButton btnSalvar = new JButton("Salvar");
			btnSalvar.addActionListener(event -> {
				try {
					this.veiculo.setModelo((Modelo) this.cbModelo.getSelectedItem());
					this.veiculo.setAno(Integer.parseInt(this.edtAno.getText()));
					this.veiculo.setChassi(this.edtChassi.getText());
					this.veiculo.setCor(this.edtCor.getText());
					this.veiculo.setPlaca(this.edtPlaca.getText());
					this.veiculo
							.setQuilometragem(Integer.parseInt(this.edtQuilometragem.getText()));
					this.veiculo.setValor(Double.parseDouble(edtValor.getText()));
					this.veiculo.setStatus(StatusDoVeiculo.N);

					if (this.veiculo.getId() != null) {
						this.veiculo = service.alterar(veiculo);
						JOptionPane.showMessageDialog(contentPane, "Veículo alterado com sucesso!");
					} else {
						this.veiculo = service.inserir(veiculo);
						JOptionPane.showMessageDialog(contentPane, "Veículo salvo com sucesso!");
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
				}
			});

			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(17)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblPlaca)
									.addComponent(lblAno)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblMarca, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(edtPlaca, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblCor, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(edtCor, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblValor))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(edtAno, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblChassi)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(edtChassi, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblQuilometragem, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(edtQuilometragem, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
									.addComponent(btnSalvar, Alignment.TRAILING)
									.addComponent(edtValor, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblModelo, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(cbModelo, 0, 248, Short.MAX_VALUE)))
						.addContainerGap())
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(18)
										.addComponent(lblMarca))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(14)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(cbMarca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblModelo))))
								.addPreferredGap(ComponentPlacement.UNRELATED))
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cbModelo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(14)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(edtAno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblChassi)
							.addComponent(edtChassi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(edtQuilometragem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblQuilometragem)
							.addComponent(lblAno))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPlaca)
							.addComponent(edtPlaca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(edtCor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(edtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblCor)
							.addComponent(lblValor))
						.addGap(18)
						.addComponent(btnSalvar)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			contentPane.setLayout(gl_contentPane);
	}

	private void limparCampos() {
		this.carregarOpcoes();
		edtAno.setText("");
		cbMarca.setSelectedItem(0);
		cbModelo.setSelectedItem(0);
		edtChassi.setText("");
		edtQuilometragem.setText("");
		edtPlaca.setText("");
		edtCor.setText("");
		edtValor.setText("");
	}

	private void preencherCampos(Veiculo veiculo) {
		this.carregarOpcoes();
		edtAno.setText(String.valueOf(veiculo.getAno()));
		cbMarca.setSelectedItem(veiculo.getModelo().getMarca());
		cbModelo.setSelectedItem(veiculo.getModelo());
		edtChassi.setText(veiculo.getChassi());
		edtQuilometragem.setText(String.valueOf(veiculo.getQuilometragem()));
		edtPlaca.setText(veiculo.getPlaca());
		edtCor.setText(veiculo.getCor());
		edtValor.setText(String.valueOf(veiculo.getValor()));
	}

	public void colocarEmInsercao() {
		this.veiculo = new Veiculo();
		this.limparCampos();
		setVisible(true);
	}

	public void colocarEmEdicao(Veiculo veiculo) {
		this.veiculo = veiculo;
		this.preencherCampos(veiculo);
		setVisible(true);
	}
	
	private void carregarOpcoes() {
		marcas = marcaService.listarTodos();
		cbMarca.removeAllItems();
		for (Marca m : marcas) {
			cbMarca.addItem(m);
		}
	}

}
