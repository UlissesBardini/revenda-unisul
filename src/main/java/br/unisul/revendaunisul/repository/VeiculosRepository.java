package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.entity.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Integer> {

	public Veiculo getByPlaca(String placa);
	
	public Veiculo getByChassi(String chassi);

	@Query("SELECT v "
			+ "FROM Veiculo v "
			+ "JOIN FETCH v.modelo "
			+ "WHERE v.modelo = :modelo")
	public List<Veiculo> listarPor(@Param("modelo") Modelo modelo);
	
}
