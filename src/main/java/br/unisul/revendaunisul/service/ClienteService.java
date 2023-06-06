package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.repository.ClientesRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ClienteService {

	@Autowired
	private ClientesRepository repository;

	@Validated(AoInserir.class)
	public Cliente inserir(@NotNull(message = "O cliente não pode ser nulo") @Valid Cliente novoCliente) {
		return this.repository.save(novoCliente);
	}

	@Validated(AoAlterar.class)
	public Cliente alterar(@NotNull(message = "O cliente não pode ser nulo") @Valid Cliente cliente) {
		return this.repository.save(cliente);
	}

	public Cliente buscarPor(@NotNull(message = "O id do cliente não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O cliente com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id do cliente não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
}