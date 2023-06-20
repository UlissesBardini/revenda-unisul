package br.unisul.revendaunisul.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Usuario;
import br.unisul.revendaunisul.repository.ColaboradoresRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ColaboradorService {

	@Autowired
	private ColaboradoresRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VendaService vendaService;

	private void validar(Colaborador colaborador) {
		this.validarCpf(colaborador);
		this.validarTelefone(colaborador);
		this.validarIdade(colaborador);
	}
	
	private void validarCpf(Colaborador colaborador) {
		String cpfPattern = "(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}";
		Preconditions.checkArgument(colaborador.getCpf().matches(cpfPattern), 
					"O CPF do colaborador é invalido");
	}
	
	private void validarTelefone(Colaborador colaborador) {
		String telefonePattern = "\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}";
		Preconditions.checkArgument(colaborador.getTelefone().matches(telefonePattern), 
					"O telefone do colaborador é inválido");
	}
	
	private void validarIdade(Colaborador colaborador) {
		int idade = Period.between(colaborador.getDataDeNascimento(), LocalDate.now()).getYears();
		Preconditions.checkArgument(idade >= 18, "O colaborador não pode ter menos de 18 anos");
	}
	
	@Validated(AoInserir.class)
	public Colaborador inserir(
			@Valid Colaborador novoColaborador) {
		this.validar(novoColaborador);
		
		Usuario novoUsuario = usuarioService.inserir(novoColaborador.getUsuario());
		novoColaborador.setUsuario(novoUsuario);
		
		novoColaborador.setDataDeCadastro(LocalDate.now());
		return this.repository.save(novoColaborador);
	}

	@Validated(AoAlterar.class)
	public Colaborador alterar(@NotNull(message = "O colaborador não pode ser nulo") @Valid Colaborador colaboradorSalvo) {
		this.buscarPor(colaboradorSalvo.getId());
		this.validar(colaboradorSalvo);
		
		Usuario usuarioSalvo = usuarioService.alterar(colaboradorSalvo.getUsuario());
		colaboradorSalvo.setUsuario(usuarioSalvo);
		
		this.em.detach(repository.saveAndFlush(colaboradorSalvo));
		this.em.clear();
		return repository.buscarPor(colaboradorSalvo.getId());
	}

	public Colaborador buscarPor(@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		return Optional.of(repository.buscarPor(id))
				.orElseThrow(() -> new IllegalArgumentException("O colaborador com id '" + id + "' não existe."));
	}

	public void excluirPor(@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		this.buscarPor(id);
		Preconditions.checkArgument(!vendaService.isExistePor(this.buscarPor(id)), "O colaborador já está vinculado a uma venda");
		repository.deleteById(id);
	}
	
	public List<Colaborador> listarPor(String nomeCompleto) {
		nomeCompleto = nomeCompleto != null ? nomeCompleto : "";
		String nomeInformado = "%" + nomeCompleto + "%";
		return repository.listarPor(nomeInformado);
	}

	public Colaborador buscarPor(Usuario usuario) {
		return Optional.of(repository.buscarPor(usuario))
				.orElseThrow(() -> new IllegalArgumentException("Não há um colaborador registrado para este usuario"));
	}

	public List<Colaborador> listarTodos() {
		return repository.listarTodos();
	}
}