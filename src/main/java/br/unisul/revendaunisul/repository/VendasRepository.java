package br.unisul.revendaunisul.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Cliente;
import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Veiculo;
import br.unisul.revendaunisul.entity.Venda;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Integer> {

	@Query("SELECT v "
			+ "FROM Venda v "
			+ "WHERE v.data BETWEEN :dtInicio AND :dtFim")
	public List<Venda> listarPor(
			@Param("dtInicio") LocalDate dataInicio,
			@Param("dtFim") LocalDate dataFim);

	@Query("SELECT v "
			+ "FROM Venda v "
			+ "JOIN FETCH v.colaborador "
			+ "JOIN FETCH v.cliente "
			+ "JOIN FETCH v.veiculo "
			+ "WHERE v.id = :id")
	public Venda buscarPor(@Param("id") Integer id);
	
	public boolean existsByColaborador(Colaborador colaborador);
	
	public boolean existsByCliente(Cliente cliente);
	
	public boolean existsByVeiculo(Veiculo veiculo);
	
}
