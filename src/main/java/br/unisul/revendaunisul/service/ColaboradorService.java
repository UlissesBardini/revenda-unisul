package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.repository.ColaboradoresRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ColaboradorService {

	@Autowired
	private ColaboradoresRepository repository;

	@Validated(AoInserir.class)
	public Colaborador inserir(
			@NotNull(message="O colaborador não pode ser nulo")
			@Valid Colaborador novoColaborador) {
		return this.repository.save(novoColaborador);
	}
	
	@Validated(AoAlterar.class)
	public Colaborador alterar(
			@NotNull(message="O colaborador não pode ser nulo")
			@Valid Colaborador colaborador) {
		return this.repository.save(colaborador);
	}

	public Colaborador buscarPor(
			@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("O colaborador com id '" + id + "' não existe."));
	}

	public void excluirPorId(
			@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}