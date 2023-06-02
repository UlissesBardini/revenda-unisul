package br.unisul.revendaunisul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull(message = "O id da marca não pode ser nulo")
	private Integer id;
	
	@Column(name="nome")
	@Size(max = 50, message = "O nome da marca deve ter até 50 caracteres")
	@NotEmpty(message = "O nome da marca é obrigatório")
	private String nome;
	
}
