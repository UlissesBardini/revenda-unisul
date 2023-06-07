package br.unisul.revendaunisul.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Marca;

public class MarcaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 2;
	private List<Marca> marcas;

	public MarcaTableModel(List<Marca> marcas) {
		this.marcas = marcas;
	}

	@Override
	public int getRowCount() {
		return marcas.size();
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
			return "Nome";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Marca marcaAtual = marcas.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return marcaAtual.getId();
		case 1:
			return marcaAtual.getNome();
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public Marca getBy(int rowIndex) {
		return marcas.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		marcas.remove(rowIndex);
	}

}
