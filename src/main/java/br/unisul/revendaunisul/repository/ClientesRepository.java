package br.unisul.revendaunisul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Cliente;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

	@Query("SELECT c "
			+ "FROM Cliente c "
			+ "WHERE Upper(c.nomeCompleto) LIKE Upper(:nomeCompleto)")
	public List<Cliente> listarPor(@Param("nomeCompleto") String nomeCompleto);

	@Query("SELECT c "
			+ "FROM Cliente c ")
	public List<Cliente> listarTodos();
	
	public Cliente findByCpf(String cpf);
	
	public Cliente findByTelefone(String cpf);
	
}
