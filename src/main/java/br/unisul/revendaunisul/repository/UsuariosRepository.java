package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

	@Query(value = "SELECT u " 
					+ "FROM Usuario u " 
					+ "WHERE u.login = :login " 
					+ "AND u.senha = :senha ")
	public Usuario buscarLogin(@Param("login") String login, @Param("senha") String senha);

}
