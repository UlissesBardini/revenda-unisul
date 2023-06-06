package br.unisul.revendaunisul.service;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.repository.VeiculosRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class VeiculoService {

	@Autowired
	private VeiculosRepository repository;
	
	@Autowired
	private EntityManager em;

	@Validated(AoInserir.class)
	public Veiculo inserir(
			@NotNull(message="O veículo não pode ser nulo")
			@Valid Veiculo novoVeiculo) {
		this.validar(novoVeiculo);
		return this.repository.save(novoVeiculo);
	}

	@Validated(AoAlterar.class)
	public Veiculo alterar(
			@NotNull(message="O veículo não pode ser nulo")
			@Valid Veiculo veiculoSalvo) {
		this.buscarPor(veiculoSalvo.getId());
		this.validar(veiculoSalvo);
		this.em.detach(repository.saveAndFlush(veiculoSalvo));
		this.em.clear();
		return this.buscarPor(veiculoSalvo.getId());
	}

	public Veiculo buscarPor(@NotNull(message = "O id do veículo não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O veículo com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id do veículo não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
	
	private void validar(Veiculo veiculo) {
		this.validarModelo(veiculo.getModelo());
		this.validarAno(veiculo.getAno());
		this.validarPlaca(veiculo);
		this.validarChassi(veiculo);
	}
	
	private void validarModelo(Modelo modelo) {
		Preconditions.checkArgument(modelo.getId() != null, "O id do modelo não pode ser nulo");
	}
	
	private void validarAno(int ano) {
		Preconditions.checkArgument(ano <= LocalDate.now().getYear(),
				"O ano do veículo não pode ser superior ao ano atual");
	}
	
	private void validarPlaca(Veiculo veiculo) {
		String placaPattern = "/[A-Z]{3}-[0-9][0-9A-Z][0-9]{2}/";
		Preconditions.checkArgument(veiculo.getPlaca().matches(placaPattern),
				"A placa deve possuir o formato 'ABC-1234' ou 'ABC-1D34'");
		
		Veiculo veiculoEncontrado = repository.getByPlaca(veiculo.getPlaca());
		if (veiculoEncontrado != null) {
			Preconditions.checkArgument(veiculoEncontrado.getId().equals(veiculo.getId()),
					"A placa '" + veiculo.getPlaca() + "' já está associada a outro veículo");
		}
	}
	
	private void validarChassi(Veiculo veiculo) {
		Veiculo veiculoEncontrado = repository.getByChassi(veiculo.getChassi());
		if (veiculoEncontrado != null) {
			Preconditions.checkArgument(veiculoEncontrado.getId().equals(veiculo.getId()),
					"O chassi '" + veiculo.getChassi() + "' já está associado a outro veículo");
		}
	}
	
}