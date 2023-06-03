package br.unisul.revendaunisul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name="marcas")
@Entity(name="Marca")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Marca {

	@Id
	@Column(name="id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Null(message="O id deve ser nulo para inserção", groups = AoInserir.class)
	@NotNull(message="O id não pode ser nulo para alteração", groups = AoAlterar.class)
	private Integer id;
	
	@Column(name="nome")
	@Size(max = 50, message = "O nome da marca deve ter até 50 caracteres")
	@NotEmpty(message = "O nome da marca é obrigatório")
	private String nome;
	
}
