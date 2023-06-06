package br.unisul.revendaunisul.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Cliente")
@Table(name = "clientes")
@EqualsAndHashCode
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	@Null(message = "O id deve ser nulo para inserção", groups = AoInserir.class)
	@NotNull(message = "O id não pode ser nulo para alteração", groups = AoAlterar.class)
	private Integer id;

	@Size(max = 50, message = "O nome completo n�o deve conter mais de 50 caracteres")
	@NotBlank(message = "O nome completo n�o deve ser um espa�o em branco")
	@Column(name = "nm_completo")
	private String nomeCompleto;

	//@MatchesPattern("/(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}/")
	@Size(max = 14, message = "O cpf n�o deve conter mais de 50 caracteres")
	@NotBlank(message = "O cpf n�o deve ser um espa�o em branco")
	@Column(name = "cpf")
	private String cpf;

	@NotNull(message = "A data de nascimento n�o deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;

	//@MatchesPattern("/\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}/")
	@Size(max = 15, message = "O telefone n�o deve conter mais de 15 caracteres")
	@NotBlank(message = "O telefone n�o deve ser um espa�o em branco")
	@Column(name = "telefone")
	private String telefone;

}