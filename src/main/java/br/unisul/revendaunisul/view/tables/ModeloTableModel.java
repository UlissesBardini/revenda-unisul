package br.unisul.revendaunisul.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Modelo;

public class ModeloTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 2;
	private List<Modelo> modelos;

	public ModeloTableModel(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	@Override
	public int getRowCount() {
		return modelos.size();
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
		case 2:
			return "Marca";
		case 3:
			return "Tipo";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Modelo modeloAtual = modelos.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return modeloAtual.getId();
		case 1:
			return modeloAtual.getNome();
		case 2:
			return modeloAtual.getMarca().getNome();
		case 3:
			return modeloAtual.getTipo();
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public Modelo getBy(int rowIndex) {
		return modelos.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		modelos.remove(rowIndex);
	}

}
