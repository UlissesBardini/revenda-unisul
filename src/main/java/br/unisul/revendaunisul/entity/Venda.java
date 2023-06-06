package br.unisul.revendaunisul.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import br.unisul.revendaunisul.enums.FormaDePagamento;
import br.unisul.revendaunisul.validation.AoAlterar;
import br.unisul.revendaunisul.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Table(name = "vendas")
@Entity(name = "Venda")
public class Venda {

	// id, id_colaborador, id_cliente, id_veiculo, data, forma_pagamento,
	// qtde_parcelas

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	@Null(message = "O id deve ser nulo para inserção", groups = AoInserir.class)
	@NotNull(message = "O id não pode ser nulo para alteração", groups = AoAlterar.class)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_colaborador")
	@NotNull(message = "O colaborador da venda não pode ser nulo")
	private Colaborador colaborador;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	@NotNull(message = "O cliente da venda não pode ser nulo")
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name = "id_veiculo")
	@NotNull(message = "O veículo da venda não pode ser nulo")
	private Veiculo veiculo;

	@Column(name = "data")
	@NotNull(message = "A data da venda não pode ser nula")
	private LocalDate data;

	@NotNull(message = "A forma de pagamento não pode ser nula")
	@Column(name = "forma_pagamento")
	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;

	@Column(name = "qtde_parcelas")
	@Positive(message = "A quantidade de parcelas deve ser positiva")
	private int quantidadeDeParcelas;

}
