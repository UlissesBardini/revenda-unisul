package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Marca;
import br.unisul.revendaunisul.entity.Modelo;
import br.unisul.revendaunisul.enums.TipoDeVeiculo;

@Repository
public interface ModelosRepository extends JpaRepository<Modelo, Integer> {

	@Query("SELECT m "
			+ "FROM Modelo m "
			+ "JOIN FETCH m.marca "
			+ "WHERE m.id = :id")
	public Modelo buscarPor(@Param("id") Integer id);
	
	@Query("SELECT m "
			+ "FROM Modelo m "
			+ "JOIN FETCH m.marca "
			+ "WHERE Upper(m.nome) LIKE Upper(filtro) "
			+ "AND (:marca IS NULL OR :marca = m.marca) "
			+ "AND (:tipo IS NULL OR :tipo = m.tipo) ")
	public List<Modelo> listarPor(
			@Param("filtro") String filtro,
			@Param("marca") Marca marca,
			@Param("tipo") TipoDeVeiculo tipo);
	
	@Query("SELECT m "
			+ "FROM Modelo m "
			+ "WHERE m.marca = :marca")
	public List<Modelo> listarPor(@Param("marca") Marca marca);
	
	public boolean existsByMarca(Marca marca);
	
}
