package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Colaborador;
import br.unisul.revendaunisul.entity.Usuario;

@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaborador, Integer> {
	
	@Query(value = "SELECT c "
					+ "FROM Colaborador c "
					+ "JOIN FETCH c.usuario "
					+ "WHERE Upper(c.nomeCompleto) LIKE Upper(:nomeCompleto) ")
	public List<Colaborador> listarPor(@Param("nomeCompleto") String nomeCompleto);
	
	@Query(value = "SELECT c "
				 + "FROM Colaborador c "
				 + "JOIN FETCH c.usuario "
				 + "WHERE c.id = :id ")
	public Colaborador buscarPor(@Param("id") Integer id);
	
	@Query("SELECT c "
			+ "FROM Colaborador c "
			+ "JOIN FETCH c.usuario "
			+ "WHERE c.usuario = :usuario")
	public Colaborador buscarPor(@Param("usuario") Usuario usuario);
	
}
