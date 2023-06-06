package br.unisul.revendaunisul.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.repository.ColaboradoresRepository;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;

@Validated
@Service
public class ColaboradorService {

	@Autowired
	private ColaboradoresRepository repository;

	public void validar(Colaborador colaborador) {
		this.validarCpf(colaborador);
		this.validarTelefone(colaborador);
	}
	
	public void validarCpf(Colaborador colaborador) {
		String cpfPattern = "/(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}/";
		Preconditions.checkArgument(colaborador.getCpf().matches(cpfPattern), 
					"O CPF do colaborador é invalido");
	}
	
	public void validarTelefone(Colaborador colaborador) {
		String telefonePattern = "/\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}/";
		Preconditions.checkArgument(colaborador.getTelefone().matches(telefonePattern), 
					"O telefone do colaborador é inválido");
	}
	
	@Validated(AoInserir.class)
	public Colaborador inserir(
			@NotNull(message = "O colaborador não pode ser nulo") @Valid Colaborador novoColaborador) {
		
		return this.repository.save(novoColaborador);
	}

	@Validated(AoAlterar.class)
	public Colaborador alterar(@NotNull(message = "O colaborador não pode ser nulo") @Valid Colaborador colaborador) {
		return this.repository.save(colaborador);
	}

	public Colaborador buscarPor(@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("O colaborador com id '" + id + "' não existe."));
	}

	public void excluirPorId(@NotNull(message = "O id do colaborador não pode ser nulo") Integer id) {
		repository.deleteById(id);
	}
	
	public List<Colaborador> listarPor(String nomeCompleto) {
		nomeCompleto = nomeCompleto != null ? nomeCompleto : "";
		String nomeInformado = "%" + nomeCompleto + "%";
		return repository.listarPor(nomeInformado);
	}
}