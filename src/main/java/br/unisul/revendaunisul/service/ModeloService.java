package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.repository.ModelosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ModeloService {

	@Autowired
	private ModelosRepository repository;

	@Validated(AoInserir.class)
	public Modelo inserir(@NotNull(message = "O modelo não pode ser nulo") @Valid Modelo novoModelo) {
		return this.repository.save(novoModelo);
	}

	@Validated(AoAlterar.class)
	public Modelo alterar(@NotNull(message = "O modelo não pode ser nulo") @Valid Modelo modelo) {
		return this.repository.save(modelo);
	}

	public Modelo buscarPor(@NotNull(message = "O id do modelo não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O modelo com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id do modelo não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}