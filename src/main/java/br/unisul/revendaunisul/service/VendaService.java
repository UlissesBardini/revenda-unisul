package br.unisul.revendaunisul.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.entity.Venda;
import br.unisul.revendaunisul.enums.FormaDePagamento;
import br.unisul.revendaunisul.enums.StatusDoVeiculo;
import br.unisul.revendaunisul.repository.VendasRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class VendaService {

	@Autowired
	private VendasRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	@Lazy
	private VeiculoService veiculoService;

	@Validated(AoInserir.class)
	public Venda inserir(@NotNull(message = "A venda não pode ser nula") @Valid Venda novaVenda) {
		this.validar(novaVenda);
		novaVenda.setData(LocalDate.now());
		Veiculo veiculoVendido = novaVenda.getVeiculo();
		veiculoVendido.setStatus(StatusDoVeiculo.V);
		veiculoService.alterar(veiculoVendido);
		
		return this.repository.save(novaVenda);
	}

	@Validated(AoAlterar.class)
	public Venda alterar(@NotNull(message = "A venda não pode ser nula") @Valid Venda vendaSalva) {
		this.buscarPor(vendaSalva.getId());
		this.validar(vendaSalva);
		this.em.detach(repository.saveAndFlush(vendaSalva));
		this.em.clear();
		return repository.buscarPor(vendaSalva.getId());
	}

	public Venda buscarPor(@NotNull(message = "O id da venda não pode ser nulo") Integer id) {
		return Optional.of(repository.buscarPor(id))
				.orElseThrow(() -> new IllegalArgumentException("A venda com id '" + id + "' não existe."));
	}

	public void excluirPor(@NotNull(message = "O id da venda não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}

	public List<Venda> listarPor(
			@NotNull(message = "A data de início não pode ser nula") LocalDate dataInicio,
			@NotNull(message = "A data de fim não pode ser nula") LocalDate dataFim) {
		return repository.listarPor(dataInicio, dataFim);
	}
	
	public void validar(Venda venda) {
		if (venda.getFormaDePagamento() == FormaDePagamento.PARCELADO) {
			Preconditions.checkArgument(
					venda.getQuantidadeDeParcelas() > 0, "A venda parcelada deve ter ao menos uma parcela.");
		} else {
			venda.setQuantidadeDeParcelas(0);
		}
	}
	
	public boolean isExistePor(Veiculo veiculo) {
		return repository.existsByVeiculo(veiculo);
	}
	
	public boolean isExistePor(Colaborador colaborador) {
		return repository.existsByColaborador(colaborador);
	}
	
	public boolean isExistePor(Cliente cliente) {
		return repository.existsByCliente(cliente);
	}
	
}