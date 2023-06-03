package br.unisul.revendaunisul.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.repository.UsuariosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class UsuarioService {

	@Autowired
	private UsuariosRepository repository;
	
	@Validated(AoInserir.class)
	public Usuario inserir(
			@NotNull(message="O usuário não pode ser nulo")
			@Valid Usuario novoUsuario) {
		return this.repository.save(novoUsuario);
	}
	
	@Validated(AoAlterar.class)
	public Usuario alterar(
			@NotNull(message="O usuário não pode ser nulo")
			@Valid Usuario usuario) {
		return this.repository.save(usuario);
	}
	
	public Usuario buscarPor(
			@NotNull(message = "O id do usuário não pode ser nulo") Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("O usuário com id '" + id + "' não existe."));
	}
	
	public void excluirPorId(
			@NotNull(message = "O id do usuário não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
	
}
