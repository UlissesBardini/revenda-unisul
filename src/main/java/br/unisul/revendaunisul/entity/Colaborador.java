package br.unisul.revendaunisul.entity;

import java.time.LocalDate;

import javax.annotation.MatchesPattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Colaborador")
@Table(name = "colaboradores")
@Data
@EqualsAndHashCode
public class Colaborador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Integer id;
	
	@OneToOne
	@NotNull(message = "O usuário não deve ser nulo")
	@JoinColumn(name="usuario")
	private Usuario usuario;

	@Size(max = 50, message = "O nome completo não deve conter mais de 50 caracteres")
	@NotBlank(message = "O nome completo não deve ser um espaço em branco")
	@Column(name = "nm_completo")
	private String nomeCompleto;
	
	@Size(min=14, max = 14, message = "O cpf deve conter exatamente 14 caracteres")
	@NotBlank(message = "O cpf não deve ser um espaço em branco")
	@MatchesPattern("/(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}/")
	@Column(name = "cpf")
	private String cpf;
	
	@Past(message = "A data de nascimento não deve ser posterior a data atual")
	@NotNull(message = "A data de nascimento não deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;
	
	@Past(message = "A data de cadastro não deve ser posterior a data atual")
	@NotNull(message = "A data de cadastro não deve ser nula")
	@Column(name = "dt_cadastro")
	private LocalDate dataDeCadastro;
	
	@Size(min = 14, max = 14, message = "O telefone deve conter exatamente 14 caracteres")
	@NotBlank(message = "O telefone não deve ser nulo")
	@Column(name = "telefone")
	@MatchesPattern("/\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}/")
	private String telefone;
}
