package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.repository.VeiculosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class VeiculoService {

	@Autowired
	private VeiculosRepository repository;

	@Validated(AoInserir.class)
	public Veiculo inserir(
			@NotNull(message="O veículo não pode ser nulo")
			@Valid Veiculo novoVeiculo) {
		return this.repository.save(novoVeiculo);
	}
	
	@Validated(AoAlterar.class)
	public Veiculo alterar(
			@NotNull(message="O veículo não pode ser nulo")
			@Valid Veiculo veiculo) {
		return this.repository.save(veiculo);
	}

	public Veiculo buscarPor(
			@NotNull(message = "O id do veículo não pode ser nulo") Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("O veículo com id '" + id + "' não existe."));
	}

	public void excluirPorId(
			@NotNull(message = "O id do veículo não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}