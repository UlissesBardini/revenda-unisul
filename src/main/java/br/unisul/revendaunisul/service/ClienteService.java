package br.unisul.revendaunisul.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.repository.ClientesRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ClienteService {

	@Autowired
	private ClientesRepository repository;

	@Autowired
	private EntityManager em;
	
	@Autowired
	private VendaService vendaService;
	
	private void validar(Cliente cliente) {
		this.validarCpf(cliente);
		this.validarTelefone(cliente);
		this.validarIdade(cliente);
	}
	
	private void validarCpf(Cliente cliente) {
		String cpfPattern = "(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}";
		Preconditions.checkArgument(cliente.getCpf().matches(cpfPattern), 
					"O CPF do cliente é invalido");
		Cliente clienteCpf = repository.findByCpf(cliente.getCpf());
		if (clienteCpf != null) {			
			Preconditions.checkArgument(clienteCpf.getId().equals(cliente.getId()),
					"O CPF '" + cliente.getCpf() + "' já está associado a outro cliente");
		}
	}
	
	private void validarTelefone(Cliente cliente) {
		String telefonePattern = "\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}";
		Preconditions.checkArgument(cliente.getTelefone().matches(telefonePattern), 
					"O telefone do cliente é inválido");
		Cliente clienteTelefone = repository.findByTelefone(cliente.getTelefone());
		if (clienteTelefone != null) {			
			Preconditions.checkArgument(clienteTelefone.getId().equals(cliente.getId()),
					"O Telefone '" + cliente.getTelefone() + "' já está associado a outro cliente");
		}
	}
	
	private void validarIdade(Cliente cliente) {
		int idade = Period.between(cliente.getDataDeNascimento(), LocalDate.now()).getYears();
		Preconditions.checkArgument(idade >= 18, "O colaborador não pode ter menos de 18 anos");
	}
	
	@Validated(AoInserir.class)
	public Cliente inserir(
			@NotNull(message = "O cliente não pode ser nulo")
			@Valid Cliente novoCliente) {
		this.validar(novoCliente);
		return this.repository.save(novoCliente);
	}

	@Validated(AoAlterar.class)
	public Cliente alterar(@NotNull(message = "O cliente não pode ser nulo") @Valid Cliente cliente) {
		this.buscarPor(cliente.getId());
		this.validar(cliente);
		this.em.detach(repository.saveAndFlush(cliente));
		this.em.clear();
		return this.buscarPor(cliente.getId());
	}

	public Cliente buscarPor(@NotNull(message = "O id do cliente não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O cliente com id '" + id + "' não existe."));
	}

	public void excluirPor(@NotNull(message = "O id do cliente não pode ser nulo") Integer id) {
		this.buscarPor(id);
		Preconditions.checkArgument(!vendaService.isExistePor(this.buscarPor(id)), "O cliente já está vinculado a uma venda");
		repository.deleteById(id);
	}

	public List<Cliente> listarPor(String nomeCompleto) {
		nomeCompleto = nomeCompleto != null ? nomeCompleto : "";
		String nomeParaBusca = "%" + nomeCompleto + "%";
		return repository.listarPor(nomeParaBusca);
	}

	public List<Cliente> listarTodos() {
		return repository.listarTodos();
	}

}