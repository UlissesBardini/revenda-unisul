package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Marca;

@Repository
public interface MarcasRepository extends JpaRepository<Marca, Integer> {

	public Marca getByNome(String nome);

	@Query("SELECT m "
			+ "FROM Marca m "
			+ "WHERE Upper(m.nome) LIKE Upper(:filtro) "
			+ "ORDER BY m.id")
	public List<Marca> listarPor(@Param("filtro") String filtro);
	
}
