package br.unisul.revendaunisul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unisul.revendaunisul.entity.Colaborador;

@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaborador, Integer> {

}
