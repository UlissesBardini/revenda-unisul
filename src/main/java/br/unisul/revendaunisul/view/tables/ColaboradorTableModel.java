package br.unisul.revendaunisul.view.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.unisul.revendaunisul.entity.Colaborador;

public class ColaboradorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final int QTDE_COLUNAS = 4;
	private List<Colaborador> colaboradores;

	public ColaboradorTableModel(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	@Override
	public int getRowCount() {
		return colaboradores.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Cadastro";
		case 1:
			return "Nome Completo";
		case 2:
			return "Perfil";
		case 3:
			return "Login";
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Colaborador colaboradorAtual = colaboradores.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return colaboradorAtual.getDataDeCadastroFormatada();
		case 1:
			return colaboradorAtual.getNomeCompleto();
		case 2:
			return colaboradorAtual.getUsuario().getPerfil();
		case 3:
			return colaboradorAtual.getUsuario().getLogin();
		default:
			throw new IllegalArgumentException("Índice inválido");
		}
	}

	public Colaborador getBy(int rowIndex) {
		return colaboradores.get(rowIndex);
	}
	
	public void removeBy(int rowIndex) {
		colaboradores.remove(rowIndex);
	}
	
}
