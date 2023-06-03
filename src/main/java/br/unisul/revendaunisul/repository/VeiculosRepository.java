package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Veiculo;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Integer> {

}
