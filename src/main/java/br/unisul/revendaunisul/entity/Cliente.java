package br.unisul.revendaunisul.entity;

import java.time.LocalDate;

import javax.annotation.MatchesPattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private Integer id;

	@Size(max = 50, message = "O nome completo nï¿½o deve conter mais de 50 caracteres")
	@NotBlank(message = "O nome completo nï¿½o deve ser um espaï¿½o em branco")
	@Column(name = "nm_completo")
	private String nomeCompleto;
<<<<<<< HEAD

	@MatchesPattern("/(?:[0-9]{3}\\.){2}[0-9]{3}-[0-9]{2}/")
	@Size(max = 14, message = "O cpf não deve conter mais de 50 caracteres")
	@NotBlank(message = "O cpf não deve ser um espaço em branco")
	@Column(name = "cpf")
	private String cpf;

	@NotNull(message = "A data de nascimento não deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;

	@MatchesPattern("/\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}/")
	@Size(max = 15, message = "O telefone não deve conter mais de 15 caracteres")
	@NotBlank(message = "O telefone não deve ser um espaço em branco")
=======
	
	@Size(max = 50, message = "O cpf nï¿½o deve conter mais de 50 caracteres")
	@NotBlank(message = "O cpf nï¿½o deve ser um espaï¿½o em branco")
	@Column(name = "cpf")
	private String cpf;
	
	@NotNull(message = "A data de nascimento nï¿½o deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;
	
	@Size(max = 15, message = "O telefone nï¿½o deve conter mais de 15 caracteres")
	@NotBlank(message = "O telefone nï¿½o deve ser um espaï¿½o em branco")
>>>>>>> 6c038a32488afcb73f4016985155f1ad6d9aacb9
	@Column(name = "telefone")
	private String telefone;

}
