package br.unisul.revendaunisul.service;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.repository.UsuariosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class UsuarioService {

	@Autowired
	private UsuariosRepository repository;

	@Autowired
	private EntityManager em;

	@Validated(AoInserir.class)
	public Usuario inserir(@Valid Usuario novoUsuario) {
		return this.repository.save(novoUsuario);
	}

	@Validated(AoAlterar.class)
	public Usuario alterar(@Valid Usuario usuarioSalvo) {
		this.em.detach(repository.saveAndFlush(usuarioSalvo));
		this.em.clear();
		return usuarioSalvo;
	}

	public Usuario buscarPor(@NotNull(message = "O id do usuário não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O usuário com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id do usuário não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}

	public Usuario buscarLogin(String login, String senha) {
		Usuario usuarioEncontrado = repository.buscarLogin(login, senha);
		Preconditions.checkArgument(usuarioEncontrado != null, "O login ou senha estão incorretos.");
		return usuarioEncontrado;
	}

}
