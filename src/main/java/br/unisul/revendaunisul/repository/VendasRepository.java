package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Venda;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Integer> {

}
