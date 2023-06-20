package br.unisul.revendaunisul.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Venda;

public class VendaTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 4;
	private List<Venda> vendas;
	
	public VendaTableModel(List<Venda> vendas) {
		this.vendas = vendas;
	}
	
	@Override
	public int getRowCount() {
		return vendas.size();
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
			return "Veículo";
		case 2:
			return "Vendedor";
		case 3:
			return "Data";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Venda vendaAtual = vendas.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return vendaAtual.getId();
		case 1:
			return vendaAtual.getVeiculo().toString();
		case 2:
			return vendaAtual.getColaborador().getNomeCompleto();
		case 3:
			return vendaAtual.getDataFormatada();
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public Venda getBy(int rowIndex) {
		return vendas.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		vendas.remove(rowIndex);
	}
	
}
