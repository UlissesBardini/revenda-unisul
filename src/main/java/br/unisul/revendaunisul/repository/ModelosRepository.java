package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Modelo;

@Repository
public interface ModelosRepository extends JpaRepository<Modelo, Integer> {

}
