package br.unisul.revendaunisul.entity;

import java.beans.Transient;

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
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.unisul.revendaunisul.enums.StatusDoVeiculo;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "veiculos")
@Entity(name = "Veiculo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Veiculo {

	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Null(message = "O id deve ser nulo para inserção", groups = AoInserir.class)
	@NotNull(message = "O id não pode ser nulo para alteração", groups = AoAlterar.class)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_modelo")
	@NotNull(message = "O modelo do veículo não pode ser nulo")
	private Modelo modelo;

	@Column(name = "ano")
	@Positive(message = "O ano deve ser positivo")
	private int ano;

	@Size(max = 20)
	@Column(name = "cor")
	@NotEmpty(message = "A cor do veículo é obrigatória")
	private String cor;
	
	@Size(max=8)
	@Column(name="placa")
	@NotEmpty(message = "A placa do veículo é obrigatória")
	private String placa;

	@Column(name = "quilometragem")
	@Positive(message = "A quilometragem deve ser positiva")
	private int quilometragem;

	@Size(max = 17)
	@Column(name = "chassi")
	@NotEmpty(message = "o chassi do veículo é obrigatório")
	private String chassi;

	@Column(name = "valor")
	@Positive(message = "O valor deve ser positivo")
	private double valor;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "O status do veículo não pode ser nulo")
	private StatusDoVeiculo status;

	@Transient
	public String toString() {
		return this.modelo.toString() + " " + this.cor + " " + this.placa;
	}

}
