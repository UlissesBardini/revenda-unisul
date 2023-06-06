package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.repository.MarcasRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class MarcaService {

	@Autowired
	private MarcasRepository repository;

	@Validated(AoInserir.class)
	public Marca inserir(@NotNull(message = "A marca não pode ser nula") @Valid Marca novaMarca) {
		return this.repository.save(novaMarca);
	}

	@Validated(AoAlterar.class)
	public Marca alterar(@NotNull(message = "A marca não pode ser nula") @Valid Marca marca) {
		return this.repository.save(marca);
	}

	public Marca buscarPor(@NotNull(message = "O id da marca não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("A marca com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id da marca não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}
