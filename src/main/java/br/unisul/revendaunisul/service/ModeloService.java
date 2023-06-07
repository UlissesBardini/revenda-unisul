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
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.enums.TipoDeVeiculo;
import br.unisul.revendaunisul.repository.ModelosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ModeloService {

	@Autowired
	private ModelosRepository repository;

	@Autowired
	private EntityManager em;
	
	@Validated(AoInserir.class)
	public Modelo inserir(@NotNull(message = "O modelo não pode ser nulo") @Valid Modelo novoModelo) {
		return this.repository.save(novoModelo);
	}

	@Validated(AoAlterar.class)
	public Modelo alterar(
			@NotNull(message="O modelo não pode ser nulo")
			@Valid Modelo modeloSalvo) {
		this.buscarPor(modeloSalvo.getId());
		this.validar(modeloSalvo);
		this.em.detach(repository.saveAndFlush(modeloSalvo));
		this.em.clear();
		return this.buscarPor(modeloSalvo.getId());
	}

	public Modelo buscarPor(@NotNull(message = "O id do modelo não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O modelo com id '" + id + "' não existe."));
	}

	public void excluirPor(@NotNull(message = "O id do modelo não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
	
	public List<Modelo> listarPor(String filtro, Marca marca, TipoDeVeiculo tipo) {
		return repository.listarPor("%" + filtro +"%", marca, tipo);
	}
	
	public List<Modelo> listarPor(Marca marca) {
		return repository.listarPor(marca);
	}

	public List<Modelo> listarTodos() {
		return repository.findAll();
	}
	
	private void validar(Modelo modelo) {
		Preconditions.checkArgument(modelo.getMarca().getId() != null, "O id da marca não pode ser nulo");
	}
	
}