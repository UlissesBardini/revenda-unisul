package br.unisul.revendaunisul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import br.unisul.revendaunisul.enums.Perfil;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Data
@EqualsAndHashCode
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	@Null(message="O id deve ser nulo para inserção", groups = AoInserir.class)
	@NotNull(message="O id não pode ser nulo para alteração", groups = AoAlterar.class)
	private Integer id;
	
	@Column(name = "perfil")
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O perfil do usuário não pode ser nulo")
	private Perfil perfil;

	@Column(name = "login")
	@Size(max = 50, message = "O login do usuário não deve conter mais de 50 caracteres")
	@NotBlank(message = "O login do usário não deve estar em branco")
	private String login;
	
	@Column(name = "senha")
	@Size(max = 50, message = "A senha do usuário não deve conter mais de 50 caracteres")
	@NotBlank(message = "A senha do usuário não deve estar em branco")
	private String senha;
	
}
