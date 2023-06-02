package br.unisul.revendaunisul.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@NotNull(message = "O usu�rio n�o deve ser nulo")
	private Usuario usuario;

	@Size(max = 50, message = "O nome completo n�o deve conter mais de 50 caracteres")
	@NotBlank(message = "O nome completo n�o deve ser um espa�o em branco")
	@Column(name = "nm_completo")
	private String nomeCompleto;
	
	@Size(max = 14, message = "O cpf n�o deve conter mais de 14 caracteres")
	@NotBlank(message = "O cpf n�o deve ser um espa�o em branco")
	@Column(name = "cpf")
	private String cpf;
	
	@Past(message = "A data de nascimento n�o deve ser posterior a data atual")
	@NotNull(message = "A data de nascimento n�o deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;
	
	@Past(message = "A data de cadastro n�o deve ser posterior a data atual")
	@NotNull(message = "A data de cadastro n�o deve ser nula")
	@Column(name = "dt_cadastro")
	private LocalDate dataDeCadastro;
	
	@Size(max = 14, message = "O telefone n�o deve conter mais de 14 caracteres")
	@NotBlank(message = "O telefone n�o deve ser nulo")
	@Column(name = "telefone")
	private String telefone;
}
