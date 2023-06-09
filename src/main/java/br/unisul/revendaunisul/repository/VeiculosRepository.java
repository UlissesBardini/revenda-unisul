package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.entity.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Integer> {

	@Query("SELECT v "
			+ "FROM Veiculo v "
			+ "JOIN FETCH v.modelo m "
			+ "JOIN FETCH m.marca "
			+ "WHERE v.id = :id")
	public Veiculo buscarPor(@Param("id") Integer id);
	
	public Veiculo findByPlaca(String placa);
	
	public Veiculo findByChassi(String chassi);

	@Query("SELECT v "
			+ "FROM Veiculo v "
			+ "JOIN FETCH v.modelo m "
			+ "JOIN FETCH m.marca "
			+ "WHERE v.status = 'N' "
			+ "AND m = :modelo")
	public List<Veiculo> listarPor(@Param("modelo") Modelo modelo);
	
	public boolean existsByModelo(Modelo modelo);

	@Query("SELECT v "
			+ "FROM Veiculo v "
			+ "JOIN FETCH v.modelo m "
			+ "JOIN FETCH m.marca "
			+ "WHERE v.status = 'N'")
	public List<Veiculo> listarTodos();

	@Query("SELECT v "
			+ "FROM Veiculo v "
			+ "JOIN FETCH v.modelo m "
			+ "JOIN FETCH m.marca mm "
			+ "WHERE v.status = 'N' "
			+ "AND mm = :marca")
	public List<Veiculo> listarPor(@Param("marca") Marca marca);
	
}
