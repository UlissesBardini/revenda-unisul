package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Venda;
import br.unisul.revendaunisul.repository.VendasRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class VendaService {

	@Autowired
	private VendasRepository repository;

	@Validated(AoInserir.class)
	public Venda inserir(
			@NotNull(message="A venda não pode ser nula")
			@Valid Venda novaVenda) {
		return this.repository.save(novaVenda);
	}
	
	@Validated(AoAlterar.class)
	public Venda alterar(
			@NotNull(message="A venda não pode ser nula")
			@Valid Venda venda) {
		return this.repository.save(venda);
	}

	public Venda buscarPor(
			@NotNull(message = "O id da venda não pode ser nulo") Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("A venda com id '" + id + "' não existe."));
	}

	public void excluirPorId(
			@NotNull(message = "O id da venda não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}