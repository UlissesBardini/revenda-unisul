package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Marca;

@Repository
public interface MarcasRepository extends JpaRepository<Marca, Integer> {

}
