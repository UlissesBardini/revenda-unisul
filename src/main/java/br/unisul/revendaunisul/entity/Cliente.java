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

	@Size(max = 50, message = "O nome completo n�o deve conter mais de 50 caracteres")
	@NotBlank(message = "O nome completo n�o deve ser um espa�o em branco")
	@Column(name = "nm_completo")
	private String nomeCompleto;
	
	@Size(max = 50, message = "O cpf n�o deve conter mais de 50 caracteres")
	@NotBlank(message = "O cpf n�o deve ser um espa�o em branco")
	@Column(name = "cpf")
	private String cpf;
	
	@NotNull(message = "A data de nascimento não deve ser nula")
	@Column(name = "dt_nascimento")
	private LocalDate dataDeNascimento;
	
	@Size(max = 15, message = "O telefone não deve conter mais de 15 caracteres")
	@NotBlank(message = "O telefone não deve ser um espao em branco")
	@Column(name = "telefone")
	private String telefone;

}
