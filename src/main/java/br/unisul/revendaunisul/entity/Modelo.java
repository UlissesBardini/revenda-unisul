package br.unisul.revendaunisul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.unisul.revendaunisul.enums.Combustivel;
import br.unisul.revendaunisul.enums.TipoDeVeiculo;
import br.unisul.revendaunisul.enums.Transmissao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name="modelos")
@Entity(name="Modelo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modelo {

	@Id
	@Column(name="id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message="O id do modelo não pode ser nulo")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_marca")
	@NotNull(message="A marca do modelo não pode ser nula")
	private Marca marca;
	
	@Column(name="nome")
	@Size(max = 50, message = "O nome do modelo deve ter até 50 caracteres")
	@NotEmpty(message = "O nome do modelo é obrigatório")
	private String nome;
	
	@Column(name="tipo")
	@Enumerated(EnumType.STRING)
	@NotNull(message="O tipo do modelo não pode ser nulo")
	private TipoDeVeiculo tipo;
	
	@Column(name="transmissao")
	@Enumerated(EnumType.STRING)
	@NotNull(message="A transmissão do modelo não pode ser nula")
	private Transmissao transmissao;
	
	@Column(name="combustivel")
	@Enumerated(EnumType.STRING)
	@NotNull(message="O combustível do modelo não pode ser nulo")
	private Combustivel combustivel;
	
}
