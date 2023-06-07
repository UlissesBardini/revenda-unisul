package br.unisul.revendaunisul.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.repository.MarcasRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class MarcaService {

	@Autowired
	private MarcasRepository repository;
	
	@Autowired
	private EntityManager em;

	@Validated(AoInserir.class)
	public Marca inserir(
			@NotNull(message="A marca não pode ser nula")
			@Valid Marca novaMarca) {
		this.validar(novaMarca);
		return this.repository.save(novaMarca);
	}

	@Validated(AoAlterar.class)
	public Marca alterar(
			@NotNull(message="A marca não pode ser nula")
			@Valid Marca marcaSalva) {
		this.buscarPor(marcaSalva.getId());
		this.validar(marcaSalva);
		this.em.detach(repository.saveAndFlush(marcaSalva));
		this.em.clear();
		return this.buscarPor(marcaSalva.getId());
	}

	public Marca buscarPor(@NotNull(message = "O id da marca não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("A marca com id '" + id + "' não existe."));
	}

	public void excluirPor(@NotNull(message = "O id da marca não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
	
	private void validar(Marca marca) {
		Marca marcaEncontrada = this.repository.getByNome(marca.getNome());
		if (marcaEncontrada != null) {
			Preconditions.checkArgument(marcaEncontrada.getId().equals(marca.getId()),
					"A marca '" + marca.getNome() + "' já existe");
		}
	}

	public List<Marca> listarPor(String filtro) {
		return repository.listarPor("%" + filtro +"%");
	}

	public List<Marca> listarTodos() {
		return repository.findAll();
	}
}
