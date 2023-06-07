package br.unisul.revendaunisul.view.tables;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Veiculo;

public class VeiculoTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 2;
	private List<Veiculo> veiculos;
	
	public VeiculoTableModel(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}
	
	@Override
	public int getRowCount() {
		return veiculos.size();
	}
	
	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Id";
		case 1:
			return "Modelo";
		case 2:
			return "Ano";
		case 3:
			return "Valor";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Veiculo veiculoAtual = veiculos.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return veiculoAtual.getId();
		case 1:
			return veiculoAtual.getModelo().toString();
		case 2:
			return veiculoAtual.getAno();
		case 3:
			DecimalFormat df = new DecimalFormat("###.###");
			return "R$ " + df.format(veiculoAtual.getValor());
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public Veiculo getBy(int rowIndex) {
		return veiculos.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		veiculos.remove(rowIndex);
	}
	
}
