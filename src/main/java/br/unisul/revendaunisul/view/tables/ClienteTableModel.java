package br.unisul.revendaunisul.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Cliente;

public class ClienteTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 4;
	private List<Cliente> clientes;
	
	public ClienteTableModel(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	@Override
	public int getRowCount() {
		return clientes.size();
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
			return "Nome Completo";
		case 2:
			return "Telefone";
		case 3:
			return "CPF";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente clienteAtual = clientes.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return clienteAtual.getId();
		case 1:
			return clienteAtual.getNomeCompleto();
		case 2:
			return clienteAtual.getTelefone();
		case 3:
			return clienteAtual.getCpf();
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	public Cliente getBy(int rowIndex) {
		return clientes.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		clientes.remove(rowIndex);
	}
	
}
